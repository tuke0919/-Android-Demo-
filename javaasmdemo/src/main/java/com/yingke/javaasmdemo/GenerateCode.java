package com.yingke.javaasmdemo;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ThreadFactory;

/**
 * 功能： asm demo，实现aop
 * 参考：https://my.oschina.net/ta8210/blog/162796
 * </p>
 * <p>Copyright xxx.xxx.com 2020 All right reserved </p>
 *
 * @author tuke 时间 2020-10-12
 * @email <p>
 * 最后修改人：无
 * <p>
 */
public class GenerateCode {

    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        // 读 class文件
        String className = "com/yingke/javaasmdemo/TestBean.class";
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(className);
        ClassReader classReader = new ClassReader(is);
        ClassVisitor classVisitor = new AopClassAdapter(Opcodes.ASM6, classWriter);
        // 开始读 class
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);

        // 写入新文件
        String classImplName = "./javaasmdemo/src/main/java/com/yingke/javaasmdemo/generate/TestBean_Impl.class";
        byte[] bytes = classWriter.toByteArray();
        File file = new File(classImplName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream os = new FileOutputStream(classImplName);
        os.write(bytes);
        is.close();
        os.close();

    }
}
