package cn.yase.decorator;

/**
 * 装饰角色
 * @author yase
 * @create 2019-01-26
 */
public class Decorator implements Component  {

    private Component component;

    @Override
    public void doSomething() {
        component.doSomething();
    }

    public Decorator(Component component){
        this.component = component;
    }
}
