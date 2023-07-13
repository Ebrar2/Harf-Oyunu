package com.example.yazlab22;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatDrawableManager;

public class HarfBtn  extends   AppCompatButton {

    private char harf='-';
    private int renkid;
    private boolean secilimi=false;
    private int konum;

    private boolean buzHarfiMi=false;

   private int buzimage;
    private int buzSecimSay=0;

    @SuppressLint("RestrictedApi")
    public HarfBtn(Context context) {
        super(context);
        setEnabled(false);
        setBackgroundColor(Color.GRAY);

    }
    public void buzHarfiTasi()
    {
        int buzimage=R.drawable.esasbuz;
      setBackgroundResource(buzimage);

    }
    public void  butonSecimiKaldir()
    {
        this.secilimi=false;
        if(buzHarfiMi==false)
            setBackgroundColor(renkid);
        else
            setBackgroundResource(buzimage);
    }
    public void birkezSecilmisBuz()
    {
        int kirikBuz=R.drawable.kirikbuz;
        buzSecimSay=1;
        setBackgroundResource(kirikBuz);
        buzimage=kirikBuz;
        secilimi=false;
    }
    public void butonSec(int konum)
    {
        this.konum=konum;
        secilimi=true;
        setBackgroundColor(Color.GRAY);
    }
    public void buzHarfiYap()
    {
         int buz=R.drawable.buz;
         if(secilimi==false)
             setBackgroundResource(buz);
         buzimage=buz;
         buzSecimSay=0;
         buzHarfiMi=true;
    }
    public void olusturNormalHarfButon(char c,int renkid)
    {
        setEnabled(true);
        this.harf=c;
        this.renkid=renkid;
        secilimi=false;
        buzHarfiMi=false;
        buzSecimSay=0;
        setBackgroundColor(renkid);
    }
    public void olustuBuzButon(char c,int renkid)
    {
        setEnabled(true);
        this.harf=c;
        this.renkid=renkid;
        this.buzHarfiMi=true;
        this.buzimage=R.drawable.esasbuz;
        this.secilimi=false;
        this.buzSecimSay=0;

        setBackgroundResource(buzimage);
    }
    public void harfiYokEt()
    {
        setText("");
        setBackgroundColor(Color.GRAY);
        secilimi=false;
        setEnabled(false);
        harf='-';
        buzSecimSay=0;
        buzHarfiMi=false;
        buzimage=0;
    }
    public void harfiDegistir(String text,int renkid,char harf,int buzSecimSay,boolean buzHarfiMi,int buzimage)
    {
        setText(text);
        secilimi=false;
        this.harf=harf;
        this.buzSecimSay=buzSecimSay;
        this.buzHarfiMi=buzHarfiMi;
        this.buzimage=buzimage;
        this.renkid=renkid;
        this.konum=-1;
        if(!buzHarfiMi)
            setBackgroundColor(renkid);
        else
        {
           setBackgroundResource(buzimage);
        }
    }
    public boolean isBuzHarfiMi() {
        return buzHarfiMi;
    }

    public void setBuzHarfiMi(boolean buzHarfiMi) {
        this.buzHarfiMi = buzHarfiMi;
    }

    public int getRenkid() {
        return renkid;
    }

    public void setRenkid(int renkid) {
        this.renkid = renkid;
    }

    public char getHarf() {
        return harf;
    }

    public void setHarf(char harf) {
        this.harf = harf;
    }

    public int getBuzimage() {
        return buzimage;
    }

    public void setBuzimage(int buzimage) {
        this.buzimage = buzimage;
    }



    public boolean isSecilimi() {
        return secilimi;
    }

    public void setSecilimi(boolean secilimi) {
        this.secilimi = secilimi;
    }

    public int getKonum() {
        return konum;
    }

    public void setKonum(int konum) {
        this.konum = konum;
    }
    public int getBuzSecimSay() {
        return buzSecimSay;
    }

    public void setBuzSecimSay(int buzSecimSay) {
        this.buzSecimSay = buzSecimSay;
    }


}
