package com.galaxy.guava.test;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author wang.baozhi
 * @since 2019/10/18 下午2:10
 *
 * https://stackoverflow.com/questions/3621067/why-is-the-range-of-bytes-128-to-127-in-java
 */
public class BitUnitTest {



    public void printBit(byte b){
        StringBuffer sb=new StringBuffer();
        for(int k=0;k<=7;k++){
            int n = (b >> k) & 1;
            sb.append(n);
        }
        System.out.println(sb.reverse());
    }



    @Test
    public void intTypeTest() {
        int i = -1;
        StringBuffer sb=new StringBuffer();
        for(int k=0;k<=31;k++){
            int n = (i >> k) & 1;
            sb.append(n);
        }
        System.out.println(sb.reverse());
    }


    @Test
    public void byteTypeTest2() {
        byte b = -1;
        printBit(b);//11111111
        System.out.println((byte)(b<<4));//-16
        printBit((byte)(b<<4));//11110000
    }


    @Test
    public void byteTypeTest() {
        byte i = 127;
        byte j = -128;

        byte signI = (byte)( (i >> 7) & 1);
        byte signJ = (byte)( (j >> 7) & 1);
       // byte signJ = (j >> 7) & 1;


        StringBuffer sb1=new StringBuffer();
        for(int k=0;k<=7;k++){
            int m = (i >> k) & 1;
            sb1.append(m);
        }
        System.out.println(sb1.reverse());


        StringBuffer sb2=new StringBuffer();
        for(int k=0;k<=7;k++){
            int n = (j >> k) & 1;
            sb2.append(n);
        }
        System.out.println(sb2.reverse());

        //在java虚拟机中整数有byte、short、int、long四种 分别表示 8位、16位、32位、64位有符号整数。整数使用补码表示。
        //127 补码表示0111 1111，它的原码、反码、补码相同.当使用有符号右移运算符">>"，右移7位后，得到127的符号位0
        assertEquals(0,signI);

        //-128 补码表示1000 0000，它的原码1000 0000、反码1111 1111、补码1000 0000.
        // 当使用有符号右移运算符">>"，右移7位后，得到-128的符号位1
        assertEquals(1,signJ);
    }

}