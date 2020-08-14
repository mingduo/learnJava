package common.buessiness.problems.redundantcode.reflection.right;

import lombok.Data;

/**
 * @author : weizc
 * @since 2020/8/13
 */
@Data
@BankAPI(url = "http://localhost:4369/reflection/bank/createUser")
public class CreateUserApi extends AbstractAPI{
    @BankAPIField(order = 1, type = "S", length = 10)
    private String name;
    @BankAPIField(order = 2, type = "S", length = 18)
    private String identity;
    @BankAPIField(order = 4, type = "S", length = 11)
    private String mobile;
    @BankAPIField(order = 3, type = "N", length = 5)
    private int age;
}
