package com.xaicif.sso.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto extends BaseSignRequest{

    private String userId;

    public UserInfoDto(String userId) {
        this.userId = userId;
    }
}
