package com.ais.brm.study.brmTest.kafka.kafkastream.lowLevel;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * test.
 * Created by zhaocaiwen on 2017/11/28.
 */
@Component("WordCountProcessor")
public class WordCountProcessor implements ProcessorSupplier<String, String> {

    @Override
    public Processor<String, String> get() {
        return new Processor<String, String>() {
            private ProcessorContext context;
            private KeyValueStore<String, Integer> kvStore;

            @Override
            @SuppressWarnings("unchecked")
            public void init(ProcessorContext context) {
                this.context = context;
                this.context.schedule(1000);
                this.kvStore = (KeyValueStore<String, Integer>) context.getStateStore("CountsOfPhoneNumbers3");
            }

            @Override
            public void process(String dummy, String line) {

                String phoneNumber = line;


                if (StringUtils.isNotEmpty(phoneNumber)) {
                    Integer count = Optional.ofNullable
                            (this.kvStore.get(phoneNumber))
                            .map(t -> t + 1).orElse(1);

                    this.kvStore.put(phoneNumber, count);

                }

                context.commit();
            }

            @Override
            public void punctuate(long timestamp) {
                try (KeyValueIterator<String, Integer> iter = this.kvStore.all()) {
                    System.out.println("----------- " + new Date(timestamp) + " ----------- ");

                    while (iter.hasNext()) {
                        KeyValue<String, Integer> entry = iter.next();

                        System.out.println("[" + entry.key + ", " + entry.value + "]");

                        //context.forward(entry.key, entry.value.toString());
                    }
                }
            }

            @Override
            public void close() {
            }
        };
    }
}
