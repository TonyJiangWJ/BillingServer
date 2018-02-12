package com.tony.billing.constants.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Author by Tony- on 2018/1/27
 */
public enum EnumLiabilityType {
    JD_WHITE("1", "京东白条"),
    ALIPAY_HUABEI("2", "花呗"),
    CREDIT_CARD_ZS("3", "招商银行信用卡"),
    XIAOMI_FENQI("4", "小米分期"),
    ALIPAY_JIEBEI("5", "借呗"),
    JD_GOLD("6", "京东金条"),
    XIAOMI_LOAN("7", "小米贷款"),
    OTHER("99", "其他");
    private String type;
    private String desc;

    private static Map<String, EnumLiabilityType> pool = new HashMap<>();

    static {
        for (EnumLiabilityType types : EnumLiabilityType.values()) {
            pool.put(types.getType(), types);
        }
    }

    EnumLiabilityType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static EnumLiabilityType getEnumByType(String type) {
        return pool.get(type);
    }
}
