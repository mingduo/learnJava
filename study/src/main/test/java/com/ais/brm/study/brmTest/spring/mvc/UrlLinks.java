package com.ais.brm.study.brmTest.spring.mvc;

import org.junit.Test;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author : weizc
 * @description:
 * @since 2019/5/20
 */
public class UrlLinks {
    @Test
    public void url() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("https://localhost:8080/test/{hello}")
                .scheme("http").port("8081").queryParam("q", "{q}");

        UriComponents uriComponents = uriComponentsBuilder.build();

        URI uri = uriComponents.expand("hello", "123").toUri();

        System.out.println("uri=" + uri);


        System.out.println("url=" + uriComponentsBuilder.buildAndExpand("yes boss", "13").toUri());


        System.out.println("url=" + uriComponentsBuilder.build("yes", "13"));

        DefaultUriBuilderFactory u = new DefaultUriBuilderFactory("https://localhost:8080/test");
        URI build = u.uriString("/{t}").queryParam("q", "{q}").build("t", "123");
        System.out.println("url=" + build);

    }

    @Test
    public void url_encoding() {
        URI uri = UriComponentsBuilder.fromPath("/hotel list/{city}").queryParam("q", "{q}")
                .encode().buildAndExpand("New York", "foo+bar").toUri();
        System.out.println("uri=>" + uri);

        uri = UriComponentsBuilder.fromPath("/hotel list/{city}?q={q}")
                .build("New York", "foo+bar");

        System.out.println("uri=>" + uri);

    }
}
