package com.ais.brm.study.brmTest.hadoop.mapreduce.flow;

import com.ais.brm.study.brmTest.hadoop.mapreduce.flow.FlowBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {


    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();
        String[] fields = line.split("\t");

        String phone = fields[1];

        int upFlow = Integer.parseInt(fields[fields.length - 3]);
        int dFlow = Integer.parseInt(fields[fields.length - 2]);

        context.write(new Text(phone), new FlowBean(phone, upFlow, dFlow));
    }


}
