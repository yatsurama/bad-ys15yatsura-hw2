/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class DArrayTest {
    
    public DArrayTest() {
    }
    
    DArray array;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        array = new DArray(8);
        array.setLength(6);
        array.set(0, "zero");
        array.set(1, "one");
        array.set(2, "two");
        array.set(3, "three");
        array.set(4, "four");
        array.set(5, "five");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of validateIndex method, of class DArray.
     */
    
    @Test 
    public void testDefaultConstructor() {
        DArray array = new DArray();
    }
    
    @Test 
    public void testConstructorWithParametr() {
        DArray array = new DArray(64);
    }
    
    @Test
    public void testValidateIndex_InTheMiddle() {
        int index = 3;
        array.validateIndex(index);
    }
   
    @Test
    public void testValidateIndex_InTheBeginning() {
        int index = 0;
        array.validateIndex(index);
    }
    
    @Test
    public void testValidateIndex_InTheEnd() {
        int index = 5;
        array.validateIndex(5);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testValidateIndex_LessThanZero() {
        int index = -2;
        array.validateIndex(index);
    }
   
    @Test(expected = IndexOutOfBoundsException.class)
    public void testValidateIndex_MoreThanSize() {
        int index = 8;
        array.validateIndex(index);
    }

    /**
     * Test of validateLength method, of class DArray.
     */
    @Test
    public void testValidateLength_DoubleOnce() {
        array.validateLength(7);
        array.push("six");
        array.validateLength(8);
        array.push("seven");
        array.validateLength(9);
        array.push("eight");
        array.validateLength(10);
        array.push("nine");
        String expResult = "nine";
        String result = array.get(9);
        assertEquals(expResult, result);
    }
 
    @Test
    public void testValidateLength_DoubleTwice() {
        for(int i = 6; i<25; i++) {
            array.validateLength(i+1);
            array.push("string"+i);
        }
            
        String expResult = "string21";
        String result = array.get(21);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateLength_DoubleThrice() {
        for(int i = 6; i<35; i++) {
            array.validateLength(i+1);
            array.push("string"+i);
        }
            
        String expResult = "string33";
        String result = array.get(33);
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class DArray.
     */
    @Test
    public void testGet_Three() {
        String expResult = "three";
        String result = array.get(3);
        assertEquals(expResult, result);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_LessThanZero() {
        String result = array.get(-2);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_MoreThanSize() {
        String result = array.get(20);
    }

    /**
     * Test of set method, of class DArray.
     */

    @Test
    public void testSet_Three() {
        String expResult = "tres";
        array.set(3, "tres");
        String result = array.get(3);
        assertEquals(expResult, result);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSet_LessThanZero() {
        array.set(-1, "string");
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSet_MoreThanSize() {
        array.set(9, "string");
    }
    /**
     * Test of push method, of class DArray.
     */
    @Test
    public void testPush_OneItem() {
        array.push("hello");
        String expResult = "hello";
        String result = array.get(6);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testPush_Null() {
        array.push(null);
        String result = array.get(6);
        assertNull(result);
    }
    
    @Test
    public void testPush_ToEmptyArray() {
        DArray emptyArray = new DArray();
        emptyArray.push("I am first!");
        String expResult = "I am first!";
        String result = emptyArray.get(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of iterator method, of class DArray.
     */
    @Test
    public void testIterator_Next() {
        Iterator<String> it = array.iterator();
        String expResult = "zero";
        String result = it.next();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testHasNext_EmptyArray() {
        DArray emptyArray = new DArray();
        Iterator<String> it = emptyArray.iterator();
        boolean expResult = false;
        boolean result = it.hasNext();
        assertEquals(expResult, result);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testHasNext_CheckInNext() {
        DArray emptyArray = new DArray();
        Iterator<String> it = emptyArray.iterator();
        String result = it.next();
    }
    
    @Test
    public void testHasNext_NotEmptyArray() {
        Iterator<String> it = array.iterator();
        boolean expResult = true;
        boolean result = it.hasNext();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEquals_NullObj() {
        assertFalse(array.equals(null));
    }
    
    @Test
    public void testEquals_WithInt() {
        assertFalse(array.equals(5));
    }
    
    @Test
    public void testEquals_DifferentLengths() {
        assertFalse(array.equals(new DArray()));
    }
    
    @Test
    public void testEquals_DifferentElements() {
        DArray other = new DArray();
        other.push("zero");
        other.push("one");
        other.push("dva");
        other.push("three");
        other.push("four");
        other.push("five");
        assertFalse(array.equals(new DArray()));
    }
    
    @Test
    public void testHashCode_() {
        assertEquals(array.hashCode(), 42);
    }

}
