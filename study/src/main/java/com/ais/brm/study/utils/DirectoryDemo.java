package com.ais.brm.study.utils;

import java.io.File;
import java.util.stream.IntStream;

/**
 * 
 * @apiNode:
 * @since 2020/4/3
 * @author : weizc 
 */
public class DirectoryDemo {

    public static void main(String[] args) {
        printDirectory(new File("study"),0);
    }

    private static void printDirectory(File file,int i){
        if(file.isFile()){
            IntStream.range(0,i).forEach(t-> System.out.print("-"));
            System.out.println("当前文件是 " + file.getName() );

        }
        if(file.isDirectory()){
            for(File dict:file.listFiles()){
                IntStream.range(0,i).forEach(t-> System.out.print("-"));
                System.out.println("当前目录是 " + file.getName() );
                printDirectory(dict,i+1);
            }
        }
    }
}
