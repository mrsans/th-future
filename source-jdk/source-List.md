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

java#ArrayList#add方法示例
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
}
```

#### ArrayList 查询

##### ArrayList#get

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



ArrayList的get方法示例：
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
}
```









### 

















