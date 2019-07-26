package com.ais.brm.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebautoconfigApplicationTests {
    @Autowired
    Environment environment;
    @Autowired
    RestTemplate restTemplate;

    @Test
    public void contextLoads() {

    }


    @Test
    public void testProperties() {
        System.out.println(environment.toString());
    }

}
