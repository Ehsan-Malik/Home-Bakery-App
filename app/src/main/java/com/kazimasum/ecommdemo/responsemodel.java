package com.kazimasum.ecommdemo;

public class responsemodel
{
    String id, catid, pname, pimage, pdesc, pprice, pstatus, shop;

    public responsemodel() {
    }


    public responsemodel(String id, String catid, String pname, String pimage, String pdesc, String pprice, String pstatus, String shop) {
        this.id = id;
        this.catid = catid;
        this.pname = pname;
        this.pimage = pimage;
        this.pdesc = pdesc;
        this.pprice = pprice;
        this.pstatus = pstatus;
        this.shop = shop;
    }

    public String getId() {
        return id;
    }

    public String getCatid() {
        return catid;
    }

    public String getPname() {
        return pname;
    }

    public String getPimage() {
        return pimage;
    }

    public String getPdesc() {
        return pdesc;
    }

    public String getPprice() {
        return pprice;
    }

    public String getPstatus() {
        return pstatus;
    }


    public String getShop() {
        return shop;
    }

}
