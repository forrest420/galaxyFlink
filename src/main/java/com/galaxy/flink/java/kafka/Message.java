package com.galaxy.flink.java.kafka;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;

/**
 * @author wang.baozhi
 * @since 2019/12/21 上午11:03
 */

@AllArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
public class Message {

    private final Long id;
    private final String name;


    public Message(Long id){
        this.name="";
        this.id=1L;
    }


/*
    @ConstructorProperties({"id"})
    public Message(Long id){
        this.id=id;
    }*/

}
