package com.galaxy.flink.java.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wang.baozhi
 * @since 2019/8/26 上午9:44
 */
public class FruitGenerator2 implements Generator<String> {
    private String[] fruits = new String[]{"Apple", "Banana", "Pear"};



    public static void main(String[] args) throws Exception {
        FruitGenerator2 f=new FruitGenerator2();
        Object obj =f.genericMethod(Class.forName("com.test.test"));
       // List<String>[] ls = new ArrayList<String>[10];
        List<?>[] ls2 = new ArrayList<?>[10];
        List<String>[] ls3 = new ArrayList[10];
    }

    @Override
    public String next(){

        Generic<Integer> gInteger = new Generic<Integer>(123);

        showKeyValue1(gInteger);
        Object o=new Object();

        Random rand = new Random();
        return fruits[rand.nextInt(3)];

    }




    public <T> T genericMethod(Class<T> tClass) throws InstantiationException,
            IllegalAccessException{
        T instance = tClass.newInstance();
        return instance;
    }

    public void showKeyValue1(Generic<?> obj){
       System.out.println(obj.getKey());
    }
}
