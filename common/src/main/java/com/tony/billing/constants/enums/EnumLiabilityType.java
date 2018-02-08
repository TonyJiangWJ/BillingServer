package com.tony.billing.constants.enums;

/**
 * Author by Tony- on 2018/1/27
 */
public enum EnumLiabilityType {
    JD_WHITE("1", "京东白条"),
    ALIPAY_HUABEI("2", "花呗"),
    CREDIT_CARD_ZS("3", "招商银行信用卡"),
    XIAOMI_FENQI("4", "小米分期");
    private String type;
    private String desc;

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
}
