package common.buessiness.problems.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author : weizc
 * @since 2020/7/23
 */
public interface SystemPropertySourceUtils {

    static void loadProperties(Resource resource) {
        EncodedResource encodedResource = new EncodedResource(resource, "utf-8");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(encodedResource);
            properties.forEach((k, v) ->
                    System.setProperty(k.toString(), v.toString())
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
