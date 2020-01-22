package bean;

import domain.Car;
import domain.Employee;
import org.apache.commons.beanutils.*;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author : weizc
 * @description:
 * @since 2019/3/11
 */
public class BeanTest {


    //  BeanUtils.copyProperties(left, right, MicsUtils.getNullPropertyNames(right, false));

    /**
     * 获取 isNull 的数组 属性集合
     *
     * @param source
     * @param isNull
     * @return
     */
    public static String[] getNullPropertyNames(Object source, boolean isNull) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .filter(pd -> isNull ? src.getPropertyValue(pd.getName()) == null : src.getPropertyValue(pd.getName()) != null)
                .map(PropertyDescriptor::getName).toArray(String[]::new);
    }


    //bean的读取
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Employee a = new Employee();
        Car c = new Car();
        a.setCar(c);
        a.setCreatTime(new Date());

        BeanUtils.setProperty(a, "name", "123");
        BeanUtils.setProperty(a, "car.id", 12);

        String carId = BeanUtils.getProperty(a, "car.id");
        String creatTime = BeanUtils.getProperty(a, "creatTime");

        Object creatTime1 = PropertyUtils.getProperty(a, "creatTime");
        Object carId2 = PropertyUtils.getProperty(a, "car.id");


        System.out.println("-------------");
        System.out.println(a);
        System.out.println(creatTime);
        System.out.println(creatTime1);
        System.out.println(carId2);

    }

    // 带有时间控制器的转换
    @Test
    public void test() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Employee em = new Employee();
        // 自带时间转换器
        // ConvertUtils.register(new DateLocaleConverter(), Date.class);
        // 自定义时间控制器

        Converter converter = new Converter() {
            @Override
            public Date convert(Class type, Object value) {
                // 当value参数等于空时返回空
                if (value == null) {
                    return null;
                }
                // 自定义时间的格式为yyyy-MM-dd
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                // 创建日期类对象
                Date dt = null;
                try {
                    // 使用自定义日期的格式转化value参数为yyyy-MM-dd格式
                    dt = sdf.parse((String) value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 返回dt日期对象
                return dt;
            }

        };


        ConvertUtils.register(converter, Date.class);
        BeanUtils.setProperty(em, "creatTime", "1997-22-22");
        String string = BeanUtils.getProperty(em, "creatTime");
        System.out.println(string);
    }

    //嵌套方法的结果
    @Test
    public void demo() {
        Employee user = new Employee();
        try {
            BeanUtils.setProperty(user, "name", "大神");
            //转换为map
            Map<String, String> map = BeanUtils.describe(user);
            System.out.println(map.get("name"));
            //执行方法
            MethodUtils.invokeMethod(user, "speak", null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
