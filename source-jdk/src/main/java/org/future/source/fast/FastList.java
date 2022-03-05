package org.future.source.fast;

import java.util.Arrays;

/**
 * @author: zhpj
 * @date: 2022-03-05 14:30
 */
public class FastList<T>  {

    private Object[] ele ;

    private int pointer = 0;

    public FastList(int cap) {
        this.ele = new Object[cap];
    }

    public FastList() {
        ele = new Object[8];
    }

    public T add(T e) {
        if (pointer >= ele.length) {
            Object[] newEle = new Object[pointer + (pointer >> 1)];
            System.arraycopy(ele, 0, newEle, 0, ele.length);
            ele = newEle;
        }
        ele[pointer++] = e;
        return e;
    }

    public T set(int index, T e) {
        ele[index] = e;
        return e;
    }

    public T remove(int index) {
        Object[] newEle = new Object[this.pointer];
        System.arraycopy(ele, 0, newEle, 0, index);
        System.arraycopy(ele, index + 1, newEle, index, pointer - index);
        pointer--;
        ele = newEle;
        return (T)ele[index];
    }

    public T get(int index) {
        return (T)ele[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(ele);
    }
}
