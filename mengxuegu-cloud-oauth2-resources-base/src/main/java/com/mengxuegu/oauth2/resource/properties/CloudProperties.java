package com.mengxuegu.oauth2.resource.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mengxuegu.cloud")
public class CloudProperties {

    private  CloudClientDetails cloudClientDetails;

    public CloudClientDetails getCloudClientDetails() {
        return cloudClientDetails;
    }

    public void setCloudClientDetails(CloudClientDetails cloudClientDetails) {
        this.cloudClientDetails = cloudClientDetails;
    }
}
