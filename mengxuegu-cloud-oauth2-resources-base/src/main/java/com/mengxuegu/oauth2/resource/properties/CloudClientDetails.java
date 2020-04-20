package com.mengxuegu.oauth2.resource.properties;

import lombok.Data;

//@Component
//@PropertySource(value= "classpath:/userresources.properties")
//@ConfigurationProperties(prefix = "cloud")
@Data
public class CloudClientDetails  {

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;

}
