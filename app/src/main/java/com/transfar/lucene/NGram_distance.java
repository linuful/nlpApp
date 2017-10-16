package com.transfar.lucene;

import org.apache.lucene.search.spell.NGramDistance;

public class NGram_distance {

    public static void main(String[] args) {

        NGramDistance ng = new NGramDistance();
        float score1 = ng.getDistance("Gorbachev", "Gorbechyov");
        System.out.println(score1);
        float score2 = ng.getDistance("girl", "girlfriend");
        System.out.println(score2);
    }
}