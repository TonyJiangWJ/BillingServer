package com.tony.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author by tonyjiang on 16-8-12.
 * 生成唯一的编码用的工具类
 */
public class CodeGeneratorUtil {
    private final static AtomicInteger counter = new AtomicInteger();

    private final static String ZEO_SEQ = "0000000000000000000000";

    //  获取指定长度的唯一编码
    public static String getCode(int size) {
        if (size < 14) {
            return mod(counter.addAndGet(1), size);
        }
        StringBuilder code = new StringBuilder();
        if (counter.get() == 999999999) {
            synchronized (counter) {
                counter.set(0);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        code.append(sdf.format(new Date())).append(
                mod(counter.addAndGet(1), size - 14));
        return code.toString();
    }

    private static String mod(int value, int size) {
        if (("" + value).length() == size) {
            return "" + value;
        }

        if (("" + value).length() > size) {
            return ("" + value).substring(0, size);
        } else {
            return ZEO_SEQ.substring(0, size - ("" + value).length()) + value;
        }
    }
}
