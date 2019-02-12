package demo.jni.com.javeproject.clonetest;

import java.io.Closeable;

/**
 * 功能：
 */
public class Person implements Cloneable {
    // 基本类型
    private int age;
    // 引用类型
    private STRING name;

    public Person(int age, STRING name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public STRING getName() {
        return name;
    }

    public void setName(STRING name) {
        this.name = name;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    // 包装成一个引用类型
    public static class STRING {
        private String name;

        public STRING(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
