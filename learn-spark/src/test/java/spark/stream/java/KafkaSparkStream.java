package spark.stream.java;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-8-30</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class KafkaSparkStream {
    /*
    public static void main(String[] args) throws InterruptedException {
        //准备kafka的参数
        Map<String, Object> kafkaParams = getKafkaParam();

        //创建SparkStreaming，并设置间隔时间
        JavaStreamingContext streamingContext = getJavaStreamingContext();


        String topic = "topic_test";
        String groupId="group";
        Collection<String> topics = Arrays.asList(topic);
        *//*
        指定zk的地址，后期更新消费的偏移量时使用(以后可以使用Redis、
        MySQL来记录偏移量)
        *//*
        String zkQuorum = "10.21.20.224:2181";
        //创建一个 ZKGroupTopicDirs 对象,其实是指定往zk中写入数据的目录，
        // 用于保存偏移量\

        ZKGroupTopicDirs zkGroupDirs = new ZKGroupTopicDirs(groupId, topic);
        //获取 zookeeper 中的路径 "/g001/offsets/wordcount/"
        String zkTopicPath = zkGroupDirs.consumerOffsetDir();
    *//*    zookeeper 的host 和 ip，创建一个 client,用于跟新偏移量量的
        是zookeeper的客户端，可以从zk中读取偏移量数据，并更新偏移量
*//*
        ZkClient zkClient = new ZkClient(zkQuorum);
  *//*      查询该路径下是否字节点（默认有字节点为我们自己保存不同 partition 时生成的）
         /g001/offsets/wordcount/0/10001"
         /g001/offsets/wordcount/1/30001"
        /g001/offsets/wordcount/2/10001"
        zkTopicPath  -> /g001/offsets/wordcount/
        *//*
        int children = zkClient.countChildren(zkTopicPath);
        //如果 zookeeper 中有保存 offset，我们会利用这个 offset 作为 kafkaStream 的起始位置
        HashMap<TopicAndPartition, Long> fromOffsets = new HashMap<>();


        //如果保存过 offset
        if (children > 0) {
            for (int i = 0; i < children; i++) {
                // /g001/offsets/wordcount/0/10001
                Long partitionOffset = zkClient.readData(zkTopicPath + "/" + i);
                // wordcount/0
                TopicAndPartition topicAndPartition = TopicAndPartition.apply(topic, i);
                //将不同 partition 对应的 offset 增加到 fromOffsets 中
                // wordcount/0 -> 10001
                fromOffsets.put(topicAndPartition, partitionOffset);
            }
            //Key: kafka的key   values: "hello tom hello jerry"
            //这个会将 kafka 的消息进行 transform，最终 kafak 的数据都会变成 (kafka的key, message) 这样的 tuple
        }


        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        //位置策略
                        LocationStrategies.PreferConsistent(),
                        //消费者策略
                        ConsumerStrategies.Subscribe(topics, kafkaParams)
                );

        */

    /**
     * 直连方式只有在KafkaDStream的RDD中才能获取偏移量，
     * 那么就不能到调用DStream的Transformation
     * 所以只能子在kafkaStream调用foreachRDD，获取RDD的偏移量，
     * 然后就是对RDD进行操作了
     * 依次迭代KafkaDStream中的KafkaRDD
     *//*
        //偏移量的范围

        stream.foreachRDD(kafkaRDD -> {
            OffsetRange[] offsetRanges = ((HasOffsetRanges) kafkaRDD.rdd()).offsetRanges();
            kafkaRDD.foreachPartition(consumerRecords -> {
                OffsetRange o = offsetRanges[TaskContext.get().partitionId()];
                System.out.println(
                        o.topic() + " " + o.partition() + " " + o.fromOffset() + " " + o.untilOffset());
            });
            // begin your transaction

            kafkaRDD.foreach(line->{
                System.out.println("key = [" + line.key()+ "]");
                System.out.println("value = [" + line.value()+ "]");
                System.out.println("topic = [" + line.topic()+ ","+line.partition()+","
                +line.offset()+"]");

            });

          //  ((CanCommitOffsets) stream.inputDStream()).commitAsync(offsetRanges);

        });


        // Start the computation
        streamingContext.start();
        // Wait for the computation to terminate
        streamingContext.awaitTermination();
    }*/
    private static JavaStreamingContext getJavaStreamingContext() {
        SparkConf conf = new SparkConf()
                .setMaster("local[2]")
                .setAppName("kafkaSparkContext");
        return new JavaStreamingContext
                (conf, Durations.seconds(5));
    }

    private static Map<String, Object> getKafkaParam() {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "10.21.20.224:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", true);
        return kafkaParams;
    }
}
