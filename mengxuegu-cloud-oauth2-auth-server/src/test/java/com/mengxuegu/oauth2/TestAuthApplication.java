package com.mengxuegu.oauth2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

//test里面的com.mengxuegu.oauth2必须和src里面com.mengxuegu.oauth2 一样
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAuthApplication {

    @Autowired

    PasswordEncoder passwordEncoder;

    @Test
    public void testPwd() {
        System.out.println(passwordEncoder.encode("mengxuegu-secret"));
    }

}
