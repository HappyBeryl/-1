package entity;

import lombok.Data;

/*
订单项表
 */
@Data
public class OrderItem {
    private Integer id;
    private String orderId;
    private Integer goodsId;
    private String goodsName;
    private String goodsIntroduce;
    private Integer goodsNum;
    private String goodsUnit;
    private Integer goodsPrice;
    private Integer goodsDiscount;

    //单位为元
    public double getGoodsPrice() {
        return goodsPrice * 1.0 / 100;
    }

    //单位为分
    public int getGoodsPriceInt() {
        return goodsPrice;
    }

}
