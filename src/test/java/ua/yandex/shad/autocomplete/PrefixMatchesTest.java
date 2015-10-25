package ua.yandex.shad.autocomplete;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.collections.DArray;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {
    
    PrefixMatches prefMatches;
    @Mock private Tuple mockYou = Mockito.mock(Tuple.class);
    @Mock private Tuple mockHey = Mockito.mock(Tuple.class);
    @Mock private Tuple mockHell = Mockito.mock(Tuple.class);
    @Mock private Tuple mockHello = Mockito.mock(Tuple.class);
    @Mock private Tuple mockTiger = Mockito.mock(Tuple.class);
    @Mock private Tuple mockYouth = Mockito.mock(Tuple.class);
    @Mock private Tuple mockHe = Mockito.mock(Tuple.class);
    @Mock private Tuple mockHead = Mockito.mock(Tuple.class);
    @Mock private Tuple mockAntananarivu = Mockito.mock(Tuple.class);
    @Mock private Tuple mockEmpty = Mockito.mock(Tuple.class);
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        prefMatches = new PrefixMatches();
        initializeMocks();
        prefMatches.trie.add(mockHey);
        prefMatches.trie.add(mockYou);
        prefMatches.trie.add(mockHell);
        prefMatches.trie.add(mockHello);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of load method, of class PrefixMatches.
     */
    
   public void initializeMocks() {
        when(mockYou.getTerm()).thenReturn("you");
        when(mockYou.getWeight()).thenReturn(3);
        when(mockHey.getTerm()).thenReturn("hey");
        when(mockHey.getWeight()).thenReturn(3);
        when(mockHell.getTerm()).thenReturn("hell");
        when(mockHell.getWeight()).thenReturn(4);
        when(mockHello.getTerm()).thenReturn("hello");
        when(mockHello.getWeight()).thenReturn(5);
        when(mockTiger.getTerm()).thenReturn("tiger");
        when(mockTiger.getWeight()).thenReturn(5);
        when(mockYouth.getTerm()).thenReturn("youth");
        when(mockYouth.getWeight()).thenReturn(5);
        when(mockHe.getTerm()).thenReturn("he");
        when(mockHe.getWeight()).thenReturn(2);
        when(mockHead.getTerm()).thenReturn("head");
        when(mockHead.getWeight()).thenReturn(4);
        when(mockAntananarivu.getTerm()).thenReturn("antananarivu");
        when(mockAntananarivu.getWeight()).thenReturn(12);
        when(mockEmpty.getTerm()).thenReturn("");
        when(mockEmpty.getWeight()).thenReturn(0);
    }
    
    @Test
    public void testLoad_EmptyList() {
        
        PrefixMatches prefixMatches = new PrefixMatches();
        int expResult = 0;
        int result = prefixMatches.load();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoad_SixObjects() {
        
        PrefixMatches prefixMatches = new PrefixMatches();
        int expResult = 6;
        int result = prefixMatches.load("fox bat", "tiger", "rat", "bee spider" );
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoad_AllShort() {
        
        PrefixMatches prefixMatches = new PrefixMatches();
        int expResult = 0;
        int result = prefixMatches.load("by", "hi", "he is", "a");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoad_TwoShort() {
        
        PrefixMatches prefixMatches = new PrefixMatches();
        int expResult = 3;
        int result = prefixMatches.load("opera", "chrome", "ie", "vk", "facebook");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoad_ToFilledTrie() {
        
        int expResult = 7;
        int result = prefMatches.load("good", "bye", "master");
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class PrefixMatches.
     */
    
   @Test
    public void testContains_NullArgument() {
        assertFalse(prefMatches.contains(null));
    }
    
   @Test
    public void testContains_EmptyWord() {
        assertFalse(prefMatches.contains(""));
    }
    
   @Test
    public void testContains_EmptyTree() {
        PrefixMatches emptyObj = new PrefixMatches();
        assertFalse(emptyObj.contains("hello"));
    }
    
    @Test
    public void testContains_WordInTrie() {
        assertTrue(prefMatches.contains("hell"));
    }
    
    @Test
    public void testContains_WordNotInTrie() {
        assertFalse(prefMatches.contains("hedge"));
    }

    /**
     * Test of delete method, of class PrefixMatches.
     */
    @Test
    public void testDelete_Null() {
        assertFalse(prefMatches.delete(null));
    }
    
    @Test
    public void testDelete_FromEmptyObject() {
        PrefixMatches emptyObj = new PrefixMatches();
        assertFalse(emptyObj.delete("hello"));
    }
    
    @Test
    public void testDelete_IfDeletedWordInTrie() {
        assertTrue(prefMatches.delete("you"));
    }
    
    @Test
    public void testDelete_DeletedTwo() {
        prefMatches.delete("hey");
        prefMatches.delete("hell");
        int expResult = 2;
        int result = prefMatches.trie.size();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testDelete_AllItems() {
        prefMatches.delete("you");
        prefMatches.delete("hello");
        prefMatches.delete("hey");
        prefMatches.delete("hell");
        int expResult = 0;
        int result = prefMatches.trie.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of wordsWithPrefix method, of class PrefixMatches.
     */
    @Test
    public void testWordsWithPrefix_String_int_AllWords() {
        DArray expResult = new DArray();
        expResult.push("hey");
        expResult.push("hell");     
        expResult.push("hello");
        DArray result = new DArray();
        result = prefMatches.wordsWithPrefix("he", 4);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefix_String_int_TwoLengths() {
        DArray expResult = new DArray();
        expResult.push("hey");
        expResult.push("hell");     
        DArray result = new DArray();
        result = prefMatches.wordsWithPrefix("he", 2);
        assertEquals(expResult, result);
    }
 
    @Test
    public void testWordsWithPrefix_String_int_OneLength() {
        DArray expResult = new DArray();
        expResult.push("hey");  
        DArray result = new DArray();
        result = prefMatches.wordsWithPrefix("he", 1);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefix_String_int_NoLengths() {
        DArray expResult = new DArray();  
        DArray result = new DArray();
        result = prefMatches.wordsWithPrefix("he", 0);
        assertEquals(expResult, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_String_int_NegativeK() {  
        DArray result = new DArray();
        result = prefMatches.wordsWithPrefix("he", -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_String_int_PrefixLessThenTwoSymbols() {  
        DArray result = new DArray();
        result = prefMatches.wordsWithPrefix("y", 3);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_String_int_EmptyPrefix() {  
        DArray result = new DArray();
        result = prefMatches.wordsWithPrefix("", 1);
    }

     /**
     * Test of wordsWithPrefix method, of class PrefixMatches.
     */
    @Test
    public void testWordsWithPrefix_String() {
        DArray expResult = new DArray();
        expResult.push("hey");
        expResult.push("hell");     
        expResult.push("hello");
        DArray result = new DArray();
        result = prefMatches.wordsWithPrefix("he");
        assertEquals(expResult, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_String_PrefixLessThenTwoSymbols() {  
        DArray result = new DArray();
        result = prefMatches.wordsWithPrefix("y");
    }
    /**
     * Test of size method, of class PrefixMatches.
     */
    @Test
    public void testSize_EmptyTrie() {
        PrefixMatches emptyObj = new PrefixMatches();
        assertEquals(emptyObj.size(), 0);
    }
    
    @Test
    public void testSize_FourItems() {
        assertEquals(prefMatches.size(), 4);
    }
    
}
