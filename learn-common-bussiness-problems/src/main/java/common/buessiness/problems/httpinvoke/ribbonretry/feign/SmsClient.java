package common.buessiness.problems.httpinvoke.ribbonretry.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * 
 * @since 2020/7/23
 * @author : weizc 
 */
@FeignClient(name = "smsClient")
public interface SmsClient {

    @GetMapping("/ribbonretryissueserver/sms")
   void sendWrongMessage(@RequestParam("mobile") String mobile,@RequestParam("message") String message);

    @PostMapping("/ribbonretryissueserver/sms")
    void sendRightMessage(@RequestParam("mobile")  String mobile,@RequestParam("message") String message);
}
