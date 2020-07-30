package common.buessiness.problems.exception.predefinedexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *  
 * @since 2020/7/30
 * @author : weizc 
 */
@RequestMapping("predefinedexception")
@RestController
@Slf4j
public class PredefinedexceptionController {

    @GetMapping("wrong")
    public void wrong(){
        try {
            createOrderWrong();
        }catch (Exception e){
            log.error("createOrder get error",e);
        }
        try {
            /**
             * 异常对应22行
             * common.buessiness.problems.exception.handleexception.BussinessException: 订单已经存在
             * 	at common.buessiness.problems.exception.predefinedexception.Exceptions.<clinit>(Exceptions.java:7) ~[classes/:na]
             * 	at common.buessiness.problems.exception.predefinedexception.PredefinedexceptionController.createOrderWrong(PredefinedexceptionController.java:62) ~[classes/:na]
             * 	at common.buessiness.problems.exception.predefinedexception.PredefinedexceptionController.wrong(PredefinedexceptionController.java:22) ~
             */
            cancelOrderWrong();
        }catch (Exception e){
            log.error("cancelOrder get error",e);
        }
    }

    @GetMapping("right")
    public void right(){
        try {
            createOrderRight();
        }catch (Exception e){
            log.error("createOrder get error",e);
        }
        try {
            cancelOrderRight();
        }catch (Exception e){
            log.error("cancelOrder get error",e);
        }
    }

    private void cancelOrderRight() {
        throw Exceptions.orderExists();

    }

    private void createOrderRight() {
        throw Exceptions.orderExists();
    }

    private void cancelOrderWrong() {
        throw Exceptions.ORDEREXISTS;

    }

    private void createOrderWrong() {
        throw Exceptions.ORDEREXISTS;
    }


}
