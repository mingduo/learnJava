package com.ais.brm.study.brmTest.guava;

import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-10-10</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class FluentIterableEx {

    public static void main(String[] args) {
        List<Car2> cars = Lists.newArrayList(
                new Car2(1, "Audi", 52642),
                new Car2(2, "Mercedes", 57127),
                new Car2(3, "Skoda", 9000),
                new Car2(4, "Volvo", 29000));


        List<String> results = FluentIterable.from(cars)
                .filter(getCar2Predicate())
                .transform(Functions.toStringFunction())
                .toList();

        System.out.println(results);
    }

    private static Predicate<Car2> getCar2Predicate() {
        return car -> car.getPrice() <= 30000;
    }


}

@AllArgsConstructor
@Data(staticConstructor = "of")
class Car2 {
    private long Id;
    private String Name;
    private int Price;
}
