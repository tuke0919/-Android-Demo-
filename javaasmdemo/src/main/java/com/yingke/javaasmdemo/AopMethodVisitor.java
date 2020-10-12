package com.yingke.javaasmdemo;


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
public class AopMethodVisitor extends MethodVisitor implements Opcodes {

    public AopMethodVisitor(int api) {
        super(api);
    }

    public AopMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        // 方法代码执行 前
        mv.visitMethodInsn(INVOKESTATIC, "com/yingke/javaasmdemo/AopInterceptor", "beforeInvoke", "()V", false);
    }

    @Override
    public void visitInsn(int opcode) {
        // 在返回之前安插after 代码。
        if (opcode >= IRETURN && opcode <= RETURN) {
            mv.visitMethodInsn(INVOKESTATIC, "com/yingke/javaasmdemo/AopInterceptor", "afterInvoke", "()V", false);
        }
        super.visitInsn(opcode);
    }
}
