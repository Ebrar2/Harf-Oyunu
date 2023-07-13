package com.example.yazlab22;


import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.gridlayout.widget.GridLayout;

import java.util.*;

public class OyunEkrani extends AppCompatActivity {
    int puan=0;
    TextView textyazKelime;
    String yazilanKelime="";
    CountDownTimer countDownTimer;
    int dusmeSuresi=5000;
    int harfDenge=0;
    List<HarfBtn> secilenHarfler=new ArrayList<>();
    List<HarfBtn> buzHarfleri=new ArrayList<>();
    TextView hataSayisiTextView;
    int hataSayisi=0;
    Sozluk sozluk;
    List<HarfBtn> harfBtns;
    List<renkveHarf> renkveHarfList;
    TextView puanTextView;
    float x1,x2,y1,y2;
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = motionEvent.getX();
                y1 = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = motionEvent.getX();
                y2 = motionEvent.getY();
                if (x1 < x2) {
                    if(countDownTimer!=null)
                        aramaIslemiYap();
                }

        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_oyun_ekrani);


        hataSayisiTextView = findViewById(R.id.hataSayisi);
        textyazKelime = findViewById(R.id.yazilanKelime);
        Button redbtn = findViewById(R.id.iptalbtn);
        redbtn.setBackgroundColor(Color.RED);
        Button arabtn = findViewById(R.id.arabtn);
        arabtn.setBackgroundColor(Color.GREEN);
        puanTextView=findViewById(R.id.puanBilgi);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grLbutonlar);
        sozluk =new Sozluk(this);


        int width = 1100 / 8;
        int heigth = 1000 / 10;
        harfBtns = new ArrayList<>();
        renkveHarfList=olusturRenkHarf();


        arabtn.setEnabled(false);
        redbtn.setEnabled(false);

        arabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aramaIslemiYap();
            }

        });

        redbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HarfBtn harfBtn;
                for (int i = 0; i < secilenHarfler.size(); i++) {
                    harfBtn = secilenHarfler.get(i);
                    harfBtn.butonSecimiKaldir();

                }
                secilenHarfler.clear();
                textyazKelime.setText("");
                yazilanKelime = "";
            }
        });
        for (int i = 0; i < 80; i++) {

            harfBtns.add(new HarfBtn(this));
        }
        for (int i = 0; i < 80; i++) {

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width , heigth + 45);
            gridLayout.addView(harfBtns.get(i), params);

        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                yazdir3Satir(harfBtns, renkveHarfList,arabtn,redbtn);

            }
        },5000);


    }
    @Override
    protected void onPause() {

        super.onPause();
        if(countDownTimer!=null)
        {
            countDownTimer.cancel();
        }
    }
    public void aramaIslemiYap()
    {
        String getText=textyazKelime.getText().toString();
        System.out.println("TEXT:"+getText);
        if (sozluk.kelimeAraAgacta(getText)) {
            secilenHarfleriYokEt(harfBtns);
            yazilanKelime="";
            textyazKelime.setText(yazilanKelime);
            puan+= sozluk.kelimePuani(getText);
            if (puan >= 100 && dusmeSuresi == 5000) {
                countDownTimer.cancel();
                dusmeSuresi -= 1000;
                countDownTimer = harflerinTekrarDusmesi(harfBtns, renkveHarfList, dusmeSuresi);
                countDownTimer.start();
            } else if (puan >= 200 && dusmeSuresi == 4000) {
                countDownTimer.cancel();
                dusmeSuresi -= 1000;
                countDownTimer = harflerinTekrarDusmesi(harfBtns, renkveHarfList, dusmeSuresi);
                countDownTimer.start();

            } else if (puan >= 300 && dusmeSuresi == 3000) {
                countDownTimer.cancel();
                dusmeSuresi -= 1000;
                countDownTimer = harflerinTekrarDusmesi(harfBtns, renkveHarfList, dusmeSuresi);
                countDownTimer.start();

            } else if (puan >= 400 && dusmeSuresi == 2000) {
                countDownTimer.cancel();
                dusmeSuresi -= 1000;
                countDownTimer = harflerinTekrarDusmesi(harfBtns, renkveHarfList, dusmeSuresi);
                countDownTimer.start();

            }

        } else {
            hataSayisi++;
            if (hataSayisi == 3) {
                List<Integer> devamEdecekHarfler=new ArrayList<>();
                for (int i = 0; i <8 ; i++) {
                    devamEdecekHarfler.add(i);
                }
                List<renkveHarf> randomHarfler=randomHarfOlustur(renkveHarfList);
                if(countDownTimer!=null)
                {
                    countDownTimer.cancel();
                    countDownTimer = harflerinTekrarDusmesi(harfBtns, renkveHarfList, dusmeSuresi);
                    countDownTimer.start();
                }
                boolean devammi=ucHataSonucu(devamEdecekHarfler,harfBtns,randomHarfler);
                if (!devammi)
                {
                    System.out.println("Devammi!!!!!!!!!!!!!");
                        countDownTimer.cancel();
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(OyunEkrani.this,SonEkran.class);
                                intent.putExtra("puan",puan);
                                startActivity(intent);
                            }
                        },500);

                }
                hataSayisi=0;
            }

        }
        hataSayisiTextView.setText("HATA:"+hataSayisi);
        puanTextView.setText("PUAN:"+puan);

    }
    public boolean ucHataSonucu(List<Integer> devamEdecekHarfler,List<HarfBtn> harfBtns,List<renkveHarf> randomHarfler)
    {
        String strharf;
        List<Integer> yeniListe=new ArrayList<>();
        int index;
        for (int i = 0; i < devamEdecekHarfler.size(); i++) {
            index=devamEdecekHarfler.get(i);
            strharf=""+randomHarfler.get(index%8).getHarf();
            harfBtns.get(index).setText(strharf);
            harfBtns.get(index).setBackgroundColor(randomHarfler.get(index%8).getRenk());
        }
        for (int i = 0; i <devamEdecekHarfler.size() ; i++) {
            index=devamEdecekHarfler.get(i);
            if ((index+8)<80)
            {
                if(harfBtns.get(index+8).getHarf()=='-')
                {
                    yeniListe.add(index+8);
                    Handler handler=new Handler();
                    int getir=index;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            harfBtns.get(getir).setText("");
                           harfBtns.get(getir).setBackgroundColor(Color.GRAY);
                        }
                    },200);
                }
                else if(index<8)
                {
                    return  false;
                }
            }

            if ((index+8)>=80 || harfBtns.get(index+8).getHarf()!='-')
            {
                harfBtns.get(index).olusturNormalHarfButon(randomHarfler.get(index%8).getHarf(),randomHarfler.get(index%8).getRenk());
                harfTiklanmaSet(harfBtns.get(index));
            }
            buzHarfiKontrol(harfBtns);
        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(yeniListe.size()!=0)
                {
                    ucHataSonucu(yeniListe,harfBtns,randomHarfler);
                }
            }
        },300);
        return  true;
    }
    public void secilenHarfleriYokEt(List<HarfBtn> harfBtns)
    {
        int []indexler=new int[secilenHarfler.size()];
        for (int i = 0; i < indexler.length; i++) {
            HarfBtn harfBtn=secilenHarfler.get(i);
            indexler[i]=indexHarfBtnDondur(harfBtn,harfBtns);

        }
        secilenHarfler.clear();
        Arrays.sort(indexler);
        for (int i = 0; i <indexler.length ; i++) {

            System.out.println(harfBtns.get(indexler[i]).getHarf()+":---:"+harfBtns.get(indexler[i]).isBuzHarfiMi()+":"+harfBtns.get(indexler[i]).getBuzSecimSay());
            if(!harfBtns.get(indexler[i]).isBuzHarfiMi() ||( harfBtns.get(indexler[i]).getBuzSecimSay()==1))
            {
                if(harfBtns.get(indexler[i]).getBuzSecimSay()==1)
                {
                    System.out.println("Buzharf:size:"+buzHarfleri.size());
                    if(buzHarfleri.contains(harfBtns.get(indexler[i])))
                    {
                        System.out.println("BURADA:buz:size:"+buzHarfleri.get(0).getHarf());
                        if(buzHarfleri.size()==1)
                        {
                            buzHarfleri.clear();
                        }
                        else {
                            buzHarfleri.remove(harfBtns.get(indexler[i]));

                        }
                    }

                }
                harfinUstVeAltindakilerleBirlikteYok(harfBtns,indexler[i],indexler[i]-8);

            }
            else
            {
                harfBtns.get(indexler[i]).birkezSecilmisBuz();
            }
        }
    }
    public int indexHarfBtnDondur(HarfBtn harfBtn,List<HarfBtn> harfBtns)
    {
        return  harfBtns.indexOf(harfBtn);
    }
    public void harfinUstVeAltindakilerleBirlikteYok(List<HarfBtn> harfBtns,int index,int ust)
    {
        if(ust<0)
        {
            harfBtns.get(index).harfiYokEt();
            return;
        }
        if(harfBtns.get(ust).getHarf()=='-')
        {
            harfBtns.get(index).harfiYokEt();
            return;
        }
        else
        {
            HarfBtn tempUst=harfBtns.get(ust);
            String text=""+tempUst.getHarf();
            harfBtns.get(index).harfiDegistir(text,tempUst.getRenkid(),tempUst.getHarf(),tempUst.getBuzSecimSay(),tempUst.isBuzHarfiMi(),tempUst.getBuzimage());

            if(buzHarfleri.contains(tempUst))
            {
                buzHarfleri.add(harfBtns.get(index));
                buzHarfleri.remove(tempUst);

            }
            harfinUstVeAltindakilerleBirlikteYok(harfBtns,ust,ust-8);

        }

    }
    public List<renkveHarf> randomHarfOlustur(List<renkveHarf> renkveHarfList)
    {
        List<renkveHarf> randomHarfler=new ArrayList<>();
        int[] sesliHarf={0,5,10,11,17,18,24,25};
        int random;
        for (int i = 0; i <8 ; i++) {
            random=(int)(Math.random()*29);
            randomHarfler.add(renkveHarfList.get(random));
        }
        int sesliIndex=(int)(Math.random()*8);
        int index=(int)(Math.random()*8);
        randomHarfler.set(index,renkveHarfList.get(sesliHarf[sesliIndex]));
        sesliIndex=(int)(Math.random()*8);
        index=(int)(Math.random()*8);
        randomHarfler.set(index,renkveHarfList.get(sesliHarf[sesliIndex]));
        return randomHarfler;
    }

    public CountDownTimer harflerinTekrarDusmesi(List<HarfBtn> harfBtns,List<renkveHarf> renkveHarfList,int sayGeri)
    {
        CountDownTimer countDownTimer=new CountDownTimer(100000000,sayGeri) {
            @Override
            public void onTick(long millisUntilFinished) {
                boolean kontrol=harfDusur(harfBtns,renkveHarfList);
                if(kontrol==false)
                {
                    System.out.println("Kontrol:::::::::::");
                    this.cancel();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(OyunEkrani.this,SonEkran.class);
                            intent.putExtra("puan",puan);
                            startActivity(intent);
                        }
                    },500);

                }

            }

            @Override
            public void onFinish() {
                System.out.println("YOOOOOOOOOOOOOOK:::::::::::");
                Intent intent=new Intent(OyunEkrani.this,SonEkran.class);
                intent.putExtra("puan",puan);
                startActivity(intent);
            }
        };
        return countDownTimer;
    }

    public void buzHarfiKontrol(List<HarfBtn> harfBtns)
    {
        int konumu=0;
        System.out.println("BUZLARRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        for (int i = 0; i < buzHarfleri.size(); i++) {
            konumu=harfBtns.indexOf(buzHarfleri.get(i));
            System.out.println("BUZ1:"+konumu+"-------------"+buzHarfleri.get(i).getHarf());
            if(konumu%8!=0)
            {
                if(harfBtns.get(konumu-1).getHarf()!='-' && !harfBtns.get(konumu-1).isBuzHarfiMi()) {
                    harfBtns.get(konumu -1).buzHarfiYap();
                }
            }
            if(konumu%8!=7)
            {
                if(harfBtns.get(konumu+1).getHarf()!='-' && !harfBtns.get(konumu+1).isBuzHarfiMi()) {

                    harfBtns.get(konumu +1).buzHarfiYap();
                }
            }
            if((konumu-8)>0)
            {
                if(harfBtns.get(konumu-8).getHarf()!='-' && !harfBtns.get(konumu-8).isBuzHarfiMi())
                {
                    harfBtns.get(konumu-8).buzHarfiYap();

                }
            }
            if((konumu+8)<80)
            {
                if(harfBtns.get(konumu+8).getHarf()!='-' && !harfBtns.get(konumu+8).isBuzHarfiMi()) {
                    harfBtns.get(konumu + 8).buzHarfiYap();
                }
            }

        }
        System.out.println("BUZLARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
    }
    public boolean harfDusur(List<HarfBtn> harfBtns,List<renkveHarf> renkveHarfList)
    {
        int random;
        if(harfDenge==3)
        {
            int[] sesliHarf={0,5,10,11,17,18,24,25};
            int seslirandom=(int)(Math.random()*8);
            random=sesliHarf[seslirandom];
            harfDenge=0;
        }
        else
        {
            int[] sessizHarf={0,1,2,3,4,5,6,7,8,9,12,13,14,15,16,19,20,21,22,23,26,27,28};
            int sessizrandom=(int)(Math.random()*23);
            random=sessizHarf[sessizrandom];
            harfDenge++;
        }
        renkveHarf randomHarf=renkveHarfList.get(random);
        int randomSutun = (int) (Math.random() * 8);
        System.out.println("RandomSutun:"+randomSutun);
        if (harfBtns.get(randomSutun+8).getHarf() != '-') {
            {
                harfBtns.get(randomSutun).setBackgroundColor(randomHarf.getRenk());
                harfBtns.get(randomSutun).setText(""+randomHarf.getHarf());
                System.out.println("RandomSutun:"+"DAN ÇIKTIIIIIIIII");
                return false;
            }
        } else {
            int randomBuz=(int)(Math.random()*30);
            boolean buz=false;
            if(randomBuz==15)
            {
               buz=true;
                tekKelimeDusmesi(harfBtns,randomHarf,randomSutun,buz);
            }
            else
            {
                tekKelimeDusmesi(harfBtns,randomHarf, randomSutun,buz);

            }

            return  true;
        }
    }
    public void  harfTiklanmaSet(HarfBtn setHarfBtn)
    {
        setHarfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HarfBtn harfBtn=(HarfBtn) v;
                String strBtnHarf=""+harfBtn.getHarf();
                if(harfBtn.isSecilimi())
                {
                    char[] cevir=yazilanKelime.toCharArray();
                    cevir[harfBtn.getKonum()]=' ';
                    String setKelime="";
                    for (int j = 0; j <cevir.length; j++) {
                        setKelime+=cevir[j];
                    }
                    yazilanKelime=setKelime;
                    textyazKelime.setText(yazilanKelime.replace(" ",""));
                    secilenHarfler.remove(harfBtn);
                    harfBtn.butonSecimiKaldir();
                }
                else
                {
                    yazilanKelime=yazilanKelime+strBtnHarf;
                    textyazKelime.setText(yazilanKelime.replace(" ",""));
                    secilenHarfler.add(harfBtn);
                    harfBtn.butonSec(yazilanKelime.length()-1);

                }
            }
        });
    }
    public void tekKelimeDusmesi(List<HarfBtn> harfBtns,renkveHarf randomHarfveRenk,int randomSutun,boolean buz)
    {
        String strRandomHarf=""+randomHarfveRenk.getHarf();
        if(!buz)
            harfBtns.get(randomSutun).setBackgroundColor(randomHarfveRenk.getRenk());
        else
            harfBtns.get(randomSutun).buzHarfiTasi();
        harfBtns.get(randomSutun).setText(strRandomHarf);
        if((randomSutun+8)>=80)
        {
            if(buz)
            {
                buzHarfleri.add(harfBtns.get(randomSutun));
                harfBtns.get(randomSutun).olustuBuzButon(randomHarfveRenk.getHarf(),randomHarfveRenk.getRenk());
            }
            else
            {
                harfBtns.get(randomSutun).olusturNormalHarfButon(randomHarfveRenk.getHarf(),randomHarfveRenk.getRenk());
            }
            harfTiklanmaSet(harfBtns.get(randomSutun));
            buzHarfiKontrol(harfBtns);
            return;
        }

        if (harfBtns.get(randomSutun+8).getHarf()!='-')
        {
            if(buz)
            {
                buzHarfleri.add(harfBtns.get(randomSutun));
                harfBtns.get(randomSutun).olustuBuzButon(randomHarfveRenk.getHarf(),randomHarfveRenk.getRenk());
            }
            else
            {
                harfBtns.get(randomSutun).olusturNormalHarfButon(randomHarfveRenk.getHarf(),randomHarfveRenk.getRenk());
            }
            harfTiklanmaSet(harfBtns.get(randomSutun));
            buzHarfiKontrol(harfBtns);
            return;
        }
        else
        {
            Handler handler=new Handler();
            int sutun=randomSutun;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    harfBtns.get(sutun).setText("");
                    harfBtns.get(sutun).setBackgroundColor(Color.GRAY);
                    tekKelimeDusmesi(harfBtns,randomHarfveRenk,sutun+8,buz);
                }
            },100);
        }
    }
    public void yazdir3Satir(List<HarfBtn> harfBtns,List<renkveHarf> renkveHarfList,Button arabtn,Button redbtn)
    {
        ilkHarfleriYerlestir(harfBtns,randomHarfOlustur(renkveHarfList),80,0);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ilkHarfleriYerlestir(harfBtns,randomHarfOlustur(renkveHarfList),72,0);
            }
        },2000);
      //  Handler handler2=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ilkHarfleriYerlestir(harfBtns,randomHarfOlustur(renkveHarfList) ,64,0);
            }
        },4000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                arabtn.setEnabled(true);
                redbtn.setEnabled(true);
            }
        },5000);
      //  Handler handler3=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                countDownTimer=harflerinTekrarDusmesi(harfBtns,renkveHarfList,5000);
                countDownTimer.start();
            }
        },9000);
    }
    public void ilkHarfleriYerlestir(List<HarfBtn> harfBtns,List<renkveHarf> randomRenkveHarfList,int son,int ilerle)
    {

        int k=0;
        if(ilerle==son)
            return;
        for(int i=ilerle;i<8+ilerle;i++)
        {
            String s=""+randomRenkveHarfList.get(k).getHarf();
            harfBtns.get(i).setText(s);
            harfBtns.get(i).setBackgroundColor(randomRenkveHarfList.get(k).getRenk());
            k++;
        }
        if(ilerle!=son-8)
        {
            Handler handler=new Handler();
            int git=ilerle+8;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int i=ilerle;i<8+ilerle;i++)
                    {
                        harfBtns.get(i).setText("");
                        harfBtns.get(i).setBackgroundColor(Color.GRAY);
                    }
                    ilkHarfleriYerlestir(harfBtns,randomRenkveHarfList,son,git);
                }
            },100);
        }
        else
        {
            k=0;
            for (int i = ilerle; i <ilerle+8 ; i++) {

                harfBtns.get(i).olusturNormalHarfButon(randomRenkveHarfList.get(k).getHarf(),randomRenkveHarfList.get(k).getRenk());
                k++;
                harfTiklanmaSet(harfBtns.get(i));

            }

        }

    }
    public List<renkveHarf> olusturRenkHarf()
    {
        char[] alfabe={'A','B','C','Ç','D','E','F','G','Ğ','H','I','İ','J','K','L','M','N','O','Ö','P','R','S','Ş','T','U','Ü','V','Y','Z'};
        int[] renkler={
                Color.parseColor("#EF9A9A"),
                Color.parseColor("#F48FB1"),
                Color.parseColor("#CE93D8"),
                Color.parseColor("#B39DDB"),
                Color.parseColor("#9FA8DA"),
                Color.parseColor("#90CAF9"),
                Color.parseColor("#81D4FA"),
                Color.parseColor("#4DD0E1"),
                Color.parseColor("#80CBC4"),
                Color.parseColor("#A5D6A7"),
                Color.parseColor("#C5E1A5"),
                Color.parseColor("#E6EE9C"),
                Color.parseColor("#F9A825"),
                Color.parseColor("#FFAB91"),
                Color.parseColor("#BCAAA4"),
                Color.parseColor("#90A4AE"),
                Color.parseColor("#607D8B"),
                Color.parseColor("#FF5252"),
                Color.parseColor("#FF80AB"),
                Color.parseColor("#EA80FC"),
                Color.parseColor("#B388FF"),
                Color.parseColor("#8C9EFF"),
                Color.parseColor("#82B1FF"),
                Color.parseColor("#80D8FF"),
                Color.parseColor("#00E5FF"),
                Color.parseColor("#1DE9B6"),
                Color.parseColor("#69F0AE"),
                Color.parseColor("#FFFF8D"),
                Color.parseColor("#5D4037")


        };
        List<renkveHarf> renkveHarf=new ArrayList<>();
        for (int i = 0; i <29 ; i++) {
            renkveHarf.add(new renkveHarf(alfabe[i],renkler[i]));
        }
        return renkveHarf;
    }

}