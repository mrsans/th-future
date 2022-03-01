package org.future.leecode;

import org.junit.jupiter.api.condition.OS;

import java.util.ArrayList;
import java.util.List;

public class Leecode_2 {

    public static void main(String[] args) {

        String s = "";
        String t = "";

        List<int[]> list = new ArrayList<>();
        list.add(new int[]{123});
//        filter(s, t);


    }

    private static void filter(String s, String t) {
        int index1 = -1;
        String[] split1 = s.split("");
        String[] split2 = t.split("");

        for (int i = 0; i < split1.length; i++) {

            for (int j = 0; j < split2.length; j++) {

                if (split1[i].equals(split2[j])) {
                    index1 = i;
                    break;
                }

            }


        }


    }


}
