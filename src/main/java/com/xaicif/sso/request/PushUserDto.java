package com.xaicif.sso.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PushUserDto extends BaseSignRequest {

    @NotEmpty(message = "loginName is empty")
    private String loginName;
    //统一社会信用代码
    @NotEmpty(message = "uscc is empty")
    private String uscc;
    //公司名称
    @NotEmpty(message = "company is empty")
    private String company;


    @NotEmpty(message = "mobile is empty")
    private String mobile;
    //实名信息 名称
    @NotEmpty(message = "realName is empty")
    private String realName;
    //实名信息 身份证号
    @NotEmpty(message = "idCard is empty")
    private String idCard;

    //U盾序列号
    private String cfcaKeyId;
}
