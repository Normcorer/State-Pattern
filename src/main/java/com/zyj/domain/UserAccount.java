package com.zyj.domain;

import com.zyj.constant.LotteryConstant;
import com.zyj.enumeration.RechargeAmountEnum;

import java.math.BigDecimal;

public class UserAccount {
    private String userName;

    private BigDecimal amount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static UserAccount generateAccount(String userName) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName(userName);
        userAccount.setAmount(BigDecimal.ZERO);
        return userAccount;
    }

    public void charge(RechargeAmountEnum rechargeAmountEnum) {
        this.amount = this.amount.add(rechargeAmountEnum.getValue());
    }

    public void refund(RechargeAmountEnum rechargeAmountEnum) {
        if (this.amount.compareTo(rechargeAmountEnum.getValue()) >= 0) {
            this.amount = this.amount.subtract(rechargeAmountEnum.getValue());
            System.out.println("退款成功，当前客户余额：" + this.amount);
        } else {
            System.out.println("退款失败，余额不足，请重新输入退款金额，当前余额：" + this.amount);
        }
    }

    public boolean isEmpty() {
        return this.amount.compareTo(BigDecimal.ZERO) <= 0;
    }

    // 默认抽奖一次5个金币
    public boolean isEnough() {
        return this.amount.compareTo(LotteryConstant.DEFAULT_COST) >= 0;
    }
}
