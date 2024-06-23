package com.normaliser;

import com.classes.Job;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;

public class UI {

    public void run() throws Exception {

        JsonReader jsonReader = new JsonReader("memory.json");
        Manager manager = Manager.getInstance(jsonReader, new Normaliser());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;

        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Normalize Job Title");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter job title: ");
                    String jobTitle = reader.readLine();
                    Set<Job> normalizedJobTitle = manager.normalise(jobTitle);

                    Job bestAnswer = null;
                    ArrayList<Job> jobWithBiggestScores = new ArrayList<>(normalizedJobTitle);
                    if(jobWithBiggestScores.size() > 1) {
                        while (bestAnswer == null) {
                            System.out.println(String.format("We found %d possibilities that matches %s. Chose the most correct one:", jobWithBiggestScores.size(), jobTitle));
                            for (int i = 0; i < jobWithBiggestScores.size(); i++) {
                                System.out.println(String.format("%d. %s",(i + 1), jobWithBiggestScores.get(i).getJobTitle()));
                            }
                            int input;
                            try{
                                input = Integer.parseInt(reader.readLine()) - 1;
                                bestAnswer = jobWithBiggestScores.get(input);
                            } catch (Exception e) {
                                System.out.println("Invalid option. Please choose again.");
                            }
                        }

                    } else {
                        bestAnswer = jobWithBiggestScores.get(0);
                    }

                    manager.saveBestAnswerInMemory(bestAnswer);
                    System.out.println("Normalized Job Title: " + bestAnswer.getJobTitle());
                    System.out.println("##########################################################################################");

                    break;
                case "2":
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
    }

}
