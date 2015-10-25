package ua.yandex.shad.tries;
import ua.yandex.shad.collections.DArray;

public interface Trie {

    void add(Tuple word);

    boolean contains(String word);

    boolean delete(String word);

    DArray words();

    DArray wordsWithPrefix(String pref);
    
    int size();
}
