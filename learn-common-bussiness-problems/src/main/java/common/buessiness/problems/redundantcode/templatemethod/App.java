package common.buessiness.problems.redundantcode.templatemethod;

import common.buessiness.problems.redundantcode.templatemethod.wrong.InternalUserCart;
import common.buessiness.problems.redundantcode.templatemethod.wrong.NormalUserCart;
import common.buessiness.problems.redundantcode.templatemethod.wrong.VipUserCart;
import common.buessiness.problems.utils.PrintlnUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : weizc
 * @since 2020/8/12
 */
@Slf4j
public class App {

    /**
     * 需求
     * 普通用户需要收取运费，运费是商品价格的 10%，无商品折扣；
     * VIP 用户同样需要收取商品价格 10% 的快递费，但购买两件以上相同商品时，第三件开
     * 始享受一定折扣；
     * 内部用户可以免运费，无商品折扣
     *
     * @param args
     */
    private static Map<Long, Integer> items = new HashMap<>();

    static {
        items.put(1L, 2);
        items.put(2L, 4);
    }

    public static void main(String[] args) {


        wrong();

        PrintlnUtils.println();

        right();

    }

    private static void right() {

        int userId = ThreadLocalRandom.current().nextInt(100) % 4;

        String userCategory = Db.getUserCategory(userId);
       // AbstractCart cart = (AbstractCart) applicationContext.getBean(userCategory + "UserCart");
        Cart cart = getUserChart(userId, userCategory);

        log.info("userCategory:{} ", userCategory);
        log.info("Cart : {} ", cart);
    }

    private static void wrong() {

        int userId = ThreadLocalRandom.current().nextInt(100) % 4;

        String userCategory = Db.getUserCategory(userId);


        Cart cart = getUserChart(userId, userCategory);

        log.info("userCategory:{} ", userCategory);
        log.info("Cart : {} ", cart);
    }

    private static Cart getUserChart(int userId, String userCategory) {
        if (userCategory.equals("Normal")) {
            NormalUserCart normalUserCart = new NormalUserCart();
            return normalUserCart.process(userId, items);
        }

        if (userCategory.equals("Vip")) {
            VipUserCart vipUserCart = new VipUserCart();
            return vipUserCart.process(userId, items);
        }

        if (userCategory.equals("Internal")) {
            InternalUserCart internalUserCart = new InternalUserCart();
            return internalUserCart.process(userId, items);
        }
        return null;
    }


}
