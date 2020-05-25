package entity;

import lombok.Data;

/*
商品
 */
@Data
public class Goods {
    private Integer id;
    private String name;
    private String introduce;
    private Integer stock;
    private String unit;
    private Integer price;//商品价格  12.34  -》 1234
    private Integer discount;

    //单位为元
    public double getPrice() {
        return price * 1.0 / 100;
    }

    //单位为分
    public int getPriceInt() {
        return price;
    }

}
