package com.ais.brm.study.brmTest.collection;

import org.junit.Test;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-9-12</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class VolatileTest{

    int a=1;
    int b=2;

    //赋值操作
    public  void change(){
        a=3;
        b=a;
    }

    //打印操作
    public  void print(){
        System.out.println("b:"+b+",a:"+a);
    }

    @Test
    public void testNorMal(){
        VolatileTest vt=new VolatileTest();

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                vt.change();
            }).start();


            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                vt.print();
            }).start();
        }


    }
}