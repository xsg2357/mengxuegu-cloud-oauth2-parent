package com.mengxuegu.oauth2.resource.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:application.yml") //定义要读取的配置文件的位置
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
