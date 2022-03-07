## 反射

反射是拿到类的字节码文件，然后操作这个类，方法，字段等

### 字节码文件的获取

 1. TestClass.class 类.class 
 2. 创建对象后，对象.getClass()
 3. Class.forName("类的全路径包名")

```java
 @Slf4j
public class TestReflect {
    // 反射的几种方式
    @Test
    public void testGetReflect() throws ClassNotFoundException {
        Class<TestClass> clazz_1 = TestClass.class;
        TestClass innerTestClass = new TestClass();
        Class<? extends TestClass> clazz_2 = innerTestClass.getClass();
        Class<?> clazz_3 = Class.forName("org.future.source.reflect.TestReflect");
        log.debug("{},{},{}", clazz_1, clazz_2, clazz_3);
    }
}
```

### 反射的获取方法









