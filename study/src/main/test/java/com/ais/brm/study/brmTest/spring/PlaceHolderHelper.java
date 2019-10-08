package com.ais.brm.study.brmTest.spring;

import org.junit.Test;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.SystemPropertyUtils;

import java.util.Properties;

/**
 * @author : weizc
 * @description:
 * @since 2019/1/8
 */
public class PlaceHolderHelper {


    @Test
    public void test1() {
        String a = "{name}{age}{sex}";
        String b = "{name{age}{sex}}";

        PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("{",
                SystemPropertyUtils.PLACEHOLDER_SUFFIX,
                SystemPropertyUtils.VALUE_SEPARATOR, true);

        Properties properties = createProps();

        System.out.println("替换前:" + a);
        System.out.println("替换后:" + propertyPlaceholderHelper.replacePlaceholders(a, placeholderName -> {
            String value = properties.getProperty(placeholderName);
            return value;
        }));

        System.out.println("====================================================");
        System.out.println("替换前:" + b);
        System.out.println("替换后:" + propertyPlaceholderHelper.replacePlaceholders(b, placeholderName -> {
            String value = properties.getProperty(placeholderName);
            return value;
        }));
    }


    @Test
    public void test2() {
        String a = "${name},${age},${sex},${a:1},${xx}";

        Properties properties = createProps();


        PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper(SystemPropertyUtils.PLACEHOLDER_PREFIX,
                SystemPropertyUtils.PLACEHOLDER_SUFFIX,
                SystemPropertyUtils.VALUE_SEPARATOR, true);


        System.out.println("替换前:" + a);
        System.out.println("替换后:" + propertyPlaceholderHelper.replacePlaceholders(a, placeholderName -> {
            String value = properties.getProperty(placeholderName);
            return value;
        }));


        System.out.println("====================================================");


        propertyPlaceholderHelper = new PropertyPlaceholderHelper(SystemPropertyUtils.PLACEHOLDER_PREFIX,
                SystemPropertyUtils.PLACEHOLDER_SUFFIX,
                SystemPropertyUtils.VALUE_SEPARATOR, false);


        System.out.println("替换前:" + a);
        System.out.println("替换后:" + propertyPlaceholderHelper.replacePlaceholders(a, placeholderName -> {
            String value = properties.getProperty(placeholderName);
            return value;
        }));


    }


    private static Properties createProps() {
        Properties properties = new Properties();
        properties.setProperty("name", "wangzha");
        properties.setProperty("age", "18");
        properties.setProperty("sex", "man");
        properties.setProperty("name18man", "love");
        return properties;
    }

}
