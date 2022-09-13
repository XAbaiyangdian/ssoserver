package com.xaicif.sso.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoLoginDto {

    private String mobile;

    private String password;

    //短信验证码
    private String captcha;
}
