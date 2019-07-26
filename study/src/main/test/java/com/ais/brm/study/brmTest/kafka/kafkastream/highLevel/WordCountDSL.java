package com.ais.brm.study.brmTest.kafka.kafkastream.highLevel;

import com.ais.brm.study.brmTest.kafka.kafkastream.lowLevel.WordCountProcessor;
import com.ais.brm.common.utils.DateUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author weizc
 */
@Component
public class WordCountDSL {




    public  void doWordCountByWindow() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount-dsl");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.21.20.224:9092");

        //props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zookeeper0:12181/kafka");
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> stream = builder.stream("migu_streaming_topic_new");
//   \W+
        stream.process(new WordCountProcessor());
        KGroupedStream<String, String> krvrkGroupedStream = stream
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("@@@")))
                .groupBy((key, value) -> value);
        KTable<Windowed<String>, Long> window1 = krvrkGroupedStream.count(TimeWindows.of(TimeUnit.SECONDS.toMillis(5)), "window");
        window1.print();
        //设置时间窗口

        window1.foreach((Windowed<String> window, Long value) -> {
            Date d=new Date();
            Date d2=new Date();
            d.setTime(window.window().start());
            d2.setTime(window.window().end());
            String start = DateUtils.formatTime(d);
            String end =DateUtils.formatTime(d2);
            System.out.printf("key=%s, value=%s, start=%s, end=%s\n",window.key(), value, start, end);

        });

        KafkaStreams streams = new KafkaStreams(builder, props);
        try {
            streams.start();
          /*  Thread.sleep(3000L);//If commented out,error occur

            //设置时间窗口
            ReadOnlyKeyValueStore<String, Long> keyValueStore =
            streams.store("store", QueryableStoreTypes.keyValueStore());
            KeyValueIterator<String, Long> range = keyValueStore.all();

            while (true) {
                while (range.hasNext()) {
                    KeyValue<String, Long> next = range.next();
                    System.out.println("count for " + next.key + ": " + next.value);

                }

                range.close();
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public   void doWordCountBySessionWin() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount-dsl");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.21.20.224:9092");

        //props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zookeeper0:12181/kafka");
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> stream = builder.stream("migu_streaming_topic_new");
//   \W+

        SessionWindows with = SessionWindows.with(TimeUnit.SECONDS.toMillis(5));
        KGroupedStream<String, String> krvrkGroupedStream = stream
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("@@@")))
                .groupBy((key, value) -> value);
        KTable<Windowed<String>, Long> window1 = krvrkGroupedStream.count(with, "window");
        window1.print();
        //设置时间窗口

        window1.foreach((Windowed<String> window, Long value) -> {
            Date d=new Date();
            Date d2=new Date();
            d.setTime(window.window().start());
            d2.setTime(window.window().end());
            String start = DateUtils.formatTime(d);
            String end =DateUtils.formatTime(d2);
            System.out.printf("key=%s, value=%s, start=%s, end=%s\n",window.key(), value, start, end);

        });

        KafkaStreams streams = new KafkaStreams(builder, props);
        try {
            streams.start();
          /*  Thread.sleep(3000L);//If commented out,error occur

            //设置时间窗口
            ReadOnlyKeyValueStore<String, Long> keyValueStore =
            streams.store("store", QueryableStoreTypes.keyValueStore());
            KeyValueIterator<String, Long> range = keyValueStore.all();

            while (true) {
                while (range.hasNext()) {
                    KeyValue<String, Long> next = range.next();
                    System.out.println("count for " + next.key + ": " + next.value);

                }

                range.close();
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
