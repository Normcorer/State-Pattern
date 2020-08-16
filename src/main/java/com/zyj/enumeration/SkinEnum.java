package com.zyj.enumeration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SkinEnum {
    /**
     * 参照国外LOL皮肤翻译
     */
    FRELJORD_ASHE(0 ,"弗雷尔卓德-艾希"),
    MEDIEVAL_TWITCH(1 ,"中世纪-图奇"),
    ASSASSIN_MASTER_YI(2, "刺客-易大师"),
    RECON_TEEMO(3, "侦察兵-提莫"),
    FORSAKEN_JAYCE(4, "被遗弃者-杰斯"),
    ROYAL_SHACO(5, "皇族-萨科"),
    JAIL_BREAK_GRAVES(6, "越狱-格雷福斯"),
    MIDNIGHT_AHRI(7, "午夜-阿狸"),
    PULSEFIRE_RIVEN(8, "脉冲火-锐雯"),
    MECHA_KHA_ZIX(9, "霸天异形-卡兹克");


    private Integer value;
    private String description;

    SkinEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDescriptionByValue(int value) {
        return Arrays.stream(values())
                .filter(x -> x.getValue().equals(value)).findFirst().map(SkinEnum::getDescription).orElse("");
    }

    public List<SkinEnum> getList() {
        return new ArrayList<>(Arrays.asList(values()));
    }
}
