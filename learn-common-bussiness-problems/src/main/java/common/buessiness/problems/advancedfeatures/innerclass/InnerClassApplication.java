package common.buessiness.problems.advancedfeatures.innerclass;

public class InnerClassApplication {

    private String gender = "male";

    public static void main(String[] args) throws Exception {

        InnerClassApplication application = new InnerClassApplication();
        application.test();

    }

    private void test() {
        MyInnerClass myInnerClass = new MyInnerClass();
        System.out.println(myInnerClass.name);
        myInnerClass.test();
    }

    class MyInnerClass {
        private String name = "xx";

        void test() {
            System.out.println(gender);
        }
    }
}
