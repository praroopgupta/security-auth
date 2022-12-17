package com.pg.securityauth.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Errors {

    ESA_400("ESA_400", "Bad request!"),
    ESA_401("ESA_401", "Authentication/Authorization failed!"),
    ESA_999("ESA_999", "Internal server error, please contact customer support!");

    private final String code;
    private final String description;
}
