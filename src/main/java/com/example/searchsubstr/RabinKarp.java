package com.example.searchsubstr;

import java.math.BigInteger;
import java.util.Random;

public class RabinKarp {
    private String pat;      // 模式字符串  // 仅拉斯维加斯算法需要
    private long patHash;    // 模拟字符串的散列值
    private int m;           // 模拟字符串的长度
    private long q;          // 一个很大的素数
    private int R;           // 字母表的大小
    private long RM;         // R^(M-1) % Q

    public RabinKarp(String pat) {
        this.pat = pat;      // 保存模拟字符串
        R = 256;
        m = pat.length();
        q = longRandomPrime();//随机素数
//        System.out.println(" q " + q);

        RM = 1;
        for (int i = 1; i <= m-1; i++) //计算R^(M-1) % Q
            RM = (R * RM) % q; //用于减去第一个数字时的计算
        patHash = hash(pat, m);
    }

//    hash值采用除留余数法
    private long hash(String key, int m) {
        long h = 0;
        for (int j = 0; j < m; j++)
            h = (R * h + key.charAt(j)) % q;
        System.out.println(key +" :h "+h);
        return h;
    }

    private boolean check(String txt, int i) {
        for (int j = 0; j < m; j++)
            if (pat.charAt(j) != txt.charAt(i + j))
                return false;
        return true; //检查模式与txt(i..i-M+1)的匹配
    }

    //在文本中查找相等的散列值
    public int search(String txt) {
        int n = txt.length();
        if (n < m) return n;
        long txtHash = hash(txt, m);//计算txt文本中前M个字符串的hash值
        // 一开始匹配成功
        if ((patHash == txtHash) && check(txt, 0))
            return 0;
        for (int i = m; i < n; i++) {
            // 减去第一个数字，加上最后一个数字，再次匹配
            txtHash = (txtHash + q - RM*txt.charAt(i-m) % q) % q;
            txtHash = (txtHash*R + txt.charAt(i)) % q;

            int offset = i - m + 1;
            if ((patHash == txtHash) && check(txt, offset))
                return offset;
        }
        return -1; //未找到匹配
    }

    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());//第一个参数表示生成指定31位的正数BigInteger，第二个长度生成素数的数据源
        return prime.longValue();//返回素数
    }

    public static void main(String[] args) {
        String pat = "26535";
        RabinKarp karp = new RabinKarp(pat);
        String txt = "3141592653589793";
        int i = karp.search(txt);
        System.out.println(i);
    }
}
