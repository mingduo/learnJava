package kafkastream.lowLevel;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by zhaocaiwen on 2017/11/28.
 */
@Component
public class WordCounter {
    private KafkaStreams streams;
    @Autowired
    private ParsePhoneNumberProcessor parsePhoneNumberProcessor;
    @Autowired
    private WordCountProcessor wordCountProcessor;


    public void doWordCount() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.21.20.224:9092");
        //    props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // setting offset reset to earliest so that we can re-run the demo code with the same pre-loaded data
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        TopologyBuilder builder = new TopologyBuilder();
        builder.addSource("Source", "migu_streaming_topic_new");
        builder.addProcessor("parsePhoneNumberProcessor", parsePhoneNumberProcessor, "Source");
        builder.addProcessor("wordCountProcessor", wordCountProcessor, "parsePhoneNumberProcessor");

        builder.addSink("Sink", "streams-phonenumbercount-processor-output3", "wordCountProcessor");

        streams = new KafkaStreams(builder, props);

        try {
            streams.start();
        } catch (Throwable e) {
        }
    }

    public void doClose() {
        streams.close();
    }


}
