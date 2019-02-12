package demo.jni.com.javeproject.clonetest;




public class Body implements Cloneable {
    // 引用类型成员变量
    public Head head;

    public Body() {
    }

    public Body(Head head) {
        this.head = head;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 浅拷贝：分配空间，复制内容，但成员变量是 引用类型，需成员变量再次 浅拷贝
        Body newBody = (Body) super.clone();
        if (head != null) {
            newBody.head = (Head) head.clone();
        }
        return newBody;
    }
}
