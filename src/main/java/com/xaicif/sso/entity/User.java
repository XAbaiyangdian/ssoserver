package com.xaicif.sso.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "sso_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"uscc", "loginName"})})
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userId;
    //登录名 企业内唯一
    @Column(nullable = false)
    private String loginName;
    //公司统一社会信用代码
    @Column(nullable = false)
    private String uscc;
    //公司名称
    private String company;
    //登录密码
    private String password;

    //手机号 不唯一
    private String mobile;
    //实名信息 名称
    private String realName;
    //实名信息 身份证号
    private String idCard;
    //U盾序列号
    private String cfcaKeyId;

    //clientCode  数据来源
    private String source;

    private Long lastModifyTime;
    private String lastModifyClient;

}
