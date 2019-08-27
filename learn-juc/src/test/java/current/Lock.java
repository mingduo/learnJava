package current;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-9-12</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class Lock {
    /**
     *
     synchronized 是一个同步锁 synchronized （this）
     同步锁 当一个线程A 访问 【资源】的代码同步块的时候，
     A线程就会持续持有当前锁的状态，如果其他线程B-E 也要访问
     【资源】的代码同步块的时候将会收到阻塞，因此需要排队等待
     A线程释放锁的状态。（如图情况1）但是注意的是，当一个
     线程B-E 只是不能方法 A线程 【资源】的代码同步块，仍然可以访
     问其他的非资源同步块。

     ReentrantLock 可重入锁 通常两类：公平性、非公平性
     公平性：根据线程请求锁的顺序依次获取锁，当一个线程A 访问
     【资源】的期间，线程A 获取锁资源，此时内部存在一个计数器num+1，
     在访问期间，线程B、C请求 资源时，发现A 线程在持有当前资源，
     因此在后面生成节点排队（B 处于待唤醒状态），假如此时a线程再次请
     求资源时，不需要再次排队，可以直接再次获取当前资源 （内部计
     数器+1 num=2） ，当A线程释放所有锁的时候（num=0），此时会
     唤醒B线程进行获取锁的操作，其他C-E线程就同理。（情况2）

     非公平性：当A线程已经释放所之后，准备唤醒线程B获取资源的时候，
     此时线程M 获取请求，此时会出现竞争，线程B 没有竞争过M线程，测
     试M获取的线程因此，M会有限获得资源，B继续睡眠。（情况2）
     synchronized 是一个非公平性锁。 非公平性 会比公平性锁的效率要搞
     很多原因，不需要通知等待。
     ReentrantLock 提供了 new Condition可以获得多个Condition对象,可
     以简单的实现比较复杂的线程同步的功能.通过await(),signal()以实现。
     ReentrantLock 提供可以中断锁的一个方法lock.lockInterruptibly()方法。

     Jdk 1.8 synchronized和 ReentrantLock 比较的话，官方比较建议
     用synchronized。

     在了解Segment 机制之后我们继续看一下ConcurrentHashMap核心
     构造方法代码。

     *
     *
     */

    @Test
    public void test(){
        // 公平锁
        ReentrantLock lock=new ReentrantLock();
        lock.lock();
        System.out.println("3");
        lock.unlock();

        // 非公平锁

        synchronized (this){
            System.out.println("5");

        }
    }
}
