package com.zhan.matrix_calculation.frame;

import com.zhan.matrix_calculation.fraction.Fraction;
import com.zhan.matrix_calculation.matrix.Matrix;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * 窗口
 */
public class MyFrame extends JFrame {

    final int width = 1400;
    final int height = 500;

    public static JTextArea jMessage = new JTextArea(
            "没有提示\n" +
            "可以在输入框输入整数或分数\n");
    Box box = new Box(0);
    Matrix copy;

    //左方块
    JTabbedPane jTabbedPane = new JTabbedPane(SwingConstants.TOP);
    GridLayout glIn1 = new GridLayout(4, 4, 10, 10);
    JPanel jpIn1 = new JPanel(glIn1);
    GridLayout glIn2 = new GridLayout(3, 3, 10, 10);
    JPanel jpIn2 = new JPanel(glIn2);
    JPopupMenu jPopupMenuIn = new JPopupMenu();

    private void init1() {
        jTabbedPane.addTab("矩阵1", jpIn1);
        jTabbedPane.addTab("矩阵2", jpIn2);

        Border border = new TitledBorder(new LineBorder(Color.black), "输入");
        Box b = new Box(1);
        b.setBorder(border);

        for (int i = 0; i < glIn1.getRows() * glIn1.getColumns(); i++) {
            jpIn1.add(new JTextField());
        }
        for (int i = 0; i < glIn2.getRows() * glIn2.getColumns(); i++) {
            jpIn2.add(new JTextField());
        }

        b.add(jTabbedPane);
        b.setPreferredSize(new Dimension(500, height));
        box.add(b);

        JMenuItem m1 = new JMenuItem("复制");
        JMenuItem m2 = new JMenuItem("粘贴");
        m1.addActionListener(e -> copy = readJpIn());
        m2.addActionListener(e -> {
            if (this.copy == null) {
                jMessage.append("您还未曾有复制过，粘贴什么？？？\n");
            } else {
                setGlIn(copy.getFss().length, copy.getFss()[0].length);
                writeToJpIn(copy);
            }
        });
        jPopupMenuIn.add(m1);
        jPopupMenuIn.add(m2);

        jpIn1.setComponentPopupMenu(jPopupMenuIn);
        jpIn2.setComponentPopupMenu(jPopupMenuIn);
    }

    //从JPanel1中提取JTextFiled[]
    private JTextField[] getJpInValue() {
        JPanel jPanel = (JPanel) jTabbedPane.getSelectedComponent();
        Component[] components = jPanel.getComponents();
        JTextField[] jTextFields = new JTextField[components.length];
        for (int i = 0; i < components.length; i++) {
            jTextFields[i] = (JTextField) components[i];
        }
        return jTextFields;
    }

