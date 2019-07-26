package com.ais.brm.study.brmTest.spark.core.java;

import org.apache.spark.Partitioner;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//学科分区
public  class SubjectPartitioner extends Partitioner {

    List<String> subject;
    Map<String, Integer> numPartionMap;

    public SubjectPartitioner(List<String> subject) {
        numPartionMap = IntStream.range(0, subject.size()).boxed()
                .collect(Collectors.toMap
                        (t -> subject.get(t), Function.identity()));
        this.subject = subject;

    }

    @Override
    public int numPartitions() {
        return subject.size();
    }

    @Override
    public int getPartition(Object key) {
        String sub = (String) key;
        String in =sub.split("_")[0];
        Integer out = numPartionMap.get(in);
        return out;
    }
}



