package com.galaxy.flink.java.kafka;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author wang.baozhi
 * @since 2019/8/22 下午7:55
 */
public class WordsCapitalizer implements MapFunction<String, String> {
    @Override
    public String map(String s) {
        return s.toUpperCase();
    }
}