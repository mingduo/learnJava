package common.buessiness.problems.exception.handleexception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 *  
 * @since 2020/7/29
 * @author : weizc 
 */
@AllArgsConstructor
@Data
public class APIResponse<T> {

    boolean success;
    T data;
    int code;
    String errorMesssage;
}
