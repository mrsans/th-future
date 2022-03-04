## Java-Collection

java的集合接口Collection

Collection继承了Iterable迭代器

```java
    // Iterable迭代器是一个接口，其中有三个方法 
    Iterator<T> iterator();
    default void forEach(Consumer<? super T> action)
    default Spliterator<T> spliterator()
```
迭代器示例如下：
```java
/**
 * 自定义迭代器，实现 Iterable中的iterator()方法
 * 创建一个Iterator对象，实现hasNext和next方法。hasNext判断是否有下一个，next是下一个
 * @author: zhpj
 * @date: 2022-03-04 13:33
 */
public class CustomIterable implements Iterable<Integer>{

    private int[] nums ;

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
```

### Collection API

```java
public interface Collection<E> extends Iterable<E> {
    
    // 集合的大小
    int size();
    // 是否为空
    boolean isEmpty();
    // 是否包含
    boolean contains(Object o);
    // 迭代器
    Iterator<E> iterator();
    // 转换成数组
    Object[] toArray();
    <T> T[] toArray(T[] a);
    // 添加元素
    boolean add(E e);
    // 删除元素
    boolean remove(Object o);
    // 是否包含所有的元素
    boolean containsAll(Collection<?> c);
    
    //
    boolean addAll(Collection<? extends E> c);
}
```



