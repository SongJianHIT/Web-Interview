/**
 * @projectName Web-Interview
 * @package tech.songjian.rpc
 * @className tech.songjian.rpc.TestJianRPC
 */
package tech.songjian.rpc;

import tech.songjian.rpc.consumer.SkuService;
import tech.songjian.rpc.consumer.UserService;
import tech.songjian.rpc.consumerStub.JianRPCProxy;

/**
 * TestJianRPC
 * @description
 * @author SongJian
 * @date 2023/6/8 16:20
 * @version
 */
public class TestJianRPC {
    public static void main(String [] args){

        //第1次远程调用
        SkuService skuService = (SkuService) JianRPCProxy.create(SkuService.class);
        String resp_Msg = skuService.findByName("uid");
        System.out.println(resp_Msg);

        //第2次远程调用
        UserService userService = (UserService) JianRPCProxy.create(UserService.class);
        System.out.println(userService.findById());
    }
}

