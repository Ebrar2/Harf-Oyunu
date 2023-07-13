package com.example.yazlab22;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;

public class Sozluk {
    static KelimeAgaci agac;
    public Context c;
    static HashMap<Character, Integer> harfDegerleri;

    public Sozluk(Context c) {
        this.c=c;
        agac = new KelimeAgaci();
        sozlukOku("turkce_kelime_listesi_olustur.txt", agac);

        setHarfDegerleri();
    }

    void sozlukOku(String fileName, KelimeAgaci trie){
        try{
            InputStreamReader inputStreamReader =new InputStreamReader(c.getAssets().open(fileName));
            BufferedReader br = new BufferedReader(inputStreamReader);
            String word;
             Locale locale =new java.util.Locale("tr", "TR");
            while ( (word  = br.readLine()) != null ){
                trie.kelimeEkle(word.toUpperCase(locale));
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public boolean kelimeAraAgacta(String s)
    {
        return agac.kelimeAra(s);
    }
    public  int kelimePuani(String kelime){
        int puan = 0;
        for (char c : kelime.toCharArray()){
            puan += harfDegerleri.get(c);
        }
        return puan;
    }

    void setHarfDegerleri(){
        harfDegerleri = new HashMap<>();
        harfDegerleri.put('A',1);
        harfDegerleri.put('B',3);
        harfDegerleri.put('C',4);
        harfDegerleri.put('Ç',4);
        harfDegerleri.put('D',3);
        harfDegerleri.put('E',1);
        harfDegerleri.put('F',7);
        harfDegerleri.put('G',5);
        harfDegerleri.put('Ğ',8);
        harfDegerleri.put('H',5);
        harfDegerleri.put('I',2);
        harfDegerleri.put('İ',1);
        harfDegerleri.put('J',10);
        harfDegerleri.put('K',1);
        harfDegerleri.put('L',1);
        harfDegerleri.put('M',2);
        harfDegerleri.put('N',1);
        harfDegerleri.put('O',2);
        harfDegerleri.put('Ö',7);
        harfDegerleri.put('P',5);
        harfDegerleri.put('R',1);
        harfDegerleri.put('S',2);
        harfDegerleri.put('Ş',4);
        harfDegerleri.put('T',1);
        harfDegerleri.put('U',2);
        harfDegerleri.put('Ü',3);
        harfDegerleri.put('V',7);
        harfDegerleri.put('Y',3);
        harfDegerleri.put('Z',4);


    }
}
