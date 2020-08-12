package common.buessiness.problems.redundantcode.templatemethod.right;


import common.buessiness.problems.redundantcode.templatemethod.Item;

import java.math.BigDecimal;

public class NormalUserCart extends AbstractUserCart{


    @Override
    protected void processCouponPrice(long userId, Item item) {
        //运费为商品总价的10%
        item.setDeliveryPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).multiply(new BigDecimal("0.1")));

    }

    @Override
    protected void processDeliveryPrice(long userId, Item item) {
        //无优惠
        item.setCouponPrice(BigDecimal.ZERO);
    }
}
