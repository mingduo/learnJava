package common.buessiness.problems.equals.compareto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class CompareToMain {

    /**
     * 代码里本来使用了 ArrayList 的 indexOf 方法进行元素搜
     * 索，但是一位好心的开发同学觉得逐一比较的时间复杂度是 O(n)，效率太低了，于是改为
     * 了排序后通过 Collections.binarySearch 方法进行搜索，实现了 O(log n) 的时间复杂度。
     * 没想到，这么一改却出现了 Bug。
     */
    public static void main(String[] args) {

        wrong();

        System.out.println("=====");

        right();
    }

    private static void right() {
        List<StudentRight> list=new ArrayList<>();
        list.add(new StudentRight(1,"zs") );
        list.add(new StudentRight(2,"w5") );
        StudentRight student = new StudentRight(2, "l4");

        log.info("ArrayList.indexOf('l4')=={}",list.indexOf(student));

        Collections.sort(list);

        log.info("Collections.binarySearch(list,'l4')=={}",Collections.binarySearch(list,student));
    }

    private static void wrong() {
        List<Student> list=new ArrayList<>();
        list.add(new Student(1,"zs") );
        list.add(new Student(2,"w5") );
        Student student = new Student(2, "l4");

        log.info("ArrayList.indexOf('l4')=={}",list.indexOf(student));

        Collections.sort(list);

        log.info("Collections.binarySearch(list,'l4')=={}",Collections.binarySearch(list,student));

    }


    @Data
    @AllArgsConstructor
   static class Student implements Comparable<Student> {
        private int id;
        private String name;

        @Override
        public int compareTo(Student other) {
            int result = Integer.compare(other.id, id);
            if (result == 0)
                log.info("this {} == other {}", this, other);
            return result;
        }
    }

    /**
     * binarySearch 方法内部调用了元素的 compareTo 方法进行比较；
     */
    @Data
    @AllArgsConstructor
    static class StudentRight implements Comparable<StudentRight>{
        private int id;
        private String name;
        @Override
        public int compareTo(StudentRight other) {
            int result = Comparator.comparingInt(StudentRight::getId)
                    .thenComparing(StudentRight::getName)
                    .compare(other, this);
            if (result == 0)
                log.info("this {} == other {}", this, other);
            return result;
        }
    }
}