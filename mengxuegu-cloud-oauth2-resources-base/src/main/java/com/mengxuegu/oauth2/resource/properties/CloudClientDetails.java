package com.mengxuegu.oauth2.resource.properties;

import lombok.Data;

import java.io.Serializable;

@Data
public class CloudClientDetails implements Serializable {

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;

}
