package common.buessiness.problems.redundantcode.templatemethod.right;


import common.buessiness.problems.redundantcode.templatemethod.Item;

import java.math.BigDecimal;

public class InternalUserCart extends AbstractUserCart{



    @Override
    protected void processCouponPrice(long userId, Item item) {
        //无优惠
        item.setCouponPrice(BigDecimal.ZERO);
    }

    @Override
    protected void processDeliveryPrice(long userId, Item item) {
        //免运费
        item.setDeliveryPrice(BigDecimal.ZERO);
    }
}
