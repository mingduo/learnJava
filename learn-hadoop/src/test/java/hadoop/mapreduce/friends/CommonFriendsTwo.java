package hadoop.mapreduce.friends;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class CommonFriendsTwo {

    public static class CommonFriendsOneMapper extends Mapper<LongWritable, Text, Text, Text> {
        Text k = new Text();
        Text v = new Text();

        /**
         * B-C	A
         * B-D	A
         * B-F	A
         */
        // 输出：　B->A  C->A  D->A ...
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] userAndFriends = value.toString().split("\t");
            k.set(userAndFriends[0]);
            v.set(userAndFriends[1]);
            context.write(k, v);


        }


    }


    public static class CommonFriendsOneReducer extends
            Reducer<Text, Text, Text, Text> {

        // 一组数据：  B-C-->  A
        // 一组数据：  B-C -->  B
        @Override
        protected void reduce(Text key, Iterable<Text> users, Context context)
                throws IOException, InterruptedException {

            StringBuilder stringBuilder = new StringBuilder();
            for (Text text : users) {
                stringBuilder.append(text).append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            context.write(key, new Text(stringBuilder.toString()));
        }

    }


    public static void main(String[] args) throws Exception {


        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);

        job.setJarByClass(CommonFriendsTwo.class);

        job.setMapperClass(CommonFriendsOneMapper.class);
        job.setReducerClass(CommonFriendsOneReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job,
                new Path("F:\\idea\\myLearn\\learn" +
                        "\\learnJava\\mrdata\\friends\\out1"));
        FileOutputFormat.setOutputPath(job,
                new Path("F:\\idea\\myLearn\\learn\\" +
                        "learnJava\\mrdata\\friends\\out2"));

        job.waitForCompletion(true);
    }
}