    //将JPanelIn读取到Matrix
    public Matrix readJpIn() {
        JPanel jPanel = (JPanel) jTabbedPane.getSelectedComponent();
        JTextField[] jTextFields = getJpInValue();
        GridLayout g = (GridLayout) jPanel.getLayout();
        int row = g.getRows();
        int column = g.getColumns();
        String[][] fss = new String[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                fss[i][j] = jTextFields[i * column + j].getText();
            }
        }
        return new Matrix(fss);
    }

    //将Matrix写入到JPanelIn
    public void writeToJpIn(Matrix matrix) {
        JPanel jPanel = (JPanel) jTabbedPane.getSelectedComponent();
        JTextField[] jTextFields = getJpInValue();
        GridLayout g = (GridLayout) jPanel.getLayout();
        int column = g.getColumns();
        Fraction[][] fss = matrix.getFss();
        for (int i = 0; i < fss.length; i++) {
            for (int j = 0; j < fss[i].length; j++) {
                jTextFields[i * column + j].setText(fss[i][j].toString());
            }
        }
    }

    //重新设置JpIn的GridLayout
    private void setGlIn(int row, int column) {
        if (row>10 || column>10){
            jMessage.append("真的要设置这么多吗？\n");
            return;
        }
        JPanel jp = (JPanel) jTabbedPane.getSelectedComponent();
        GridLayout g = (GridLayout) jp.getLayout();
        g.setRows(row);
        g.setColumns(column);
        jp.removeAll();
        for (int i = 0; i < row * column; i++) {
            jp.add(new JTextField());
        }
        jp.updateUI();
    }

    //中间方块
    Matrix previousMatrix;
    Matrix currentMatrix;
    int previousMatrixIndex = 0;
    int currentMatrixIndex = 0;
    private void init2() {
        previousMatrix = readJpIn();
        currentMatrix = readJpIn();
        Box b = new Box(1);

        Border border1 = new TitledBorder(new LineBorder(Color.black), "输入功能");
        JPanel jp1 = new JPanel(new GridLayout(3,3,5,5));
        jp1.setPreferredSize(new Dimension(400, 150));
        jp1.setBorder(border1);
        JButton jb00 = new JButton("设置矩阵行列数");
        JButton jb04 = new JButton("添加矩阵");
        JButton jb01 = new JButton("全部清空");
        JButton jb02 = new JButton("空位补 0");
        JButton jb03 = new JButton("空位补 1");
        initDialog();
        jb00.addActionListener(e -> jDialog.setVisible(true));
        jb04.addActionListener(e -> {
            int count = jTabbedPane.getTabCount();
            if (count >= 8) {
                jMessage.append("弄这么多矩阵干嘛？？？\n");
            } else {
                JPanel jp = new JPanel(new GridLayout(3, 3, 10, 10));
                jTabbedPane.addTab("矩阵" + (count + 1), jp);
                for (int i = 0; i < 9; i++) {
                    jp.add(new JTextField());
                }
                jp.setComponentPopupMenu(jPopupMenuIn);
            }
        });
        jb01.addActionListener(e -> {
            for (JTextField jtf : getJpInValue()) {
                jtf.setText("");
            }
        });
        jb02.addActionListener(e -> {
            for (JTextField jtf : getJpInValue()) {
                if (jtf.getText().equals("")) {
                    jtf.setText("0");
                }
            }
        });
        jb03.addActionListener(e -> {
            for (JTextField jtf : getJpInValue()) {
                if (jtf.getText().equals("")) {
                    jtf.setText("1");
                }
            }
        });
        jp1.add(jb00);
        jp1.add(jb04);
        jp1.add(jb01);
        jp1.add(jb02);
        jp1.add(jb03);

        Border border2 = new TitledBorder(new LineBorder(Color.black), "输出功能");
        JPanel jp2 = new JPanel(new GridLayout(5,3,5,5));
        jp2.setPreferredSize(new Dimension(400, 250));
        jp2.setBorder(border2);
        JButton jb1 = new JButton("矩阵求值");
        JButton jb2 = new JButton("矩阵转置");
        JButton jb3 = new JButton("矩阵求逆");
        JButton jb4 = new JButton("伴随矩阵");
        JButton jb5 = new JButton("数乘矩阵");
        JTextField jtf5 = new JTextField();
        JButton jb6 = new JButton("矩阵的秩");
        JButton jb7 = new JButton("阶梯型矩阵");
        JButton jb10 = new JButton("最简形矩阵");
        JButton jb8 = new JButton("是对称矩阵?");
        JButton jb9 = new JButton("是反对称矩阵?");
        JButton jbExchangeable = new JButton("矩阵是否可交换");
        JButton jbMatrixMultiply = new JButton("矩阵矩阵相乘");
        jb1.addActionListener(e->{
            Fraction value = readJpIn().getValue();
            if (value!=null){
                jMessage.append("矩阵的值为：" + value+"\n");
            }
        });
        jb2.addActionListener(e -> {
            Matrix m = readJpIn().getTranspose();
            writeToJpOut(m);
        });
        jb3.addActionListener(e -> {
            Matrix m = readJpIn().getInverse();
            writeToJpOut(m);
        });
        jb4.addActionListener(e -> {
            Matrix m = readJpIn().getAdJoint();
            writeToJpOut(m);
        });
        jb5.addActionListener(e -> {
            String text = jtf5.getText();
            if (text.equals("")){
                jMessage.append("请在框中输入分数或整数\n");
            }else {
                Fraction f = new Fraction(text);
                Matrix m = readJpIn().multiplyWithNum(f);
                writeToJpOut(m);
            }
        });
        jb6.addActionListener(e -> {
            Integer integer = readJpIn().getRank();
            if (integer!=null){
                jMessage.append("矩阵的秩为："+integer+"\n");
            }
        });
        jb7.addActionListener(e -> {
            Matrix m = readJpIn().getStep();
            writeToJpOut(m);
        });
        jb8.addActionListener(e -> {
            boolean b1 = readJpIn().isSymmetric();
            if (b1){
                jMessage.append("矩阵"+(currentMatrixIndex+1)+"是对称矩阵\n");
            }else {
                jMessage.append("矩阵"+(currentMatrixIndex+1)+"不是对称矩阵\n");
            }
        });
        jb9.addActionListener(e -> {
            boolean b1 = readJpIn().isAntisymmetric();
            if (b1){
                jMessage.append("矩阵"+(currentMatrixIndex+1)+"是反对称矩阵\n");
            }else {
                jMessage.append("矩阵"+(currentMatrixIndex+1)+"不是反对称矩阵\n");
            }
        });
        jb10.addActionListener(e -> {
            Matrix m = readJpIn().getMinimalist();
            writeToJpOut(m);
        });
        jbExchangeable.addActionListener(e -> {
            boolean exchangeable = previousMatrix.canExchangeable(readJpIn());
            if (exchangeable){
                jMessage.append("矩阵"+(previousMatrixIndex+1)+"与矩阵"+(1+currentMatrixIndex)+"可以交换\n");
            }else {
                jMessage.append("矩阵"+(previousMatrixIndex+1)+"与矩阵"+(1+currentMatrixIndex)+"不可交换\n");
            }
        });
        jbMatrixMultiply.addActionListener(e -> {
            Matrix m = previousMatrix.multiplyWithMatrix(readJpIn());
            writeToJpOut(m);
        });
        jTabbedPane.addChangeListener(e -> {
            previousMatrix = currentMatrix;
            previousMatrixIndex = currentMatrixIndex;
            currentMatrixIndex = jTabbedPane.getSelectedIndex();
            jTabbedPane.getTitleAt(currentMatrixIndex);
            currentMatrix = readJpIn();
            jbMatrixMultiply.setText("矩阵"+(previousMatrixIndex+1)+"×矩阵"+(currentMatrixIndex+1));
            jbExchangeable.setText((previousMatrixIndex+1)+"与"+(currentMatrixIndex+1)+"可交换?");
        });
        jp2.add(jb1);
        jp2.add(jb2);
        jp2.add(jb3);
        jp2.add(jb4);
        jp2.add(jb6);
        jp2.add(jb7);
        jp2.add(jb8);
        jp2.add(jb9);
        jp2.add(jbMatrixMultiply);
        jp2.add(jbExchangeable);
        jp2.add(jb5);
        jp2.add(jtf5);
        jp2.add(jb10);

        Border border3 = new TitledBorder(new LineBorder(Color.black), "信息");
        JScrollPane jScrollPane = new JScrollPane(jMessage);
        jScrollPane.setPreferredSize(new Dimension(400, 100));
        jScrollPane.setBorder(border3);

        b.add(jp1);
        b.add(jp2);
        b.add(jScrollPane);
        box.add(b);
    }

    //矩阵行列数设置窗口
    JDialog jDialog = new JDialog(this, "设置矩阵行列数", true);

    private void initDialog() {
        Box b = new Box(1);

        Box b1 = new Box(0);
        b1.add(new JLabel(" 矩阵行数："));
        JTextField jDialogJtf1 = new JTextField(10);
        b1.add(jDialogJtf1);

        Box b2 = new Box(0);
        b2.add(new JLabel(" 矩阵列数："));
        JTextField jDialogJtf2 = new JTextField(10);
        b2.add(jDialogJtf2);

        Box b3 = new Box(0);
        JButton jb1 = new JButton("确认");
        JButton jb2 = new JButton("取消");
        jb1.addActionListener(e -> {
            try {
                int row = Integer.parseInt(jDialogJtf1.getText());
                int column = Integer.parseInt(jDialogJtf2.getText());
                if (row > 0 && column > 0) {
                    setGlIn(row, column);
                    jDialog.setVisible(false);
                } else {
                    jMessage.append("请输入正确的数字\n");
                }
            } catch (Exception ex) {
                jMessage.append("请输入正确的内容\n");
            }
        });
        jb2.addActionListener(e -> jDialog.setVisible(false));
        b3.add(jb1);
        b3.add(jb2);

        b.add(b1);
        b.add(b2);
        b.add(b3);
        jDialog.add(b);
        jDialog.pack();
        jDialog.setResizable(false);
        jDialog.setLocationRelativeTo(this);
    }

    //右方块
    JPanel jpOut = new JPanel(new GridLayout(4, 4, 10, 10));
    JPopupMenu jPopupMenuOut = new JPopupMenu();
    private void init3() {
        JMenuItem jMenuItem1 = new JMenuItem("复制");
        jMenuItem1.addActionListener(e-> copy = readJpOut());
        jPopupMenuOut.add(jMenuItem1);

        Border border = new TitledBorder(new LineBorder(Color.black), "输出");
        Box b = new Box(1);
        b.setPreferredSize(new Dimension(500, height));

        for (int i = 0; i < 16; i++) {
            jpOut.add(new JLabel("0"));
        }

        b.add(jpOut);
        b.setBorder(border);
        box.add(b);
        jpOut.setComponentPopupMenu(jPopupMenuOut);
    }
    //将Matrix写入JpOut
    private void writeToJpOut(Matrix matrix){
        int row = matrix.getFss().length;
        int column = matrix.getFss()[0].length;
        setGlOut(row,column);
        JLabel[] jLabels = getJpOutValue();
        Fraction[][] fss = matrix.getFss();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                jLabels[i * column + j].setText(fss[i][j].toString());
            }
        }
    }
    //从JpOut中获取Matrix
    private Matrix readJpOut(){
        JLabel[] jLabels = getJpOutValue();
        GridLayout g = (GridLayout) jpOut.getLayout();
        int row = g.getRows();
        int column = g.getColumns();
        String[][] fss = new String[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                fss[i][j] = jLabels[i * column + j].getText();
            }
        }
        return new Matrix(fss);
    }
    //从JpOut中获取JLabel[]
    private JLabel[] getJpOutValue(){
        Component[] components = jpOut.getComponents();
        JLabel[] jLabels = new JLabel[components.length];
        for (int i = 0; i < components.length; i++) {
            jLabels[i] = (JLabel) components[i];
        }
        return jLabels;
    }
    //设置jpOut的GridLayout
    private void setGlOut(int row,int column){
        GridLayout g = (GridLayout) jpOut.getLayout();
        g.setRows(row);
        g.setColumns(column);
        jpOut.removeAll();
        for (int i = 0; i < row * column; i++) {
            jpOut.add(new JLabel("无"));
        }
        jpOut.updateUI();
    }

    private void initialize() {
        init1();
        init2();
        init3();

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../resources/matrix.png")));
        this.setTitle("Matrix Calculator");
        this.add(box);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        new MyFrame().initialize();
    }
}
