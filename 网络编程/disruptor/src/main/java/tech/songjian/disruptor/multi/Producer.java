/**
 * @projectName Web-Interview
 * @package tech.songjian.disruptor.multi
 * @className tech.songjian.disruptor.multi.Producer
 */
package tech.songjian.disruptor.multi;

import com.lmax.disruptor.RingBuffer;

/**
 * Producer
 * @description 生产者
 * @author SongJian
 * @date 2023/6/8 19:58
 * @version
 */
public class Producer {
    private RingBuffer<Order> ringBuffer;

    /**
     * 为生产者绑定ringbuffer
     * @param ringBuffer
     */
    public Producer(RingBuffer<Order> ringBuffer){
        this.ringBuffer = ringBuffer;
    }

    /**
     * 发送数据
     * @param uuid
     */
    public void sendData (String uuid) {
        //1.获取到可用sequence
        long sequence = ringBuffer.next();
        try{
            Order order = ringBuffer.get(sequence);
            order.setId(uuid);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}

