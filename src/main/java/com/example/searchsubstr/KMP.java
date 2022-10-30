package com.example.searchsubstr;

public class KMP {
    private String pat;
    private int[][] dfa;       // the KMP automoton

    public KMP(String pat) {
        this.pat = pat;
        int R = 256;
        int M = pat.length();

        //根据模版字符串，构建dfa[][]
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int x = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x];     // 复制匹配失败情况下的值.
            dfa[pat.charAt(j)][j] = j+1;   // 设置匹配成功情况下的值
            x = dfa[pat.charAt(j)][x];     // 更新重启状态
        }
    }

    public int search(String txt) {
        // simulate operation of DFA on text
        int i, j, N = txt.length(), M = pat.length();
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == M) return i - M;    // 找到返回下标
        return -1;                    // 未找到时返回-1
    }


    public static void main(String[] args) {
        String pat = "DET";
        String txt = "BABGHBBDETBABABA";

        KMP kmp = new KMP(pat);
        int offset = kmp.search(txt);
        System.out.println(offset);

    }
}
