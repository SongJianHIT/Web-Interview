/**
 * @projectName Web-Interview
 * @package tech.songjian.disruptor.multi
 * @className tech.songjian.disruptor.multi.OrderEvent
 */
package tech.songjian.disruptor.multi;

/**
 * OrderEvent
 * @description
 * @author SongJian
 * @date 2023/6/8 19:50
 * @version
 */
public class Order {

    private String id;
    private String name;
    private double price;

    public Order(){ }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

