package com.tony.billing.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;


/**
 * @author jiangwj20966 2018/9/28
 */
public class JsonFormator {

    /**
     * 可视化JSON
     * @param uglyString 原始JSON字符串
     * @return 格式化后的JSON
     */
    public static String prettyJsonString(String uglyString) {
        if (StringUtils.isBlank(uglyString)) {
            return "";
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = new JsonParser().parse(uglyString);
        return gson.toJson(jsonElement);
    }
}
