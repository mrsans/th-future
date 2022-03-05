package org.future.source.linkedlist;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: zhpj
 * @date: 2022-03-05 9:28
 */
@Slf4j
public class TestLinkedList {

    // 设置集合固定位置的元素
    @Test
    public void testSet() {
        List<String> list = new LinkedList<>();
        list.add("123");
        list.add("456");
        list.set(1, "798");
        log.info("list集合元素为：{}", list);
    }

    // 获取固定位置的元素
    @Test
    public void testIndexOf() {
        List<String> list = new LinkedList<>();
        list.add("456");
        list.add("123");
        log.info("获取固定位置元素:{}", list.indexOf("123"));
        log.info("倒叙获取固定位置的元素：{}", list.lastIndexOf("456"));
    }

    // 测试LinkedList的get方法
    @Test
    public void testGet() {
        List<String> list = new LinkedList<>();
        list.add("123");
        list.add("456");
        list.add("789");
        log.info("list查到的数据为：{}", list.get(1));
    }

    // 将参数集合添加到 LinkedList中
    @Test
    public void testAddAll_4() {
        List<String> list = new LinkedList<>();
        List<String> tempList= new ArrayList<>();
        tempList.add("123");
        list.addAll(0, tempList);
        log.info("新的集合元素为：{}", list);
    }

    // 将参数集合添加到 LinkedList中
    @Test
    public void testAddAll_3() {
        List<String> list = new LinkedList<>();
        List<String> tempList= new ArrayList<>();
        tempList.add("123");
        list.addAll(tempList);
        log.info("新的集合元素为：{}", list);
    }

    // 添加元素带有索引位置
    @Test
    public void testAdd_2() {
        List<String> list = new LinkedList<>();
        list.add(0, "234");
        list.add(1, "234");
        log.info("add方法添加元素，新的集合为：{}", list);
    }

    // 测试linkedlist的添加方法
    @Test
    public void testAdd_1() {
        List<String> list = new LinkedList<>();
        list.add("123");
        list.add("1234");
        list.add("12345");
        log.info("linkedList添加后的结果为：{}", list);
    }

    @Test
    public void testConstruct() {
        List<String> list_1 = new LinkedList<>();
        List<String> tempList =new LinkedList<>();
        tempList.add("new-1");
        tempList.add("new-2");
        List<String> list_2 = new LinkedList<>(tempList);
        list_2.add("456");
        list_1.add("!23");
        log.info("获取到的集合为：{}", list_2);
    }


}
