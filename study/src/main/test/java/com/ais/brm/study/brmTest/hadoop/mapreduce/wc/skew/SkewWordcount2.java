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

public class SkewWordcount2 {

    public static class SkewWordcountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        Text k = new Text();
        IntWritable v = new IntWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] wordAndCount = value.toString().split("\t");
            v.set(Integer.parseInt(wordAndCount[1]));
            k.set(wordAndCount[0].split("\001")[0]);

            context.write(k, v);

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

        job.setJarByClass(SkewWordcount2.class);

        job.setMapperClass(SkewWordcountMapper.class);
        job.setReducerClass(SkewWordcountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 设置maptask端的局部聚合逻辑类
        job.setCombinerClass(SkewWordcountReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path("f:/mrdata/wordcount/skew-out"));
        FileOutputFormat.setOutputPath(job, new Path("f:/mrdata/wordcount/skew-out2"));

        job.setNumReduceTasks(3);

        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);

    }


}
