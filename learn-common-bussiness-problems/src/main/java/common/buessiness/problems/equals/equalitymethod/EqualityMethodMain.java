package common.buessiness.problems.equals.equalitymethod;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author : weizc
 * @since 2020/7/27
 */
@Slf4j
public class EqualityMethodMain {
    public static void main(String[] args) {

        wrong();

        println();
        /**
         * point vs null
         * point vs Object
         * point vs another point
         */
        wrong2();

        println();

        right();
    }

    private static void wrong() {
        Point p1 = new Point(1, 2, "a");
        Point p2 = new Point(1, 2, "b");
        Point p3 = new Point(1, 2, "a");
        log.info("p1.equal(p2)=={}", p1.equals(p2));
        log.info("p2.equal(p3)=={}", p2.equals(p3));

    }

    private static void wrong2() {
        PointWrong p1 = new PointWrong(1, 2, "a");
        PointWrong p2 = new PointWrong(1, 2, "b");

        pointCompare(p1, p2);
    }

    private static void right() {
        PointRight p1 = new PointRight(1, 2, "a");
        PointRight p2 = new PointRight(1, 2, "b");

        pointCompare(p1, p2);
    }


    private static void println() {
        System.out.println("======");

    }

    private static void pointCompare(Object p1, Object p2) {
        try {
            log.info("p1.equals(null)=={}", p1.equals(null));
        } catch (Exception e) {
            log.error(e.toString());
        }
        try {
            log.info("p1.equals(expression)=={}", p1.equals(new Object()));
        } catch (Exception e) {
            log.error(e.toString());
        }

        log.info("p1.equal(p2)=={}", p1.equals(p2));


        Set<Object> points = new HashSet<>();
        points.add(p1);

        log.info("points.contains(p1)=={}", points.contains(p2));
    }


    static class Point {
        private final String desc;
        private int x;
        private int y;

        public Point(int x, int y, String desc) {
            this.x = x;
            this.y = y;
            this.desc = desc;
        }
    }

    static class PointWrong {
        private final String desc;
        private int x;
        private int y;

        public PointWrong(int x, int y, String desc) {
            this.x = x;
            this.y = y;
            this.desc = desc;
        }

        @Override
        public boolean equals(Object o) {
            PointWrong that = (PointWrong) o;
            return x == that.x &&
                    y == that.y;
        }

    }

    static class PointRight {
        private final String desc;
        private int x;
        private int y;

        public PointRight(int x, int y, String desc) {
            this.x = x;
            this.y = y;
            this.desc = desc;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PointRight that = (PointRight) o;
            return x == that.x &&
                    y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}


