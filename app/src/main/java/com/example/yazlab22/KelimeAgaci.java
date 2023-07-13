package com.example.yazlab22;

import java.util.HashMap;

public class KelimeAgaci {
    private AgacDugum kok;

    public KelimeAgaci() {this.kok = new AgacDugum();}

    public void kelimeEkle(String kelime) {
        if (kelime == null) return;
        AgacDugum basla = kok;
        for (char c : kelime.toCharArray()){
            HashMap<Character, AgacDugum> child = basla.children;
            if (child.containsKey(c)){
                basla = child.get(c);
            } else{
                basla = new AgacDugum(c);
                child.put(c, basla);
            }
        }
        basla.yaprak = true;
    }

    public boolean kelimeAra(String kelime) {
        if (kelime == null|| kelime == "") return false;
        kelime = kelime.toUpperCase();
        AgacDugum basla = kok;
        for (char c: kelime.toCharArray()){
            HashMap<Character, AgacDugum> cocuk = basla.children;
            if (cocuk.containsKey(c)){
                basla = cocuk.get(c);
            } else{
                return false;
            }
        }
        if (basla.yaprak){
            return true;
        } else {
            return false;
        }
    }
}
