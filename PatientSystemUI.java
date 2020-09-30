import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PatientSystemUI {
  private static HashTableMap systemMap;

  /**
   * A helper method that reads a line of input, converts it to lowercase and
   * returns the first character in the line.
   *
   * @param sc The scanner from which to read the input line.
   * @return The first character from the line converted to lowercase, or '\0' if
   *         there is no first character.
   */
  public static char readChar(Scanner sc) {
    String temp = sc.nextLine();
    if (temp == null || temp == "") {
      return '\0';
    }
    temp = temp.toLowerCase();
    if (temp.length() != 0) {
      return temp.charAt(0);
    }
    return '\0';
  }

  /**
   * Add Patient - allows the user to add patients to the system. Prompts the user
   * for each required field.
   * 
   * @param scnr - The Scanner object to be used. Passed by calling method.
   */
  public static void addPatient(Scanner scnr) {
    System.out.println("----===Add Patient===----");
    System.out.println("Please enter patient information");

    boolean pass = false;

    // Prompts and verifies a valid patient name
    String name = "";
    while (!pass) {
      System.out.println("Please enter patient Name: ");
      name = scnr.nextLine();
      if (name.length() > 0) { // Was a name entered
        pass = true;
        continue;
      }
      System.out.println("Please enter a valid patuent name");
    }

    // Prompts and verifies a valid patient location
    System.out.println("Please select patient location:\n" + "(A) UW Health East Clinic\n"
        + "(B) UW Health University Hospital\n" + "(C) UW Health West Clinic\n" + "(D) UW Health 20 S. Park Clinic\n"
        + "(E) American Family Children's Hospital\n" + "(F) UnityPoint Health - Meriter\n" + "(G) Arboretum Clinic\n"
        + "(H) Oakwood Clinic\n");

    pass = false;
    char locTmp = 0;
    while (!pass) {
      System.out.println("Location: ");
      locTmp = readChar(scnr);
      if (locTmp == '\0' || locTmp < 'a' || locTmp > 'h') { // Was a valid character entered
        System.out.println("Please enter a valid location.");
        continue;
      } else {
        pass = true;
      }
    }

    int location = locTmp - 96; // Converts ASCII to a formatted location value

    // Prompts and verifies a valid patient floor
    pass = false;
    int floor = -1;
    while (!pass) {
      System.out.println("Please enter patient floor: ");
      if (scnr.hasNextInt()) {
        floor = scnr.nextInt();
        scnr.nextLine();
        if (floor > 0 && floor < 11) { // Was a valid floor entered
          pass = true;
          continue;
        }
      }
      scnr.nextLine();
      System.out.println("Please enter a valid floor number");
    }
    floor--; // Stores floors 1-10 to 0-9

    // Prompts and verifies a valid initial covid test result
    pass = false;
    int cvdTest = -1;
    char testTmp;
    while (!pass) {
      System.out.println("Please enter patient COVID test result (P - positive/N - negative): ");
      testTmp = readChar(scnr);
      switch (testTmp) { // Was a valid test result entered
        case ('p'):
          cvdTest = 1;
          pass = true;
          break;
        case ('n'):
          cvdTest = 0;
          pass = true;
          break;
        default:
          System.out.println("Please enter a valid test result");
      }
    }

    // Prompts and verifies a valid patient age
    pass = false;
    int age = -1;
    while (!pass) {
      System.out.println("Please enter patient age: ");
      if (scnr.hasNextInt()) {
        age = scnr.nextInt();
        scnr.nextLine();
        if (age >= 0 && age < 1000) { // Was a valid age entered
          pass = true;
          continue;
        }
      }
      scnr.nextLine();
      System.out.println("Please enter a valid age");
    }

    // Prompts and verifies a valid patient UID
    pass = false;
    int patientID = -1;
    while (!pass) {
      System.out.println("Please enter patient UID: ");
      if (scnr.hasNextInt()) {
        patientID = scnr.nextInt();
        scnr.nextLine();
        if (patientID >= 0 && patientID < 1000) { // Was a valid patient UID entered
          pass = true;
          continue;
        }
      }
      scnr.nextLine();
      System.out.println("Please enter a valid patient ID");
    }

    // Creates the patient ID
    int hashID = systemMap.buildID(location, floor, cvdTest, age, patientID);

    // Adds the new patient to the hash table
    systemMap.put(hashID, name);
    System.out.println("Patient " + hashID + " has been added.");
  }

  /**
   * Discharge Patient - allows the user to remove patients from the system.
   * Prompts the user for the patientID of the patient to be removed.
   * 
   * @param scnr - The Scanner object to be used. Passed by calling method.
   */
  public static void removePatient(Scanner scnr) {
    System.out.println("----===Discharge Patient===----");
    System.out.println("Please enter the ID of the patient you want to discharge.");
    boolean pass = false;
    int patientID = -1;
    while (!pass) {
      System.out.println("Patient ID: ");
      if (scnr.hasNextInt()) {
        patientID = scnr.nextInt();
        scnr.nextLine();
        if (patientID >= 100000000 && patientID < 1000000000) { // If the entered ID within a valid
                                                                // range
          pass = true;
          continue;
        }
      }
      scnr.nextLine();
      System.out.println("Please enter a valid patient ID");
    }

    // Tries to remove the entered patient
    String removed = systemMap.remove(patientID);
    if (removed == null) {
      System.out.println("A patient with the ID " + patientID + " could not be found.");
    } else {
      System.out.println("Patient " + removed + " has been discharged.");
    }
  }

  /**
   * Display Patient - allows the user to display all the information in a
   * patient's file from the system. Prompts the user for the patientID of the
   * patient to be displayed.
   * 
   * @param scnr - The Scanner object to be used. Passed by calling method.
   */
  public static void displayPatient(Scanner scnr) {
    System.out.println("----===Display Patient File===----");
    System.out.println("Please enter the ID of the patient you want to display.");

    boolean pass = false;
    int patientID = -1;
    while (!pass) {
      System.out.println("Patient ID: ");
      if (scnr.hasNextInt()) {
        patientID = scnr.nextInt();
        scnr.nextLine();
        if (patientID >= 100000000 && patientID < 1000000000) { // If the entered ID within a valid
                                                                // range
          pass = true;
          continue;
        }
      }
      scnr.nextLine();
      System.out.println("Please enter a valid patient ID");
    }

    PatientNode patient;
    // Checks if the entered patient is in the system
    if (systemMap.containsKey(patientID)) {
      patient = systemMap.getNode(patientID);
    } else {
      System.out.println("A patient with the ID" + patientID + " could not be found.");
      return;
    }

    // Displays patient INFO
    System.out.println("Displaying Patient File: " + patientID);
    System.out.println("-Name: " + patient.getValue());
    System.out.println("-Age: " + patient.getAge());
    System.out.println("-Location: " + patient.getLocation());
    System.out.println("-Floor: " + patient.getFloor());
    String cvd = "";
    if (patient.getCovid() == 1) { // Converts COVID bit to test result
      cvd = "Positive";
    } else {
      cvd = "Negative";
    }
    System.out.println("-Initial COVID Test Results: " + cvd);
    System.out.println("-Patient UID: " + patient.getuID());
  }

  /**
   * Display Info - allows the user to specific information in a patient's file
   * from the system. Prompts the user for the patientID of the patient to be
   * displayed.
   * 
   * @param scnr - The Scanner object to be used. Passed by calling method.
   */
  public static void getInfo(Scanner scnr) {
    System.out.println("----===Display Patient Information===----");
    System.out.println("Please enter the ID of the patient whose information you want to display.");

    boolean pass = false;
    int patientID = -1;
    while (!pass) {
      System.out.println("Patient ID: ");
      if (scnr.hasNextInt()) {
        patientID = scnr.nextInt();
        scnr.nextLine();
        if (patientID >= 100000000 && patientID < 1000000000) { // If the entered ID within a valid
                                                                // range
          pass = true;
          continue;
        }
      }
      scnr.nextLine();
      System.out.println("Please enter a valid patient ID");
    }

    PatientNode patient;
    // Checks if the entered patient is in the system
    if (systemMap.containsKey(patientID)) {
      patient = systemMap.getNode(patientID);
    } else {
      System.out.println("A patient with the ID" + patientID + " could not be found.");
      return;
    }

    // Prompts the user for a value to display and displays the selected field
    System.out.println("--=Patient: " + patientID + ", " + patient.getValue() + "=--");
    System.out.println("Please select information to display:\n" + "(A) - Location\n" + "(B) - Floor Number\n"
        + "(C) - Initial COVID Test Results\n" + "(D) - Age\n" + "(E) - Patient UID\n");
    pass = false;
    char selectTmp;
    while (!pass) {
      System.out.println("Please Select (Q to quit): ");
      selectTmp = readChar(scnr);
      switch (selectTmp) {
        case ('a'):
          System.out.println("Patient Location: " + patient.getLocation());
          continue;
        case ('b'):
          System.out.println("Patient Floor Number: " + patient.getFloor());
          continue;
        case ('c'):
          String cvd = "";
          if (patient.getCovid() == 1) {
            cvd = "Positive";
          } else {
            cvd = "Negative";
          }
          System.out.println("Patient Initial COVID Test Results: " + cvd);
          continue;
        case ('d'):
          System.out.println("Patient Age: " + patient.getAge() + " years old");
          continue;
        case ('e'):
          System.out.println("Patient UID: " + patient.getuID());
          continue;
        case ('q'):
          System.out.println("Quitting");
          pass = true;
          break;
        default:
          System.out.println("Please enter a valid field");
          continue;
      }
    }
  }

  /**
   * Display COVID Stats - allows the user to display the COVID data for the
   * patients within the system.
   * 
   */
  public static void cvdStats() {
    System.out.println("----===Display COVID Statistics===----");
    System.out.println("Total Patients: " + systemMap.size());
    System.out.println("Number of Positive Initial Test Results: " + systemMap.getCovidSum());
    DecimalFormat df = new DecimalFormat("##.##");
    System.out.println("Percent Infected in System: "
        + df.format((((double) systemMap.getCovidSum()) / systemMap.size()) * 100) + "%");
  }

  /**
   * List Patients - Lists all the patients and their Patient IDs currently within
   * the system.
   * 
   */
  public static void listPatients() {
    System.out.println("----===List Patients===----");
    systemMap.printTable();
  }

  /**
   * Help - Provides additional information about each of the available user
   * commands.
   * 
   */
  public static void help() {
    System.out.println("----===Help===----");
    System.out.println("(A) - Add Patient\n"
        + "-Allows you to add a new patient to the system.\nYou will be prompted for:\n-Patient Name\n"
        + "-Patient Age\n-Patient Treatment Location\n-Patient Floor\n" + "-Patient COVID Test Results\n"
        + "-Patient UID\n");
    System.out.println(
        "(R) - Discharge Patient\n" + "-Allows you to discharge patients from the system by their Patient ID.\n");
    System.out.println("(D) - Display Patient File\n"
        + "-Displays all the information stored in the system's patient file for a selected patient.\n");
    System.out.println("(G) - Get Patient Information\n"
        + "-Allows you to look up specific information from a selected patient's file.\n");
    System.out.println(
        "(C) - Display COVID Statistics\n" + "-Displays the COVID information for the patients within this system.\n"
            + "-Based on their initial COVID test results.\n");
    System.out
        .println("(L) - List All Patients\n" + "-Lists all patients currently within the system by Patient ID.\n");
    System.out.println("If you have forgotten the Patient ID for a patient, \n"
        + "you can use List to list all patients and their IDs by UID.");
  }

  /**
   * main - The central driver method for the Patient Management System
   * 
   * @param args
   */
  public static void main(String[] args) {
    systemMap = new HashTableMap(500);
    Scanner scnr = new Scanner(System.in);
    for (int i = 0; i < 80; i++) {
      System.out.print("*");
    }
    System.out.println("");
    System.out.println("Welcome to the CS400 patient management system.\nHow can I help you today?");
    int optMark = 1;
    int quit = 0;
    while (quit == 0) {
      for (int i = 0; i < 80; i++) {
        System.out.print("*");
      }
      System.out.println("");
      if (optMark == 1) {
        System.out.println("(A) - Add Patient\n" + "(R) - Discharge Patient\n" + "(D) - Display Patient File\n"
            + "(G) - Get Patient Information\n" + "(C) - Display COVID Statistics\n" + "(L) - List All Patients\n"
            + "(H) - Help(Provides more information about each command)\n" + "(E) - Exit\n");
        optMark = 0;
      }
      System.out.println("Please type your choice((?) for full menu): ");

      char temp = readChar(scnr);

      switch (temp) {
        case ('a'):
          addPatient(scnr);
          break;
        case ('r'):
          removePatient(scnr);
          break;
        case ('d'):
          displayPatient(scnr);
          break;
        case ('g'):
          getInfo(scnr);
          break;
        case ('c'):
          cvdStats();
          break;
        case ('l'):
          listPatients();
          break;
        case ('h'):
          help();
          break;
        case ('e'):
          System.out.println("Thank you! Bye!");
          System.exit(0);
          break;
        case ('?'):
          optMark = 1;
          break;
        default:
          System.out.println("Please enter a valid choice.");
          break;
      }
    }
  }
}
