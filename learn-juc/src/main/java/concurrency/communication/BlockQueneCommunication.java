package concurrency.communication;


import java.util.concurrent.*;

/**
 * <table border="1">
 * <tr><th>@Description: 子线程输出 主线程阻塞
 * 主线程输出 子线程阻塞</th></tr>
 * <tr><td>@Date:Created in 2018-9-17</td>
 * </tr>
 * </table>
 * 两个线程通信
 *
 * @author :    weizc
 */
public class BlockQueneCommunication implements SynchronizedCommunication {


    private BlockingQueue<Integer> mainQueue = new ArrayBlockingQueue<>(1);
    private BlockingQueue<Integer> subQueue = new ArrayBlockingQueue<>(1);


    public static void main(String[] args) throws InterruptedException {
        new BlockQueneCommunication().execute();
    }
    @Override
    public void execute() throws InterruptedException {

        ExecutorService pool = Executors.newSingleThreadExecutor();

        pool.execute(() -> {
            useBlockQueueCommunication(mainQueue, subQueue);

        });

        useBlockQueueCommunication(subQueue, mainQueue);

        pool.awaitTermination(5, TimeUnit.SECONDS);
        pool.shutdown();
    }

    private void useBlockQueueCommunication(BlockingQueue<Integer> mainQueue, BlockingQueue<Integer> subQueue) {
       while (true) {
           try {
               mainQueue.put(1);


               SynchronizedCommunication.foreachPrint();

               subQueue.take();

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }




}


