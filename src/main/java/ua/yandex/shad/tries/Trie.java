package ua.yandex.shad.tries;
import ua.yandex.shad.collections.DArray;

public interface Trie {

    public void add(Tuple word);

    public boolean contains(String word);

    public boolean delete(String word);

    public DArray words();

    public DArray wordsWithPrefix(String pref);
    
    public int size();
}
