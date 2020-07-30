package common.buessiness.problems.exception.handleexception;

/**
 * 
 *  
 * @since 2020/7/29
 * @author : weizc 
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

    private static int GENERIC_SERVER_ERROR_CODE = 2000;
    private static String GENERIC_SERVER_ERROR_MESSAGE = "服务器忙，请稍后再试";

    @ExceptionHandler(value = Exception.class)
    public APIResponse errorHandle(HttpServletRequest request, HandlerMethod method, Exception e){
        if(e instanceof BussinessException){
            BussinessException be = (BussinessException) e;
            log.warn("业务异常 url:{},method:{}",request.getRequestURI(),method.getMethod().toString(),e);
            return new APIResponse(false,null,be.code, e.getMessage());
        }else {
            log.error("系统异常 url:{},method:{}",request.getRequestURI(),method.getMethod().toString(),e);
            return new APIResponse(false,null ,GENERIC_SERVER_ERROR_CODE,GENERIC_SERVER_ERROR_MESSAGE);
        }
    }
}
