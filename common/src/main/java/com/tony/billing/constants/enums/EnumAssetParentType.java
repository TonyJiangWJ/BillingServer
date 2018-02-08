package com.tony.billing.constants.enums;

/**
 * Author by Tony- on 2018/1/27
 */
public enum EnumAssetParentType {
    CRASH("1", "现金"),
    ALIPAY("2", "支付宝"),
    JD("3", "京东"),
    BANK_CARD("4", "银行卡");

    private String type;
    private String desc;

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
}
