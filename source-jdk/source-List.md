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

#### LinkedList 构造器

##### LinkedList#LinkedList()
##### LinkedList#LinkedList(Collection<? extends E> c)

有参构造器，将有参数的集合追加到LinkedList上。

```java
public LinkedList() {}
public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
}
public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
}
public boolean addAll(int index, Collection<? extends E> c) {
        checkPositionIndex(index);
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
        return false;
        Node<E> pred, succ;
        if (index == size) {
            succ = null;
            pred = last;
            } else {
            succ = node(index);
            pred = succ.prev;
        }
        for (Object o : a) {
        Node<E> newNode = new Node<>(pred, e, null);
        if (pred == null)
            first = newNode;
            else
            pred.next = newNode;
            pred = newNode;
        }
        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }
        size += numNew;
        modCount++;
        return true;
}
```
```java
@Slf4j
public class TestLinkedList {

    @Test
    public void testConstruct() {
        List<String> list_1 = new LinkedList<>();
        List<String> tempList = new LinkedList<>();
        tempList.add("new-1");
        tempList.add("new-2");
        List<String> list_2 = new LinkedList<>(tempList);
        list_2.add("456");
        list_1.add("!23");
        log.info("获取到的集合为：{}", list_2);
    }
}
```

#### LinkedList添加函数

##### LinkedList#add(E e)

linkedList的add方法，追加一个元素到linkedlist的末尾，首先创建一个内部类对象Node节点，将当前元素的最后一个节点和数据元素传递过去，传递过去后，其prev指向
原来的last节点，然后last节点指向新的节点，如果当前linkedList的最后一个节点是null，那么此集合是空元素集合，第一个节点指向此Node，如果不是空元素集合，
那么原集合的next节点指向新的node节点

```java
public boolean add(E e) {
        linkLast(e);
        return true;
}

void linkLast(E e) {
    final Node<E> l = last;
    // 创建一个node元素，此元素的前一个prev节点，指向原集合的last节点
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    // 如果last节点是null，那么就是一个空元素集合，第一个元素就是新node
    if (l == null)
        first = newNode;
    else
        // 不是空元素集合，那么 最后一个节点的下一个节点就是 新node
        l.next = newNode;
    size++;
    modCount++;
 }
```

##### LinkedList#add(int index, E element)

LinkedList添加元素，index索引位置，E element 数据元素，首先检测元素的位置索引，如果位置索引小于0且大于当前长度，那么抛出异常。否则判断追加的位置，
如果是最后一个元素，那么直接将last节点和next节点赋值成新的node，否则 linkBefore 优先寻找节点所在的位置，然后在对应的位置将前一个元素的next节点指向
新的节点，后一个元素的prev节点指向新的node即可。

```java
public void add(int index, E element) {
        checkPositionIndex(index);
        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
}

private void checkPositionIndex(int index){
        if(!isPositionIndex(index))
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
}
private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
}

void linkLast(E e) {
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else 
        l.next = newNode;
    size++;
    modCount++;
}

void linkBefore(E e, Node<E> succ) {
    final Node<E> pred = succ.prev;
    final Node<E> newNode = new Node<>(pred, e, succ);
    succ.prev = newNode;
    if (pred == null)
        first = newNode;
    else
        pred.next = newNode;
    size++;
    modCount++;
}
```

##### LinkedList#addAll(Collection<T> c) 和 addAll(int index, Collection<T> c)

从源码中可以看出，addAll(Collection<T> c) 调用的是 addAll(int index, Collection<T> c) 的方法，同样，addAll会优先检查索引是否越界，检查完成后，
检查 参数集合的数组长度，如果数组长度为0， 那么不添加到linkedlist中，如果index参数和size参数一致，那么只有一个元素，否则，开始追加添加到node节点

```java
public boolean addAll(int index, Collection<? extends E> c) {
        checkPositionIndex(index);
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
        return false;
        Node<E> pred, succ;
        if (index == size) {
            succ = null;
            pred = last;
        } else {
            succ = node(index);
            pred = succ.prev;
        }
        for (Object o : a) {
            Node<E> newNode = new Node<>(pred, e, null);
            if (pred == null)
            first = newNode;
            else
            pred.next = newNode;
            pred = newNode;
        }
        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }
        size += numNew;
        modCount++;
        return true;
   }
```

```java
@Slf4j
public class TestLinkedList {

    // 测试linkedlist的添加方法
    @Test
    public void testAdd() {
        List<String> list = new LinkedList<>();
        list.add("123");
        list.add("1234");
        list.add("12345");
        log.info("linkedList添加后的结果为：{}", list);
    }
    // 添加元素带有索引位置
    @Test
    public void testAdd_2() {
        List<String> list = new LinkedList<>();
        list.add(0, "234");
        list.add(1, "234");
        log.info("add方法添加元素，新的集合为：{}", list);
    }
    // 将参数集合添加到 LinkedList中
    @Test
    public void testAdd_3() {
        List<String> list = new LinkedList<>();
        List<String> tempList= new ArrayList<>();
        tempList.add("123");
        list.addAll(tempList);
        log.info("新的集合元素为：{}", list);
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
}
```




### Vector

### Arrays.asList(Object[])

















