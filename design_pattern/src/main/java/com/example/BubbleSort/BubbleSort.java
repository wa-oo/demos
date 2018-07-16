package com.example.BubbleSort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] array = new int[]{5,8,6,3,9,2,1,7};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 使用双循环进行排序,外部循环控制所有的回合,内部循环代表每一轮的冒泡处理
     * 先进行元素比较,再进行元素交换
     * @param array
     * @version 1.0
     */
    private static void sort(int array[]){
        int tmp = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j+1]){
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
    }
}
