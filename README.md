# PageHelperEnhance

这个组件主要是基于[Mybatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)组件封装了分页查询全部数据以及边查询边操作的生产者消费者模式。

## 代码示例
使用这个组件你可以很优雅的写出一些分页查询和分页操作的代码。

```java
public class Demo {

    private Mapper mapper;

    public void method1() {
        // 分页查询所有数据，以避免单条sql查询数据量过大而导致数组过大且无法被回收时候引发GC overhead limit exceeded错误
        List<Object> allObjects = PageHelperEnhance.executeQuery(mapper::selectAll);
    }

    public void method2() {
        PageHelperEnhance.executeService(mapper::selectAll, list -> {
            // 优雅的分页处理数据，默认页码会自增
        });
    }

    public void method3() {
        PageHelperEnhance.executeService(1, 1000, new AlwaysPageNumberStrategy(), mapper::selectAll, list -> {
            // 仍然是优雅的分页处理数据，但每次都读取第一页的数据，适用于边查边操作，且操作会导致查询数据减少的情况
        });
    }

    public interface Mapper {

        // 这是模拟一个mapper的查询方法
        List<Object> selectAll();

    }

}
```

## maven引入

已发布到maven中央仓库：https://central.sonatype.com/artifact/com.baofeidyz/PageHelperEnhance/

```xml
<dependency>
    <groupId>com.baofeidyz</groupId>
    <artifactId>PageHelperEnhance</artifactId>
    <version>1.0.0</version>
</dependency>
```