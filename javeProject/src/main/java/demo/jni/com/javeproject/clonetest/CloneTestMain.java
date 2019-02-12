package demo.jni.com.javeproject.clonetest;

/**
 * 功能：深拷贝和浅拷贝的测试
 */
public class CloneTestMain {

    public static void main(String[] args) throws CloneNotSupportedException {
        Body body = new Body(new Head(new Face(23)));
        Body body1 = (Body) body.clone();

        System.out.println("body = " + body);
        System.out.println("body1 = " + body1);

        System.out.println("body.head = " + body.head);
        System.out.println("body1.head = " + body1.head);

        System.out.println("body.head.face = " + body.head.face);
        System.out.println("body1.head.face = " + body1.head.face);

        System.out.println("body.head.face.age = "+ body.head.face.age);
        System.out.println("body1.head.face.age = " + body1.head.face.age);
    }

}
