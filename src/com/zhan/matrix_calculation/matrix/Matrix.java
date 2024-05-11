package com.zhan.matrix_calculation.matrix;

import com.zhan.matrix_calculation.fraction.Fraction;

import static com.zhan.matrix_calculation.frame.MyFrame.jMessage;

/**
 * 矩阵类
 * 矩阵的方法不会改变矩阵本身，而是返回一个新的矩阵
 *
 * @author zhan
 */
public class Matrix implements MatrixBase {
    private Fraction[][] fss;
    private int row;
    private int column;

    public Matrix(String[][] fss) {
        this.row = fss.length;
        this.column = fss[0].length;
        this.fss = new Fraction[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                this.fss[i][j] = new Fraction(fss[i][j]);
            }
        }
    }

    /**
     * 每个单元都为value的矩阵
     */
    public Matrix(String value, int row, int column) {
        this.row = row;
        this.column = column;
        this.fss = new Fraction[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                fss[i][j] = new Fraction(value);
            }
        }
    }


    public Matrix(Fraction[][] fss) {
        this.fss = fss;
        this.row = fss.length;
        this.column = fss[0].length;
    }

    public Fraction[][] getFss() {
        return fss;
    }

    public void setFss(Fraction[][] fss) {
        this.fss = fss;
        this.row = fss.length;
        this.column = fss[0].length;
    }

    //求矩阵的转置矩阵
    public Matrix getTranspose() {
        Fraction[][] mss = new Fraction[column][row];
        for (int i = 0; i < mss.length; i++) {
            for (int j = 0; j < mss[i].length; j++) {
                mss[i][j] = fss[j][i];
            }
        }
        return new Matrix(mss);
    }

    //求矩阵的值
    public Fraction getValue() {
        if (!isSquare()) {
            System.out.println("矩阵不是方阵，没有值");
            jMessage.append("矩阵不是方阵，没有值\n");
            return null;
        } else if (row == 1) {
            return fss[0][0];
        } else if (row == 2) {
            Fraction mid = new Fraction(fss[0][0].getValue());
            Fraction mid2 = new Fraction(fss[1][0].getValue());
            return mid
                    .multiply(fss[1][1])
                    .reduce(mid2.multiply(fss[0][1]));
        } else {
            Fraction f = new Fraction("0");
            for (int i = 0; i < row; i++) {
                Matrix sub = this.getSubform(1, i + 1);
                String s = i % 2 == 0 ? "1" : "-1";
                f.add(sub.getValue().multiply(new Fraction(s).multiply(fss[0][i])));
            }
            return f;
        }
    }

    //求行列式的余子式
    private Matrix getSubform(int Row, int Column) {
        Fraction[][] M = new Fraction[row - 1][column - 1];
        for (int i = 0; i < M.length; i++) {
            if (i < Row - 1) {
                for (int j = 0; j < M.length; j++) {
                    if (j < Column - 1) {
                        M[i][j] = fss[i][j];
                    } else {
                        M[i][j] = fss[i][j + 1];
                    }
                }
            } else {
                for (int k = 0; k < M.length; k++) {
                    if (k < Column - 1) {
                        M[i][k] = fss[i + 1][k];
                    } else {
                        M[i][k] = fss[i + 1][k + 1];
                    }
                }
            }
        }
        return new Matrix(M);
    }

    //矩阵相乘AB
    public Matrix multiplyWithMatrix(Matrix matrixRight) {
        if (column != matrixRight.row) {
            jMessage.append("左矩阵的列数不等于右矩阵的行数，不可乘\n");
            System.out.println("左矩阵的列数不等于右矩阵的行数，不可乘");
            return null;
        } else {
            int n = matrixRight.column;
            Matrix value = new Matrix("0", row, matrixRight.column);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < column; k++) {
                        Fraction fraction = new Fraction(fss[i][k].getValue());
                        value.getFss()[i][j].add(fraction.multiply(matrixRight.fss[k][j]));
                    }
                }
            }
            return value;
        }
    }

    //数乘矩阵 aA
    public Matrix multiplyWithNum(Fraction f) {
        Fraction[][] fractions = this.fss.clone();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                fractions[i][j].multiply(f);
            }
        }
        return new Matrix(fractions);
    }

    //求伴随矩阵 A^*
    public Matrix getAdJoint() {
        if (!isSquare()) {
            System.out.println("不是方阵，没有伴随矩阵");
            jMessage.append("不是方阵，没有伴随矩阵\n");
            return null;
        } else {
            Fraction[][] f = new Fraction[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    String s = (i + j) % 2 == 0 ? "1" : "-1";
                    Fraction fraction = new Fraction(s);
                    f[i][j] = fraction.multiply(getSubform(i + 1, j + 1).getValue());
                }
            }
            return new Matrix(f);
        }
    }

    //求逆矩阵 A^-1
    public Matrix getInverse() {
        if (!isSquare()) {
            System.out.println("不是方阵，没有逆矩阵");
            jMessage.append("不是方阵，没有逆矩阵\n");
            return null;
        }
        Fraction f = getValue();
        if (f.getValue().equals("0")) {
            System.out.println("矩阵的值为0，矩阵不可逆");
            jMessage.append("矩阵的值为0，矩阵不可逆\n");
            return null;
        } else {
            Matrix adJoint = getAdJoint();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    adJoint.getFss()[i][j].device(f);
                }
            }
            return adJoint.getTranspose();
        }
    }

    //矩阵是否可交换 AB==BA
    public boolean canExchangeable(Matrix matrix2) {
        if (this.isSquare() && matrix2.isSquare()) {
            return this.multiplyWithMatrix(matrix2).equals(matrix2.multiplyWithMatrix(this));
        } else {
            jMessage.append("两个矩阵不可即左乘又右乘\n");
            System.out.println("两个矩阵不可即左乘又右乘");
            return false;
        }
    }

    //是否为对称矩阵
    public boolean isSymmetric() {
        Matrix m = this.getTranspose();
        return this.equals(m);
    }

    //是否为反对称矩阵
    public boolean isAntisymmetric() {
        Matrix m = this.getTranspose();
        m = m.multiplyWithNum(new Fraction("-1"));
        return this.equals(m);
    }

    //求矩阵的秩
    public Integer getRank() {
        int rank = Math.min(row, column);
        Fraction[][] mid = this.clone();
        for (int i = 0; i < rank; i++) {
            if (!fss[i][i].equals(new Fraction("0"))) {
                for (int j = 0; j < row; j++) {
                    if (j != i) {
                        Fraction f1 = new Fraction(fss[j][i].getValue());
                        Fraction factor = f1.device(fss[i][i]);
                        for (int k = i; k < column; k++) {
                            Fraction f2 = new Fraction(factor.getValue());
                            fss[j][k].reduce(f2.multiply(fss[i][k]));
                        }
                    }
                }
            } else {
                boolean reduce = true;
                for (int j = i + 1; j < row; j++) {
                    if (!fss[j][i].equals(new Fraction("0"))) {
                        Fraction[] temp = fss[i];
                        fss[i] = fss[j];
                        fss[j] = temp;
                        reduce = false;
                        break;
                    }
                }
                if (reduce) {
                    rank--;
                    for (int j = 0; j < row; j++) {
                        fss[j][i] = fss[j][rank];
                    }
                }
                i--;
            }
        }
        this.setFss(mid);
        return rank;
    }

    //将方阵化为阶梯型矩阵
    public Matrix getStep() {
        Fraction[][] mid = this.clone();
        for (int i = 0; i < row; i++) {
            int pivotRow = i;
            while (pivotRow < row && fss[pivotRow][i].equals(new Fraction("0"))) {
                pivotRow++;
            }
            if (pivotRow == row) {
                continue;
            }
            swapRows(i, pivotRow);
            rowMultiply(i, new Fraction("1").device(fss[i][i]));
            for (int j = i + 1; j < row; j++) {
                addRows(j, i, new Fraction("-1").multiply(fss[j][i]));
            }
        }
        Fraction[][] m = this.clone();
        this.setFss(mid);
        return new Matrix(m);
    }

    //交换两行
    private void swapRows(int row1, int row2) {
        Fraction[] temp = fss[row1];
        fss[row1] = fss[row2];
        fss[row2] = temp;
    }

    //行乘以某数
    private void rowMultiply(int row, Fraction scalar) {
        for (int j = 0; j < column; j++) {
            fss[row][j].multiply(scalar);
        }
    }

    //行1加上行2
    private void addRows(int row1, int row2, Fraction scalar) {
        for (int j = 0; j < column; j++) {
            Fraction f = new Fraction(scalar.getValue());
            fss[row1][j].add(f.multiply(fss[row2][j]));
        }
    }

    //化为最简形矩阵
    public Matrix getMinimalist() {
        Fraction[][] f = new Fraction[row][column];
        Matrix step1 = this.getStep();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                f[i][j] = step1.getFss()[row - 1 - i][column - 1 - j];
            }
        }
        Matrix m2 = new Matrix(f);
        Matrix step2 = m2.getStep();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                m2.getFss()[i][j] = step2.getFss()[row - 1 - i][column - 1 - j];
            }
        }
        return m2.getStep();
    }

    //矩阵是否为方阵
    private boolean isSquare() {
        for (Fraction[] fs : fss) {
            if (fs.length != fss.length) {
                return false;
            }
        }
        return true;
    }

    //获取fss的float值
    public float[][] nextFloats() {
        float[][] floats = new float[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                floats[i][j] = fss[i][j].fractionToFloat();
            }
        }
        return floats;
    }

    @Override
    public boolean equals(Object o) {
        Matrix m2 = (Matrix) o;
        if (this.row != m2.row || this.column != m2.column) {
            return false;
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (!this.getFss()[i][j].equals(m2.getFss()[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Fraction[] fs : this.fss) {
            for (Fraction f : fs) {
                sb.append(f).append("  \t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    protected Fraction[][] clone() {
        Fraction[][] fractions = new Fraction[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                fractions[i][j] = new Fraction(getFss()[i][j].getValue());
            }
        }
        return fractions;
    }

//    public static void main(String[] args) {
//        String[][] s = new String[][]{
//                {"3","2","4","2"},
//                {"2","7","5","9"},
//                {"6","4","8","4"}
//        };
//        Matrix m = new Matrix(s);
//        System.out.println(m.getStep());
//        System.out.println(m.getMinimalist());
//        System.out.println(m);
//    }
}
