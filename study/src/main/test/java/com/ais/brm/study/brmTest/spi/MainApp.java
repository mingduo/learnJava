package com.ais.brm.study.brmTest.spi;

import com.ais.brm.study.spi.Printer;

import java.util.ServiceLoader;

public class MainApp {

    /**
     * 这里需要重点说明，每一个SPI接口都需要在自己项目的静态资源目录中
     * 声明一个services文件，文件名为实现规范接口的类名全路径，
     * 在此例中便是moe.cnkirito.spi.api.Printer，在文件中，
     * 则写上一行具体实现类的全路径，在此例中便是moe.cnkirito.spi.api.GoodPrinter。
     * @param args
     */
    public static void main(String[] args) {

        ServiceLoader<Printer> printers = ServiceLoader.load(Printer.class);

        printers.forEach(t->t.print());
    }
}