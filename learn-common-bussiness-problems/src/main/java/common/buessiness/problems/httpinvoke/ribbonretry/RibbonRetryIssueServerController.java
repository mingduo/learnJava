package common.buessiness.problems.httpinvoke.ribbonretry;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryPolicy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("ribbonretryissueserver")
@Slf4j
public class RibbonRetryIssueServerController {


    @SneakyThrows
    @GetMapping("/sms")
    public void sendWrongMessage(@RequestParam String mobile, @RequestParam String message, HttpServletRequest request){
        logAndSleep(mobile, message, request);
    }

    /**
     * @see  RibbonLoadBalancedRetryPolicy  get 重试 1 次
     * @param mobile
     * @param message
     * @param request
     */

    @SneakyThrows
    @PostMapping("/sms")
    public void sendRightMessage(@RequestParam String mobile,@RequestParam String message,HttpServletRequest request){
        logAndSleep(mobile, message, request);

    }

    private void logAndSleep(@RequestParam String mobile, @RequestParam String message, HttpServletRequest request) throws InterruptedException {
        log.info("request url:{},request port;{} mobile:{} message:{}",request.getRequestURI()
                ,request.getRemotePort(),mobile,message);
        TimeUnit.SECONDS.sleep(2);
    }
}