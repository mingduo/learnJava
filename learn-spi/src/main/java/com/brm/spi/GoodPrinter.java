package com.brm.spi;

public class GoodPrinter implements Printer {
    @Override
    public void print() {
        System.out.println("你是个好人~");
    }
}