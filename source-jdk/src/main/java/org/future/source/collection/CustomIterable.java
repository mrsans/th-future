package org.future.source.collection;

import java.util.Iterator;
import java.util.Objects;

/**
 * 自定义迭代器，实现 Iterable中的iterator()方法
 * 创建一个Iterator对象，实现hasNext和next方法。hasNext判断是否有下一个，next是下一个
 * @author: zhpj
 * @date: 2022-03-04 13:33
 */
public class CustomIterable implements Iterable<Integer>{

    private int[] nums;

    private int point = -1;

    public CustomIterable(int[] nums) {
        if (Objects.isNull(nums)) {
            throw new NullPointerException("nums不能为空");
        }
        this.nums = nums;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return point < nums.length - 1;
            }

            @Override
            public Integer next() {
                point++;
                return nums[point];
            }
        };
    }

}
