package com.mengxuegu.oauth2.web.properties;

import lombok.Data;

@Data
public class CloudClientDetails {

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;

}
