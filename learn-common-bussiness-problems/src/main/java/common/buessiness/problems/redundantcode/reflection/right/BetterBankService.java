package common.buessiness.problems.redundantcode.reflection.right;

import common.buessiness.problems.redundantcode.reflection.BankService;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author : weizc
 * @since 2020/8/13
 */
public class BetterBankService implements BankService {


    /**
     *
     * rpc 调用 代码优化
     */

    @Override
    public  String createUser(String name, String identity, String mobile, int age) throws IOException {

        CreateUserApi createUserAPI = new CreateUserApi();
        createUserAPI.setName(name);
        createUserAPI.setIdentity(identity);
        createUserAPI.setAge(age);
        createUserAPI.setMobile(mobile);
        return remoteCall(createUserAPI);
    }


    @Override
    public  String pay(long userId, BigDecimal amount) throws IOException {

        PayAPI payAPI = new PayAPI();
        payAPI.setUserId(userId);
        payAPI.setAmount(amount);
        return remoteCall(payAPI);
        


    }

    @SneakyThrows
    private String remoteCall(AbstractAPI api) {
        BankAPI bankAPI = api.getClass().getAnnotation(BankAPI.class);
        String url=bankAPI.url();

        String params = Arrays.stream(api.getClass().getDeclaredFields())
                .sorted(Comparator.comparingInt(f -> f.getAnnotation(BankAPIField.class).order()))
                .peek(field -> field.setAccessible(true))
                .map(fn -> {
                    BankAPIField an = fn.getAnnotation(BankAPIField.class);
                    Object value = null;
                    try {
                        value = fn.get(api);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    int len = an.length();

                    switch (an.type()) {
                        case "S": {
                            return String.format("%-" + len + "s", value.toString()).replace(' ', '_');
                        }
                        case "N": {
                            return String.format(String.format("%" + len + "s", value.toString()).replace(' ', '0'));

                        }
                        default: {
                            return null;
                        }
                    }
                }).collect(Collectors.joining());

        //最后加上MD5作为签名
        params+=(DigestUtils.md2Hex(params));

        return Request.Post(url)
                .bodyString(params, ContentType.APPLICATION_JSON)
                .execute().returnContent().asString();
    }


}
