package org.future.source.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author: zhpj
 * @date: 2022-03-04 13:32
 */
@Slf4j
public class TestCollection {


    @Test
    public void testCollection() {

        Collection

    }

    /**
     * 测试自定义迭代器
     */
    @Test
    public void testCustomIterable() {
        CustomIterable iterable = new CustomIterable(new int[]{1, 2, 3, 4, 7});
        Iterator<Integer> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            log.info("获取到的数据为：{}", next);
        }
    }


}
