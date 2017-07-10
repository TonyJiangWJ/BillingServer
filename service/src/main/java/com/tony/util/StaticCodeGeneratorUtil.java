package com.tony.util;


import com.tony.annotation.Table;
import com.tony.entity.Budget;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Author jiangwj20966 on 2017/6/2.
 * 偷懒用
 */
public class StaticCodeGeneratorUtil {
    public static void main(String[] args) {
//        generateAll(CostRecord.class, true);
//        System.out.println(insertSqlGenerator(CostRecord.class, true));
//        System.out.println("under_score_case SQL:");
        generateAll(Budget.class, true);
    }

    public static void generateAll(Class clz, boolean isSqlCamelCase) {
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >");
        System.out.println("<mapper namespace=\"com.tony.dao." + clz.getSimpleName() + "Dao\">");
        System.out.println(allSqlGenerator(clz, isSqlCamelCase));
        System.out.println(insertSqlGenerator(clz, isSqlCamelCase));
        System.out.println(updateSqlGenerator(clz, isSqlCamelCase));
//        System.out.println(whereCaseGenerator(clz, isSqlCamelCase));
        System.out.println(pageSqlGenerator(clz, isSqlCamelCase));
        System.out.println("</mapper>");
    }

    public static String allSqlGenerator(Class clz, Boolean isSqlCamelCase) {
        Field[] fields = clz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append("<sql id=\"all\">\n");
        for (Field field : fields) {
            sb.append(isSqlCamelCase ? getCamelCaseCode(field) : getUnderScoreCaseCode(field));
            sb.append(" ").append(field.getName()).append(",\n");
        }
        if (sb.lastIndexOf(",") > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("</sql>");
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
        String tableName = getTableName(clz);
        StringBuilder sb = new StringBuilder();
        Field[] fields = clz.getDeclaredFields();
        sb.append("<insert id=").append("\"insert\" useGeneratedKeys=\"true\" keyProperty=\"id\"");
        sb.append(" parameterType=\"").append(clz.getName()).append("\">\n");
        sb.append("INSERT INTO ");
        if (tableName != null) {
            sb.append(tableName);
        }
        sb.append("  (\n");
        sb.append("<trim suffixOverrides=\",\">\n");
        for (Field field : fields) {
            sb.append(dynamicSqlColumnGenerator(isCamelCaseCode, field));
        }
        sb.append("</trim>\n");
        sb.append(") VALUES (\n");
        int l = sb.length();
        sb.append("<trim suffixOverrides=\",\">");
        for (Field field : fields) {
            sb.append(dynamicSqlValueGenerator(isCamelCaseCode, field));
        }
        sb.append("</trim>\n");
        sb.append(")\n");
        sb.append("</insert>");
        return sb.toString();
    }

    private static String dynamicSqlColumnGenerator(boolean isCamelCase, Field field) {
        StringBuilder sb = new StringBuilder();
        sb.append("<if test=\" ").append(field.getName()).append("!=null");
        if (field.getType().getName().equals(String.class.getName())) {
            sb.append(" and ").append(field.getName()).append("!=''");
        }
        sb.append(" \">\n");
        sb.append(isCamelCase ? getCamelCaseCode(field) : getUnderScoreCaseCode(field)).append(",\n");
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
        return "#{" + field.getName() + ",jdbcType=" +
                getJDBCType(field) +
                "},\n";
    }

    public static String updateSqlGenerator(Class clz, boolean isCamelCase) {
        String tableName = getTableName(clz);
        StringBuilder sb = new StringBuilder();
        Field[] fields = clz.getDeclaredFields();
        sb.append("<update id=\"update\" parameterType=\"").append(clz.getName()).append("\">\n");
        sb.append("UPDATE ").append(tableName).append(" \n");
        sb.append("<trim prefix=\"SET\" suffixOverrides=\",\">\n");
        for (Field field : fields) {
            sb.append(dynamicSqlUpdateGenerator(field, isCamelCase));
        }
        sb.append("</trim>\n");
        sb.append("</update>");
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
        sb.append("<trim prefix=\"WHERE\" prefixOverrides=\"and\">\n");
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            sb.append(dynamicSqlWhereGenerator(field, isCamelCase));
        }
        sb.append("</trim>\n");
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

    public static String pageSqlGenerator(Class clz, boolean isCamelCase) {
        String tableName = getTableName(clz);
        return "<select id=\"page\" parameterType=\"java.util.Map\" resultType=\"" + clz.getName() + "\">\n" +
                "SELECT <include refid=\"all\"/> FROM " + (tableName == null ? "" : getTableName(clz)) + "\n"
                + whereCaseGenerator(clz, isCamelCase) +
                "ORDER BY\n" +
                "<if test=\"orderBy != null\">\n" +
                "${orderBy} ${sort}\n" + "</if>" +
                "<if test=\"orderBy == null or orderBy == '' \">\n" +
                "id ${sort}\n</if>\nLIMIT #{index} , #{offset}\n" + "</select>";
    }

    private static String getTableName(Class clz) {
        String tableName = null;
        if (clz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clz.getAnnotation(Table.class);
            tableName = table.value();
        }
        return tableName;
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