package com.tony.billing.constants.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Author by Tony- on 2018/1/27
 */
public enum EnumLiabilityType {
    JD_WHITE("1", "3", "京东白条"),
    ALIPAY_HUABEI("2", "1", "花呗"),
    CREDIT_CARD_ZS("3", "2", "招商银行信用卡"),
    XIAOMI_FENQI("4", "4", "小米分期"),
    ALIPAY_JIEBEI("5", "1", "借呗"),
    JD_GOLD("6", "3", "京东金条"),
    XIAOMI_LOAN("7", "4", "小米贷款"),
    CREDIT_CARD_ZH("8", "2", "中行信用卡"),
    OTHER("99", "99", "其他");
    private String type;
    private String parentType;
    private String desc;

    private static Map<String, EnumLiabilityType> pool = new HashMap<>();

    static {
        for (EnumLiabilityType types : EnumLiabilityType.values()) {
            pool.put(types.getType(), types);
        }
    }

    EnumLiabilityType(String type, String parentType, String desc) {
        this.type = type;
        this.parentType = parentType;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getParentType() {
        return parentType;
    }

    public String getDesc() {
        return desc;
    }

    public static EnumLiabilityType getEnumByType(String type) {
        return pool.get(type);
    }
}
