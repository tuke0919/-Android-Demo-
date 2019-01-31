package demo.jni.com.javeproject.annotationtest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 功能：测试反射操作注解
 */
public class AnnotationMain {

    public static void main(String[] args) {
       Person person = new Person();
       person.setName("ai");
       person.setUserName("aiai");

       System.out.println(query(person));
    }

    public static String query(Person person) {
        StringBuilder sb = new StringBuilder();
        Class p = person.getClass();
        // 是否存在注解
        boolean isExitTable = p.isAnnotationPresent(Table.class);
        if (!isExitTable) {
            return "";
        }
        // 注解实例
        Table table = (Table) p.getAnnotation(Table.class);
        // 注解的值
        String tableName = table.value();
        sb.append("select * from ").append(tableName).append(" where 1 = 1 ");
        // 字段数组
        Field[] fields = p.getDeclaredFields();
        for (int i = 0 ; i < fields.length ; i++) {
            Field field = fields[i];
            // 字段是否被修饰
            boolean isExitColumn = field.isAnnotationPresent(Column.class);
            if (!isExitColumn) {
                continue;
            }
            // 注解实例
            Column column = field.getDeclaredAnnotation(Column.class);
            // 注解的值
            String columeName = column.value();
            String fieldName = field.getName();
            String filedValue = null;
            String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);

            try {
                // 方法实例
                Method method = p.getMethod(getMethodName);
                // 激活方法，获取值
                filedValue = (String) method.invoke(person);

            } catch (Exception e) {
                e.printStackTrace();
            }
            sb.append(" and ").append(columeName).append(" = ").append(filedValue);

        }
        return sb.toString();
    }
}
