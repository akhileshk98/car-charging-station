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

    public void NewUserRegistration(ArrayList<UserName> List) {
        Scanner in = new Scanner(System.in);

        // Ask if the user is a admin or a new user
        System.out.println("Are you a admin or newuser?" );
        System.out.println("Enter 'yes' or 'no'");
        String reservedUserInput = in.nextLine().toLowerCase();

        if (reservedUserInput.equals("yes")) {
            // If reserved user, directly enter the admin username
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
            // If new user, choose a unique username
            do {
                System.out.println("Please choose a unique user name between 5 to 10 characters:");
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
