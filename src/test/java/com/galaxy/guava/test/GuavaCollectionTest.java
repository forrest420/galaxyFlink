package com.galaxy.guava.test;

import com.google.common.base.Function;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.*;

/**
 * @author wang.baozhi
 * @since 2019/11/1 上午10:21
 */
public class GuavaCollectionTest {
    @Test
    public void collectionTest1() {
        List<Integer> list = Arrays.asList(1, 2, 6, 7, 11, 9, 10, 4);
        System.out.println("原始集合:" + list);

        // 有空元素放置到最后
        System.out.println("自然正序:" + Ordering.natural().nullsLast().sortedCopy(list));

        // 按照自然排序然后翻转, 有空元素排到最后
        System.out.println("自然逆序:" + Ordering.natural().reverse().nullsLast().sortedCopy(list));

        // 获取集合最大元素
        System.out.println("最大元素:" + Ordering.natural().max(list));

        // 获取集合元素中最大的3个元素
        System.out.println("最大元素:" + Ordering.natural().greatestOf(list, 3));

        // 获取集合最小元素
        System.out.println("最小元素:" + Ordering.natural().min(list));

        // 获取集合元素中最小的3个元素
        System.out.println("最小元素:" + Ordering.natural().leastOf(list, 3));

        //原始集合不会发生变化
        System.out.println("原始集合:" + list);

    }

    class User {
        private int age;
        private String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    @Test
    public void collectionTest2() {
        List<User> list = Arrays.asList(new User(1, "Abc"), new User(3, "BAb"), new User(2, "zbc"), new User(4, "fac"));
        System.out.println("原始集合:" + list);

        Ordering<User> natureAgeAscOrdering = Ordering.natural().nullsFirst().onResultOf(new Function<User, Integer>() {
            public Integer apply(User input) {
                return input.getAge();
            }
        });
        System.out.println("年龄升序:" + natureAgeAscOrdering.sortedCopy(list));

        Ordering<User> natureNameAscOrdering = Ordering.natural().nullsFirst().onResultOf(new Function<User, String>() {
            public String apply(User input) {
                return input.getName();
            }
        });
        System.out.println("姓名升序:" + natureNameAscOrdering.sortedCopy(list));

        //原始集合不会发生变化
        System.out.println("原始集合:" + list);

    }


    @Test
    public void collectionTest3() {

        //create a multiset collection
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("d");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("b");
        multiset.add("b");
        multiset.add("b");
        //print the occurrence of an element
        System.out.println("Occurrence of 'b' : " + multiset.count("b"));
        //print the total size of the multiset
        System.out.println("Total Size : " + multiset.size());
        //get the distinct elements of the multiset as set
        Set<String> set = multiset.elementSet();
        //display the elements of the set
        System.out.println("Set [");
        for (String s : set) {
            System.out.println(s);
        }
        System.out.println("]");
        //display all the elements of the multiset using iterator
        Iterator<String> iterator = multiset.iterator();
        System.out.println("MultiSet [");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("]");
        //display the distinct elements of the multiset with their occurrence count
        System.out.println("MultiSet [");
        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            System.out.println("Element: " + entry.getElement() + ", Occurrence(s): " + entry.getCount());
        }
        System.out.println("]");

        //remove extra occurrences
        multiset.remove("b", 2);
        //print the occurrence of an element
        System.out.println("Occurence of 'b' : " + multiset.count("b"));
    }


    @Test
    public void collectionTest4() {
        int[] intArray = {1,2,3,4,5,6,7,8,9};

        //convert array of primitives to array of objects
        List<Integer> objectArray = Ints.asList(intArray);
        System.out.println(objectArray.toString());

        //convert array of objects to array of primitives
        intArray = Ints.toArray(objectArray);
        System.out.print("[ ");
        for(int i = 0; i< intArray.length ; i++){
            System.out.print(intArray[i] + " ");
        }
        System.out.println("]");
        //check if element is present in the list of primitives or not
        System.out.println("5 is in list? "+ Ints.contains(intArray, 5));

        //Returns the minimum
        System.out.println("Min: " + Ints.min(intArray));

        //Returns the maximum
        System.out.println("Max: " + Ints.max(intArray));

        //get the byte array from an integer
       // byte[] byteArray = Ints.toByteArray(20000);
        byte[] byteArray = Ints.toByteArray(255);
        for(int i = 0; i< byteArray.length ; i++){
            System.out.print(byteArray[i] + " ");
        }

    }

    @Test
    public void collectionTest5() {
        Multimap<String,String> multimap = ArrayListMultimap.create();

        multimap.put("lower", "a");
        multimap.put("lower", "b");
        multimap.put("lower", "c");
        multimap.put("lower", "d");
        multimap.put("lower", "e");

        multimap.put("upper", "A");
        multimap.put("upper", "B");
        multimap.put("upper", "C");
        multimap.put("upper", "D");

        List<String> lowerList = (List<String>)multimap.get("lower");
        System.out.println("Initial lower case list");
        System.out.println(lowerList.toString());
        lowerList.add("f");
        System.out.println("Modified lower case list");
        System.out.println(lowerList.toString());

        List<String> upperList = (List<String>)multimap.get("upper");
        System.out.println("Initial upper case list");
        System.out.println(upperList.toString());
        upperList.remove("D");
        System.out.println("Modified upper case list");
        System.out.println(upperList.toString());

        Map<String, Collection<String>> map = multimap.asMap();
        System.out.println("Multimap as a map");
        for (Map.Entry<String,Collection<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            Collection<String> value =  multimap.get(key);
            System.out.println(key + ":" + value);
        }

        System.out.println("Keys of Multimap");
        Set<String> keys =  multimap.keySet();
        keys.forEach(System.out::println);

        System.out.println("Values of Multimap");
        Collection<String> values = multimap.values();
        System.out.println(values);

    }
}
