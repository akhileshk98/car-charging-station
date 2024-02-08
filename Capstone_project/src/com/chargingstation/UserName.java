package com.chargingstation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserName {
    String userName;
    Car c;
    String password;
    static int count = 0;

    // Map to store reserved usernames and passwords
    static Map<String, String> reservedUsernames = new HashMap<>();

    static {
        reservedUsernames.put("akhilesh", "akhileshpass");
        reservedUsernames.put("akhil", "akhilpass");
        reservedUsernames.put("krithika", "krithikapass");
        reservedUsernames.put("vipink", "vipinkpass");
    }

    UserName(String userName, Car c) {
        this.userName = userName;
        this.c = c;
    }

    UserName() {
    }

    public boolean NewUserRegistration(ArrayList<UserName> List) {
        Scanner in = new Scanner(System.in);
        int attempts = 0; // Counter to track incorrect attempts

        while (attempts < 3) { // Allow maximum 3 attempts
            // Ask if the user is an admin or a new user
            System.out.println("Are you an admin or a new user?");
            System.out.println("Enter 'yes' or 'no' (if new user press no)");
            String reservedUserInput = in.nextLine().toLowerCase();

            if (reservedUserInput.equals("yes") || reservedUserInput.equals("no")) {
                if (reservedUserInput.equals("yes")) {
                    // If a reserved user, directly enter the admin username
                    do {
                        System.out.println("Enter your admin username:");
                        this.userName = in.nextLine();

                        if (!reservedUsernames.containsKey(this.userName)) {
                            System.out.println("Invalid admin username. Please try again.");
                        }
                    } while (!reservedUsernames.containsKey(this.userName));

                    // Set the password for the chosen reserved username
                    System.out.println("Enter the password for the admin username:");
                    this.password = in.nextLine();
                } else {
                    // If a new user, choose a unique username
                    do {
                        System.out.println("Please choose a unique username between 5 to 10 characters:");
                        this.userName = in.nextLine();

                        if (this.userName.length() < 5 || this.userName.length() > 10) {
                            System.out.println("Username must have between 5 to 10 characters. Please try again.");
                        } else if (reservedUsernames.containsKey(this.userName)) {
                            System.out.println("Username is reserved. Please choose a different one.");
                        } else if (isUserNameExists(List, this.userName)) {
                            System.out.println("Username already exists. Please choose a different one.");
                        }
                    } while (this.userName.length() < 5 || this.userName.length() > 10 || reservedUsernames.containsKey(this.userName) || isUserNameExists(List, this.userName));

                    // Ensure the password has between 5 and 15 characters
                    do {
                        System.out.println("Please enter the password between 5 to 15 characters:");
                        this.password = in.nextLine();

                        if (this.password.length() < 5 || this.password.length() > 15) {
                            System.out.println("Password must have between 5 to 15 characters. Please try again.");
                        }
                    } while (this.password.length() < 5 || this.password.length() > 15);
                }

                // Create a new Car object and add the user to the list
                this.c = new Car(count++);
                List.add(this);
                return true; // Return true on successful registration
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                attempts++; // Increment the attempts counter
            }
        }

        System.out.println("Maximum attempts reached. Registration aborted.");
        return false; // Return false if maximum attempts are reached
    }

    private boolean isUserNameExists(ArrayList<UserName> userList, String userName) {
        for (UserName user : userList) {
            if (user.userName.equals(userName)) {
                return true; // Username already exists
            }
        }
        return false; // Username is unique
    }
}
