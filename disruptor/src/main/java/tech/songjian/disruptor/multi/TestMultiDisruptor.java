/**
 * @projectName Web-Interview
 * @package tech.songjian.disruptor.multi
 * @className tech.songjian.disruptor.multi.TestMultiDisruptor
 */
package tech.songjian.disruptor.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.w3c.dom.events.EventException;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TestMultiDisruptor
 * @description
 * @author SongJian
 * @date 2023/6/8 20:01
 * @version
 */
public class TestMultiDisruptor {
    public static void main(String[] args) throws InterruptedException {
        RingBuffer<Order> ringBuffer = RingBuffer.create(
                ProducerType.MULTI, // 多生产者
                new EventFactory<Order>() { // 订单工厂
                    public Order newInstance() {
                        return new Order();
                    }
                },
                1024 * 1024,    // bufferSize
                new YieldingWaitStrategy()  // 等待策略
        );

        // 2、创建 ringbuffer 屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        // 3、创建多个消费者数组
        ConsumerHandler[] consumers = new ConsumerHandler[10];

        for (int i = 0; i < consumers.length; ++i) {
            consumers[i] = new ConsumerHandler("Consumer" + i);
        }

        // 4、构建多消费者工作池
        WorkerPool<Order> orderWorkerPool = new WorkerPool<Order>(
                ringBuffer,
                sequenceBarrier,
                new EventExceptionHandler(),
                consumers
        );

        // 5、设置多个消费者的 seq 序号器，用于单独统计消费者的消费进度
        ringBuffer.addGatingSequences(orderWorkerPool.getWorkerSequences());

        // 6、启动workpool
        orderWorkerPool.start(Executors.newFixedThreadPool(5));

        // 要生产 100 个生产者，每个生产者发送100条数据，投递10000
        final CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 100; ++i) {
            final Producer producer = new Producer(ringBuffer);

            new Thread(new Runnable() {
                public void run() {
                    try {
                        latch.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 每个生产者生产100 条数据
                    for (int j = 0; j < 100; ++j) {
                         producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }

        // 把所有线程都创建玩
        TimeUnit.SECONDS.sleep(2);
        // 唤醒
        latch.countDown();
        // 休眠10s，让生产者将100循环走完
        TimeUnit.SECONDS.sleep(10);
        System.err.println("任务总数:" + consumers[0].getCount());
    }


    static class EventExceptionHandler implements ExceptionHandler<Order> {

        public void handleEventException(Throwable throwable, long l, Order order) {

        }

        public void handleOnStartException(Throwable throwable) {

        }

        public void handleOnShutdownException(Throwable throwable) {

        }
    }
}

