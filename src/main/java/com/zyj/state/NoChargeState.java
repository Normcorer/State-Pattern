package com.zyj.state;

import com.zyj.enumeration.RechargeAmountEnum;

/**
 * 未充值状态
 */
public class NoChargeState extends UserState {

    public NoChargeState(SkinLotteryContext skinLotteryContext) {
        super(skinLotteryContext);
    }

    @Override
    public void toCharge(RechargeAmountEnum rechargeAmountEnum) {
        // 为客户充值
        skinLotteryContext.userAccount.charge(rechargeAmountEnum);
        // 打印余额
        System.out.println("您当前的余额为：" + skinLotteryContext.userAccount.getAmount());
        // 已经充值，转成已充值状态
        skinLotteryContext.setCurrentState(skinLotteryContext.getChargedState());
    }

    @Override
    public void refund(RechargeAmountEnum rechargeAmountEnum) {
        System.out.println("对不起，您当前处于未充值状态");
    }

    @Override
    public void lottery() {
        System.out.println("对不起，未充值不能进行抽奖");
    }

    @Override
    public void generateSkin() {
        System.out.println("对不起，未充值不能生成皮肤");
    }

    @Override
    public void getState() {
        System.out.println("当前状态是：未充值状态");
    }
}
