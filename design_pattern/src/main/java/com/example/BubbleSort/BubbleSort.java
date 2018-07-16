package com.example.BubbleSort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {

        int[] array1 = new int[]{5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7};
        int[] array2 = new int[]{5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7};
        int[] array3 = new int[]{5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7,5,8,4,6,3,9,2,1,0,7};
        sort1(array1);
        sort2(array2);
        sort3(array3);
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));
        System.out.println(Arrays.toString(array3));
    }

    /**
     * 使用双循环进行排序,外部循环控制所有的回合,内部循环代表每一轮的冒泡处理
     * 先进行元素比较,再进行元素交换
     * @param array
     * @version 1.0
     */
    private static void sort1(int array[]){
        // 当前时间对应的毫秒数
        long startMili=System.currentTimeMillis();
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
        long endMili=System.currentTimeMillis();
        System.out.println("总耗时为："+(endMili-startMili)+"毫秒");
    }

    /**
     * 利用isSorted作为标记
     * 如果本轮排序中有元素交换,则说明数列无序
     * 如果没有元素交换,则说明数列有序,直接跳出大循环
     * @param array
     * @version 2.0
     */
    private static void sort2(int array[]){
        // 当前时间对应的毫秒数
        long startMili=System.currentTimeMillis();
        int tmp = 0;
        for (int i = 0; i < array.length; i++) {
            //有序标记,每一轮的初始是true
            boolean isSorted = true;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j+1]){
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    //有元素交换,所以不是有序,标记变为false
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
        long endMili=System.currentTimeMillis();
        System.out.println("总耗时为："+(endMili-startMili)+"毫秒");
    }

    /**
     * sortBorder就是无序数列的边界
     * 每一轮排序过程中,sortBorder之后的元素就不需要比较了,因为是有序的
     * @param array
     */
    private static void sort3(int array[]){
        // 当前时间对应的毫秒数
        long startMili=System.currentTimeMillis();

        int tmp = 0;

        //记录最后一次交换的位置
        int lastExchangeIndex = 0;

        //无序数列的边界,每次比较只需要比到这里为止
        int sortBorder = array.length-1;

        for (int i = 0; i < array.length; i++) {

            //有序标记,每一轮的初始是true
            boolean isSorted = true;

            for (int j = 0; j < sortBorder; j++) {

                if (array[j] > array[j+1]){
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;

                    //有元素交换,所以不是有序,标记变为false
                    isSorted = false;

                    //把无序数列的边界更新为最后一次交换元素的位置
                    lastExchangeIndex = j;
                }
            }

            sortBorder = lastExchangeIndex;
            if (isSorted) {
                break;
            }
        }

        long endMili=System.currentTimeMillis();
        System.out.println("总耗时为："+(endMili-startMili)+"毫秒");
    }
}
