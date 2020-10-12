package com.yingke.javaasmdemo;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 功能：
 * </p>
 * <p>Copyright xxx.xxx.com 2020 All right reserved </p>
 *
 * @author tuke 时间 2020-10-12
 * @email <p>
 * 最后修改人：无
 * <p>
 */
public class AopClassAdapter extends ClassVisitor implements Opcodes {

    public AopClassAdapter(int api) {
        super(api);
    }

    public AopClassAdapter(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        // 更改类名，并使新类（name + "_Impl"）继承原有的类。实际上 是代理 ClassWriter，在写class信息
        super.visit(version, access, name + "_Impl", signature, superName, interfaces);

        {   // 输出 新类（name + "_Impl"）的一个默认 构造方法
            MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        // 不要 构造方法了，因为 生成了一个新类
        if ("<init>".equals(name)) {
            return null;
        }
        // 只对 helloAop方法 执行代理,因为 生成了一个新类
        if (!name.equals("helloAop")) {
            return null;
        }

        MethodVisitor oldMv = super.visitMethod(access, name, desc, signature, exceptions);
        return new AopMethodVisitor(api, oldMv);
    }
}
