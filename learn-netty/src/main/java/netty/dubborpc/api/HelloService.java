package netty.dubborpc.api;

/**
 * 
 *  rpc api
 * 服务提供方和 服务消费方都需要
 * @since 2020/8/7
 * @author : weizc 
 */
public interface HelloService {

    String sayHi(String message);
}
