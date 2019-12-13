package com.galaxy.guava.test;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//import   org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author wang.baozhi
 * @since 2019/10/17 下午4:54
 */
public class GuavaTest {


    @Test
    public void nullOptionalTest() {
        Optional<Integer> num1 = Optional.of(null);// 此处抛出异常 java.lang.NullPointerException
        Optional<Integer> num2 = Optional.of(5);
        Integer num3 = num1.get() + num2.get();
    }

    @Test
    public void immutableSetTest() {
        final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
                "red",
                "orange",
                "yellow",
                "green",
                "blue",
                "purple");

        final ImmutableSet<String> bars;
        Set aa=new HashSet<String>();
        bars = ImmutableSet.copyOf(aa);
        bars.add("");
    }

    @Test
    public void immutableSetTest2() {
      /*  ImmutableSet s=ImmutableSet.of("d","a", "b", "c", "a", "d", "b");
        UnmodifiableIterator it= s.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }*/

        Map<String, Integer> salary = ImmutableMap.<String, Integer>builder()
                .put("John", 1000)
                .put("Jane", 1500)
                .put("Adam", 2000)
                .put("Tom", 2000)
                .build();
        ImmutableSortedMap<String, Integer> salary2 = new ImmutableSortedMap
                .Builder<String, Integer>(Ordering.natural())
                .put("John", 1000)
                .put("Jane", 1500)
                .put("Adam", 2000)
                .put("Tom", 2000)
                .build();

        assertEquals(2000, salary2.lastEntry().getValue().intValue());
    }


    @Test
    public void whenGroupingListsUsingMultimap_thenGrouped() {
        List<String> names = Lists.newArrayList("John", "Adam", "Tom");
        Function<String,Integer> func = new Function<String,Integer>(){
            public Integer apply(String input) {
                return input.length();
            }
        };
        Multimap<Integer, String> groups = Multimaps.index(names, func);
        assertThat(groups.get(3), containsInAnyOrder("Tom"));
        assertThat(groups.get(4), containsInAnyOrder("John", "Adam"));

        Map<String, String> aNewMap = Maps.newHashMap();
    }


}
