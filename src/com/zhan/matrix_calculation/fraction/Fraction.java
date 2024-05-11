package com.zhan.matrix_calculation.fraction;

import static com.zhan.matrix_calculation.frame.MyFrame.jMessage;

/**
 * 分数对象的类，有分数相关计算
 * 以String为值，(String)value= (int)up+"/"+(int)down
 *
 * @author Zhan
 */
public class Fraction implements FractionBase {


    //值String,分子up，分母down
    private String value;
    private int up;
    private int down;

    /**
     * 无参构造器，值为0
     */
    public Fraction() {
        this.value = "0";
        this.up = 0;
        this.down = 1;
    }

    public Fraction(float f) {
        String s = f + "";
        int mid = s.indexOf(".");
        int valueDown = 1;
        for (int i = 0; i < s.length() - mid; i++) {
            valueDown *= 10;
        }
        int valueUp = (int) (f * valueDown);
        Fraction fraction = new Fraction(valueUp, valueDown);
        fraction.reduction();
        this.value = fraction.getValue();
        this.up = fraction.getUp();
        this.down = fraction.getDown();
    }

    /**
     * String为参的构造器
     */
    public Fraction(String value) {
        if (value == null || value.equals("")) {
            this.value = "0";
        } else if (value.matches(standard2)) {
            this.value = value;
            this.up = Integer.parseInt(value);
            this.down = 1;
        } else if (value.matches(standard1)) {
            int mid = value.indexOf("/");
            this.value = value;
            this.up = Integer.parseInt(value.substring(0, mid));
            this.down = Integer.parseInt(value.substring(mid + 1));
        } else {
            System.out.println("分式格式错误");
            jMessage.append("分式格式错误\n");
        }
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    /**
     * 以int类型的分子分母的构造器
     */
    public Fraction(int up, int down) {
        if (down == 0) {
            System.out.println("分子不可为零");
            jMessage.append("分子不可为零\n");
        } else {
            this.up = up;
            this.down = down;
            this.value = up + "/" + down;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        value = value.trim();
        if (value.matches(standard1) || value.matches(standard2)) {
            this.value = value;
        } else {
            System.out.println("分式格式错误");
            jMessage.append("分式格式错误\n");
        }
    }

    //分数相加
    public Fraction add(Fraction s2) {
        this.up = this.up * s2.down + this.down * s2.up;
        this.down = this.down * s2.down;
        this.reduction();
        return this;
    }

    //分数相减
    public Fraction reduce(Fraction s2) {
        this.up = this.up * s2.down - this.down * s2.up;
        this.down = this.down * s2.down;
        this.reduction();
        return this;
    }

    //分数相乘
    public Fraction multiply(Fraction s2) {
        this.up = this.up * s2.up;
        this.down = this.down * s2.down;
        this.reduction();
        return this;
    }

    //分数相除
    public Fraction device(Fraction s2) {
        int valueUp = this.up * s2.down;
        this.down = this.down * s2.up;
        this.up = valueUp;
        this.reduction();
        return this;
    }

    //分数化简
    private void reduction() {
        if (this.up == 0) {
            this.value = "0";
            this.down = 1;
        } else if (this.down == 1) {
            this.value = this.up + "";
        } else if (this.down == -1) {
            this.down = 1;
            this.up = -up;
            this.value = this.up + "";
        } else {
            int a = this.up, b = this.down;
            int r;//最大公因数
            do {
                r = a % b;
                a = b;
                b = r;
            } while (b != 0);
            this.up /= a;
            this.down /= a;
            if (this.down == 1) {
                this.value = up + "";
            } else if (this.down == -1) {
                this.down = 1;
                this.up = -up;
                this.value = up + "";
            } else if (this.down < 0) {
                this.down = -this.down;
                this.up = -this.up;
                this.value = up + "/" + down;
            } else {
                this.value = up + "/" + down;
            }
        }
    }

    //获取当前分数的float值
    public float fractionToFloat() {
        return (0f + up) / down;
    }

    //float转分数
    public static Fraction floatToFraction(float f) {
        String s = f + "";
        int mid = s.indexOf(".");
        int valueDown = 1;
        for (int i = 0; i < s.length() - mid; i++) {
            valueDown *= 10;
        }
        int valueUp = (int) (f * valueDown);
        Fraction fraction = new Fraction(valueUp, valueDown);
        fraction.reduction();
        return fraction;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        Fraction f = (Fraction) obj;
        return this.getValue().equals(f.getValue());
    }

}
