package com.zyj.state;

import com.zyj.enumeration.RechargeAmountEnum;

public abstract class UserState {
    /**
     * 这是用户的操作动作。
     * 其实状态模式就是多条件判断的替代方案。
     * 在每个状态下，用户都有可能会执行下面的动作
     *
     * 默认抽奖一次为5个金币
     */
    SkinLotteryContext skinLotteryContext;

    public UserState(SkinLotteryContext skinLotteryContext) {
        this.skinLotteryContext = skinLotteryContext;
    }

    // 充值动作
    public abstract void toCharge(RechargeAmountEnum rechargeAmountEnum);
    // 退款动作
    public abstract void refund(RechargeAmountEnum rechargeAmountEnum);
    // 抽奖动作
    public abstract void lottery();
    // 生成皮肤动作
    public abstract void generateSkin();
    // 获取当前状态
    public abstract void getState();
}
