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

ArrayList#Construct 构造器：

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

ArrayList#add()


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
    
}
```










### 

















