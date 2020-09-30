// --== CS400 File Header Information ==--
// Name: Matthew Thompson Soto
// Email: mathompson23@wisc.edu
// Team: KF
// TA: Siddharth Mohan
// Lecturer: Prof. Gary Dahl
// Notes to Grader: N/A

import java.util.NoSuchElementException;

/**
 * Test suite for the HashTableMap.java and COVID-19 UW Hospital Patient
 * Organization System
 * 
 * @author Matthew Thompson Soto (Test Engineer)
 */
public class Tests {
	/**
	 * Method that tests the functionality of HashTableMap's put() function.
	 * 
	 * @return true if all tests passed, false otherwise.
	 */
	public static boolean testPut() {
		PatientNode patient1 = new PatientNode(131471125, "Marcus Peters");
		PatientNode patient2 = new PatientNode(220777239, "John Doe");
		PatientNode patient3 = new PatientNode(481237436, "Gary Dahl");
		PatientNode patient4 = new PatientNode(360982508, "Abraham Lincoln");

		HashTableMap map = new HashTableMap();
		map.put(patient1.getKey(), patient1);
		map.put(patient2.getKey(), patient2);
		map.put(patient3.getKey(), patient3);
		map.put(patient4.getKey(), patient4);

		if (map.put(patient1.getKey(), patient1)) { // Tests that a patient cannot be added twice.
			return false;
		}

		// Checks if size and value remain unchanged.
		if (map.size() != 4 && map.numberOfCovidPos() != 2 && map.get(patient3.getKey()) != patient3) {

			return false;
		}

		return true;
	}

	/**
	 * Method that tests the functionality of HashTableMap's get() and clear()
	 * functions.
	 * 
	 * @return true if all tests passed, false otherwise.
	 */
	public static boolean testGet() {
		PatientNode patient1 = new PatientNode(131471125, "Marcus Peters");
		PatientNode patient2 = new PatientNode(220777239, "John Doe");
		PatientNode patient3 = new PatientNode(481237436, "Gary Dahl");
		PatientNode patient4 = new PatientNode(360982508, "Abraham Lincoln");
		PatientNode patient5 = new PatientNode(721362254, "Maria Santos");

		HashTableMap map = new HashTableMap();
		map.put(patient1.getKey(), patient1);
		map.put(patient2.getKey(), patient2);
		map.put(patient3.getKey(), patient3);
		map.put(patient4.getKey(), patient4);

		try { // Checks for patient not in system.
			map.get(patient5.getKey());
			return false;
		} catch (NoSuchElementException e) {
			System.out.println("testGet(): Exception caught!");
		}

		// If one of the patients put is not retrievable.
		if (map.get(patient4.getKey()) != patient4 && map.get(patient3.getKey()) != patient3
				&& map.get(patient2.getKey()) != patient2 && map.get(patient1.getKey()) != patient1) {
			return false;
		}

		map.clear(); // Tests clear.

		if (map.size() != 0 && map.numberOfCovidPos() != 0) { // Tests clear's reset.
			return false;
		}

		return true;
	}

	/**
	 * Method that tests the functionality of HashTableMap's resize() function, as
	 * well as numberOfCovidPos(), getCapacity(), and size()
	 * 
	 * @return true if all tests passed, false otherwise.
	 */
	public static boolean testResize() {
		HashTableMap map = new HashTableMap(5); // Capacity of 5.
		PatientNode patient1 = new PatientNode(131471125, "Marcus Peters");
		PatientNode patient2 = new PatientNode(220777239, "John Doe");
		PatientNode patient3 = new PatientNode(481237436, "Gary Dahl");
		PatientNode patient4 = new PatientNode(360982508, "Abraham Lincoln");

		map.put(patient1.getKey(), patient1);
		map.put(patient2.getKey(), patient2);
		map.put(patient3.getKey(), patient3);
		map.put(patient4.getKey(), patient4); // Adding patient should double table's capacity.

		if (map.size() != 4 && map.getCapacity() != 10 && map.numberOfCovidPos() != 2) { // Checks values.
			return false;
		}

		return true;
	}

	/**
	 * Method that tests the functionality of HashTableMap's remove() function.
	 * 
	 * @return true if all tests passed, false otherwise.
	 */
	public static boolean testRemove() {
		HashTableMap map = new HashTableMap();
		PatientNode patient1 = new PatientNode(131471125, "Marcus Peters");
		PatientNode patient2 = new PatientNode(220777239, "John Doe");
		PatientNode patient3 = new PatientNode(481237436, "Gary Dahl");

		map.put(patient1.getKey(), patient1);
		map.put(patient2.getKey(), patient2);
		map.put(patient3.getKey(), patient3);
		map.put(patient1.getKey(), patient1); // Tests an extra put.

		if (map.size() != 3) { // Checks size before removing.
			return false;
		}

		if (map.remove(patient1.getKey()) != patient1) { // Checks if removed patient is correct patient.
			return false;
		}

		if (map.remove(patient1.getKey()) != null) { // Checks if where the patient was is null.
			return false;
		}

		try { // Checks if the patient does not exist.
			map.get(patient1.getKey());
			return false;
		} catch (NoSuchElementException e) {
			System.out.println("testRemove(): Exception caught!");
		}

		if (map.size() != 2 && map.numberOfCovidPos() != 1) { // Makes sure values are updated.
			return false;
		}

		return true;
	}

	/**
	 * Method that tests the functionality of HashTableMap's containsKey() function.
	 * 
	 * @return true if all tests passed, false otherwise.
	 */
	public static boolean testContainsKey() {
		HashTableMap map = new HashTableMap();
		PatientNode patient1 = new PatientNode(131072125, "Marcus Peters");
		PatientNode patient2 = new PatientNode(220077239, "John Doe");
		PatientNode patient3 = new PatientNode(481023436, "Gary Dahl");

		map.put(patient1.getKey(), patient1);
		map.put(patient2.getKey(), patient2);

		if (map.containsKey(patient3.getKey())) { // Checks if patient3's key is in table.
			return false;
		}

		if (!map.containsKey(patient2.getKey())) { // Checks if patient2's key is not in table.
			return false;
		}

		return true;
	}

	/**
	 * Method that tests the PatientNode class creates a patient with all specified
	 * qualities, such as location, floor, COVID +/-, age, user ID, name.
	 * 
	 * @return true if all tests passed, false otherwise.
	 */
	public static boolean testPatientNode() {
		int location = 2;
		int floor = 3;
		int covid = 1;
		int age = 102;
		int ID = 628;
		String name = "Matthew Thompson";
		
		HashTableMap map = new HashTableMap();
		Integer patientID = map.buildID(location, floor, covid, age, ID);
		
		PatientNode patient = new PatientNode(patientID, name);
		
		if (patient.getAge() != age) {
			return false;
		}
		
		if (patient.getFloor() != 3) {
			System.out.println(patient.getFloor());
			return false;
		}
		
		if (patient.getCovid() != covid) {
			return false;
		}
		
		if (!patient.getLocation().equals("UW Health University Hospital")) {
			return false;
		}
		
		if (patient.getuID() != ID) {
			return false;
		}
		
		return true;
	}

	public static void main(String[] args) {
		System.out.println("Initializing tests...");
		System.out.println("- Result for testPut(): " + testPut());
		System.out.println("- Result for testGet(): " + testGet());
		System.out.println("- Result for testResize(): " + testResize());
		System.out.println("- Result for testRemove(): " + testRemove());
		System.out.println("- Result for testContainsKey(): " + testContainsKey());
		System.out.println("- Result for testPatientNode(): " + testPatientNode());
	}
}
