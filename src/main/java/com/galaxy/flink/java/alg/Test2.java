package com.galaxy.flink.java.alg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wang.baozhi
 * @since 2019/10/21 下午8:30
 */
public class Test2 {
    public static void main(String[] args) {
        List<String> arrayList=new ArrayList<>();
        String element="aaa";
        arrayList.add(element);
        Test2 test2 = new Test2();
        test2.test();
    }


    public void test() {
        byte i = 127;
        byte j = -128;
        System.out.println(i);
        System.out.println(j);

        int maxBitI = (i >> 8) & 1;
        int maxBitJ = (j >> 8) & 1;
        System.out.println(maxBitI);
        System.out.println(maxBitJ);
    }

    public void tes2() {
        int x = 15;
        for (int i = 0; i < 32; i++) {
            int ret = (x >> i) & 1;
            System.out.println(ret);
        }


    }
}