package com.tony.util;

import com.tony.entity.CostRecord;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Author jiangwj20966 on 2017/6/2.
 * 偷懒用
 */
public class StaticCodeGeneratorUtil {
    public static void main(String[] args) {
        System.out.println(allSqlGenerator(CostRecord.class, false));
        System.out.println(insertSqlGenerator(CostRecord.class, true));
        System.out.println(updateSqlGenerator(CostRecord.class, true));
        System.out.println(whereCaseGenerator(CostRecord.class, true));
    }


    public static String allSqlGenerator(Class clz, Boolean isSqlCamelCase) {
        Field[] fields = clz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            sb.append(isSqlCamelCase ? getCamelCaseCode(field) : getUnderScoreCaseCode(field));
            sb.append(" ").append(field.getName()).append(",\n");
        }
        if (sb.lastIndexOf(",") > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        return sb.toString();
    }

    private static String getUnderScoreCaseCode(Field field) {
        String fieldName = field.getName();
        StringBuilder sb = new StringBuilder();
        for (char c : fieldName.toCharArray()) {
            if (Character.isLowerCase(c)) {
                sb.append(c);
            } else {
                sb.append('_').append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    private static String getCamelCaseCode(Field field) {
        return field.getName();
    }

    public static String insertSqlGenerator(Class clz, Boolean isCamelCaseCode) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = clz.getDeclaredFields();
        sb.append("INSERT INTO (\n");
        for (Field field : fields) {
            sb.append(dynamicSqlColumnGenerator(isCamelCaseCode, field));
        }
        if (sb.lastIndexOf(",") > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append(") VALUES (\n");
        int l = sb.length();
        for (Field field : fields) {
            sb.append(dynamicSqlValueGenerator(isCamelCaseCode, field));
        }
        if (sb.lastIndexOf(",") > l) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append(")");
        return sb.toString();
    }

    private static String dynamicSqlColumnGenerator(boolean isCamelCase, Field field) {
        StringBuilder sb = new StringBuilder();
        sb.append("<if test=\" ").append(field.getName()).append("!=null");
        if (field.getType().getName().equals(String.class.getName())) {
            sb.append(" and ").append(field.getName()).append("!=''");
        }
        sb.append(" \">\n");
        sb.append(isCamelCase ? getCamelCaseCode(field) : getUnderScoreCaseCode(field)).append("\n");
        sb.append("</if>\n");
        return sb.toString();
    }

    private static String dynamicSqlValueGenerator(boolean isCamelCase, Field field) {
        StringBuilder sb = new StringBuilder();
        sb.append("<if test=\" ").append(field.getName()).append("!=null");
        if (field.getType().getName().equals(String.class.getName())) {
            sb.append(" and ").append(field.getName()).append("!=''");
        }
        sb.append(" \">\n");
        sb.append(generatorMyBatisCode(field));
        sb.append("</if>\n");
        return sb.toString();
    }

    private static String generatorMyBatisCode(Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#{").append(field.getName()).append(",jdbcType=");
        stringBuilder.append(getJDBCType(field));
        stringBuilder.append("},\n");
        return stringBuilder.toString();
    }

    public static String updateSqlGenerator(Class clz, boolean isCamelCase) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = clz.getDeclaredFields();
        sb.append("UPDATE tableName SET ");
        for (Field field : fields) {
            sb.append(dynamicSqlUpdateGenerator(field, isCamelCase));
        }
        if (sb.lastIndexOf(",") > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        return sb.toString();
    }

    private static String dynamicSqlUpdateGenerator(Field field, boolean isCamelCase) {
        StringBuilder sb = new StringBuilder();
        sb.append("<if test=\" ").append(field.getName()).append("!=null");
        if (field.getType().getName().equals(String.class.getName())) {
            sb.append(" and ").append(field.getName()).append("!=''");
        }
        sb.append(" \">\n");
        sb.append(isCamelCase ? getCamelCaseCode(field) : getUnderScoreCaseCode(field));
        sb.append("=").append(generatorMyBatisCode(field));
        sb.append("</if>\n");
        return sb.toString();
    }

    public static String whereCaseGenerator(Class clz, boolean isCamelCase) {
        StringBuilder sb = new StringBuilder();
        sb.append("WHERE 1=1 \n");
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            sb.append(dynamicSqlWhereGenerator(field, isCamelCase));
        }
        if (sb.lastIndexOf(",") > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        return sb.toString();
    }

    private static String dynamicSqlWhereGenerator(Field field, boolean isCamelCase) {
        StringBuilder sb = new StringBuilder();
        sb.append("<if test=\" ").append(field.getName()).append("!=null");
        if (field.getType().getName().equals(String.class.getName())) {
            sb.append(" and ").append(field.getName()).append("!=''");
        }
        sb.append(" \">\n");
        sb.append("and ");
        sb.append(isCamelCase ? getCamelCaseCode(field) : getUnderScoreCaseCode(field));
        sb.append("=").append(generatorMyBatisCode(field).replaceAll(",$", ""));
        sb.append("</if>\n");
        return sb.toString();
    }

    //  几种常用类型的JDBCType转换，新加的要在这个里面加上
    private static String getJDBCType(Field field) {
        if (field.getType().getName().equals(Integer.class.getName())) {
            return "INTEGER";
        } else if (field.getType().getName().equals(Long.class.getName())) {
            return "BIGINT";
        } else if (field.getType().getName().equals(Date.class.getName())) {
            return "TIMESTAMP";
        } else if (field.getType().getName().equals(String.class.getName())) {
            return "VARCHAR";
        } else if (field.getType().getName().equals(Double.class.getName())) {
            return "DOUBLE";
        } else {
            throw new RuntimeException("还没有定义这个类型");
        }

    }
}
