package com.zyj;

import com.zyj.domain.UserAccount;
import com.zyj.enumeration.RechargeAmountEnum;
import com.zyj.state.SkinLotteryContext;

public class SkinLotteryClient {
    public static void main(String[] args) {
        SkinLotteryContext skinLotteryContext = new SkinLotteryContext(UserAccount.generateAccount("用户1"));
        skinLotteryContext.getStatus();
        skinLotteryContext.toCharge(RechargeAmountEnum.THIRTY);
        skinLotteryContext.getStatus();
        // 第一次退费
        skinLotteryContext.refund(RechargeAmountEnum.TEN);
        skinLotteryContext.getStatus();
        // 第二次退费
        skinLotteryContext.refund(RechargeAmountEnum.TEN);
        skinLotteryContext.getStatus();
        // 第一次抽奖
        skinLotteryContext.lottery();
        skinLotteryContext.getStatus();
        // 第二次抽奖
        skinLotteryContext.lottery();
        skinLotteryContext.getStatus();
        // 第三次抽奖
        skinLotteryContext.lottery();
        skinLotteryContext.getStatus();
    }
}
