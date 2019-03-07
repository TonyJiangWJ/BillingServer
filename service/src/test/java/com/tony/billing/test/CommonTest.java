package com.tony.billing.test;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;

/**
 * @author jiangwenjie 2019-02-02
 */
public interface CommonTest {

    Logger getLogger();

    default void debugInfo(Object object) {
        getLogger().info("result:{}", JsonFormator.prettyJsonString(JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss")));
    }

    default void debugInfo(String marker, Object object) {
        getLogger().info(marker, JsonFormator.prettyJsonString(JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss")));
    }

    default boolean randomAssert() {
        return Math.ceil(Math.random() * 100) % 2 == 0;
    }


    default String generateValue() {
        String source = "秋后的蝉叫得是那样地凄凉而急促面对着长亭正是傍晚时分一阵急雨刚停住在京都城外设帐饯别却没有畅饮的心绪正在依依不舍的时候船上的人已催着出发握着手互相瞧着满眼泪花直到最后也无言相对千言万语都噎在喉间说不出来想到这回去南方这一程又一程千里迢迢一片烟波那夜雾沉沉的楚地天空竟是一望无边" +
                "自古以来多情的人最伤心的是离别更何况又逢这萧瑟冷落的秋季这离愁哪能经受得了谁知我今夜酒醒时身在何处怕是只有杨柳岸边面对凄厉的晨风和黎明的残月了这一去长年相别相爱的人不在一起我料想即使遇到好天气好风景也如同虚设即使有满腹的情意又能和谁一同欣赏呢";
        int keyLength = (int) (5 + Math.floor(Math.random() * 100) % 10);
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            name.append(source.charAt((int) (Math.floor(Math.random() * source.length()) % source.length())));
        }
        return name.toString();
    }

    default String generateValue(int length) {
        String source = "秋后的蝉叫得是那样地凄凉而急促面对着长亭正是傍晚时分一阵急雨刚停住在京都城外设帐饯别却没有畅饮的心绪正在依依不舍的时候船上的人已催着出发握着手互相瞧着满眼泪花直到最后也无言相对千言万语都噎在喉间说不出来想到这回去南方这一程又一程千里迢迢一片烟波那夜雾沉沉的楚地天空竟是一望无边" +
                "自古以来多情的人最伤心的是离别更何况又逢这萧瑟冷落的秋季这离愁哪能经受得了谁知我今夜酒醒时身在何处怕是只有杨柳岸边面对凄厉的晨风和黎明的残月了这一去长年相别相爱的人不在一起我料想即使遇到好天气好风景也如同虚设即使有满腹的情意又能和谁一同欣赏呢";
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < length; i++) {
            name.append(source.charAt((int) (Math.floor(Math.random() * source.length()) % source.length())));
        }
        return name.toString();
    }
}
