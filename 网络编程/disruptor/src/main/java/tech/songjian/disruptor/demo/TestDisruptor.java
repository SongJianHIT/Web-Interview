/**
 * @projectName Web-Interview
 * @package tech.songjian.disruptor.demo
 * @className tech.songjian.disruptor.demo.TestDisruptor
 */
package tech.songjian.disruptor.demo;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.omg.SendingContext.RunTime;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TestDisruptor
 * @description 测试
 * @author SongJian
 * @date 2023/6/8 19:21
 * @version
 */
public class TestDisruptor {
    public static void main(String[] args) {
        // 准备工作
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 8;
        // 获取CPU处理器数量
        int nThreads = Runtime.getRuntime().availableProcessors();
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        // 1、实例化 disruptor 对象
        // 对象工厂，环形队列大小，消费线程池，生产者模式，等待策略
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(orderEventFactory,
                        ringBufferSize,
                        executorService,
                        ProducerType.SINGLE,
                        new BlockingWaitStrategy());

        // 2、添加消费者监听（也就是消费者如何区消费）
        disruptor.handleEventsWith(new OrderEventHandler());

        // 3、启动
        disruptor.start();

        // 4、收到容器后通过生产者去生产消息
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        // 生产者
        OrderEventProducer orderEventProducer = new OrderEventProducer(ringBuffer);

        // 先初始化 ByteBuffer 长度为 8 Byte
        ByteBuffer buf = ByteBuffer.allocate(8);
        // 生产
        for (long i = 0; i < 100000; ++i) {
            buf.putLong(0, i);
            orderEventProducer.sendData(buf);
        }

        disruptor.shutdown();
        executorService.shutdown();
    }
}

