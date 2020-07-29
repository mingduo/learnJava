package netty.protocoltcp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 *  解决tcp 沾包拆包问题
 * @since 2020/7/29
 * @author : weizc 
 */
//协议包
@NoArgsConstructor
@AllArgsConstructor
public class MessageProtocol {
    int len; //关键
    byte[] content;

}
