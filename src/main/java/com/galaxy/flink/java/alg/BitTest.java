package com.galaxy.flink.java.alg;

import java.util.Random;

/**
 * @author wang.baozhi
 * @since 2019/10/21 下午6:27
 */
/*
 * 使用bit方式存储大量int类型的数字
 */
public class BitTest {
    public static void main(String[] args){
        int totalCount = 100; //待排序数的数量
        //java中int占4字节,一个字节包含8个bit,每一个bit可以代表一个数.计算初始化多少个int
        int len = (totalCount - 1)/32 +1;
        int[] storageIntArray = new int[len]; //声明并初始化

        //初始化被存取的int数组
        int[] inputIntArray = new int[totalCount];
        //产生随机输入数据,数据是乱序的,可能重复
        Random rand = new Random();
        for(int i=0;i<totalCount;i++){
            int randomInt=rand.nextInt(totalCount);
            inputIntArray[i] = randomInt;
        }

        //将数据以bit方式存入int数组
        for(int i=0;i<totalCount;i++){
            set(storageIntArray,inputIntArray[i]);
        }

        //从小到大,输出存储的数字
        for(int i=0;i<totalCount;i++){
            if(read(storageIntArray,i)==1)
                System.out.print(i+" ");
        }
    }


    //将第index位置的int值进行bit操作
    public static void set(int[] num,int i){
           int index=i/32;//确定使用int数组中的下标
           int position=i%32;//确定一个int表示32位bit中的哪一位bit
           num[index] |= (1<<position);//将int值的position位置bit值设置为1
    }


    //读取第i位,判断是0/1
    public static  int read(int[] num,int i){
        int index=i/32;//确定使用int数组中的下标
        int position=i%32;//确定一个int表示32位bit中的哪一位bit
        int result=(num[index] >> position) & 1;//判断第index个int值得第position个bit值是否是1
        return result;
    }

}
