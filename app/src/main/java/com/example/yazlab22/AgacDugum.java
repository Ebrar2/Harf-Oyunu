package com.example.yazlab22;

import java.util.HashMap;

public class AgacDugum {

        Character c;
        Boolean yaprak = false;
        HashMap<Character, AgacDugum> children = new HashMap<>();
        public AgacDugum() {}
        public AgacDugum(Character c) {
            this.c = c;
        }
}
