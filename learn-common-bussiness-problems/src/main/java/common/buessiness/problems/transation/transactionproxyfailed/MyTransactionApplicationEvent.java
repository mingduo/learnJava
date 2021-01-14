package common.buessiness.problems.transation.transactionproxyfailed;

import org.springframework.context.ApplicationEvent;

/**
 * 
 *  
 * @since 2020/9/7
 * @author : weizc 
 */
public class MyTransactionApplicationEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MyTransactionApplicationEvent(Object source) {
        super(source);
    }
}
