package com.galaxy.flink.java.interfaces;

/**
 * @author wang.baozhi
 * @since 2019/8/26 上午9:43
 */
public interface Generator<T> {
    public T next();
}
