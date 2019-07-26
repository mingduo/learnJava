package com.ais.brm.study.brmTest.guava;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-10-10</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class ToStringEx {
    public static void main(String[] args) {

        Car car1 = new Car(1, "Audi", 52642);
        Car car2 = new Car(2, "Mercedes", 57127);
        Car car3 = new Car(3, "Skoda", 9000);

        System.out.println(car1);
        System.out.println(car2);
        System.out.println(car3);
    }
}

@AllArgsConstructor
@Data(staticConstructor = "of")
class Car{
    private long Id;
    private String Name;
    private int Price;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Car.class)
                .add("id", Id)
                .add("name", Name)
                .add("price", Price)
                .toString();
    }

}
