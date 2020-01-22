package com.ais.brm.study.brmTest.kafka.kafkastream.lowLevel;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.stream.Stream;

/**
 * phone number parser.
 * Created by zhaocaiwen on 2017/11/28.
 */
@Component
public class ParsePhoneNumberProcessor implements ProcessorSupplier<String, String> {

    @Override
    public Processor<String, String> get() {
        return new Processor<String, String>() {
            private ProcessorContext context;
            private KeyValueStore<String, Integer> kvStore;

            @Override
            @SuppressWarnings("unchecked")
            public void init(ProcessorContext context) {
                this.context = context;
            }

            @Override
            public void process(String dummy, String line) {
                String[] words = line.toLowerCase(Locale.getDefault()).split("@@@");
                Stream.of(words).forEach(t ->
                        context.forward(t, t)
                );

                context.commit();
            }

            @Override
            public void punctuate(long timestamp) {

            }

            @Override
            public void close() {
            }
        };
    }
}
