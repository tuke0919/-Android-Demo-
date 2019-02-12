package demo.jni.com.javeproject.clonetest;


public class Head implements Cloneable {
    // 引用类型成员变量
    public Face face;
    public Head() {}
    public Head(Face face){this.face = face;}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 浅拷贝：分配空间，复制内容，但成员变量是 引用类型，需成员变量再次 浅拷贝
        Head newHead = (Head) super.clone();
        if (face != null) {
            newHead.face = (Face) face.clone();
        }
        return newHead;
    }
}
