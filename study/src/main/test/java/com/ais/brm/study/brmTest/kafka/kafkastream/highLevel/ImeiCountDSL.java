package com.ais.brm.study.brmTest.kafka.kafkastream.highLevel;

import com.ais.brm.common.utils.DateUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author weizc
 */
public class ImeiCountDSL {

    public static void main(String[] args) {
        ImeiCountDSL d=new ImeiCountDSL();
        d.doWordCount();
    }




    public  void doWordCount() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-dsl");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.21.20.224:9092");

        //props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zookeeper0:12181/kafka");
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> stream = builder.stream("migu_streaming_topic_new");

        KGroupedStream<String, Long> krvrkGroupedStream = stream.filter((v, t) ->
        {
            String[] words = t.toLowerCase(Locale.getDefault()).split(
                    "@@@");
            if (words.length > 20) return true;
            return  false;
        }).map((k, v) -> {
            String[] words = v.toLowerCase(Locale.getDefault()).split(
                    "@@@");
                String phoneNumber = words[2];
                String datetime = words[1].substring(0,8);
                String imei = words[20];
                return KeyValue.pair(datetime + "_" + phoneNumber + "_" + imei, 1L);


        }).groupByKey(
                Serdes.String(), /* key (note: type was modified)*/
                Serdes.Long() );
        KTable<String, Long> kTable = krvrkGroupedStream.reduce((Long a, Long b) -> (a + b),"store");
        KStream<String, Long> stringLongKStream = kTable.toStream();
        stringLongKStream.print();
        stringLongKStream.writeAsText("F:\\d.txt","store");
        //设置时间窗口
        KTable<Windowed<String>, Long> aggregate1 =
        krvrkGroupedStream.reduce((Long a,Long b)->(a+b),
                TimeWindows.of(TimeUnit.SECONDS.toMillis(5))
                ,"windowStore"
                );

        aggregate1.foreach((Windowed<String> window, Long value) -> {
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
            Thread.sleep(3000L);//If commented out,error occur


            ReadOnlyKeyValueStore<String, Long> keyValueStore =
            streams.store("store", QueryableStoreTypes.keyValueStore());
            KeyValueIterator<String, Long> range = keyValueStore.all();

            while (true) {
                while (range.hasNext()) {
                    KeyValue<String, Long> next = range.next();
                    System.out.println("count for " + next.key + ": " + next.value);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
