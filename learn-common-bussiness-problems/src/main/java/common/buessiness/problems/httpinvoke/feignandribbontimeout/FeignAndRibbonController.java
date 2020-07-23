package common.buessiness.problems.httpinvoke.feignandribbontimeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 
 *  
 * @since 2020/7/23
 * @author : weizc 
 */
@Slf4j
@RestController
@RequestMapping("feignandribbon")
public class FeignAndRibbonController {


    @Autowired
    Client client;

    @GetMapping("client")
    public void client(){
        long begin=System.currentTimeMillis();
        try {
            client.server();

        }catch (Exception e){
            log.error("执行超时：{} ms ,错误信息:{}",System.currentTimeMillis()-begin,e.getMessage());

        }
    }

    /**
     * //@see FeignClientFactoryBean#feign(org.springframework.cloud.openfeign.FeignContext)
     * //@see FeignClientFactoryBean#configureFeign(org.springframework.cloud.openfeign.FeignContext, feign.Feign.Builder)
     * @throws InterruptedException
     */

    @GetMapping("server")
    public void server() throws InterruptedException {
        log.info("server called");
        TimeUnit.MINUTES.sleep(10);


    }
}
