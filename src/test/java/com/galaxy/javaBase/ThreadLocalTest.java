package com.galaxy.javaBase;

import jdk.internal.dynalink.beans.StaticClass;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wang.baozhi
 * @since 2019/12/12 上午11:35
 */
public class ThreadLocalTest {

    @Test
    //https://www.baeldung.com/java-threadlocal
    public void threadLocalTest() {

        ThreadLocal<String> t=new ThreadLocal<>();
        t.set("1");

        t.set("2");

        ThreadLocal<String> t2=new ThreadLocal<>();
        t2.set("3");
        System.out.println(t.get());
        System.out.println(t2.get());

        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 10);
        System.out.println(threadLocal.get());

    }

    @Test
    public void threadTest(){
        final int HASH_INCREMENT = 0x61c88647;
        AtomicInteger ai= new AtomicInteger(2);
        int result=ai.getAndAdd(HASH_INCREMENT);
        System.out.println(result);
    }
    
}
