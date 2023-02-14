# 【八股】Java容器

## 1 ArrayList

### 1.1 ArrayList是线程安全的吗？

`ArrayList` 是 Java 提供的容器之一，其内部采用了动态数组实现，同时 **并不是线程安全的**。在多线程的情况下，让线程去修改 数组，会报 `ConcurrentModificationException` 异常，即并发修改异常。

### 1.2 ArrayList不是线程安全的，如何解决？

主要有三种解决方法：

1. 使用 `Vector` 替代 `ArrayList` 

在 `Vector` 中，使用 `synchronized` 关键字实现了线程安全。如 `add(E e)` 方法：

```java
public synchronized boolean add(E e) {
	modCount++;
	ensureCapacityHelper(elementCount + 1);
	elementData[elementCount++] = e;
	return true;
}
```

2. 使用 `Collections` 类提供的方法来修饰 `ArrayList` 

   使用 `Collections.synchronizedList()` 修饰容器可以实现线程安全。

   ```java
   List<String> list = Collections.synchronizedList(new ArrayList<>());
   ```

   但在 **使用迭代器遍历 List 时，需要手动进行同步：**

   ```java
   static class SynchronizedList<E>
           extends SynchronizedCollection<E>
           implements List<E> {
           private static final long serialVersionUID = -7754090372962971524L;
   
           final List<E> list;
   
           SynchronizedList(List<E> list) {
               super(list);
               this.list = list;
           }
           SynchronizedList(List<E> list, Object mutex) {
               super(list, mutex);
               this.list = list;
           }
   
           public boolean equals(Object o) {
               if (this == o)
                   return true;
               synchronized (mutex) {return list.equals(o);}
           }
           public int hashCode() {
               synchronized (mutex) {return list.hashCode();}
           }
   
           public E get(int index) {
               synchronized (mutex) {return list.get(index);}
           }
           public E set(int index, E element) {
               synchronized (mutex) {return list.set(index, element);}
           }
           public void add(int index, E element) {
               synchronized (mutex) {list.add(index, element);}
           }
           public E remove(int index) {
               synchronized (mutex) {return list.remove(index);}
           }
   
           public int indexOf(Object o) {
               synchronized (mutex) {return list.indexOf(o);}
           }
           public int lastIndexOf(Object o) {
               synchronized (mutex) {return list.lastIndexOf(o);}
           }
   
           public boolean addAll(int index, Collection<? extends E> c) {
               synchronized (mutex) {return list.addAll(index, c);}
           }
   
           public ListIterator<E> listIterator() {
               return list.listIterator(); // Must be manually synched by user
           }
   
           public ListIterator<E> listIterator(int index) {
               return list.listIterator(index); // Must be manually synched by user
           }
     .........
   ```

   `SynchronizedList` 就是在 `List` 的操作外包加了一层 `synchronized` 同步控制。

3. 使用 `CopyOnWriteArrayList` 替代 `ArrayList`

   `CopyOnWriteArrayList` 采用的是 **写时复制** 的思想。

   ```java
   List<String> list = new CopyOnWriteArrayList<>();
   ```

   **读不需要锁，可以并行，读和写也可以并行，但多个线程不能同时写，每个写操作都需要先获取锁。** 

   `CopyOnWriteArrayList` 内部使用 `ReentrantLock` 锁：

   ```java
   // 锁声明	
   final transient ReentrantLock lock = new ReentrantLock();
   .....
   // add 方法
   	public boolean add(E e) {
   		final ReentrantLock lock = this.lock;
   		lock.lock();
   		try {
   			Object[] elements = getArray();
   			int len = elements.length;
   			Object[] newElements = Arrays.copyOf(elements, len + 1);
   			newElements[len] = e;
   			setArray(newElements);
   			return true;
   		} finally {
   		lock.unlock();
   		}
   	}
   ```

### 1.3 ArrayList的扩容机制是怎么样的？

`ArrayList` 是一个数组结构的存储容器，**默认情况下，数组的长度为 `10`**，同时我们也可以在构建 `ArrayList` 时指定数组初始长度。随着程序运行，不断地向 `ArrayList` 中添加数据，当添加的数据达到 `10` 个的时候，`ArrayList` 中就没有足够的容量去存储后续的数据，此时 `ArrayList` 会触发自动扩容。扩容的流程：

1. 首先创建一个新的数组，**这个新数组的长度是原来数组长度的 `1.5` 倍**
2. 然后，使用 `Arrays.copyOf()` 方法，把老数组里面的数据拷贝到新的数组里面
3. 扩容完成之后，再把当前需要添加的元素加入到新数组里面

### 1.4 `Arrays.copyOf()` 与 `System.arraycopy()` 区别？

首先来看看 `Arrays.copyOf()`：

```java
public static int[] copyOf(int[] original, int newLength) {
	// 申请一个新的数组
    int[] copy = new int[newLength];
// 调用System.arraycopy,将源数组中的数据进行拷贝,并返回新的数组
    System.arraycopy(original, 0, copy, 0,
                     Math.min(original.length, newLength));
    return copy;
}
```

发现里面实际上还是调用了 `System.arraycopy()`:

    // 我们发现 arraycopy 是一个 native 方法,接下来我们解释一下各个参数的具体意义
    /**
    *   复制数组
    * @param src 源数组
    * @param srcPos 源数组中的起始位置
    * @param dest 目标数组
    * @param destPos 目标数组中的起始位置
    * @param length 要复制的数组元素的数量
    */
    public static native void arraycopy(Object src,  int  srcPos,
                                        Object dest, int destPos,
                                        int length);

**联系：**

看两者源代码可以发现 `copyOf()` 内部实际调用了 `System.arraycopy()` 方法

**区别：**

`arraycopy()` 需要目标数组，将原数组拷贝到你自己定义的数组里或者原数组，而且可以选择拷贝的起点和长度以及放入新数组中的位置 `copyOf()` 是系统自动在内部新建一个数组，并返回该数组。

## 2 LinkedList

### 2.1 LinkedList的底层是如何实现的？有什么特点？

`LinkedList` 的内部是由 **双向链表** 实现的，它同时实现了 `List`、`Deque`、`Queue` 接口，因此可以作为 **队列、双端队列 和 栈** 进行使用。由于是基于双向链表的逻辑结构进行实现，因此：

- 可按需分配空间，不需要预先分配很多空间
- 不支持随机访问，按照索引位置访问的效率也比较低
- 在双端添加、删除元素效率很高
- 在中间插入、删除元素效率很低

### 2.2 Queue接口中，添加、删除、取首元素的方法均有两个，有什么区别？

在 `Queue` 接口中：

- 添加元素：`add`、`offer`
- 删除元素：`remove`、`poll`
- 取首元素：`element`、`peek`

他们的用法一致，区别在于 **对特殊情况的处理方式不同，即在队列为空或队列为满的情况下，处理方式有差异。**

**`add`、`remove` 和 `element` 在特殊情况下会抛出异常，而剩下的则不会！**

> 注意：在 `LinkedList` 中，队列长度是没有限制的，因此不存在满的情况，但在别的 `Queue` 实现类中可能有限制。













