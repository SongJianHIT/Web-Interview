# 【八股】JAVA基础类

## 1 包装类

### 1.1 有了基础数据类型，为什么还需要包装类？

在 JAVA 中，有 8 种常见的基础数据类型，分别为 `boolean, byte, short, int, long, float, double, char`，他们都分别有自己的包装类型对应：`Boolean, Byte, Short, Integer, Long, Float, Double, Character`。

之所以需要包装类，是因为

- **JAVA 是一个面向对象的语言**，而 **基本数据类型不具备面向对象的特性**，当我们把基本数据类型包装成包装类型之后，它就具有了面向对象的特性。
- 同时，在使用 `ArrayList、HashMap` 等容器时，基本类型 `int` 和 `double` 是传输不进去的，因为 **容器都是装 object 类型的**，所以需要转为包装类型进行传输。

基本数据类型与包装类之间通过 **自动装箱和自动拆箱** 进行转换。

### 1.2 自动装箱与自动拆箱是如何进行的？

**装箱**：在包装类中，存在一个 **静态方法** `valueOf()`，该方法接收基本数据类型，将其转化为对应的包装类。如，

```java
// 布尔类
boolean b1 = false;
Boolean bObj = Boolean.valueOf(b1);

// 整型类
int i1 = 1;
Integer iObj = Integer.valueOf(i1);
```

**拆箱**：在包装类中，存在 **实例方法** `xxxValue()`，返回对应的基本数据类型。如，

```java
// 布尔类
boolean b1 = false;
Boolean bObj = Boolean.valueOf(b1);
boolean b2 = bObj.booleanValue();

// 整型类
int i1 = 1;
Integer iObj = Integer.valueOf(i1);
int i2 = iObj.intValue();
```

自动装箱/拆箱是 **JAVA 编译器提供的能力**，背后，他会被替换为 `valueOf() / xxxValue()` 方法。

### 1.3 享元模式的考察

```java
Integer a1 = 100;
Integer a2 = 100;
判断 a1 == a2 的返回值？
```

首先看运行结果：

