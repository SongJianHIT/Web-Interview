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



















