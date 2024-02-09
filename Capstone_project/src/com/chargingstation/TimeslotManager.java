package com.chargingstation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.time.LocalDate;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
 class TimeSlotManager{
	boolean dateValid;
	Path filepath;
	ChargingStation [] cs ;

 TimeSlotManager()
	{
        File logsDirectory = new File("TimeslotManager");
        if (!logsDirectory.exists()) {
            logsDirectory.mkdirs(); 
        }
		this.filepath = Paths.get("TimeslotManager\\Timeslot.txt");
		try {
		Files.createFile(this.filepath);
		}
		catch(IOException e)
		{
			
		}
		
	}
 // this prompt asks the user to enter the timeslot manager
 // where he or she can book the slot or enter into the admin mode
    public boolean timeslot(ChargingStation[] cs, ArrayList<UserName> List, ArrayList<Car> Priority_List) {
        this.cs = cs;
        System.out.println("Would you like go through our service");
        System.out.println("Please enter 'yes' or 'no'");
        try (Scanner in = new Scanner(System.in)) {
			int attempts = 0; // Counter for number of attempts

			while (attempts < 3) { // Repeat the loop for three attempts
			    String userInput = in.nextLine().toLowerCase(); 

			    if (userInput.equals("yes")) {
			        CheckValidUser(List, Priority_List);
			        return true; 
			    } else if (userInput.equals("no")) {
			        //System.out.println("Charging station can be used only with prebooked slots");
			        return true; 
			    } else {
			        System.out.println("Invalid input. Please enter 'yes' or 'no'");
			        attempts++; 
			    }
			}
		}
        // Print a message if maximum attempts are reached
        System.out.println("Maximum attempts reached. Exiting Timeslot Manager.");
        return false; // Return false if maximum attempts are reached without valid input
    }

// this section will validate whether the entered user credentials are correct or not
private void CheckValidUser(ArrayList<UserName>List,ArrayList<Car>Priority_List)
{
	System.out.println("Are you an existing user?");
	Scanner in = new Scanner(System.in);
	boolean user_identified = false;
	String response = in.nextLine().toLowerCase();
		if(response.equals("yes"))
		{
			List<String> userList = Arrays.asList("akhilesh", "akhil", "krithika", "vipink");
			System.out.println("Please enter the username");
			String userName = in.nextLine();

				if(userList.contains(userName)) // here we are checking whether the user is admin or not
				{
					System.out.println("Please enter the password");
					for(UserName obj : List)
					{
						if(obj.userName.equals(userName))
						{
							if(obj.password.equals(in.nextLine()))
							{
								System.out.println("Hello Admin(ID - " + obj.userName + ")");
								user_identified = true;
								try {
									adminmode(obj.c,Priority_List); // admin will be entering into the admin mode
								}catch(Exception e)
								{
									
								}						
							}
							else
							{
								System.out.println("Incorrect password");
							}
						}
					}
				}
				else
				{
					for(UserName obj: List)
					{
						if(obj.userName.equals(userName))
						{
							System.out.println("Please enter the password");
							if(obj.password.equals(in.nextLine()))
							{   
								user_identified = true;
								System.out.println("Hello "+obj.userName);
								Usermode(obj.c,Priority_List); // this is the user mode
								
							}
							else
							{
								System.out.println("Incorrect password");
							}
						}
					}
				}
				if(!user_identified)
				{
				  System.out.println("No such user, please register with us");	
				}
		}
		else if(response.equals("no")) {
			System.out.println("Please register with us to use our service");
		}
		
}

// this function describes the functionalities present in the user mode
private void Usermode(Car c,ArrayList<Car> List)
{
	Scanner in = new Scanner(System.in);
	System.out.println("Please enter the date you are looking to book a slot in dd/mm/yyyy");
	String date = in.nextLine();
	if(ValidateDate(date)==true)
	{
	  	if(isDateAfterCurrentDate(date)) {
		BookSlot(c,date,"TimeslotManager\\Timeslot.txt");
	  	List.add(c);
	  	}
		
	}
	
}
//User can pre-book the time slot for the charging
public void BookSlot(Car c, String date, String filepath)
{
	Path p = Paths.get(filepath);
	File f = new File(filepath);
	f.setWritable(true);
	try (BufferedWriter filewrite = Files.newBufferedWriter(p,APPEND);BufferedReader fileread = Files.newBufferedReader(p))
	{

		System.out.println("Please choose one of the timeslot");
		System.out.println("Enter 1 for 10:00 to 11:00");
        System.out.println("Enter 2 for 15:00 to 16:00");
        System.out.println("Enter 3 for 12:00 to 13:00");
		Scanner in = new Scanner(System.in);
		String time = in.nextLine();
		filewrite.newLine();
		filewrite.write("Car of id"+c.id+" booked ");
		c.slot_booked = true;
		System.out.println("Your slot has been booked Car ID "+c.id);
		filewrite.write(time,0,time.length());
		f.setWritable(false);
	}catch(IOException e)
	{
		System.err.println(e);
	}
	
	
}
//this function describes the functionalities present in the admin mode
private void adminmode(Car c,ArrayList<Car> List) throws IOException
{
	System.out.println("Would you like to book a slot");
	System.out.println("Please enter 'yes' or 'no' ");
	try (Scanner in = new Scanner(System.in)) {
		int attempts = 0; // Counter for number of attempts
		while (attempts < 3) { // Repeat the loop for three attempts
		    String userInput = in.nextLine().toLowerCase(); 

		    if (userInput.equals("yes")) {
		    	Usermode(c,List);
		    	logs();
		        break; 
		    } else if (userInput.equals("no")) {
		        logs();
		        break; 
		    } else {
		        System.out.println("Invalid input. Please enter 'yes' or 'no'");
		        attempts++; 
		    }
		}
	}
	

}



public void logs() {
    System.out.println("Would you like to open the Log files");
    System.out.println("Please enter 'yes' or 'no'");
    try (Scanner in = new Scanner(System.in)) {
		int attempts = 0; // Counter for number of attempts

		while (attempts < 3) { // Repeat the loop for three attempts
		    String userInput = in.nextLine().toLowerCase(); 

		    if (userInput.equals("yes")) {
		    	Logging.FetchLogFiles();
		        break; 
		    } else if (userInput.equals("no")) {
		        System.out.println("OK closing the Admin mode");
		        break; 
		    } else {
		        System.out.println("Invalid input. Please enter 'yes' or 'no'");
		        attempts++; 
		    }
		}
	}
    // Print a message if maximum attempts are reached
    System.out.println("Maximum attempts reached. Exiting User Interface."); 
}







// this function is implemented to validate the user entered date is in the correct format or not
public static boolean ValidateDate(String date)
{
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

	try
	{
		formatter.parse(date);
		return true;
	}
	catch(Exception e)
	{
		System.out.println("Invalid Date entered");
		return false;
	}
    
}

public static boolean isDateAfterCurrentDate(String date) {
    // Parse the input date string
    LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    
    // Get the current date
    LocalDate currentDate = LocalDate.now();
    if(inputDate.isAfter(currentDate)) {
    	return true;
    }
    System.out.println("As the designated date has elapsed, you are no longer able to secure a time slot for it");
    return false;
}

}
