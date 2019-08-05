package com.brm.spi;

public class BadPrinter implements Printer {

    @Override
    public void print() {

        System.out.println("我抽烟，喝酒，蹦迪，但我知道我是好女孩~");
    }
}