package common.buessiness.problems.current.threadlocal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static common.buessiness.problems.utils.Constants.right;
import static common.buessiness.problems.utils.Constants.wrong;

/**
 * 01- thread_local 问题
 * @author : weizc
 * @since 2020/7/10
 */
@RestController
@RequestMapping("threadlocal")
public class ThreadLocalMisuseController {

    ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    /**
     * 顾名思义，线程池会重用固定的几个线程，一旦线程重用，那么很可能首次从
     * ThreadLocal 获取的值是之前其他用户的请求遗留的值。这时，ThreadLocal 中的用户信
     * 息就是其他用户的信息。
     * @param userId
     * @return
     */
    @GetMapping(wrong)
    public Map<String, Object> wrong(Integer userId) {

        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        currentUser.set(userId);
        String after = Thread.currentThread().getName() + ":" + currentUser.get();

        Map<String, Object> result = new HashMap<>();
        result.put("before", before);
        result.put("after", after);
        return result;
    }


    /**
     * ，使用类似 ThreadLocal 工具来存放一些数据时，需要特别注意在
     * 代码运行完后，显式地去清空设置的数据。
     *
     * @param userId
     * @return
     */
    @GetMapping(right)
    public Map<String, Object> right(Integer userId) {
        try {
            String before = Thread.currentThread().getName() + ":" + currentUser.get();
            currentUser.set(userId);
            //设置用户信息之后再查询一次ThreadLocal中的用户信息
            String after = Thread.currentThread().getName() + ":" + currentUser.get();

            Map<String, Object> result = new HashMap<>();
            result.put("before", before);
            result.put("after", after);
            return result;
        } finally {
            //在finally代码块中删除ThreadLocal中的数据，确保数据不串
            currentUser.remove();
        }

    }


}
