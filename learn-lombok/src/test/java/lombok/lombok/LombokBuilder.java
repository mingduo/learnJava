package lombok.lombok;

import com.google.common.collect.ImmutableList;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * <table border="1">
 * <tr><th>@Description: https://projectlombok.org/features/Builder
 * https://projectlombok.org/features/BuilderSingular
 * </th></tr>
 * <tr><td>@Date:Created in 2018-9-30</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class LombokBuilder {


    /**
     * @param args
     */
    public static void main(String[] args) {
        Set<String> occupations = new HashSet<>();
        occupations.add("occupations");
        occupations.add("occupations-2");

        List<String> axises = new ArrayList<>();
        axises.add("axis");
        axises.add("axis-2");

        SingularExample<Number> singularExample = SingularExample.builder()
                .axes(axises).axis("123").axis("add")
                .occupations(occupations)
                .elf(1, 12).minutia(new Object()).build();

        System.out.println("SingularExample = [" + singularExample + "]");

        BuilderExample tom = BuilderExample.builder()
                .age(1)
                .name("tom")
                .created(123)
                .flag(true)
                .occupations(occupations).build();
        System.out.println("BuilderExample = [" + tom + "]");

    }

}

/**
 * For example: Person.builder().job("Mythbusters")
 * .job("Unchained Reaction").build();
 * would result in the List<String> jobs
 * field to have 2 strings in it. To get this behaviour,
 * the field/parameter needs to be annotated with @Singular
 *
 * @param <T>
 */
@ToString
@Builder
@Accessors(chain = true)
class SingularExample<T extends Number> {
    private @Singular
    Set<String> occupations;
    private @Singular("axis")
    ImmutableList<String> axes;
    private @Singular
    SortedMap<Integer, T> elves;
    private @Singular
    Collection<?> minutiae;
}

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
class BuilderExample {
    //not set  in  builder
    @Builder.Default
    private long created = System.currentTimeMillis();
    private String name;
    private int age;
    private boolean flag;
    private Set<String> occupations;
}