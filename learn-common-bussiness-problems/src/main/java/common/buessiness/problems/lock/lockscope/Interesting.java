package common.buessiness.problems.lock.lockscope;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *  
 * @since 2020/7/17
 * @author : weizc 
 */
@Slf4j
public class Interesting {

    volatile int a=1;
    volatile  int b=2;


    final int count=100000;
    public synchronized void  add(){
        log.info("add start");
        for (int i = 0; i <count ; i++) {

            a++;
            b++;
        }
        log.info("add done");
    }


    public void compare(){
        log.info("compare start");
        for (int i = 0; i <count ; i++) {
            if(a<b){//a<b 这种比较操作在字节码层面是加载 a、加载 b 和比较三步，
                // 代码虽然是一行但也不是原子性的
                log.info("a:{},b:{},{}",a,b,a>b);
            }
        }
        log.info("compare done");
    }



    public synchronized void compareRight(){
        log.info("compare start");
        for (int i = 0; i <count ; i++) {
            if(a<b){//a<b 这种比较操作在字节码层面是加载 a、加载 b 和比较三步，
                // 代码虽然是一行但也不是原子性的
                log.info("a:{},b:{},{}",a,b,a>b);
            }
        }
        log.info("compare done");
    }
}
