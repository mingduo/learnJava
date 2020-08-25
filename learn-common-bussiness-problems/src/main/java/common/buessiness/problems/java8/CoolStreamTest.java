package common.buessiness.problems.java8;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author : weizc
 * @since 2020/8/17
 */
public class CoolStreamTest {

    /**
     * 把整数列表转换为 Point2D 列表；
     * 遍历 Point2D 列表过滤出 Y 轴 >1 的对象；
     * 计算 Point2D 点到原点的距离；
     * 累加所有计算出的距离，并计算距离的平均值。
     *
     * @param ints
     * @return
     */
    private static double calc(List<Integer> ints) {

        List<Point2D> point2DS = new ArrayList<>();
        for (Integer i : ints) {
            point2DS.add(new Point2D.Double(i % 3, i / 3));
        }
        double total = 0D;
        int count = 0;
        for (Point2D t : point2DS) {
            if (t.getY() > 1) {
                total += t.distance(0, 0);
                count++;
            }
        }
        return count == 0 ? 0 : total / count;
    }

    @Test
    public void stream() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        double average = calc(ints);
        double averagestream = ints.stream()
                .map(i -> new Point2D.Double(i % 3, i / 3))
                .filter(t -> t.getY() > 1)
                .mapToDouble(t -> t.distance(0, 0))
                .average()
                .orElse(0d);

        Assert.assertThat(average, CoreMatchers.is(averagestream));
    }

    ConcurrentMap<Long, Product> cache = new ConcurrentHashMap<>();

    @Test
    public void coolCache() {//一条语句实现cache的常用模式
        getProductAndCacheCool(1L);
        getProductAndCacheCool(100L);

        assertCache();

    }

    @Test
    public void notcoolCache() {
        getProductAndCacheCool(1L);
        getProductAndCacheCool(100L);

        assertCache();

    }

    private void assertCache() {
        System.out.println(cache);
        Assert.assertThat(cache.size(), CoreMatchers.is(1));
        Assert.assertTrue(cache.containsKey(1L));
    }


    private Product getProductAndCacheCool(long key
    ) {
        //从cache 获取 从db查询
        return cache.computeIfAbsent(key, k ->
                Product.getData().stream()
                        .filter(t -> t.getId().equals(k))
                        .findFirst().orElse(null));
    }

    private Product getProductAndCache(long key
    ) {
        //从cache 获取 从db查询
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            Product product = null;
            for (Product t : Product.getData()) {
                if (t.getId().equals(key)) {
                    product = t;
                    break;
                }
            }
            if (product != null) {
                cache.put(key, product);
            }
            return product;
        }

    }

    /**
     * 利用 Files.walk 返回一个 Path 的流，通过两行代码就能实现递归搜索 +grep 的
     * 操作。整个逻辑是：递归搜索文件夹，查找所有的.java 文件；然后读取文件每一行内容，
     * 用正则表达式匹配 public class 关键字；最后输出文件名和这行内容。
     */
    @Test
    public void findExample() throws IOException {
        Path path = Paths.get(".");
        //无限深度，递归遍历文件夹
        try (Stream<Path> pathStream = Files.walk(path);) {
            pathStream.filter(p -> Files.isRegularFile(p))
                    //搜索java源码文件
                    .filter(p -> FileSystems.getDefault().getPathMatcher("glob:**/*.java").matches(p))
                    .flatMap(ThrowingFunction.unchecked(p ->
                            //读取文件内容，转换为Stream<List>
                            Files.readAllLines(p).stream()
                                    //使用正则过滤带有public class的行
                                    .filter(l -> Pattern.compile("public class").matcher(l).find())
                                    //把这行文件内容转换为文件名+行
                                    .map(l -> p.getFileName() + "-->" + l)))
                    .forEach(System.out::println); //打印所有的行
        }
    }

    /**
     * 斐波那契
     */
    @Test
    public void fibonacci() {


        Stream.iterate(new BigInteger[]{BigInteger.ONE, BigInteger.ONE}
                , p -> new BigInteger[]{p[1], p[0].add(p[1])})
                .limit(10)
                .forEach(t -> System.out.println(t[0]));
    }


    /**
     * 用 ThrowingFunction 包装
     * 这个方法，把受检异常转换为运行时异常
     *
     * @param <T>
     * @param <R>
     */
    @FunctionalInterface
    public interface ThrowingFunction<T, R, E extends Throwable> {

        static <T, R, E extends Throwable> Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
            return t -> {
                try {
                    return f.apply(t);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            };
        }

        R apply(T t) throws E;
    }
}
