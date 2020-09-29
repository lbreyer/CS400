// --== CS400 File Header Information ==--
// Name: Luke David Breyer
// Email: lbreyer@wisc.edu
// Team: KF
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: Hello World!

public class PatientNode {
  private Integer patientID; // The hash node's key
  private String patientName; // The hash node's stored value

  private String[] locations = new String[] {"UW Health East Clinic",
      "UW Health University Hospital", "UW Health West Clinic", "UW Health 20 S. Park Clinic",
      "American Family Children's Hospital", "UnityPoint Health - Meriter", "Arboretum Clinic",
      "Oakwood Clinic"};
  
  private String location; 
  private int floor;
  private int covid;
  private int age;
  private int uID;

  /**
   * Parameterized constructor creates a HashNode object that stores the parameterized Key and Value
   * into its corresponding fields.
   * 
   * @param KeyType   key - the hash key for this HashNode object
   * @param ValueType value - the stored value for this HashNode object
   */
  public PatientNode(Integer patientID, String value) {
    this.patientID = patientID;
    this.patientName = value;
    location = locations[(patientID / 100000000) - 1];
    floor = ((patientID % 100000000) / 10000000) + 1;
    covid = (patientID % 10000000) / 1000000;
    age = (patientID % 1000000) / 1000;
    uID = patientID % 1000;
  }



  /**
   * Returns the HashNode's key
   * 
   * @return KeyType - the key value of the HashNode object
   */
  public Integer getKey() {
    return patientID;
  }

  /**
   * Returns the HashNode's value
   * 
   * @return ValueType - the value of the HashNode object
   */
  public String getValue() {
    return patientName;
  }



  /**
   * @return the location
   */
  public String getLocation() {
    return location;
  }



  /**
   * @return the floor
   */
  public int getFloor() {
    return floor;
  }



  /**
   * @return the covid
   */
  public int getCovid() {
    return covid;
  }



  /**
   * @return the age
   */
  public int getAge() {
    return age;
  }



  /**
   * @return the uID
   */
  public int getuID() {
    return uID;
  }
}
