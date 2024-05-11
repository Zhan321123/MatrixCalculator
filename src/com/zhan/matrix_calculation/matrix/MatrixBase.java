package com.zhan.matrix_calculation.matrix;

import com.zhan.matrix_calculation.fraction.Fraction;

/**
 * MatrixBase
 * 矩阵接口
 */
public interface MatrixBase {
    /**
     * 获取矩阵分数值
     *
     * @return Fraction[][]
     */

    Fraction[][] getFss();

    /**
     * 设置矩阵分数值
     *
     * @param fss Fraction[][]
     */
    void setFss(Fraction[][] fss);

    /**
     * 获取矩阵阶数
     *
     * @return Matrix
     */
    Matrix getStep();

    /**
     * 获取矩阵转置
     *
     * @return Matrix
     */
    Matrix getTranspose();

    /**
     * 矩阵乘法
     *
     * @param matrixRight right matrix
     * @return Matrix
     */
    Matrix multiplyWithMatrix(Matrix matrixRight);

    /**
     * 数乘矩阵
     *
     * @param f fraction
     * @return Matrix
     */
    Matrix multiplyWithNum(Fraction f);

    /**
     * 化为最简形矩阵
     *
     * @return Matrix
     */
    Matrix getMinimalist();

    /**
     * 获取伴随矩阵
     *
     * @return Matrix
     */
    Matrix getAdJoint();

    /**
     * 获取逆矩阵
     *
     * @return Matrix
     */
    Matrix getInverse();

    /**
     * 判断矩阵是否为对称矩阵
     *
     * @return boolean
     */
    boolean isSymmetric();

    /**
     * 判断矩阵是否为反对称矩阵
     *
     * @return boolean
     */
    boolean isAntisymmetric();

    /**
     * 获取矩阵的秩
     *
     * @return Integer
     */
    Integer getRank();

    /**
     * 获取矩阵的float阵
     *
     * @return float[][]
     */
    float[][] nextFloats();


}
