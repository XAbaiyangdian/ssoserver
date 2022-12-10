package com.xaicif.sso.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckTicketResp {

    //用户id
    private String userId;
    //登录名
    private String loginName;
    //统一社会信用代码
    private String uscc;
    //手机号
    private String mobile;
    //u盾序列还
    private String cfcaKeyId;
    //公司名称
    private String company;
    //总包 分包  总包,分包
    private String companyRole;


    public CheckTicketResp(String userId, String loginName, String uscc, String mobile, String cfcaKeyId, String company, String companyRole) {
        this.userId = userId;
        this.loginName = loginName;
        this.uscc = uscc;
        this.mobile = mobile;
        this.cfcaKeyId = cfcaKeyId;
        this.companyRole = companyRole;
        this.company = company;
    }
}
