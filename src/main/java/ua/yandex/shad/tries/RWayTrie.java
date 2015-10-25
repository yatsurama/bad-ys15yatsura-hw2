package ua.yandex.shad.tries;

import java.util.LinkedList;
import ua.yandex.shad.collections.DArray;

public class RWayTrie implements Trie {

    public static final int R = 26;
    public static final int EMPTY_VAL = -1;
    public static final int LETTER_SHIFT = 'a';
    private Node root = new Node();
       
    static class Node {
        private int val = EMPTY_VAL;
        private Node[] next = new Node[R];
        
        public void setValue(int value) {
            val = value;
        }
        
        public int getValue() {
            return val;
        }
        
        public void setNext(char c) {
            next[indexOf(c)] = new Node();
        }
        
        public Node getNext(char c) {
            return next[indexOf(c)];
        }
    }
    
    public Node getRoot() {
        return root;
    }
    
    public static int indexOf(char c) {
        int index = c-LETTER_SHIFT;
        if (index < 0 || index >= R) {
            throw new IllegalArgumentException();
        }
        return index;
    }
     
    public int getVal(String s) {
        Node x = get(root, s, 0);
        if (x == null) {
            return EMPTY_VAL;
        }
        return (int) x.val;
    }
    
    public Node get(Node x, String s, int d) {
        if (s == null) {
            return null;
        }
        if (x == null) {
            return null;
        }
        if (d == s.length()) {
            return x;
        }
        char c = s.charAt(d);
        return get(x.next[indexOf(c)], s, d+1);
    }
     
    @Override
    public void add(Tuple t) {
        root = add(root, t, 0);
    }

    public Node add(Node y, Tuple t, int d) {
        Node x = y;
        if (x == null) {
            x = new Node();
        }
        if (d == t.getTerm().length()) {
            x.val = t.getWeight();
            return x;
        }
        char c = t.getTerm().charAt(d);
        x.next[indexOf(c)] = add(x.next[indexOf(c)], t, d+1);
        return x;
    }
    
    @Override
    public boolean contains(String word) {
        return !(getVal(word) == EMPTY_VAL);
    }

    @Override
    public boolean delete(String word) {
        
        int size = size();
        root = delete(root, word, 0);
        return size() == size-1;
    }
    
    private Node delete(Node y, String s, int d) {
        if (s == null || y == null) {
            return null;
        }
        
        if (d == s.length()) {
            y.val = EMPTY_VAL;
        } else {
            char c = s.charAt(d);
            y.next[indexOf(c)] = delete(y.next[indexOf(c)], s, d+1);
        }
        
        if (y.val != EMPTY_VAL) {
            return y;
        }
        for (char c = 'a'; c < 'a'+R; c++) {
            if (y.next[indexOf(c)] != null) {
                return y;
            }
        }
        return null;
    }

    @Override
    public DArray words() {
        return wordsWithPrefix("");
    }

    static class NodeWithWord {
        private final Node node;
        private final String word;
 
        public NodeWithWord(Node n, String w) {
            node = n;
            word = w;
        }
        
        public NodeWithWord(NodeWithWord nw) {
            node = nw.node;
            word = nw.word;
        }
    }
    
    @Override
    public DArray wordsWithPrefix(String s) {
        
        DArray words = new DArray();
        bfs(new NodeWithWord(get(root, s, 0), s), words);
        return words;
    }
    
    private void bfs(NodeWithWord x, DArray words) {
        if (x.node == null) {
            return;
        }
        
        LinkedList<NodeWithWord> queue = new LinkedList<NodeWithWord>();
        queue.add(x);
        
        while (!queue.isEmpty()) {
            NodeWithWord v = new NodeWithWord(queue.removeFirst());
            if (v.node.getValue() != EMPTY_VAL) {
                words.push(v.word);
            }
            for (char c = 'a'; c < 'a'+R; c++) {
                if (v.node.next[indexOf(c)] != null) {
                    queue.add(new NodeWithWord(v.node.next[indexOf(c)], 
                                               v.word + c));
                }
            }
        }
    }

    @Override
    public int size() {
        
        return size(root);
    }
    
    public int size(Node x) {
        if (x == null) {
            return 0;
        }
        int cnt = 0;
        if (x.val != EMPTY_VAL) {
            cnt++;
        }
        for (char c = 'a'; c < 'a'+R; c++) {
            cnt += size(x.next[indexOf(c)]);
        }
        return cnt;
    }

}
