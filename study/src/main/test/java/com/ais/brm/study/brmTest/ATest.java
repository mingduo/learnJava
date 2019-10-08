package com.ais.brm.study.brmTest;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ATest {
    Gson gson = new Gson();

    class A extends C {

    }

    class B extends C {

    }

    class C {

    }

    @Test
    public void testRules() throws Exception {
        C c = new A();
        System.out.println(c instanceof A);
    }

    @Test
    public void testRules2() throws IOException {
        LocalDateTime today = LocalDateTime.now();
        File file = new File("");
        if (!file.exists()) file.createNewFile();
        FileOutputStream fs = new FileOutputStream(file, true);
        PrintWriter pw = new PrintWriter(fs);
        pw.println(today + ">>>>>facts :" + 123 + " rule's size:" + 100);
        pw.println(">>>>>facts :" + 123 + " rule's size:" + 100);

        pw.flush();
        //Map<String, String> map = (Map) JSON.parse(s);
    }

    @Test
    public void test3() throws Exception {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.println(i);
            }
        });
        thread.start();
        thread.join();
        System.out.println("11");
    }

    @Test
    public void test31() throws Exception {
        List<String> integerList = IntStream.range(0, 5).boxed().map(i -> String.valueOf(i)).collect(Collectors.toList());
        System.out.println(integerList);
        for (int i = 0; i < integerList.size(); i++) {
            String s = integerList.get(i);
            if (s.equals("1")) integerList.remove(i);
        }
        System.out.println(integerList);


    }

    @Test
    public void test4() throws ParseException {
        List<Integer> list = Arrays.asList(1, 2, 3);
        System.out.println(list.stream().count());
        list.stream().reduce(0, (a, b) -> a + b);

    }

    @Test
    public void test5() {
        int theDay = 20170620;
        int theHour = 23;

     /*   LocalDateTime datetime = LocalDateTime.of(
                theDay/10000, theDay/100%100, theDay%100, theHour, 0);*/
        LocalDateTime datetime = LocalDateTime.now();
        DateTimeFormatter dtfd = DateTimeFormatter.ofPattern("yyyyMMdd");
        System.out.println(datetime.getHour());
        System.out.println(dtfd.format(datetime));


        System.out.println(datetime.minusHours(1).withMinute(0).withSecond(0).withNano(0));

        Timestamp timestamp = Timestamp.valueOf(datetime);
        System.out.println(timestamp);
        LocalDate d = LocalDate.now();
        System.out.println(d);
        LocalTime d2 = LocalTime.now();
        System.out.println(d2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");

        System.out.println(formatter.format(datetime.minusHours(1)));
    }

    @Test
    public void test6() throws ParseException {
        int theDay = 20140424;
        LocalDateTime date = LocalDateTime.of(
                theDay / 10000, theDay / 100 % 100, theDay % 100, 0, 0);
        LocalDateTime localDateTime = date.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        System.out.println(formatter.format(localDateTime));


    }

    @Test
    public void test7() throws IOException {
        Path p = Paths.get("F:\\idea\\myLearn\\learn\\learnJava\\mrdata\\spark\\sql\\hive\\input\\33");
        List<String> collect = IntStream.range(0, 100).mapToObj(t -> {
            Random random = new Random();
            int nextInt = random.nextInt(500);
            return t + " " + "value_" + nextInt;
        }).collect(Collectors.toList());
        Files.write(p, collect);
    }

    @Test
    public void test8() throws IOException {
        File f = new File("a.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileUtils.writeStringToFile(f, "我是大树", "UTF-8", true);
        RandomAccessFile raf = new RandomAccessFile(f, "r");
        raf.seek(20);
        String line = raf.readLine();
        if (line != null) {
            line = new String(line.getBytes("ISO-8859-1"), "utf-8");
            System.out.println(line);
        }

    }


    @Test
    public void test10() throws Exception {
        try {
            System.out.println("begin");
            new Thread(() -> {
                try {
                    System.out.println("thread start");
                    Thread.sleep(3000);
                    System.out.println("thread end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } finally {
            System.out.println("finally");
        }
        //  Thread.sleep(20*3600);
    }
}
