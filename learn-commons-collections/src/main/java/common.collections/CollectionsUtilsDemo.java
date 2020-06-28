package common.collections;

import common.collections.domain.Car;
import common.collections.domain.City;
import common.collections.domain.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.StringValueTransformer;
import org.apache.commons.collections4.functors.SwitchTransformer;

import java.util.*;

/**
 * 
 * @apiNode:
 * @since 2020/6/24
 * @author : weizc 
 */
public class CollectionsUtilsDemo {

    public static void main(String[] args) {

        User user=new User();
        user.setId(1L);
        user.setName("卡拉");
        user.setCity(City.BEIJING);
        user.setCar(new Car("BMW"));

        User user2=new User();
        user2.setId(2L);
        user2.setName("飞名");
        user2.setCity(City.SHANGHAI);
        user2.setCar(new Car("BENZ"));


        List<User> users = new ArrayList<>(Arrays.asList(user, user2));

        filter(users);

        collect(users);

        select(users);


    }

    private static void select(List<User> users) {
        System.out.println("----------------------");

        CollectionUtils.filter(users, t->t.getName().startsWith("飞"));
        System.out.println(users);
    }


    private static void collect(List<User> users) {

        System.out.println("----------------------");
        Collection<String> userNames = CollectionUtils.collect(users, User::getName);
        System.out.println(userNames);

        Transformer<User, String> stringValueTransformer = StringValueTransformer.stringValueTransformer();
        CollectionUtils.collect(users,stringValueTransformer);
        System.out.println(users);

        Transformer<User, String> switchTransformer = SwitchTransformer.switchTransformer(Collections.singletonMap(t -> t.getName().startsWith("卡"), User::getName));
        CollectionUtils.collect(users,switchTransformer);
        System.out.println(users);

    }

    private static void filter(List<User> users) {
        System.out.println("----------------------");

        CollectionUtils.filter(users, t->t.getName().startsWith("卡"));
        System.out.println(users);
    }


}
