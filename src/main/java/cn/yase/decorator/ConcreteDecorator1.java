package cn.yase.decorator;

/**
 * @author yase
 * @create 2019-01-26
 */
public class ConcreteDecorator1 extends Decorator {

    public ConcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnSomeThing();
    }

    private void doAnSomeThing (){
        System.out.println("功能B");
    }
}
