package org.future.source.list;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhpj
 * @date: 2022-03-04 19:43
 */
@Slf4j
public class TestArrayList {

    @Test
    public void aa() {
        List<String> list = new ArrayList<>();
        list.add("123");
        System.out.println(list.get(1));
    }

    // remove删除对应索引位置的对象
    @Test
    public void testRemove_2() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        list.remove(2);
        log.info("remove删除对应索引位置的对象：{}", list);
    }

    // 移除集合中一个对象，多个重复的，只删除查询到的第一个
    @Test
    public void testRemove_1() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        list.remove("123");
        log.info("remove删除一个对象：{}", list);
    }

    // 清除集合所有元素
    @Test
    public void testClear() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        list.clear();
        log.info("clear清除集合所有元素：{}", list);
    }

    // 集合变更固定位置元素
    @Test
    public void testSet() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        list.set(2, "77777");
        log.info("set修改固定位置的元素：{}", list);
    }

    // 判断集合是否没有元素，如果有元素返回false，没有元素返回true
    @Test
    public void testIsEmpty() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        log.info("isEmpty判断集合是否没有元素：{}", list.isEmpty());
    }

    // 判断集合中是否包含另一个集合所有元素
    @Test
    public void testContainsAll() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        List<String> tempList = new ArrayList<>();
        tempList.add("123");
        tempList.add("9723e9");
        list.containsAll(tempList);
        log.info("containsAll是否包含另一个集合所有元素：{}", list.containsAll(tempList));
    }

    // 判断集合中是否包含了 参数对象元素，如果包含返回true，否则返回false
    @Test
    public void testContains() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        log.info("contains是否包含元素：{}", list.contains("4576"));
    }

    // 查询集合中的元素索引位置 倒叙
    @Test
    public void testLastIndexOf() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        log.info("lastIndexOf查询到的元素为：{}", list.lastIndexOf("123"));
    }

    // 查询集合中的元素索引位置
    @Test
    public void testIndexOf() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        log.info("indexOf查询到的元素为：{}", list.indexOf("4576"));
    }

    // 查询集合中的元素
    @Test
    public void testGet() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        log.info("get查询到的元素为：{}", list.get(2));
    }

    // 测试集合添加元素
    @Test
    public void testAdd() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        log.info("data：{}", list);
    }

    // 向集合固定位置添加一个元素
    @Test
    public void testAddEle() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("10");
        list.add(1, "888");
        log.info("AddEle添加的结果为：{}", list);
    }

    @Test
    public void testAddAll() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        list.add("123");
        List<String> tempList = new ArrayList<>();
        tempList.add("new-temp-1");
        tempList.add("new-temp-2");
        list.addAll(tempList);
        log.info("data：{}", list);
        list.addAll(1, tempList);
        log.info("data：{}", list);
    }

    @Test
    public void testConstruct() {
        // 创建一个ArrayList对象 没有任何元素
        List<String> list_1 = new ArrayList<>();
        // 创建有一定空间的对象
        List<String> list_2 = new ArrayList<>(12);
        // 传递一个集合，创建一个将此参数集合平铺的初始化集合
        List<String> tempList = new ArrayList<>();
        tempList.add("123");
        tempList.add("1234");
        List<String> list_3 = new ArrayList<>(tempList);

        log.info("无参数构造函数, size:{}, data:{}", list_1.size(), list_1);
        log.info("创建一个有初始化容量的集合, size:{}, data:{}", list_2.size(), list_2);
        log.info("创建一个有初始化元素的集合, size:{}, data:{}", list_3.size(), list_3);
    }


}
