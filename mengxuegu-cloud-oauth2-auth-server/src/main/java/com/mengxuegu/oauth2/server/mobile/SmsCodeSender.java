package com.mengxuegu.oauth2.server.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送短信验证码
 */
//@Component
public class SmsCodeSender implements SmsSend {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean sendSms(String mobile, String content) {
        String sendContent = String.format("梦学员，验证码%s，请勿泄露他人。", content);
        //调用第三方的功能的sdk
        logger.info("向手机号" + mobile + "发送的短信为:" + sendContent);
        return true;
    }
}
