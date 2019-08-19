package com.snapdeal.ims.entity;

import java.io.Serializable;

import com.snapdeal.ims.enums.EmailVerificationSource;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class SdFcPasswordEntity implements Serializable {

    private static final long serialVersionUID = 3410299934052814189L;
    private String sdSdHashedPassword;
    private String fcFcHashedPassword;
    private String sdFcHashedPassword;
    private String fcSdHashedPassword;
    private String imsSdHashedPassword;
    private boolean isUpgradeInitialized;
    private EmailVerificationSource emailVerificationSource;
}