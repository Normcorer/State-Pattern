package com.zyj.state;

import com.zyj.domain.UserAccount;
import com.zyj.enumeration.RechargeAmountEnum;
import com.zyj.state.UserState;
import com.zyj.state.ChargedState;
import com.zyj.state.LotteryState;
import com.zyj.state.NoChargeState;

public class SkinLotteryContext {
    // 当前状态
    UserState currentState;
    // 未充值状态
    UserState noChargeState;
    // 已充值状态
    UserState chargedState;
    // 抽奖中状态
    UserState lotteryState;

    UserAccount userAccount;

    public SkinLotteryContext(UserAccount userAccount) {
        // 获取客户账户
        this.userAccount = userAccount;

        // 初始化当前状态，将当前状态设置为未充值状态
        this.currentState = new NoChargeState(this);
        noChargeState = new NoChargeState(this);
        chargedState = new ChargedState(this);
        lotteryState = new LotteryState(this);
    }

    // Handler处理者
    public void toCharge(RechargeAmountEnum rechargeAmountEnum) {
        currentState.toCharge(rechargeAmountEnum);
    }

    public void refund(RechargeAmountEnum rechargeAmountEnum) {
        currentState.refund(rechargeAmountEnum);
    }

    public void lottery() {
        currentState.lottery();
        // 抽奖结束，自动生成皮肤
        generateSkin();
    }

    public void generateSkin() {
        currentState.generateSkin();
    }

    public void getStatus() {
        currentState.getState();
    }

    // 设置当前的状态
    void setCurrentState(UserState currentState) {
        this.currentState = currentState;
    }

    UserState getCurrentState() {
        return currentState;
    }

    UserState getNoChargeState() {
        return noChargeState;
    }

    UserState getChargedState() {
        return chargedState;
    }

    UserState getLotteryState() {
        return lotteryState;
    }
}