![image-20230210202951346](https://p.ipic.vip/xv5hcq.png)

这是因为在 `Integer` 内部的设计中，使用了 **享元模式** 的设计。它的核心思想是 **通过复用对象，减少对象的创建数量，从而减少内存占用，提升性能。** `Integer` 内部中有一个 `IntegerCache`，它缓存了 `[-128, 127]` 对应的 `Integer` 类型。一旦程序调用了 `valueOf()` 方法，就直接从 cache 中获取 `Integer` 对象，否则就创建一个新的对象。

回到此题，两个 `Integer` 对象，因为值都是 100，并且默认通过装箱机制调用了 `valueOf()` 方法，从 `IntegerCache` 中拿到了两个完全相同的 `Integer` 实例，因此为 `true`。

> 注意：
>
> `Byte`,`Short`,`Integer`,`Long` 这 4 种包装类默认创建了数值 **[-128，127]** 的相应类型的缓存数据，`Character` 创建了数值在 **[0,127]** 范围的缓存数据，`Boolean` 直接返回 `True` or `False`。同时，`Float` 和 `Doulbe` 没有缓存。

同时需要注意，如果不走 `valueOf()` 方法（或者自动装箱），而是使用 `new Integer()` 来创建对象，结果就会有所区别：

![image-20230211194231336](https://p.ipic.vip/aru3ls.png)

### 1.4 超过 long 整型的数据应该如何表示？

基本数值类型都有一个表达范围，如果超过这个范围就会有数值溢出的风险。

在 Java 中，64 位 long 整型是最大的整数类型。

```java
long l = Long.MAX_VALUE;
System.out.println(l + 1); // -9223372036854775808
System.out.println(l + 1 == Long.MIN_VALUE); // true
```

`BigInteger` 内部使用 `int[]` 数组来存储任意大小的整形数据。相对于常规整数类型的运算来说，`BigInteger` 运算的效率会相对较低。

### 1.5 如何解决浮点数运算的精度丢失问题？

《阿里巴巴 Java 开发手册》中提到：**“为了避免精度丢失，可以使用 `BigDecimal` 来进行浮点数的运算”。**

`BigDecimal` 可以 **实现对浮点数的运算，不会造成精度丢失。**通常情况下，大部分需要浮点数精确运算结果的业务场景（比如涉及到钱的场景）都是通过 `BigDecimal` 来做的。

```java
BigDecimal a = new BigDecimal("1.0");
BigDecimal b = new BigDecimal("0.9");
BigDecimal c = new BigDecimal("0.8");

BigDecimal x = a.subtract(b);
BigDecimal y = b.subtract(c);

System.out.println(x); /* 0.1 */
System.out.println(y); /* 0.1 */
System.out.println(Objects.equals(x, y)); /* true */
```

https://javaguide.cn/java/basis/bigdecimal.html#bigdecimal-%E7%AD%89%E5%80%BC%E6%AF%94%E8%BE%83%E9%97%AE%E9%A2%98

## 2 Object类

![image-20230210202540001](https://p.ipic.vip/cno98c.png)

常考的就是 `equals()` 和 `hashcode()`

### 2.1 重写equals()方法就可以比较两个对象是否相等，为什么还要重写hashcode()方法呢？

1、**提高效率**

因为散列集合如 `HashSet` 、`HashMap` 底层在添加元素时，会先判断对象的 `hashCode` 是否相等，如果 `hashCode` 相等才会用 `equals()` 方法比较是否相等。换句话说，`HashSet` 和 `HashMap` 在判断两个元素是否相等时，会先判断 `hashCode` ，如果两个对象的 `hashCode` 不同则必定不相等。

采取重写 `hashcode()` 方法，先进行 `hashcode` 比较，如果不同，那么就没必要在进行 `equals()` 的比较了，这样就大大减少了 `equals()` 比较的次数。

![image-20230210202435039](https://p.ipic.vip/9o53sh.png)

2、**为了保证同一个对象在 `equals()` 相同的情况下 `hashcode` 值必定相同**

如果重写了 `equals()` （修改了逻辑相等）而未重写 `hashcode()` 方法，可能就会出现两个没有关系的对象 `equals()` 相同（**因为equal都是根据对象的特征进行重写的**），但 `hashcode` 确实不相同。这就导致：此时 `equals` 比较是相同的，但是 `hashmap` 内部却仍然认为这是两个对象，导致运行结果和我们期望的不符。

> **【答案】**答：如果只重写 `equals()` 方法，不重写 `hashCode()` 方法，就可能导致 `a.equals(b)` 表达式成立，但是 `hashCode` 却不相同。那么这个只重写了 `equals()` 方法的对象，在使用 **散列集合** 进行存储的时候，就会出现问题。因为散列集合是先使用 `hashCode` 进行对象比较。如果两个完全相同的对象，但是却有不同的 `hashCode`，就会 **导致这两个相同的对象存储在哈希表的不同位置**，这结果与我们的期望不符。

### 2.2 equals() 与 == 之间的区别？

`equals()` 是 `Object` 类中定义的方法，它的默认实现是比较两个 `Object` 对象的地址，默认实现是用 `==`。

```java
public boolean equals(Object obj) {    
	return (this == obj);
}
```

Java 中包括了 **基本数据类型** 和 **引用数据类型**，`==`在这两个类型中作用是不一样的。

- 基本数据类型：比较的是`==`两边值是否相等
- 引用数据类型：比较的是`==`两边内存地址是否相等

重写 `equals()` 方法需要保证一下原则：

- **自反性**：对于任何对象 `x`，`x.equals(x)` 应该返回 `true`。
- **对称性**：对于任何两个对象 `x` 和 `y`，如果 `x.equals(y)` 返回 `true`，那么 `y.equals(x)` 也应该返回 `true` 。
- **传递性**：对于多个对象 `x,y,z`，如果 `x.equals(y)` 返回 `true`，`y.equals(z)` 返回 `true` ，那么 `y.equals(z)`也应该返回 `true`。
- **一致性**：对于两个非空对象 `x,y`，在没有修改此对象的前提下，多次调用返回的结果应该相同。
- **对于任何非空的对象 `x`，`x.equals(null)` 都应该返回 `false`。**

> 并且：
>
> - 对两个对象，如果 `equals()` 返回 `true`，则 `hashCode` 必须一样，反正则不要求。
> - 子类如果重写了 `equals()`，则必须重写 `hashCode()`。

## 3 String类

### 3.1 String的底层是如何实现的？为什么String是不可变类？

> 什么是不可变对象呢？
>
> **一个对象一旦初始化后就不能更改，禁止改变对象的状态，从而增加共享对象的坚固性、减少对象访问的错误，同时还避免了在多线程共享时进行同步的需要。**

`String` 底层是一个 **字符数组** `value[]`。（注意：`Java 9` 后使用的是 **字节数组** `byte[]`）

```java
private final char value[];
```

因为这个 `value[]` 数组被 `final` 修饰，因此初始化后就无法引用别的对象。`String` 类被 `final` 修饰，因此无法被继承。而在 `String` 类中并没有提供相关的 `get/set` 方法去修改 `value[]`。

### 3.2 String是不可变类，那它内部的字符串操作方法如何实现的？

通过创建新的 `String` 对象实现，原来的 `String` 对象不会被修改。此外，如果需要频繁修改字符串，建议使用 `StringBuilder` 和 `StringBuffer`。

### 3.3 常量字符串考点

![image-20230211204339644](https://p.ipic.vip/asxjp4.png)

字符串常量被保存在 **字符串常量池** 中，每个常量只会保存一份。

当通过常量的形式使用一个字符串的时候，使用的是常量池中的那个对应的 `String` 类型的对象。

如果不是通过常量直接赋值，而是使用 `new` 创建，就会不同了。
