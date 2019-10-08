package com.ais.brm.study.brmTest.hadoop.mapreduce.wc.skew;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Random;

public class SkewWordcount {

    public static class SkewWordcountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        Random random = new Random();
        Text k = new Text();
        IntWritable v = new IntWritable(1);
        int numReduceTasks = 0;

        @Override
        protected void setup(Context context)
                throws IOException, InterruptedException {
            numReduceTasks = context.getNumReduceTasks();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] words = value.toString().split(" ");
            for (String w : words) {
                k.set(w + "\001" + random.nextInt(numReduceTasks));
                context.write(k, v);

            }

        }

    }

    public static class SkewWordcountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        IntWritable v = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int count = 0;
            for (IntWritable value : values) {
                count += value.get();
            }
            v.set(count);
            context.write(key, v);
        }

    }

    public static void main(String[] args) throws Exception {


        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);

        job.setJarByClass(SkewWordcount.class);

        job.setMapperClass(SkewWordcountMapper.class);
        job.setReducerClass(SkewWordcountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 设置maptask端的局部聚合逻辑类
        job.setCombinerClass(SkewWordcountReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path("E:/dtwork/learnJava/learn/learnJava/mrdata/wordcount/input"));
        FileOutputFormat.setOutputPath(job, new Path("E:/dtwork/learnJava/learn/learnJava/mrdata/wordcount/skew-out"));

        job.setNumReduceTasks(3);

        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);

    }


}
