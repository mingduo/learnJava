package   jdk8.stream;

@FunctionalInterface
public interface MyPredicate<T> {

    public boolean test(T t);

}
