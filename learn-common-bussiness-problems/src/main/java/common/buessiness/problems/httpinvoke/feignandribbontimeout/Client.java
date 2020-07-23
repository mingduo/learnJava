package common.buessiness.problems.httpinvoke.feignandribbontimeout;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : weizc
 * @since 2020/7/23
 */
@FeignClient(name="clientsdk")
public interface Client {

    @GetMapping("/feignandribbon/server")
    void server();
}
