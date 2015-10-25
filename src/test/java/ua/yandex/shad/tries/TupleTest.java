package ua.yandex.shad.tries;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TupleTest {
    
    public TupleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of equals method, of class Tuple.
     */
    @Test
    public void testEquals_WithNullReference() {
        Object obj = null;
        Tuple instance = new Tuple();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEquals_WithDouble() {
        Double dObj = 0.0;
        Tuple instance = new Tuple();
        boolean expResult = false;
        boolean result = instance.equals(dObj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEquals_WithUnequalTuple() {
        Tuple obj = new Tuple("tuple", 5);
        Tuple instance = new Tuple("java", 4);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEquals_WithEqualTuple() {
        Tuple obj = new Tuple("pascal", 6);
        Tuple instance = new Tuple("pascal", 6);
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEquals_WithUnequalWeight() {
        Tuple obj = new Tuple("pascal", 5);
        Tuple instance = new Tuple("pascal", 6);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of hashCode method, of class Tuple.
     */
    @Test
    public void testHashCode() {
        Tuple instance = new Tuple();
        int expResult = 42;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }
    
}
