package com.galaxy.alg;

import com.galaxy.flink.java.alg.sort.MergeSort;
import com.galaxy.flink.java.alg.sort.QuickSort;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author wang.baozhi
 * @since 2019/12/12 上午11:35
 */
public class SortTest {

    @Test
    //测试归并排序
    public void mergeSortTest() {
        int[] intArray = new int[]{3, 2, 4, 1, 5};
        MergeSort.mergeSort(intArray, 5);
        System.out.println(Arrays.toString(intArray));
    }

    @Test
    //测试快速排序
    public void quickSortTest() {
        //int[] intArray = new int[]{3, 2, 5, 1,4};
        int[] intArray = new int[]{3, 5, 6,4};
        QuickSort.quickSort(intArray, intArray.length);
        System.out.println(Arrays.toString(intArray));
    }

}
