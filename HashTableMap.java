// --== CS400 File Header Information ==--
// Name: Luke David Breyer
// Email: lbreyer@wisc.edu
// Team: KF
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: Hello World!

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTableMap implements MapADT<Integer, String> {
  private LinkedList<PatientNode>[] hashTable;
  private int capacity; // The total capacity of the hash table
  private int size; // The number of items presently within the hash table

  /**
   * Parameterized constructor method that creates a new hash table of the desired capacity. It also
   * sets the capacity field to the value of the parameter and initializes the size field to 0.
   * 
   * @param capactity - the desired capacity for the new Hash Map
   */
  public HashTableMap(int capacity) {
    hashTable = new LinkedList[capacity];
    this.capacity = capacity;
    size = 0;
  }

  /**
   * Default constructor method that creates a new hash table of the default capactity of 10. It
   * also sets the capacity field to 10 and initializes the size field to 0.
   */
  public HashTableMap() {
    hashTable = new LinkedList[10];
    this.capacity = 10;
    size = 0;
  }

  /**
   * Takes all the required fields and builds a Patient ID from them.
   * 
   * @param location - Patient's location [1]
   * @param floor    - Patient's floor [1]
   * @param covid    - Patient's initial COVID test results [1]
   * @param age      - Patient's age [3]
   * @param ID       - Patient's UID [3]
   * @return PatientID - The ID constructed from the sent fields
   */
  public int buildID(int location, int floor, int covid, int age, int ID) {
    int builtID =
        ID + (age * 1000) + (covid * 1000000) + (floor * 10000000) + (location * 100000000);
    return builtID;
  }

  /**
   * Object addition method that tests if a presented key is already present within the hash table.
   * Given that the Key is not already present the method hashes the key to a position in the table.
   * If the given position is empty, a new LinkedList is created at that position to hold future
   * PatientNodes, and a new PatientNode is created using the key and value parameters and added to
   * the LinkedList. It then tests to see if the size is greater than or equal to 80& of the
   * capacity. If it is, the table is called to resize.
   * 
   * @param KeyType   key - the key by which the point is hashed into the hash table
   * @param ValueType value - the value to be stored at the given corresponding position
   * @return boolean - true if the pair could be added and false if not
   */
  @Override
  public boolean put(Integer patientID, String patientName) {
    // Converts the key to its corresponding situational hash value
    int hashKey = Math.abs(patientID.hashCode()) % capacity;
    // Checks if the key is already present within the table
    if (containsKey(patientID)) {
      return false;
    } else {
      // If the position is empty, creates a new LinkedList and then adds a new PatientNode to the
      // list
      if (hashTable[hashKey] == null) {
        hashTable[hashKey] = new LinkedList<PatientNode>();
        hashTable[hashKey].add(new PatientNode(patientID, patientName));
      } else {
        // If there is already a LinkedList at the position, add a new HashedNode to the list
        hashTable[hashKey].add(new PatientNode(patientID, patientName));
      }
      size++;
      // If size >= 80% of capacity, resize and rehash the array
      if (((double) size) / capacity >= .8) {
        resize();
      }
      return true;
    }
  }
  
  /**
   * Allows for patients to be added from a formatted text file.
   * 
   * @param file - the file to read from
   */
  public void filePut(File file) {
    try {
      Scanner input = new Scanner(file);
      while (input.hasNext()) {
        String name = input.next() + input.next();
        int location = input.nextInt();
        int floor = input.nextInt();
        boolean covid = input.nextBoolean();
        int covidVal;
        if (covid) {
          covidVal = 1;
        }
        else {
          covidVal = 0;
        }
        int age = input.nextInt();
        int uid = input.nextInt();
        int ID = buildID(location, floor, covidVal, age, uid);
        put(ID, name);
      }
      input.close();
    } catch (FileNotFoundException expt) {
      System.out.println("ERROR: File not found!");
    }
  }

  /**
   * If the size becomes greater than or equal to 80% of the capacity, this method is called to
   * store the current hash table to a shadow array, assign the hash table to a new array of double
   * capacity, and rehash the values of the shadow array to the new hashTable array.
   */
  public void resize() {
    int storeCap = capacity;
    capacity = capacity * 2;
    // Assign the current table to a shadow array
    LinkedList<PatientNode>[] shadow = hashTable;
    // Assign the hash table to an empty array of the new capacity
    clear();
    // Rehash the nodes from the old array into the new array
    for (int i = 0; i < storeCap; i++) {
      if (shadow[i] != null) {
        for (int j = 0; j < shadow[i].size(); j++) {
          put(shadow[i].get(j).getKey(), shadow[i].get(j).getValue());
        }
      }
    }
  }

  /**
   * Non-modifying retrieval method that searches the hash table for a given key. If the key can be
   * found, the method returns the corresponding Value. If it can not be found, the method throws a
   * NoSuchElementException.
   * 
   * @param KeyType key - the key by which the point is hashed into the hash table
   * @throws NoSuchElementExcetion - when the desired key can not be found within the hash table
   * @return ValueType - the Value contained within the PatientNode object corresponding to the
   *         given Key value
   */
  @Override
  public String get(Integer patientID) throws NoSuchElementException {
    // Converts the key to its corresponding situational hash value
    int hashKey = Math.abs(patientID.hashCode()) % capacity;
    // If the corresponding position is empty, the key can not be found
    if (hashTable[hashKey] == null) {
      throw new NoSuchElementException();
    }
    // if the position is filled, it sorts through the LinkedList for the given Key value
    for (int i = 0; i < hashTable[hashKey].size(); i++) {
      if (hashTable[hashKey].get(i).getKey().equals(patientID)) {
        return hashTable[hashKey].get(i).getValue();
      }
    }
    throw new NoSuchElementException();
  }

  /**
   * Non-modifying retrieval method that searches the hash table for a given key. If the key can be
   * found, the method returns the corresponding Value. If it can not be found, the method throws a
   * NoSuchElementException.
   * 
   * @param KeyType key - the key by which the point is hashed into the hash table
   * @throws NoSuchElementExcetion - when the desired key can not be found within the hash table
   * @return ValueType - the Value contained within the PatientNode object corresponding to the
   *         given Key value
   */
  public PatientNode getNode(Integer patientID) throws NoSuchElementException {
    // Converts the key to its corresponding situational hash value
    int hashKey = Math.abs(patientID.hashCode()) % capacity;
    // If the corresponding position is empty, the key can not be found
    if (hashTable[hashKey] == null) {
      throw new NoSuchElementException();
    }
    // if the position is filled, it sorts through the LinkedList for the given Key value
    for (int i = 0; i < hashTable[hashKey].size(); i++) {
      if (hashTable[hashKey].get(i).getKey().equals(patientID)) {
        return hashTable[hashKey].get(i);
      }
    }
    throw new NoSuchElementException();
  }

  /**
   * Iterates through all patients in the system and counts the number of positive COVID test
   * results
   * 
   * @return cvdSum - The total number of positive test results
   */
  public int getCovidSum() {
    int cvdSum = 0;
    for (int i = 0; i < capacity; i++) {
      if (hashTable[i] != null) {
        for (int j = 0; j < hashTable[i].size(); j++) {
          cvdSum += hashTable[i].get(j).getCovid();
        }
      }
    }
    return cvdSum;
  }

  /**
   * Prints out every patient in the system. Prints their PatientID followed by their Name
   */
  public void printTable() {
    for (int i = 0; i < capacity; i++) {
      if (hashTable[i] != null) {
        for (int j = 0; j < hashTable[i].size(); j++) {
          System.out.println("Patient: " + hashTable[i].get(j).getKey() + " Name: "
              + hashTable[i].get(j).getValue());
        }
      }
    }
  }

  /**
   * Returns the size field when called.
   * 
   * @return int - the size field for the hash table
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Non-modifying searching method uses the get method to report the presence of a given Key within
   * the hash table.
   * 
   * @param KeyType key - the key by which the point is hashed into the hash table
   * @return boolean - if the key was found within the hash table
   */
  @Override
  public boolean containsKey(Integer patientID) {
    try {
      get(patientID);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  /**
   * Removing modifying method that checks the presence of before searching for and removing a
   * PatientNode object corresponding to the given Key value.
   * 
   * @param KeyType key - the key by which the point is hashed into the hash table
   * @return ValueType - the value of the PatientNode object removed from the array
   */
  @Override
  public String remove(Integer patientID) {
    // Verifies that the hash table contains the given key
    if (!containsKey(patientID)) {
      return null;
    } else {
      // Converts the key to its corresponding situational hash value
      int hashKey = Math.abs(patientID) % capacity;
      // Searches for and removes the corresponding PatientNode object
      for (int i = 0; i < hashTable[hashKey].size(); i++) {
        if (hashTable[hashKey].get(i).getKey().equals(patientID)) {
          size--;
          return hashTable[hashKey].remove(i).getValue();
        }
      }
      return null;
    }
  }

  /**
   * Clears the hash table by setting it equal to a fresh array and resets size to 0.
   */
  @Override
  public void clear() {
    hashTable = new LinkedList[capacity];
    size = 0;
  }

}
