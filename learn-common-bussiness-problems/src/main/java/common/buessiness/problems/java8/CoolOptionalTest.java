package common.buessiness.problems.java8;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.OptionalDouble;

/**
 * 
 *  
 * @since 2020/8/17
 * @author : weizc 
 */
public class CoolOptionalTest {

    @Test(expected = IllegalArgumentException.class)
    public void optional() {
        Assert.assertThat(Optional.of(1).get(), CoreMatchers.is(1));
//通过ofNullable来初始化一个null，通过orElse方法实现Optional中无数据的时候返回一个默
        Assert.assertThat(Optional.ofNullable(null).orElse("a"),CoreMatchers.is("a"));

        Assert.assertFalse(OptionalDouble.empty().isPresent());

        Assert.assertThat(Optional.of(1).map(Math::incrementExact).get(),CoreMatchers.is(2));

        Assert.assertThat(Optional.of(1).filter(i->i%2==0).orElse(null),CoreMatchers.is(CoreMatchers.nullValue()));

        Optional.empty().orElseThrow(IllegalArgumentException::new);

    }
}
