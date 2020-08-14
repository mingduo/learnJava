package common.buessiness.problems.redundantcode.reflection;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author : weizc
 * @since 2020/8/13
 */
public interface BankService {
     String createUser(String name, String identity, String mobile, int age) throws IOException;

     String pay(long userId, BigDecimal amount) throws IOException;
}
