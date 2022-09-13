package com.xaicif.sso.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CheckCaptchaDto {

    @NotEmpty(message = "请输入验证码")
    private String captcha;
}
