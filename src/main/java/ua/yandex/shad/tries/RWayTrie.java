package ua.yandex.shad.tries;

import java.util.*;
import java.util.Queue;

public class RWayTrie implements Trie {

    private static int R = 26;
    private Node root;
    
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }
    
    public int get(String s) {
        Node x = get(root, s, 0);
        if(x == null) {
            return -1;
        }
        return (int)x.val;
    }
    
    private Node get(Node x, String s, int d) {
        if(x == null) {
            return null;
        }
        if(d == s.length()) {
            return x;
        }
        char c = s.charAt(d);
        return get(x.next[c], s, d+1);
    }
     
    @Override
    public void add(Tuple t) {
        root = add(root, t, 0);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

   private Node add(Node x, Tuple t, int d) {
       if(x == null) {
           x = new Node();
       }
       if(d == t.term.length()) {
           x.val = t.weight;
           return x;
       }
       char c = t.term.charAt(d);
       x.next[c] = add(x.next[c], t, d+1);
       return x;
   }
    
    @Override
    public boolean contains(String word) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(String word) {
        
        root = delete(root, word, 0);
        return true;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private Node delete(Node x, String s, int d) {
        if (x == null) {
            return null;
        }
        if (d == s.length()) {
            x.val = null;
        } else {
            char c = s.charAt(d);
            x.next[c] = delete(x.next[c], s, d+1);
        }
        
        if (x.val != null) {
            return x;
        }
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        
        Queue<String> q = new LinkedList<String>();
        collect(get(root, s, 0), s, q);
        return q;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void collect(Node x, String s, Queue<String> q) {
        if (x == null) {
            return;
        }
        if(x.val != null) {
            q.add(s);
        }
        for(char c = 0; c < R; c++) {
            collect(x.next[c], s+c, q);
        }
    }
    
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new LinkedList<String>();
        collect(root, "", pat, q);
        return q;
    }
    
    public void collect(Node x, String s, String pat, Queue<String> q) {
        int d = s.length();
        if (x == null) {
            return;
        }
        if(d == pat.length() && x.val != null) {
            q.add(s);
        }
        if(d == pat.length()) {
            return;
        }
        
        char next = pat.charAt(d);
        for(char c = 0; c<R; c++) {
            if(next == '.' || next == c) {
                collect(x.next[c], s+c, pat, q);
            }
        }
     }

    @Override
    public int size() {
        
        return size(root);
        //throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        int cnt = 0;
        if (x.val != null) {
            cnt++;
        }
        for (char c = 0; c < R; c++) {
            cnt += size(x.next[c]);
        }
        return cnt;
    }

}
