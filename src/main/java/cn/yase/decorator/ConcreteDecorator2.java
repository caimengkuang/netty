package cn.yase.decorator;

/**
 * @author yase
 * @create 2019-01-26
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnSomeThing();
    }

    private void doAnSomeThing (){
        System.out.println("功能C");
    }
}
