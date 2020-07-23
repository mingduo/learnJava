package common.buessiness.problems.httpinvoke.ribbonretry;

import common.buessiness.problems.httpinvoke.ribbonretry.feign.SmsClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("ribbonretryissueclient")
@Slf4j
public class RibbonRetryIssueClientController {

    @Autowired
    SmsClient smsClient;

    @SneakyThrows
    @GetMapping("/wrong")
    public void wrong(@RequestParam String mobile, @RequestParam String message, HttpServletRequest request){
        log.info("client is called");
        try {
            smsClient.sendWrongMessage("110", UUID.randomUUID().toString());

        }catch (Exception e){
            log.error("send sms failed :{}",e.getMessage());
        }
    }



    @SneakyThrows
    @PostMapping("/right")
    public void right(@RequestParam String mobile,@RequestParam String message,HttpServletRequest request){
        log.info("client is called");
        try {
            smsClient.sendRightMessage("110", UUID.randomUUID().toString());

        }catch (Exception e){
            log.error("send sms failed :{}",e.getMessage());
        }
    }


}