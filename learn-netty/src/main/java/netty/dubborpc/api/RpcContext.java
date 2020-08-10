package netty.dubborpc.api;

import java.net.SocketAddress;

/**
 * 
 *   存放上下文信息
 * @since 2020/8/10
 * @author : weizc 
 */
public interface RpcContext {

     ThreadLocal<SocketAddress> sockAddressThreadLocal = new ThreadLocal();



     static SocketAddress get() {
        return sockAddressThreadLocal.get();
    }

     static void set(SocketAddress value) {
        sockAddressThreadLocal.set(value);
    }

     static void remove() {
        sockAddressThreadLocal.remove();
    }
}
