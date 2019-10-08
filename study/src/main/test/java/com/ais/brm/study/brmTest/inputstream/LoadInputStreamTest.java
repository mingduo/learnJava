package com.ais.brm.study.brmTest.inputstream;

import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class LoadInputStreamTest {

    /**
     * getClassLoader
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void contextLoads() throws IOException, URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("application.properties");

        Path path = Paths.get(url.toURI());

        List<String> lines = Files.readAllLines(path);

        System.out.println(lines);

    }


    /**
     * ResourceLoader 方式
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void contextLoads2() throws IOException, URISyntaxException {

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:application.properties");

        Path path = Paths.get(resource.getURI());

        List<String> lines = Files.readAllLines(path);

        System.out.println(lines);

    }

}
