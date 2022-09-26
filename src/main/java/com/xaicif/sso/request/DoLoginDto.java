package com.xaicif.sso.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class DoLoginDto {

    @NotEmpty(message = "loginName is empty")
    private String loginName;
    @NotEmpty(message = "password is empty")
    private String password;
    //统一社会信用代码
    @NotEmpty(message = "uscc is empty")
    private String uscc;
    private String subject;
    //序列号
    private String cfcaKeyId;
    private String sign;
}
