package common.buessiness.problems.equals.lombokequals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *  
 * @since 2020/7/27
 * @author : weizc 
 */
@Slf4j
public class LombokequalsMain {

    public static void main(String[] args) {
        test();

        System.out.println("=====");

        test2();
    }

    private static void test2() {
        Employee employee = new Employee("zhuye", "001","mi");
        Employee employee2 = new Employee("Joseph", "001","mi");
        log.info("employee.equals(employee2) ? {}", employee.equals(employee2));
    }

    private static void test() {

        Person person1 = new Person("zhuye", "001");
        Person person2 = new Person("Joseph", "001");
        log.info("person1.equals(person2) ? {}", person1.equals(person2));
    }

    @AllArgsConstructor
    @Data
    static class Person{

        @EqualsAndHashCode.Exclude
        private String name;
        private String identity;
    }

    @EqualsAndHashCode(callSuper = true)
    static class Employee extends Person{

        private String company;

        public Employee(String name, String identity,String company) {
            super(name, identity);
            this.company=company;
        }
    }
}
