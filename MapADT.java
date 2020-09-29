// --== CS400 File Header Information ==--
// Name: Luke David Breyer
// Email: lbreyer@wisc.edu
// Team: KF
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: Hello World!

import java.util.NoSuchElementException;

public interface MapADT<KeyType,ValueType> {
  
    public boolean put(KeyType key, ValueType value);
    public ValueType get(KeyType key) throws NoSuchElementException;
    public int size();
    public boolean containsKey(KeyType key);
    public ValueType remove(KeyType key);
    public void clear();
}
