/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplete;

import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;
import ua.yandex.shad.tries.RWayTrie;
import ua.yandex.shad.collections.DArray;
/**
 *
 * @author andrii
 */
public class PrefixMatches {

    public static final int MIN_LENGTH = 3;
    public static final int MIN_PREF = 2;
    public static final int DEFAULT_K = 3;
    
    public Trie trie = new RWayTrie();
    
    public int load(String... strings) {
        
        for (int i = 0; i < strings.length; i++) {
            String[] words = strings[i].split(" ");
            for (int j = 0; j < words.length; j++) {
                if (words[j].length() >= MIN_LENGTH) {
                    trie.add(new Tuple(words[j], words[j].length()));
                }
            }
        }
        return trie.size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public DArray wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, DEFAULT_K);
    }

    public DArray wordsWithPrefix(String pref, int k) {
        if (pref == null) {
            throw new NullPointerException();
        }
        int param = k;
        
        if (pref.length() < MIN_PREF || k < 0) {
            throw new IllegalArgumentException();
        }
        
        DArray autoComplete = new DArray();
        DArray words = trie.wordsWithPrefix(pref);
        for(int i = 0; i < words.actualLength && param > 0; i++) {
                autoComplete.push(words.get(i));
                if(i == 0 || words.get(i).length() > words.get(i-1).length()) {
                    param--;
                }
        }
        return autoComplete;
    }

    public int size() {
        return trie.size();
    }
}
