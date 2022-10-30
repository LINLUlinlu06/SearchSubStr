package com.example.searchsubstr;

import com.sun.org.apache.bcel.internal.generic.FieldGen;

public class BoyerMoore {
    private int[] rigth;
    private String pat;

    BoyerMoore(String pat){
//        计算跳跃表
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        rigth = new int[R];
        for (int c = 0; c < R; c++)
            rigth[c] = -1;   //不包含在模式字符串中的字符的值为-1
        for (int j = 0; j < M; j++) //包含在模式字符串的字符的值为
            rigth[pat.charAt(j)] = j; //它在其中出现的最右位置
    }

    public int search(String txt){
//        在txt中查找模式字符串
        int N = txt.length();
        int M = pat.length();
        int skip;
        for (int i = 0; i <= N-M; i += skip){ //模式字符串和文本在位置i匹配吗？
            skip = 0;
            for (int j = M-1; j >= 0; j--){
                if (pat.charAt(j) != txt.charAt(i+j)){
                    skip = j - rigth[txt.charAt(i+j)];//情况1，2
                    if (skip < 1) skip = 1;//情况3
                    break;
                }
            }
            if (skip == 0) return i; // 找到匹配
        }
        return -1;
    }

    public static void main(String[] args) {
        String txt = "FINDINAHAYSTACKNEEDLE";
        String pat = "NEEDLE";
        BoyerMoore bm = new BoyerMoore(pat);
        int search = bm.search(txt);
        System.out.println(search);
    }
}
