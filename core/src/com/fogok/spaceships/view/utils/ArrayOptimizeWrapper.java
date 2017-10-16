package com.fogok.spaceships.view.utils;

import java.lang.reflect.Array;

public class ArrayOptimizeWrapper<T> {

    private T[] array;
    private int size;
    private int maxSize;

    public ArrayOptimizeWrapper(Class<T> c, int size) {
        this.size = 0;
        @SuppressWarnings("unchecked")
        final T[] a = (T[]) Array.newInstance(c, size);
        this.array = a;
    }

    public void add(T element){
        array[size] = element;
        size++;
    }

    public T get(int i) {
        return array[i];
    }

    public void remove(int i){
        array[i] = array[size - 1];
        array[size - 1] = null;
        size--;
    }

    public int getSize() {
        return size;
    }

    //    public static void main(String[] args) {
//        ArrayOptimizeWrapper<Integer> s = new ArrayOptimizeWrapper<Integer>(Integer.class, 10);
//        s.add(10);
//        s.add(11);
//        s.add(12);
//        s.add(13);
//        s.remove(s.size - 1);
//        s.remove(s.size - 1);
//        s.remove(s.size - 1);
//    }     //test and example

}
