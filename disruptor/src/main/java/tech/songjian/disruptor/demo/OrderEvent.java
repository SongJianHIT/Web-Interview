/**
 * @projectName Web-Interview
 * @package tech.songjian.disruptor.demo
 * @className tech.songjian.disruptor.demo.OrderEvent
 */
package tech.songjian.disruptor.demo;

/**
 * OrderEvent
 * @description 订单事件
 * @author SongJian
 * @date 2023/6/8 19:06
 * @version
 */
public class OrderEvent {
    /**
     * 订单价格
     */
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

