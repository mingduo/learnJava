package common.buessiness.problems.lock.lockscope;

import lombok.Getter;

/**
 * 
 *  
 * @since 2020/7/17
 * @author : weizc 
 */
public class Data {

    @Getter
    private static int counter = 0;
    private static Object locker = new Object();


    public static int reset() {
        counter=0;
        return counter;
    }

    public synchronized void wrong(){
        //=synchronized(this)
        counter++;
    }

    public  void right(){
        synchronized (locker){
            counter++;
        }
    }


}
