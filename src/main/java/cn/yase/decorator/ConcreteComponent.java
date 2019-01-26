package cn.yase.decorator;

/**
 * 具体构建角色
 * @author yase
 * @create 2019-01-26
 */
public class ConcreteComponent implements Component {
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
