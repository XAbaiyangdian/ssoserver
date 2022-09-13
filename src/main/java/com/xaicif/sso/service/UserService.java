package com.xaicif.sso.service;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.xaicif.sso.common.EncryptUtils;
import com.xaicif.sso.common.IdGenerateUtils;
import com.xaicif.sso.common.RestResp;
import com.xaicif.sso.entity.User;
import com.xaicif.sso.repo.UserRepo;
import com.xaicif.sso.request.DoLoginDto;
import com.xaicif.sso.request.PushUserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Value("${user.defaultpassword}")
    private String defaultPassword;
    @Resource
    private UserRepo userRepo;

    public RestResp login(DoLoginDto doLoginDto) {
        User user = userRepo.findByMobile(doLoginDto.getMobile());
        if (user == null) {
            return RestResp.fail("账号验证失败");
        }
        if (StringUtils.isNotBlank(doLoginDto.getPassword())) {
            if (!EncryptUtils.encodeSHA256(doLoginDto.getPassword()).equals(user.getPassword())) {
                return RestResp.fail("账号验证失败");
            }
        } else {
            if (!"123456".equals(doLoginDto.getCaptcha())) {
                return RestResp.fail("验证码无效");
            }
        }
        StpUtil.stpLogic.getConfig().cookie.setHttpOnly(true);
        StpUtil.login(user.getUserId());
        return RestResp.success(StpUtil.getTokenValue());
    }

    public User getUser(String userId) {
        return userRepo.findByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResp pushUser(PushUserDto pushUserDto) {
        User user = userRepo.findByMobile(pushUserDto.getMobile());
        if (user == null) {
            user = new User();
            user.setUserId(IdGenerateUtils.generateNumberId("SSO", 10));
            user.setMobile(pushUserDto.getMobile());
            user.setCfcaKeyId(pushUserDto.getCfcaKeyId());
            user.setUscc(pushUserDto.getUscc());
            user.setPassword(EncryptUtils.encodeSHA256(defaultPassword));
            user.setRealName(pushUserDto.getRealName());
            user.setIdCard(pushUserDto.getIdCard());

            List<Object> sources = new ArrayList<>();
            sources.add(pushUserDto.getClientCode());
            user.setSource(JSON.toJSONString(sources));

            userRepo.save(user);
        } else {
            List<String> sources = JSON.parseArray(user.getSource(), String.class);
            if (!sources.contains(pushUserDto.getClientCode())) {
                sources.add(pushUserDto.getClientCode());
                user.setSource(JSON.toJSONString(sources));
                userRepo.save(user);
            }
        }
        return RestResp.success(user.getUserId());
    }
}
