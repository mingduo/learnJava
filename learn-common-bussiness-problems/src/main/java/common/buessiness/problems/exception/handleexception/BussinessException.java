package common.buessiness.problems.exception.handleexception;

import lombok.Getter;

/**
 * 
 *  
 * @since 2020/7/29
 * @author : weizc 
 */
public class BussinessException extends RuntimeException{
    @Getter
    int code;

    public BussinessException(String message, int code) {
        super(message);
        this.code = code;
    }
}
