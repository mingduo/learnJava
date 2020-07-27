package common.buessiness.problems.equals.differentclassloaderequals;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : weizc
 * @since 2020/7/27
 */
@Slf4j
public class ClassLoaderEqualsDiffenerce {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader classLoader = new MyClassLoader();

        Object pointFromMyClassLoader = classLoader.loadClass(Point.class.getName()).newInstance();

        Point point2 = new Point();

        Point pointFromSystemClassLoader = (Point) ClassLoader.getSystemClassLoader().loadClass(Point.class.getName()).newInstance();

        //false
        log.info("pointFromMyClassLoader instanceof Point:{}", pointFromMyClassLoader instanceof Point);
        //false
        log.info("pointFromMyClassLoader.getClass() ==Point.class:{}", pointFromMyClassLoader.getClass() == Point.class);
        //false
        log.info("pointFromMyClassLoader == point2:{}", pointFromMyClassLoader == point2);

        //true
        log.info("pointFromSystemClassLoader instanceof Point:{}", pointFromSystemClassLoader instanceof Point);
        //true
        log.info("pointFromSystemClassLoader.getClass() ==Point.class:{}", pointFromSystemClassLoader.getClass() == Point.class);
        //true
        log.info("pointFromSystemClassLoader == point2:{}", pointFromSystemClassLoader == point2);

    }


    static class MyClassLoader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            String className = StringUtils.substringAfterLast(name, ".") + ".class";

            InputStream inputStream = getClass().getResourceAsStream(className);
            if (inputStream == null) {
                return super.loadClass(name);
            }
            try {
                byte[]   bytes = new byte[inputStream.available()];
                inputStream.read(bytes);

                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
