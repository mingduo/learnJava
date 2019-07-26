package com.ais.brm.study.brmTest.lombok;

import lombok.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <table border="1">
 * <tr><th>@Description: http://jnb.ociweb.com/jnb/jnbJan2010.html</th></tr>
 * <tr><td>@Date:Created in 2018-9-30</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class LombokTest {

    public static void main(String[] args) {
        Person person = Person.of();
        System.out.println("person = [" + person + "]");
    }

    public void testCleanUp() {
        try {
            @Cleanup ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(new byte[] {'Y','e','s'});
            System.out.println(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testCleanUp2() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                baos.write(new byte[]{'Y', 'e', 's'});
                System.out.println(baos.toString());
            } finally {
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

@Data(staticConstructor = "of")
class Person {
    public String name;
    public int age;
}
@Getter
@Setter
@EqualsAndHashCode(exclude = {"address", "city", "state", "zip"})
class Foo {
    enum Gender {Male, Female}

    @NonNull
    private String name;
    @NonNull
    private Gender gender;

    private String ssn;
    private String address;
    @Setter(AccessLevel.PROTECTED)
    private String city;
    private String state;
    private String zip;
}

@ToString(callSuper = true, exclude = "someExcludedField")
class Bar extends Foo {
    private boolean someBoolean = true;
    private String someStringField;
    private float someExcludedField;
}