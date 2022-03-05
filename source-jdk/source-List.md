## java List 集合源码

jdk1.2中新增，位于java.util包中
```java
package java.util;
```

List是一个接口，主要的实现类有以下几个

1. ArrayList
2. LinkedList
3. Vector
4. Arrays.asList(Object[])
5. AbstractList
6. AbstractSequentialList

这里主要说ArrayList、LinkedList以及Vector

### ArrayList

#### ArrayList#Construct 构造器：

1. 无参构造器： 可以看出，当我们创建一个对象的时候，就是一个空对象，没有初始容量，即：创建一个空的元素的数组

```java
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
```

2. 含参数构造器：创建一个有初始化容量的数组，如果设置的容量大于 0， 那么声明一个你给的固定容量长度的数组，如果等于0，那么创建一个元素为
空的数组，如果小于0，那么抛出非法参数异常

```java
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+ nitialCapacity);
        }
    }
```

3. 创建一个带有集合参数的集合,将参数集合转成数组，因为此集合底层就是数组，然后判断此数组长度是否等于0，如果等于0，那么创建一个空的数组对象，
如果不等于0，那么采用Arrays.copyOf()方法进行数组拷贝，此方法的底层是System.arraycopy(),速度更快
```java
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }
```

构造器举例：

```java
@Slf4j
public class TestArrayList {
    // 构造函数
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
```
### ArrayList#添加方法

##### ArrayList#add()

向集合中添加数据

```java
    public boolean add(E e) {
        // 判断  size + 1 是否超过了长度，size为当前的数组长度，如果超过长度，那么进行增长
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        // 向数组中添加元素
        elementData[size++] = e;
        // 添加成功返回true
        return true;
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private static final int DEFAULT_CAPACITY = 10;
    // 计算容量    当前数组元素     当前数组.length + 1 的容量
    // 如果数组元素是空元素，那么取值为 10 和 minCapacity 最大值作为容量
    // 如果数组元素不为空，那么直接返回 参数 minCapacity 的容量
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
      if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
         return Math.max(DEFAULT_CAPACITY, minCapacity);
      }
      return minCapacity;
    }
    
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        // 如果 minCapacity 的容量 已经超过 数组长度，那么进行扩容，负责什么都不做
        if (minCapacity - elementData.length > 0)
            // 开始扩容
            grow(minCapacity);
    }
    /**
     * The maximum size of array to allocate. 
     * Some VMs reserve some header words in an array. 
     * Attempts to allocate larger arrays may result in OutOfMemoryError:
     * Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        // 新的容量   旧的容量 + 旧的容量右移一位 即： 当前容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        // 如果新的容量 小于  传递的参数容量
        if (newCapacity - minCapacity < 0)
        // 那么赋值成参数容量
            newCapacity = minCapacity;
        // 如果超过int的最大值，那么直接扩容到最大值减 8 ；减 8 因为JVM内存分配对象头的原因
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 进行数组的拷贝
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```
##### ArrayList#addAll(Collection<? extends E> c)

传递一个集合参数，不能为null，否则抛出空指针异常，可以看出，获取参数集合的长度+当前有元素的数组的长度，如果长度不够那么进行扩容，
扩容为1.5倍扩容，然后采用System.arraycopy拷贝的形式将参数集合复制到新的集合中。
```java
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);  // Increments modCount
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }
```

##### ArrayList#add(int index, E element)

此方法会首先检查index是否大于size，或者小于0，如果满足，那么抛出异常，索引越界异常，然后判断是否需要进行扩容，因为只添加了一个元素，所以+1即可，
然后进行数据拷贝，因为涉及到数组移动，先将原数组的参数index位置及以后的数据后移，然后将index位置插入 element 元素即可

```java
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);  
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }
```

##### ArrayList#addAll(int index, Collection<? extends E> c)
此方法会优先检查index索引是否超过了数组有元素的长度或Index<0，那么抛出IndexOutOfBoundsException异常，索引越界
```java
    public boolean addAll(int index, Collection<? extends E> c) {
        // 检查索引是否超过含有元素的数组长度，或者小于0，如果符合，那么抛出 索引月结异常
        rangeCheckForAdd(index);
        Object[] a = c.toArray();
        int numNew = a.length;
        // 判断数组是否扩容
        ensureCapacityInternal(size + numNew); 
        int numMoved = size - index;
        // 如果 从当前集合中间插入参数集合，那么需要进行移动
        if (numMoved > 0)
        //  进行数组的拷贝,拷贝的长度就是移动的长度，将原数组进行后移
            System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
        // 将参数数组添加到原数组中
        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }
```

java#ArrayList#添加方法示例

```java

@Slf4j
public class TestArrayList {
    // 测试集合添加元素
    @Test
    public void testAdd() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        log.info("data：{}", list);
    }
    // 集合添加参数集合
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
}
```

#### ArrayList 查询

##### ArrayList#get(int index)

查询集合中的元素：首先判断index索引是否超过了当前数组的长度，如果超过了，那么抛出IndexOutOfBoundsException异常
如果没超过，那么直接返回数组对应的下标元素

```java
    public E get(int index) {
        // 查看元素是否大于当前数组，如果大于那么抛出异常
        rangeCheck(index);
        return elementData(index);
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    
    E elementData(int index) {
        return (E) elementData[index];
    }
```

##### ArrayList#indexOf(Object o)

