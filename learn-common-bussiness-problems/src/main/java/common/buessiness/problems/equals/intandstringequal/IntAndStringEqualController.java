package common.buessiness.problems.equals.intandstringequal;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : weizc
 * @since 2020/7/27
 */
@RequestMapping("intandstringequal")
@Slf4j
@RestController
public class IntAndStringEqualController {

    @GetMapping("intcompare")
    public void intcompare() {

        Integer a = 127; //Integer.valueOf(127)
        Integer b = 127; //Integer.valueOf(127)
        log.info("\nInteger a = 127;\n" +
                "Integer b = 127;\n" +
                "a == b ? {}", a == b);    // true

        Integer c = 128; //Integer.valueOf(128)
        Integer d = 128; //Integer.valueOf(128)
        log.info("\nInteger c = 128;\n" +
                "Integer d = 128;\n" +
                "c == d ? {}", c == d);   //false
        //设置-XX:AutoBoxCacheMax=1000再试试

        Integer e = 127; //Integer.valueOf(127)
        Integer f = new Integer(127); //new instance
        log.info("\nInteger e = 127;\n" +
                "Integer f = new Integer(127);\n" +
                "e == f ? {}", e == f);   //false

        Integer g = new Integer(127); //new instance
        Integer h = new Integer(127); //new instance
        log.info("\nInteger g = new Integer(127);\n" +
                "Integer h = new Integer(127);\n" +
                "g == h ? {}", g == h);  //false

        Integer i = 128; //unbox
        int j = 128;
        log.info("\nInteger i = 128;\n" +
                "int j = 128;\n" +
                "i == j ? {}", i == j); //true
    }

    @GetMapping("stringcompare")
    public void stringcomare() {
        String a = "1";
        String b = "1";
        log.info("\nString a = \"1\";\n" +
                "String b = \"1\";\n" +
                "a == b ? {}", a == b); //true

        String c = new String("2");
        String d = new String("2");
        log.info("\nString c = new String(\"2\");\n" +
                "String d = new String(\"2\");" +
                "c == d ? {}", c == d); //false

        String e = new String("3").intern();
        String f = new String("3").intern();
        log.info("\nString e = new String(\"3\").intern();\n" +
                "String f = new String(\"3\").intern();\n" +
                "e == f ? {}", e == f); //true

        String g = new String("4");
        String h = new String("4");
        log.info("\nString g = new String(\"4\");\n" +
                "String h = new String(\"4\");\n" +
                "g == h ? {}", g.equals(h)); //true
    }


    @GetMapping("enumcompare")
    public void enumcompare(@RequestBody OrderQuery orderQuery) {
        StateEnum stateEnum = StateEnum.CREATED;
        log.info("orderQuery:{},stateEnum:{},result:{}", orderQuery, stateEnum, orderQuery.getStatus() == stateEnum.getState());

    }

    private List<String> list = new ArrayList<>();

    @GetMapping("internperformance")
    public int internperformance(@RequestParam(value = "size", defaultValue = "10000000") int size) {
        //-XX:+PrintStringTableStatistics
        // 解决方式是，设置 JVM 参数 -XX:StringTableSize，指定更多的桶。设置
        //-XX:StringTableSize=10000000 后
        long begin = System.currentTimeMillis();
        list = IntStream.rangeClosed(1, size).mapToObj(i -> String.valueOf(i).intern())
                .collect(Collectors.toList());
        log.info("size:{},cost :{} ms ",list.size(),System.currentTimeMillis()-begin);

        return list.size();
    }


    @Getter
    enum StateEnum {
        CREATED(1000, "已创建"),
        PAID(1001, "已支付"),
        DELIVERED(1002, "已送到"),
        FINISHED(1003, "已完成");


        final Integer state;
        final String desc;

        StateEnum(Integer state, String desc) {
            this.state = state;
            this.desc = desc;
        }
    }
}
