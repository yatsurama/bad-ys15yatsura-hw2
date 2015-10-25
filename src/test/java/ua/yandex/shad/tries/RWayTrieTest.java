package ua.yandex.shad.tries;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.collections.DArray;

@RunWith(MockitoJUnitRunner.class)
public class RWayTrieTest {
    
    private RWayTrie trie;
    private RWayTrie.Node root;
    
    @Mock private Tuple mockTiger = Mockito.mock(Tuple.class);
    @Mock private Tuple mockHell = Mockito.mock(Tuple.class);
    @Mock private Tuple mockYouth = Mockito.mock(Tuple.class);
    @Mock private Tuple mockHe = Mockito.mock(Tuple.class);
    @Mock private Tuple mockHead = Mockito.mock(Tuple.class);
    @Mock private Tuple mockAntananarivu = Mockito.mock(Tuple.class);
    @Mock private Tuple mockYou = Mockito.mock(Tuple.class);
    @Mock private Tuple mockEmpty = Mockito.mock(Tuple.class);
    
    public RWayTrieTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        manualRWayTrieAssembling();
        initializeMocks();
    }
    
    @After
    public void tearDown() {
    }
    
    public static RWayTrie.Node manualTrieGet(RWayTrie.Node root, String s) { 
        RWayTrie.Node node = root;
        for (int i = 0; i<s.length(); i++) {
            if(node == null) {
                return null;
            }
            node = node.getNext(s.charAt(i));
        }
        return node;
    }
    
    public void manualRWayTrieAssembling() {
        trie = new RWayTrie();
        root = trie.getRoot();
        root.setNext('h');
        manualTrieGet(root, "h").setNext('e');
        manualTrieGet(root, "he").setNext('y');
        manualTrieGet(root, "hey").setValue(3);
        manualTrieGet(root, "he").setNext('l');
        manualTrieGet(root, "hel").setNext('l');
        manualTrieGet(root, "hell").setValue(4);
        manualTrieGet(root, "hell").setNext('o');
        manualTrieGet(root, "hello").setValue(5);
        root.setNext('y');
        manualTrieGet(root, "y").setNext('o');
        manualTrieGet(root, "yo").setNext('u');
        manualTrieGet(root, "you").setValue(3);
    }
    
    public void initializeMocks() {
        when(mockTiger.getTerm()).thenReturn("tiger");
        when(mockTiger.getWeight()).thenReturn(5);
        when(mockHell.getTerm()).thenReturn("hell");
        when(mockHell.getWeight()).thenReturn(4);
        when(mockYouth.getTerm()).thenReturn("youth");
        when(mockYouth.getWeight()).thenReturn(5);
        when(mockHe.getTerm()).thenReturn("he");
        when(mockHe.getWeight()).thenReturn(2);
        when(mockHead.getTerm()).thenReturn("head");
        when(mockHead.getWeight()).thenReturn(4);
        when(mockAntananarivu.getTerm()).thenReturn("antananarivu");
        when(mockAntananarivu.getWeight()).thenReturn(12);
        when(mockYou.getTerm()).thenReturn("you");
        when(mockYou.getWeight()).thenReturn(3);
        when(mockEmpty.getTerm()).thenReturn("");
        when(mockEmpty.getWeight()).thenReturn(0);
    }
   
   @Test
    public void testIndexOf_a() {
        int expResult = 0;
        int result = RWayTrie.indexOf('a');
        assertEquals(expResult, result);
    }
    
   @Test
    public void testIndexOf_z() {
        int expResult = 25;
        int result = RWayTrie.indexOf('z');
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIndexOf_f() {
        int expResult = 5;
        int result = RWayTrie.indexOf('f');
        assertEquals(expResult, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIndexOf_NotLetter() {
        int result = RWayTrie.indexOf('5');
    }
    
    @Test
    public void testGetVal_WeightOfHello() {
        String s = "hello";
        int expResult = 5;
        int result = trie.getVal(s);
        assertEquals(expResult, result);
    }
    
     @Test
    public void testGetVal_AbsentWordMissInFirstLetter() {
        String s = "tear";
        int expResult = RWayTrie.EMPTY_VAL;
        int result = trie.getVal(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetVal_AbsentWordMissInThirdLetter() {
        String s = "hero";
        int expResult = RWayTrie.EMPTY_VAL;
        int result = trie.getVal(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetVal_AbsentWordMissInEmptyNode() {
        String s = "your";
        int expResult = RWayTrie.EMPTY_VAL;
        int result = trie.getVal(s);
        assertEquals(expResult, result);
    }

    @Test
    public void testGet_WeightOfNode() {
        String s = "hello";
        int expResult = 5;
        RWayTrie.Node resNode = trie.get(root, s, 0);
        int result = resNode.getValue();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGet_AbsentWord() {
        String s = "hero";
        RWayTrie.Node result = trie.get(root, s, 0);
        
        assertNull(result);
    }
    
    /**
     * Test of add method, of class RWayTrie.
     */
    @Test
    public void testAdd_Tiger_AddFromFirstSymbol() {
        trie.add(mockTiger);
        int expResult = 5;
        int result = manualTrieGet(root, "tiger").getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdd_Hell_AlreadyInTheTrie() {
        trie.add(mockHell);
        int expResult = 4;
        int result = trie.size();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdd_Youth_ContinueExsistingChain() {
        trie.add(mockYouth);
        int expResult = 5;
        int result = manualTrieGet(root, "youth").getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdd_He_AddOnlyValueNotNodes() {
        trie.add(mockHe);
        int expResult = 2;
        int result = manualTrieGet(root, "he").getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdd_Head_Branching() {
        trie.add(mockHead);
        int expResult = 4;
        int result = manualTrieGet(root, "head").getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdd_Antananarivu_LongWord() {
        trie.add(mockAntananarivu);
        int expResult = 12;
        int result = manualTrieGet(root, "antananarivu").getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdd_Tiger_Node() {
        trie.add(root, mockTiger, 0);
        int expResult = 5;
        int result = manualTrieGet(root, "tiger").getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdd_Hell_Node() {
        trie.add(root, mockHell, 0);
        int expResult = 4;
        int result = trie.size();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdd_Head_Node() {
        trie.add(root, mockHead, 0);
        int expResult = 4;
        int result = manualTrieGet(root, "head").getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdd_Empty() {
        trie.add(root, mockEmpty, 0);
        int expResult = 0;
        int result = manualTrieGet(root, "").getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class RWayTrie.
     */
    @Test
    public void testContains_EmptyWord() {
        String word = "";
        boolean expResult = false;
        boolean result = trie.contains(word);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContains_WordInTrie() {
        String word = "hello";
        boolean expResult = true;
        boolean result = trie.contains(word);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContains_AbsentWord() {
        String word = "head";
        boolean expResult = false;
        boolean result = trie.contains(word);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContains_PrefixOfWord() {
        String word = "hel";
        boolean expResult = false;
        boolean result = trie.contains(word);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContains_EmptyTrie() {
        RWayTrie emptyTrie = new RWayTrie();
        String word = "word";
        boolean expResult = false;
        boolean result = emptyTrie.contains(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class RWayTrie.
     */
    @Test
    public void testDelete_ExsistingWord() {
        String word = "hello";
        boolean expResult = true;
        boolean result = trie.delete(word);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete_NotExsistingWord() {
        String word = "bird";
        boolean expResult = false;
        boolean result = trie.delete(word);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testDelete_FromEmptyTrie() {
        String s = "hey";
        boolean expResult = false;
        RWayTrie EmptyTrie = new RWayTrie();
        boolean result = EmptyTrie.delete(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testDelete_TwoItemsSizeChange() {
        int expResult = 2;
        String word1 = "hell";
        trie.delete(word1);
        String word2 = "you";
        trie.delete(word2);
        int result = trie.size();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testDelete_AllItems() {
        int expResult = 0;
        String word1 = "hell";
        trie.delete(word1);
        String word2 = "you";
        trie.delete(word2);
        String word3 = "hello";
        trie.delete(word3);
        String word4 = "hey";
        trie.delete(word4);
        int result = trie.size();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testDelete_LongDeleteShortRemains() {
        boolean expResult = true;
        trie.delete("hello");
        boolean result = trie.delete("hell");
        assertEquals(expResult, result);
    }
    /**
     * Test of words method, of class RWayTrie.
     */
    @Test
    public void testWords_ForFourWords() {
        DArray expResult = new DArray();
        expResult.push("hey");
        expResult.push("you");
        expResult.push("hell");
        expResult.push("hello");
        DArray result = new DArray();
        result = trie.words();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWords_ForEmptyTrie() {
        RWayTrie emptyTrie = new RWayTrie();
        DArray expResult = new DArray();
        DArray result = new DArray();
        result = emptyTrie.words();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWords_WithoutOneWord() {
        manualTrieGet(root, "hey").setValue(RWayTrie.EMPTY_VAL);
        DArray expResult = new DArray();
        expResult.push("you");
        expResult.push("hell");
        expResult.push("hello");
        DArray result = new DArray();
        result = trie.words();
        assertEquals(expResult, result);
    }

    /**
     * Test of wordsWithPrefix method, of class RWayTrie.
     */
    @Test
    public void testWordsWithPrefix_ThreeWords() {
        String s = "he";
        DArray expResult = new DArray();
        expResult.push("hey");
        expResult.push("hell");
        expResult.push("hello");
        DArray result = new DArray();
        result = trie.wordsWithPrefix(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefix_ExisitsWordEqualToPrefix() {
        String s = "hell";
        DArray expResult = new DArray();
        expResult.push("hell");
        expResult.push("hello");
        DArray result = new DArray();
        result = trie.wordsWithPrefix(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefix_NotEnoughSymbolsToContinueSearch() {
        String s = "yours";
        DArray expResult = new DArray();
        DArray result = new DArray();
        result = trie.wordsWithPrefix(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefix_OneWord() {
        String s = "y";
        DArray expResult = new DArray();
        expResult.push("you");
        DArray result = new DArray();
        result = trie.wordsWithPrefix(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefix_TheLongest() {
        String s = "hello";
        DArray expResult = new DArray();
        expResult.push("hello");
        DArray result = new DArray();
        result = trie.wordsWithPrefix(s);
        assertEquals(expResult, result);
    }
   
    /**
     * Test of size method, of class RWayTrie.
     */
    @Test
    public void testSize_FourItems() {
        int expResult = 4;
        int result = trie.size();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSize_EmptyTrie() {
        int expResult = 0;
        RWayTrie emptyTrie = new RWayTrie();
        int result = emptyTrie.size();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSize_OfNodeHe() {
        int expResult = 3;
        int result = trie.size(manualTrieGet(root, "he"));
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSize_OfNodeHel() {
        int expResult = 2;
        int result = trie.size(manualTrieGet(root, "hel"));
        assertEquals(expResult, result);
    }
    
}
