package com.zyj.state;

import com.zyj.constant.LotteryConstant;
import com.zyj.enumeration.RechargeAmountEnum;

/**
 * 已充值状态
 */
public class ChargedState extends UserState {

    public ChargedState(SkinLotteryContext skinLotteryContext) {
        super(skinLotteryContext);
    }

    @Override
    public void toCharge(RechargeAmountEnum rechargeAmountEnum) {
        // 为客户充值
        skinLotteryContext.userAccount.charge(rechargeAmountEnum);
        // 打印余额
        System.out.println("您当前的余额为：" + skinLotteryContext.userAccount.getAmount());
    }

    @Override
    public void refund(RechargeAmountEnum rechargeAmountEnum) {
        // 客户退钱动作逻辑，在JavaBean中实现，判断余额是否充足
        skinLotteryContext.userAccount.refund(rechargeAmountEnum);
        // 如果退费后当前余额为0，则返回未充值状态
        if (skinLotteryContext.userAccount.isEmpty()) {
            skinLotteryContext.setCurrentState(skinLotteryContext.getNoChargeState());
        }
    }

    @Override
    public void lottery() {
        // 判断用户余额是否足够一次抽奖
        if (skinLotteryContext.userAccount.isEnough()) {
            // 减去本次抽奖的费用
            skinLotteryContext.userAccount.setAmount(skinLotteryContext.userAccount.getAmount().subtract(LotteryConstant.DEFAULT_COST));
            // 设置当前状态为抽奖中的状态
            skinLotteryContext.setCurrentState(skinLotteryContext.getLotteryState());
        } else {
            System.out.println("当前账户余额不足，不能进行这次抽奖，当前余额：" + skinLotteryContext.userAccount.getAmount());
        }
    }

    @Override
    public void generateSkin() {
        System.out.println("请在抽奖过后在进行操作");
    }

    @Override
    public void getState() {
        System.out.println("当前状态是：已充值状态");
    }
}
