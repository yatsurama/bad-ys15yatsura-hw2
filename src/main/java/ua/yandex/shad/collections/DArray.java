package ua.yandex.shad.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DArray implements Iterable<String> {
    public static final int DEFAULT_LENGTH = 16;
    private int actualLength;
    
    private String[] strings;
    
    public int actualLength() {
        return actualLength;
    }
    
    public void setLength(int l) {
        actualLength = l;
    }
    
    public DArray() {
        actualLength = 0;
        strings = new String[DEFAULT_LENGTH];
    }
    
    public DArray(int length) {
        actualLength = 0;
        strings = new String[length];
    }
    
    public void validateIndex(int index) {
        if (index < 0 || index >= actualLength) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public void validateLength(int newLength) {
        if (newLength > strings.length) {
            int arrLength = strings.length * 2;
            String[] newStrings = new String[arrLength];
            for (int i = 0; i < strings.length; i++) {
                newStrings[i] = strings[i];
            }
            strings = newStrings;
        }
    }
    
    public String get(int index) {
        validateIndex(index);
        return strings[index];
    }
    
    public void set(int index, String s) {
        validateIndex(index);
        strings[index] = s;
    }
    
    public void push(String s) {
        validateLength(actualLength+1);
        strings[actualLength++] = s;
    }
    
    @Override
    public Iterator iterator() {
        return new DArrayIterator();
    }
    
    public class DArrayIterator implements Iterator<String> {
        private int index = 0;
        
        public boolean hasNext() {
            return index < actualLength;
        }
        
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return strings[index++];
        }
    }
    
    @Override 
    public boolean equals(Object obj) {
        if (obj == null) {
                return false;
        }
        if (!(obj instanceof DArray)) {
            return false;
        }    
        DArray other = (DArray) obj;
        if (actualLength != other.actualLength) {
            return false;
        }
        for (int i = 0; i < actualLength; i++) {
            if (!strings[i].equals(other.strings[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return 42;
    }
     
}
