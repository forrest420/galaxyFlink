package com.galaxy.flink.java.alg.sort;

import java.util.Arrays;

/**
 * @author wang.baozhi
 * @since 2019/12/5 下午3:57
 */
public class MainClass {
    public static void main(String[] args) {
      //测试归并排序
        int[] intArray=new int[]{3,2,4,1,5};
        MergeSort.mergeSort(intArray,5);
        System.out.println(Arrays.toString(intArray));
    }
}
