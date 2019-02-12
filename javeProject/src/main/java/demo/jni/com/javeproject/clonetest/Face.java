package demo.jni.com.javeproject.clonetest;

/**
 * 功能：
 */
public class Face implements Cloneable {
    public int age;

    public Face(int age) {
        this.age = age;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 浅拷贝：分配空间，复制内容
        return super.clone();
    }
}
