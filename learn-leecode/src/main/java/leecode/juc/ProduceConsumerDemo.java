package leecode.juc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/4/8
 */
public class ProduceConsumerDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);


        ProduceConsumerContainer container = new LockConditionContainer();

        executorService.execute(() -> container.produce());
        executorService.execute(() -> container.consumer());


        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    static class SynchronizedContainer implements ProduceConsumerContainer {
        LinkedList<Integer> data = new LinkedList<>();
        Random r = new Random();
        int max_cap = 5;

        @Override
        public void produce() {
            while (true) {
                try {
                    synchronized (this) {
                        while (data.size() == max_cap) {
                            wait();
                        }
                        Thread.sleep(1000);
                        int i = r.nextInt(100);

                        println("生产者正在生产数据:" + i);
                        data.add(i);
                        this.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void consumer() {
            while (true) {
                try {
                    synchronized (this) {
                        while (data.isEmpty()) {
                            wait();
                        }
                        Thread.sleep(1000);
                        Integer value = data.poll();

                        println("消费者正在消费数据:" + value);
                        this.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    interface ProduceConsumerContainer {
        void produce();

        void consumer();
    }

    private static void println(String msg) {
        System.out.printf("%s:%s\n", Thread.currentThread().getName(), msg);
    }

    static class BlockingQueueContainer implements ProduceConsumerContainer {

        Random r = new Random();
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);

        @Override
        public void produce() {
            while (true) {
                int i = r.nextInt(100);
                println("生产者正在生产数据:" + i);
                try {
                    Thread.sleep(1000);
                    queue.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void consumer() {
            while (true) {

                try {
                    int i = queue.take();
                    println("消费者正在消费数据:" + i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static class LockConditionContainer implements ProduceConsumerContainer {
        Queue<Integer> queue=new LinkedList<>();
        Lock lock=new ReentrantLock();
        Condition isEmpty = lock.newCondition();
        Condition isFull = lock.newCondition();
        int max_cap=5;
        Random r=new Random();
        @Override
        public void produce() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size()==max_cap){
                        isFull.await();
                    }
                   int value= r.nextInt(100);
                    queue.offer(value);

                    Thread.sleep(1000);
                    println("生产者正在生产数据:" + value);

                    isEmpty.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }

        @Override
        public void consumer() {
            while (true) {
                lock.lock();
                try {
                    while (queue.isEmpty()){
                        isEmpty.await();
                    }
                    Thread.sleep(1000);
                    println("消费者正在生产数据:" + queue.poll());

                    isFull.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
