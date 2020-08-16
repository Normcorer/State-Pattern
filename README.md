## 前言
在重构这本书中，我接触到了状态模式，重构中把State/Strategy两个模式放在一起说，当时我也不太理解，因为光看类图，这两个模式真的差别不大。之后我会在写一篇博客主要讲诉这两个设计模式的区别。
在这篇博客中我学习了一遍状态模式，为了方便理解，写了一个简单的英雄联盟抽皮肤的小案例。案例代码我会放在Github上，在本文最后会附上地址。
***

## 定义与特点
**定义：**
对有`状态`的对象，把复杂的判断逻辑提取到不同的状态对象中，允许状态对象在其内部状态改变时改变其行为。主要是解决的这个对象的复杂状态的切换。

**优点：**
>1.状态模式将与特定状态相关的行为局部化到一个状态中，并且将不同状态的行为分割开来，满足“单一职责原则”。
2.减少对象间的相互依赖。将不同的状态引入独立的对象中会使得状态转换变得更加明确，且减少对象间的相互依赖。
3.有利于程序的扩展。通过定义新的子类很容易地增加新的状态和转换。

**缺点：**
>1.状态模式的使用必然会增加系统的类与对象的个数。
2.状态模式的结构与实现都较为复杂，如果使用不当会导致程序结构和代码的混乱。

状态模式主要允许一个对象在其内部状态发生改变时同时改变他的行为，看起来似乎修改了它的类。我这里就不总结他的定义与特点了，下面文章总结已经很清楚了。
[状态模式定义与特点原文链接](http://c.biancheng.net/view/1388.html)
***

## 状态模式的结构
状态模式包含以下主要角色：
1. 环境（Context）角色：也称为上下文，它定义了客户感兴趣的接口，维护一个当前状态，并将与状态相关的操作委托给当前状态对象来处理。
2. 抽象状态（State）角色：定义一个接口，用以封装环境对象中的特定状态所对应的行为，根据业务需求可以写多个方法来改变当前的对象的状态。
3. 具体状态（Concrete State）角色：实现抽象状态所对应的行为。

如下是状态模式的结构：
![状态模式结构](https://molzhao-pic.oss-cn-beijing.aliyuncs.com/2020-08-16/%E7%8A%B6%E6%80%81%E6%A8%A1%E5%BC%8F.jpg)
***

## 简单英雄联盟抽皮肤案例
### 案例介绍
以简单英雄联盟抽皮肤为例，当一个用户的操作有（充值、退款、抽奖、获得皮肤）四个状态，根据用户不同的操作状态，会有不同的行为。
1.在未充值状态，用户只能选择充值这个动作，另外动作均不可以选择。
2.在已充值这个状态，用户可以继续充值，或者选择退款，当退款额度大于用户余额的时候会提示用户，当用户把余额退完会回到未充值状态。当用户账户有余额的时候可以操作抽奖动作。
3.在抽奖的时候，如果扣费成功，则会生成皮肤，如果扣费失败，则会提醒用户余额不足，并且根据余额多少回到未充值或者已充值状态。
![简单英雄联盟抽奖流程图](https://molzhao-pic.oss-cn-beijing.aliyuncs.com/2020-08-16/%E7%AE%80%E5%8D%95%E8%8B%B1%E9%9B%84%E8%81%94%E7%9B%9F%E6%8A%BD%E7%9A%AE%E8%82%A4%E6%B5%81%E7%A8%8B.png)
可以看的出来每个状态的切换都伴随这很多`IF-ELSE`，如果不采用设计模式，我们在扩展功能的时候会非常麻烦。

### 具体实现
#### 环境角色（Context）
这对应的是状态模式的环境角色（Context），他维护着用户的当前状态`currentState`，定义了客户的行为方法，还提供了一个设置当前状态的方法`setCurrentState(UserState currentState)`，来方便后面状态切换时候的使用。
```java
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

```
#### 抽象角色（State）
这对应的是状态模式的抽象角色，他主要写了对象在不同状态下对应的行为，比如说用户的充值行为`toCharge()`,用户的退费行为`refund()`等。
```java
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
```

#### 具体状态（Concrete State）
##### 未充值状态
```java
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
```

##### 已充值状态
```java
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


```

##### 抽奖状态
```java
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
```
#### 运行结果
```java
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

```
```
当前状态是：未充值状态
您当前的余额为：30
当前状态是：已充值状态
退款成功，当前客户余额：20
当前状态是：已充值状态
退款成功，当前客户余额：10
当前状态是：已充值状态
恭喜获得皮肤：脉冲火-锐雯, 当前余额：5
当前状态是：已充值状态
恭喜获得皮肤：霸天异形-卡兹克, 当前余额：0
当前状态是：未充值状态
对不起，未充值不能进行抽奖
对不起，未充值不能生成皮肤
当前状态是：未充值状态
```
至于我是怎么生成皮肤的，我添加了皮肤的枚举类作为皮肤池，通过随机数随机抽取皮肤池中的皮肤实现，这样我们每增加一个皮肤只需要新增一个枚举就好了，符合开闭原则。
下面是我的皮肤枚举类：
```java
package com.zyj.enumeration;

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
```
***

> 博主是97年的，在杭州从事Java，如果有小伙伴，想要一起交流学习的，欢迎添加博主微信。
![weChat](https://molzhao-pic.oss-cn-beijing.aliyuncs.com/Common/WeChat.png)