package com.ais.brm.study.brmTest.hadoop.hdfs.datacollect;

import java.util.Timer;

public class DataCollectMain {

    public static void main(String[] args) {

        Timer timer = new Timer();

        timer.schedule(new CollectTask(), 0, 60 * 60 * 1000L);

        timer.schedule(new BackupCleanTask(), 0, 60 * 60 * 1000L);

    }


}
