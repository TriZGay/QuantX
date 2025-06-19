package io.futakotome.itick.dto;

public class StockItem {
    //产品代码
    private String c;
    //产品名称
    private String n;
    //产品类别
    private String t;
    //交易所
    private String e;

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "StockItem{" +
                "c='" + c + '\'' +
                ", n='" + n + '\'' +
                ", t='" + t + '\'' +
                ", e='" + e + '\'' +
                '}';
    }
}
