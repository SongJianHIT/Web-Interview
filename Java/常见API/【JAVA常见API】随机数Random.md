# 【JAVA常见API】随机数Random

`Random` 类是 `java.until` 下的一个根据随机算法的起源数字进行一些变化，从而得到随机数字的方法。虽然 `Random` 类产生的数字是随机的，但在相同种子数（`seed`）下的相同次数产生的随机数是相同的（伪随机）。

## 使用方法

### 构造函数

`Random` 有两种构造方法：

```java
// 无参构造，以系统自身时间为种子数
Random random = new Random();

// 有参构造，自定义种子数
Random random2 = new Random(seed)
```

### 常用方法

```java
// 返回下一个伪随机数，它是此随机数生成器的序列中均匀分布的 int 值。
int nextInt();
// 返回一个伪随机数，它是取自此随机数生成器序列的、在执行值之间均匀分布的int值。
int nextInt(int n);
// 返回下一个伪随机数，它是取自此随机数生成器序列的均匀分布的 long 值。
long nextLong();
// 返回下一个伪随机数，它是取自此随机数生成器序列的、在 0.0 和 1.0 之间均匀分布 float 值。
float nextFloat();
// 返回下一个伪随机数，它是取自此随机数生成器序列的、在 0.0 和 1.0 之间均匀分布的 double 值
double nextDouble();
// 返回下一个伪随机数，它是取自此随机数生成器序列的均匀分布的 boolean 值
boolean nextBoolean();
```