package com.tony.billing.constants.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TonyJiang on 2018/2/12
 */
@Deprecated
public enum EnumLiabilityParentType {
    ALIPAY("1", "支付宝"),
    CREDIT("2", "信用卡"),
    JD("3", "京东"),
    XIAOMI("4", "小米"),
    OTHER("99", "其他");

    private String type;
    private String desc;

    private static Map<String, EnumLiabilityParentType> pool = new HashMap<>();

    static {
        for (EnumLiabilityParentType types : EnumLiabilityParentType.values()) {
            pool.put(types.getType(), types);
        }
    }

    EnumLiabilityParentType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static EnumLiabilityParentType getEnumByType(String type) {
        return pool.get(type);
    }

    /**
     * return unmodifiableList
     *
     * @return
     */
    public static List<EnumLiabilityParentType> toList() {
        return Arrays.asList(EnumLiabilityParentType.values());
    }
}
