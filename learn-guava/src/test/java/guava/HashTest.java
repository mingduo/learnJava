package com.ais.brm.study.brmTest.guava;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : weizc
 * @description: https://github.com/google/guava/wiki/HashingExplained#bloomfilter
 * @since 2019/1/22
 */
public class HashTest {
    public static void main(String[] args) {
        //如何将特定对象类型分解为原始字段值
        Funnel<Person> personFunnel = (person, into) -> into
                .putInt(person.id)
                .putString(person.firstName, Charsets.UTF_8)
                .putString(person.lastName, Charsets.UTF_8)
                .putInt(person.birthYear);

        Person person = new Person();
        person.setFirstName("first_name").setId(1).setLastName("last_name").setBirthYear(2018);

        HashFunction hf = Hashing.md5();
        HashCode hc = hf.newHasher()
                .putLong(12)
                .putString("name", Charsets.UTF_8)
                .putObject(person, personFunnel)
                .hash();
        System.out.println("HashCode = [" + hc + "]");
    }
}

@Accessors(chain = true)
@Data
class Person {
    int id;
    String firstName;
    String lastName;
    int birthYear;
}