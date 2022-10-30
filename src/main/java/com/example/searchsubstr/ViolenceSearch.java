package com.example.searchsubstr;


public class ViolenceSearch {
    //    隐式回退
    public static int violenceSearch1(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++)
                if (txt.charAt(i + j) != pat.charAt(j))
                    break;
            if (j == M) return i;
        }
        return -1;
    }

    //    显式回退
    public static int violenceSearch2(String pat, String txt) {
        int j, M = pat.length();
        int i, N = txt.length();
        for (i = 0, j = 0; i < N && j < M; i++) {//i++ 右移一位
            if (txt.charAt(i) == pat.charAt(j)) j++;//匹配的时候j++
            else {
                i -= j;
                j = 0;
            }//不匹配的时候i回到本次匹配的开头位置，将j重置为0
        }
        if (j == M) return i - M;
        else return -1;
    }

    public static void main(String[] args) {
        String txt = "ABACADABRAC";
        String pat = "ABRC";
        int search = violenceSearch1(pat, txt);
        System.out.println(search);
    }


}
