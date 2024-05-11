package com.zhan.matrix_calculation.fraction;

public interface FractionBase {

    /**
     * 分数标准
     */
    final String standard1 = "-?\\d+/-?\\d+";
    final String standard2 = "-?\\d+";

    /**
     * 分数相加
     *
     * @param s2 Fraction
     * @return Fraction
     */
    Fraction add(Fraction s2);

    /**
     * 分数相乘
     *
     * @param s2 Fraction
     * @return Fraction
     */
    Fraction multiply(Fraction s2);

    /**
     * 分数相除
     *
     * @param s2 Fraction
     * @return Fraction
     */
    Fraction device(Fraction s2);

    /**
     * 分数相减
     *
     * @param s2 Fraction
     * @return Fraction
     */
    Fraction reduce(Fraction s2);

    /**
     * 分数转浮点数
     *
     * @return float
     */
    float fractionToFloat();

    /**
     * 获取字符值
     *
     * @return String
     */
    String getValue();

    /**
     * 设置值
     *
     * @param value String
     */
    void setValue(String value);
}
