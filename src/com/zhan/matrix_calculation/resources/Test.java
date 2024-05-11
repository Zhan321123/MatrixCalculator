package com.zhan.matrix_calculation.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException{
        imageToText();
    }

    static void imageToText() throws IOException {
        //使用Reader 读取图片
        FileInputStream inputStream = new FileInputStream(new File("D:\\code\\JavaProject\\MatrixCalculation\\src\\com\\zhan\\matrix_calculation\\resources\\matrix.png"));
        while (inputStream.available() > 0){
            System.out.print(inputStream.read());
        }
    }
}
