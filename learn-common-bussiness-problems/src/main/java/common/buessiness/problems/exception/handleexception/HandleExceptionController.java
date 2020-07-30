package common.buessiness.problems.exception.handleexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author weizc
 */
@Slf4j
@RestController
@RequestMapping("handleexception")
public class HandleExceptionController {

    @GetMapping("wrong")
    public void exception(@RequestParam("business")boolean b){
        if(b){
            throw new RuntimeException("业务异常");
        }
        throw new RuntimeException("系统异常");
    }

    @GetMapping("wrong1")
    public void wrong1(){
        try {
            readFile();
        }catch (Exception e){
            //原始异常信息丢失
            throw new RuntimeException("系统使用异常");
        }
    }

    @GetMapping("wrong2")
    public void wrong2(){
        try {
            readFile();
        }catch (Exception e){
            //只保留了异常消息，栈没有记录
            log.error("读取文件错误:{}",e.getMessage());
            //原始异常信息丢失
            throw new RuntimeException("系统使用异常");
        }
    }
    @GetMapping("wrong3")
    public void wrong3(){
        try {
            readFile();
        }catch (Exception e){
            //只保留了异常消息，栈没有记录
            log.error("读取文件错误",e);
            //原始异常信息丢失
            throw new RuntimeException("系统使用异常");
        }
    }

    @GetMapping("right1")
    public void right1(){
        try {
            readFile();
        }catch (Exception e){
            //只保留了异常消息，栈没有记录
            log.error("读取文件错误",e);
            throw new RuntimeException("系统使用异常");
        }
    }

    @GetMapping("right2")
    public void right2(){
        try {
            readFile();
        }catch (Exception e){
            log.error("读取文件错误:",e);
            //原始异常信息丢失
            throw new RuntimeException("系统使用异常",e);
        }
    }

    private void readFile() throws IOException {
        Files.readAllLines(Paths.get("a_file"));
    }

}