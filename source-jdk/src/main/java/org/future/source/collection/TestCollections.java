package org.future.source.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Collections 帮助类
 * @author: zhpj
 * @date: 2022-03-04 18:11
 */
@Slf4j
public class TestCollections {

    @Test
    public void testSingletonXxx() {
        List<String> list = Collections.singletonList("123");
        log.info("{}", list);
    }

    @Test
    public void testEmptyCollection() {
        List<String> list = Collections.emptyList();
        log.info("{}", list);
    }


    @Test
    public void testCheckedCollection() {
        List list = new ArrayList<>();
        list.add("1s");
        list.add(1);
        list.add("3");
        list.add("5");
        List<String> checkedList = Collections.checkedList(list, Integer.class);
        checkedList.add("2c");
        log.info("{}" , checkedList);
    }

    @Test
    public void testSynchronizedCollection() throws InterruptedException {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("3");
        list.add("4");
        List<String> synchronizedList = Collections.synchronizedList(list);
        for (int i = 0; i < 100; i++) {
            add(synchronizedList, i);
        }
        TimeUnit.SECONDS.sleep(5);
        log.info("获取到的同步值为：{}", synchronizedList);
    }

    public void add(List<String> synchronizedList, int num) {
        new Thread(() -> {
            synchronizedList.add("newS-" + num);
        }).start();
    }

    @Test
    public void testUnmodifiableCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("7");
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        String s = unmodifiableList.get(0);
        log.info("获取到的值：{}", s);
        unmodifiableList.set(0, "newstring");
    }

}