indexOf方法传递一个对象，首先判断参数对象是否是null，如果是null，进行循环查找，如果不为null，那么通过 参数对象.equals进行判断是否相同，
如果没有查到，那么直接返回 -1

```java
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }
```

##### ArrayList#lastIndexOf(Object o)

lastIndexOf方法传递一个对象，和indexOf方法一致，只不过是倒叙查询

```java
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }
```

##### ArrayList#contains(Object o)

此方法判断是否包含某个元素，如果包含，返回true，如果不包含返回false，源码和 indexOf 一致

```java
public boolean contains(Object o) {
    return indexOf(o) >= 0;
}
```

##### ArrayList#containsAll(Collection<?> c)

此方法判断集合中是否包含另一个集合所有元素，如果包含返回true，否则返回false。可以看到，循环调用自身的 contains方法，如果有一个不包含，那么直接返回false

```java
public boolean containsAll(Collection<?> c) {
    for (Object e : c)
        if (!contains(e))
            return false;
    return true;
}
```
##### ArrayList#isEmpty()

判断集合是否没有元素，如果没有元素返回true，有元素返回false,源码是查看数组的size是否为0

```java
public boolean isEmpty() {
    return size == 0;
}
```

ArrayList的查询方法示例：
```java
@Slf4j
public class TestArrayList {

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

    // 查询集合中的元素索引位置倒叙查询
    @Test
    public void testLastIndexOf() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("4576");
        list.add("9723e9");
        list.add("9723e9324");
        log.info("lastIndexOf查询到的元素为：{}", list.lastIndexOf("123"));
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
        log.info("contains是否包含元素：{}", list.containsAll(tempList));
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
}
```

### ArrayList#集合元素的修改

#### ArrayList#set(int index, E element)

此方法优先查看index的值的范围，如果越界，那么抛出索引越界异常，负责取出旧的值，然后将新值替换，并返回旧值

```java
public E set(int index, E element) {
    rangeCheck(index);
    E oldValue = elementData(index);
    elementData[index] = element;
    return oldValue;
}
```

```java
 @Slf4j
public class TestArrayList {

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
}
```
#### ArrayList#集合的删除

##### ArrayList#clear()

从clear中可以看出，此方法是循环清空的。最后将size置为0

```java
public void clear() {
    modCount++;
    for (int i = 0; i < size; i++)
        elementData[i] = null;
    size = 0;
}
```

##### ArrayList#remove(Object o)
此方法分为两步进行删除对象，如果对象为null，那么循环判断null，并取到符合的索引，如果不为null，那么调用对象的equals方法，进行循环查询，如果查到了，
那么取到对应的索引，然后传递给fastRemove(index)方法，进行删除。fastRemove方法可以看出，数组先进行拷贝，将原来的位置数据覆盖，然后最后一位 size - 1
的位置数据置为null，然后size - 1 即可。
```java
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false;
}

private void fastRemove(int index) {
    modCount++;
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,numMoved);
    elementData[--size] = null; // clear to let GC do its work
}
```

##### ArrayList#remove(Object o)

此方法可以看出先判断index索引是否越界，如果越界，那么抛出异常，然后取到对应位置的旧数据，然后数组拷贝，将原来的位置数据覆盖，最后一个位置置为null，
然后最后一位 size - 1

```java
public E remove(int index) {
    rangeCheck(index);
    modCount++;
    E oldValue = elementData(index);
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,  numMoved);
    elementData[--size] = null; // clear to let GC do its work
    return oldValue;
}
```
#### ArrayList#removeAll(Collection<?> c)

删除参数集合中的所有元素，可以看出是迭代删除，使用的是迭代器

```java
public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
        if (c.contains(it.next())) {
        it.remove();
        modified = true;
        }
        }
        return modified;
}
```

```java
@Slf4j
public class TestArrayList {
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
    // 删除参数集合中的所有元素，如果集合中有重复数据，依然将重复数据删除
    @Test
    public void testRemoveAll() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        list.add("1234");
        list.add("1235");
        List<String> tempList = new ArrayList<>();
        tempList.add("123");
        tempList.add("1235");
        list.removeAll(tempList);
        log.info("得到的List集合为：{}", list);
    }
}
```
```java
    
```

### ArrayList总结

1. arrayList初始化创建对象，如果没有分配数组的容量，那么默认是暂时不分配的，只不过是创建了一个数组对象，当我们调用添加操作的时候，才会初始化容量
2. arrayList的clear方法，并不是立刻清空的，是遍历清空的，它的目的是防止对象地址变更，如果集合足够庞大，且此集合后期可以进行变更，那么可以将变量重新分配，即：new ArrayList()性能更好
3. arrayList只要带有索引参数的，都会去判断是否越界，检查越界的两个方法一个是：rangeCheck()检查是否大于等于size，如果是抛异常，另一个是rangeCheckForAdd()此方法判断是否大于size或者小于0，如果是抛出索引越界异常
4. arrayList调用remove(Object obj)的时候删除的是一个对象
5. arrayList的fastRemove(int index)方法和 remove(int index) 一样，只不过remove方法有返回值，而fastRemove没有返回值
6. arrayList的indexOf和lastIndexOf一个是从前向后找，一个是从后向前找，可以用适合的方法，进行快速找到对应的数据
7. 

### LinkedList





### Vector

### Arrays.asList(Object[])

















