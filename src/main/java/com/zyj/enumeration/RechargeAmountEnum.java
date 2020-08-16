package com.zyj.enumeration;

import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RechargeAmountEnum {
    TEN(BigDecimal.TEN, "十个金币"),
    THIRTY(new BigDecimal(30), "三十个金币");

    private BigDecimal value;
    private String description;

    RechargeAmountEnum(BigDecimal value, String description) {
        this.value = value;
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDescriptionByValue(BigDecimal value) {
        return Arrays.stream(values())
                .filter(x -> x.getValue().equals(value)).findFirst().map(RechargeAmountEnum::getDescription).orElse(StrUtil.EMPTY);
    }

    public List<RechargeAmountEnum> getList() {
        return new ArrayList<>(Arrays.asList(values()));
    }
}
