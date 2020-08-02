package com.mengxuegu.oauth2.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//对象字段一定要与yml里面写成一致  不然对象会获取NULL
@Component
@ConfigurationProperties(prefix = "mengxuegu.cloud")
public class CloudProperties {

    private  CloudClientDetails cloudClientDetail;

    public CloudClientDetails getCloudClientDetail() {
        return cloudClientDetail;
    }

    public void setCloudClientDetail(CloudClientDetails cloudClientDetail) {
        this.cloudClientDetail = cloudClientDetail;
    }
}
