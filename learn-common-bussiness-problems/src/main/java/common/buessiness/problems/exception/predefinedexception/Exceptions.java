package common.buessiness.problems.exception.predefinedexception;

import common.buessiness.problems.exception.handleexception.BussinessException;

public class Exceptions {

    public static BussinessException ORDEREXISTS = new BussinessException("订单已经存在", 3001);

    public static BussinessException orderExists() {
        return new BussinessException("订单已经存在", 3001);
    }
}
