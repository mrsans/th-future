package org.future.leecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Leecode_1 {

    public static void main(String[] args) {
        List<Integer> nums_1 = Arrays.asList(1, 2, 3);// nums(2);
        List<Integer> nums_2 = Arrays.asList(4, 5); // nums(4);
        List<Integer> newList = new ArrayList<>();
        newList.addAll(nums_1);
        newList.addAll(nums_2);
        newList.sort((a,b) -> a-b);
        System.out.println(newList);

        int length = nums_1.size() + nums_2.size();
        int index1 = 0;
        int index2 = 0;
        int zws = length % 2 == 0 ? length / 2 + 1 : length / 2 + 1 ;

        List<Integer> nList = new ArrayList<>();

        for (int i = 0; i < zws; i++) {
            if (nums_1.size() == 0 || index1 == nums_1.size()) {
                nList.add(nums_2.get(index2));
                index2 ++;
                continue;
            }
            if (nums_2.size() == 0 || index2 == nums_2.size()) {
                nList.add(nums_1.get(index1));
                index1 ++;
                continue;
            }
            int value1 = nums_1.get(index1);
            int value2 = nums_2.get(index2);

            if (index1 > zws) {
                nList.add(value2);
                continue;
            }

            if (index2 > zws) {
                nList.add(value1);
                continue;
            }

            if (value1 < value2) {
                nList.add(value1);
                index1 ++;
            } else {
                nList.add(value2);
                index2 ++;
            }

        }
        System.out.println(nList);
        int size = nList.size();
        if (size == 0) {
            System.out.println("");
            return ;
        }
        if (length % 2 == 0 && nList.size() > 1) {
             int r1 = nList.get(size - 1);
             int r2 = nList.get(size - 2);
            System.out.println((r1 + r2) * 1.0/2 );
        } else {
            System.out.println( nList.get(size - 1));
        }
    }


    public static List<Integer> nums (int num) {
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            result.add(random.nextInt(4));
        }
        result.sort((a, b) -> a - b);
        return result;

    }



}
