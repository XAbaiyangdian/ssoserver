package com.xaicif.sso.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "sso_user")
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userId;
    @Column(unique = true)
    private String mobile;
    //统一社会信用代码
    private String uscc;
    //U盾ID
    private String cfcaKeyId;
    private String password;

    //clientCode
    private String source;

    //实名信息 名称
    private String realName;
    //实名信息 身份证号
    private String idCard;

}
