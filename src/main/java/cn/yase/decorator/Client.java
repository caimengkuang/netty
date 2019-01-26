package cn.yase.decorator;

/**
 * @author yase
 * @create 2019-01-26
 */
public class Client {

    public static void main(String[] args) {
        Component component = new ConcreteDecorator2(
                new ConcreteDecorator1(new ConcreteComponent())
        );

        component.doSomething();
    }
}
