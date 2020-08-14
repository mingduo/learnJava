package common.buessiness.problems.redundantcode.reflection;

import common.buessiness.problems.redundantcode.reflection.right.BetterBankService;
import common.buessiness.problems.redundantcode.reflection.wrong.WrongBankService;
import common.buessiness.problems.utils.PrintlnUtils;
import lombok.SneakyThrows;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author : weizc
 * @since 2020/8/13
 */
public class ReflectionDemo {

    public static void main(String[] args) {
        wrong();

        PrintlnUtils.println();


        right();
    }

    @SneakyThrows
    private static void right() {
        invokeApi( new BetterBankService());

    }

    @SneakyThrows
    private static void wrong() {

        invokeApi( new WrongBankService());
    }

    private static void invokeApi(BankService api) throws IOException {
        api.createUser("xd", "xxxxxxxxxxxxxxxxxx", "13612345678", 36);
        api.pay(1234L, new BigDecimal("100.5"));
    }
}
