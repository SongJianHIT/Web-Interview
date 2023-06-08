/**
 * @projectName Web-Interview
 * @package tech.songjian.rpc.consumer
 * @className tech.songjian.rpc.consumer.SkuService
 */
package tech.songjian.rpc.consumer;

/**
 * SkuService
 * @description 远程接口
 * @author SongJian
 * @date 2023/6/8 15:20
 * @version
 */
public interface SkuService {
    String findByName(String name);
}
