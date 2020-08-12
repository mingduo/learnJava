package common.buessiness.problems.redundantcode.templatemethod.right;


import common.buessiness.problems.redundantcode.templatemethod.Db;
import common.buessiness.problems.redundantcode.templatemethod.Item;

import java.math.BigDecimal;

public class VipUserCart extends AbstractUserCart{


    @Override
    protected void processCouponPrice(long userId, Item item) {
        //购买两件以上相同商品，第三件开始享受一定折扣
        if (item.getQuantity() > 2) {
            item.setCouponPrice(item.getPrice()
                    .multiply(BigDecimal.valueOf(100 - Db.getUserCouponPercent(userId)).divide(new BigDecimal("100")))
                    .multiply(BigDecimal.valueOf(item.getQuantity() - 2)));
        } else {
            item.setCouponPrice(BigDecimal.ZERO);
        }
    }

    @Override
    protected void processDeliveryPrice(long userId, Item item) {
        //运费为商品总价的10%
        item.setDeliveryPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).multiply(new BigDecimal("0.1")));

    }
}
