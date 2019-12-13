package com.galaxy.flink.java.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wang.baozhi
 * @since 2019/10/16 下午7:18
 */
public class Test {


    public static void main(String[] args) throws Exception {
        Multimap<String,Integer> map = ArrayListMultimap.create();
        map.put("aa", 1);
        map.put("aa", 2);
        System.out.println(map.get("aa"));  //[1, 2]


        List<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        String result = Joiner.on("-").join(list);
        System.out.println(result);

    }

}
