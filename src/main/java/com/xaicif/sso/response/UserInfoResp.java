package com.xaicif.sso.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoResp {

    private String userId;

    private String mobile;

    private String cfcaKeyId;

    private String uscc;

    private String realName;

    private String idCard;

    public UserInfoResp(String userId, String mobile, String cfcaKeyId, String uscc, String realName, String idCard) {
        this.userId = userId;
        this.mobile = mobile;
        this.cfcaKeyId = cfcaKeyId;
        this.uscc = uscc;
        this.realName = realName;
        this.idCard = idCard;
    }
}
