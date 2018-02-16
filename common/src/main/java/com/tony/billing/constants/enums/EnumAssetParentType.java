package com.tony.billing.constants.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Author by Tony- on 2018/1/27
 */
public enum EnumAssetParentType {
    CRASH("1", "现金"),
    ALIPAY("2", "支付宝"),
    JD("3", "京东"),
    BANK_CARD("4", "银行卡"),
    OTHER("99", "其他");

    private String type;
    private String desc;
    private static Map<String, EnumAssetParentType> pool = new HashMap<>();

    static {
        for (EnumAssetParentType types : EnumAssetParentType.values()) {
            pool.put(types.getType(), types);
        }
    }


    EnumAssetParentType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static EnumAssetParentType getEnumByType(String type) {
        return pool.get(type);
    }
}
