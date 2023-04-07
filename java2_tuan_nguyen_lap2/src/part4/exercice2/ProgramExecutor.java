package part4.exercice2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProgramExecutor {
        private static final String PROGRAMS_FILE = "programs.txt";

        public static void main(String[] args) {
                Map<String, String> programs = loadPrograms(PROGRAMS_FILE);

                Scanner scanner = new Scanner(System.in);
                int choice = 0;
                do {
                        System.out.println("\nRUN MY FAVORITE PROGRAMS\n");
                        for (Map.Entry<String, String> entry : programs.entrySet()) {
                                System.out.printf("%d. %s\n", entry.getKey(), entry.getValue());
                        }
                        System.out.printf("%d. Exit\n", programs.size() + 1);
                        System.out.print("\nYour choice: ");
                        choice = scanner.nextInt();
                        scanner.nextLine(); // consume newline character

                        if (choice < 1 || choice > programs.size() + 1) {
                                System.out.println("Invalid choice! Please enter a number between 1 and " + (programs.size() + 1) + ".");
                        } else if (choice != programs.size() + 1) {
                                String programName = null;
                                String programPath = null;
                                for (Map.Entry<String, String> entry : programs.entrySet()) {
                                        if (Integer.parseInt(entry.getKey()) == choice) {
                                                programName = entry.getValue();
                                                programPath = entry.getKey();
                                                break;
                                        }
                                }
                                if (programName != null && programPath != null) {
                                        try {
                                                System.out.printf("\nRunning %s...\n", programName);
                                                Runtime.getRuntime().exec(programPath);
                                        } catch (IOException e) {
                                                System.out.println("Error: " + e.getMessage());
                                        }
                                }
                        }
                } while (choice != programs.size() + 1);
        }
        private static Map<String, String> loadPrograms(String fileName) {
                Map<String, String> programs = new HashMap<>();
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                                if (!line.trim().equals("")) {
                                        String[] tokens = line.trim().split(",");
                                        if (tokens.length == 2) {
                                                String programName = tokens[0].trim();
                                                String programPath = tokens[1].trim();
                                                programs.put(String.valueOf(programs.size() + 1), programName);
                                                programs.put(programName, programPath);
                                        }
                                }
                        }
                } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                }
                return programs;
        }
}
