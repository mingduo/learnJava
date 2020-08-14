package common.buessiness.problems.redundantcode.reflection.wrong;

import common.buessiness.problems.redundantcode.reflection.BankService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author : weizc
 * @since 2020/8/13
 */
public class WrongBankService implements BankService {


    /**
     *
     * rpc 调用 代码优化
     */

    @Override
    public  String createUser(String name, String identity, String mobile, int age) throws IOException {
        StringBuilder sb=new StringBuilder();
        //字符串靠左，多余的地方_填充
        sb.append(String.format("%-10s", name).replace(' ','_'));
        sb.append(String.format("%-18s", identity).replace(' ', '_'));
        //数字靠右，多余的地方用0填充
        sb.append(String.format("%05d", age));
        //字符串靠左，多余的地方_填充
        sb.append(String.format("%-11s", mobile).replace(' ', '_'));
        //最后加上MD5作为签名
        sb.append(DigestUtils.md2Hex(sb.toString()));

        return Request.Post("http://localhost:4369/reflection/bank/createUser")
                .bodyString(sb.toString(), ContentType.APPLICATION_JSON)
                .execute().returnContent().asString();
    }


    @Override
    public  String pay(long userId, BigDecimal amount) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        //数字靠右，多余的地方用0填充
        stringBuilder.append(String.format("%020d", userId));
        //金额向下舍入2位到分，以分为单位，作为数字靠右，多余的地方用0填充
        stringBuilder.append(String.format("%010d", amount.setScale(2, RoundingMode.DOWN).multiply(new BigDecimal("100")).longValue()));
        //最后加上MD5作为签名
        stringBuilder.append(DigestUtils.md2Hex(stringBuilder.toString()));
        return Request.Post("http://localhost:4369/reflection/bank/pay")
                .bodyString(stringBuilder.toString(), ContentType.APPLICATION_JSON)
                .execute().returnContent().asString();

    }
}
