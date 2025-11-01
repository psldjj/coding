package com.psl.jvm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Queue<String> queue = new LinkedList<>();

        queue.add("a");
        queue.poll();

        Stack<String> strings = new Stack<>();

        System.out.println( "Hello World!" );
    }
}
