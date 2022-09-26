package com.xaicif.sso.rest;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.sso.SaSsoHandle;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.util.SaResult;
import com.xaicif.sso.common.OkHttpClient;
import com.xaicif.sso.common.RestResp;
import com.xaicif.sso.common.SignUtil;
import com.xaicif.sso.entity.ClientInfo;
import com.xaicif.sso.entity.User;
import com.xaicif.sso.repo.ClientInfoRepo;
import com.xaicif.sso.request.*;
import com.xaicif.sso.response.CheckTicketResp;
import com.xaicif.sso.response.UserInfoResp;
import com.xaicif.sso.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

@RestController
public class SsoServerController {

    @Resource
    private UserService userService;
    @Resource
    private ClientInfoRepo clientInfoRepo;
    @Value("${sign.timeout}")
    private Long signTimeout;

    @GetMapping("/sso/auth")
    public Object auth() {
        return SaSsoHandle.serverRequest();
    }

    @PostMapping("/sso/doLogin")
    public RestResp doLogin(@Valid @RequestBody DoLoginDto doLoginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RestResp.fail(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return userService.login(doLoginDto);
    }

    @PostMapping("/sso/checkTicket")
    public RestResp checkTicket(@RequestBody CheckTicketDto checkTicketDto) throws Exception {
        return checkParamSignThenExecute(baseSignRequest -> {
            Object userId = SaSsoUtil.checkTicket(checkTicketDto.getTicket());
            SaSsoUtil.registerSloCallbackUrl(userId, checkTicketDto.getSsoLogoutCall() + "::" + checkTicketDto.getClientCode());
            if (userId == null) {
                return RestResp.fail("无效ticket：" + checkTicketDto.getTicket());
            }
            User user = userService.getUser((String) userId);
            if (user == null) {
                return RestResp.fail("无效ticket：" + checkTicketDto.getTicket());
            }
            return RestResp.success(new CheckTicketResp(user.getUserId(), user.getLoginName(), user.getUscc(), user.getMobile(), user.getCfcaKeyId()));
        }, checkTicketDto);
    }

    @PostMapping("/sso/logout")
    public RestResp logout(@RequestBody LogoutDto logoutDto) throws Exception {
        return checkParamSignThenExecute(baseSignRequest -> {
            StpLogic stpLogic = SaSsoUtil.saSsoTemplate.stpLogic;
            SaSession session = stpLogic.getSessionByLoginId(logoutDto.getUserId(), false);
            if (session != null) {
                Set<String> urlSet = session.get("SLO_CALLBACK_SET_KEY_", () -> new HashSet());
                Iterator var5 = urlSet.iterator();

                while (var5.hasNext()) {
                    String url = (String) var5.next();
                    String[] split = url.split("::");
                    if (split.length != 2) {
                        continue;
                    }
                    url = split[0];
                    ClientInfo clientInfo = clientInfoRepo.findByClientCode(split[1]);
                    if (clientInfo == null) {
                        continue;
                    }
                    LogoutDto logoutCallDto = new LogoutDto(logoutDto.getUserId());
                    logoutCallDto.setTimestamp(System.currentTimeMillis());
                    logoutCallDto.setClientCode("");
                    logoutCallDto.setSignature(SignUtil.sign(logoutCallDto.toSignMap(), clientInfo.getSecretKey()));
                    String result = OkHttpClient.post(url, logoutCallDto.toJsonString());
                }
                stpLogic.logout(logoutDto.getUserId());
            }
            SaSsoUtil.ssoLogout(logoutDto.getUserId());
            return RestResp.success();
        }, logoutDto);
    }

    @PostMapping("/sso/pushUser")
    public RestResp pushUser(@RequestBody PushUserDto pushUserDto) throws Exception {
        return checkParamSignThenExecute(baseSignRequest -> userService.pushUser((PushUserDto) baseSignRequest), pushUserDto);
    }

    @PostMapping("/sso/userInfo")
    public RestResp getUserInfo(@RequestBody UserInfoDto userInfoDto) throws Exception {
        return checkParamSignThenExecute(baseSignRequest -> {
            User user = userService.getUser(userInfoDto.getUserId());
            UserInfoResp userInfoResp = new UserInfoResp(user.getUserId(),
                    user.getLoginName(),
                    user.getMobile(),
                    user.getCfcaKeyId(),
                    user.getCompany(),
                    user.getUscc(),
                    user.getRealName(),
                    user.getIdCard());
            return RestResp.success(userInfoResp);
        }, userInfoDto);
    }

    public RestResp checkParamSignThenExecute(Function<BaseSignRequest, RestResp> function, BaseSignRequest baseSignRequest) throws Exception {
        //@Valid
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BaseSignRequest>> validate = validator.validate(baseSignRequest);
        if (CollectionUtils.isNotEmpty(validate)) {
            return RestResp.fail(validate.iterator().next().getMessage());
        }
        if (baseSignRequest.getTimestamp() < System.currentTimeMillis() - signTimeout) {
            return RestResp.fail("timestamp timeout");
        }
        if (!checkSign(baseSignRequest)) {
            return RestResp.fail("signature error");
        }
        return function.apply(baseSignRequest);
    }

    private Boolean checkSign(BaseSignRequest baseSignRequest) {
        ClientInfo clientInfo = clientInfoRepo.findByClientCode(baseSignRequest.getClientCode());
        if (clientInfo == null) {
            return false;
        }
        return SignUtil.checkSign(baseSignRequest.toSignMap(), baseSignRequest.getSignature(), clientInfo.getSecretKey());
    }

    @Resource
    private void configSso(SaSsoConfig sso) {
        sso.setNotLoginView(() -> {
            return new ModelAndView("login.html");
        });
    }

    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }
}
