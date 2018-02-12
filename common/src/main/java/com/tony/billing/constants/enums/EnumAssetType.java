package com.tony.billing.constants.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumAssetType {
    CRASH("1", "现金"),
    ALIPAY_YUE("2", "余额宝"),
    ALIPAY_YULI("3", "余利宝"),
    ALIPAY_FUND("4", "基金"),
    JD_LICAIJIN("5", "理财金"),
    JD_XIAOJINKU("6", "小金库"),
    BANK_CARD_ZS("7", "招商银行卡"),
    OTHER("99", "其他");
    private String type;
    private String desc;
    private static Map<String, EnumAssetType> pool = new HashMap<>();

    static {
        for (EnumAssetType types : EnumAssetType.values()) {
            pool.put(types.getType(), types);
        }
    }

    EnumAssetType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static EnumAssetType getEnumByType(String type) {
        return pool.get(type);
    }
}
