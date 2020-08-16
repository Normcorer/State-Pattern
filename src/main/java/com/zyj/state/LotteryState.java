package com.zyj.state;

import cn.hutool.core.util.RandomUtil;
import com.zyj.enumeration.RechargeAmountEnum;
import com.zyj.enumeration.SkinEnum;

/**
 * 抽奖中状态
 */
public class LotteryState extends UserState {

    public LotteryState(SkinLotteryContext skinLotteryContext) {
        super(skinLotteryContext);
    }

    @Override
    public void toCharge(RechargeAmountEnum rechargeAmountEnum) {
        System.out.println("抽奖中，不支持充值");
    }

    @Override
    public void refund(RechargeAmountEnum rechargeAmountEnum) {
        System.out.println("抽奖中，不支持退款");
    }

    @Override
    public void lottery() {
        System.out.println("正在抽奖中，请稍等之后再点击抽奖");
    }

    @Override
    public void generateSkin() {
        System.out.println("恭喜获得皮肤：" + SkinEnum.getDescriptionByValue(RandomUtil.randomInt(0, 10)) + ", 当前余额：" + skinLotteryContext.userAccount.getAmount());
        //判断抽完这次之后，用户是否还有余额，如果没有返回未充值状态
        if (skinLotteryContext.userAccount.isEmpty()) {
            skinLotteryContext.setCurrentState(skinLotteryContext.getNoChargeState());
        } else {
            skinLotteryContext.setCurrentState(skinLotteryContext.getChargedState());
        }
    }

    @Override
    public void getState() {
        System.out.println("当前状态是：抽奖中状态");
    }
}
