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
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class TimeSlotManager{
	boolean dateValid;
	Path filepath;
	ChargingStation [] cs ;
//
//	TimeSlotManager(Path filepath)
//	{
//		this.filepath = filepath;
//		try {
//		Files.createFile(this.filepath);
//		}
//		catch(IOException e)
//		{
//			
//		}
//	}
public TimeSlotManager()
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
public void timeslot(ChargingStation [] cs,ArrayList<UserName>List,ArrayList<Car>Priority_List)
{
	this.cs = cs;
	System.out.println("Would you like to open the Timeslot Manager");
	Scanner in = new Scanner(System.in);
	if((in.nextLine()).equals("yes"))
	{
		CheckValidUser(List,Priority_List);
	}
	else
	{
		System.out.println("Charging station can be used only with prebooked slots");
	}
}
private void CheckValidUser(ArrayList<UserName>List,ArrayList<Car>Priority_List)
{
	System.out.println("Are you an existing user?");
	Scanner in = new Scanner(System.in);
	boolean user_identified = false;
	if((in.nextLine()).equals("yes"))
	{
		System.out.println("Please enter the username");
		String username = in.nextLine();

			if(username.equals("Krithika"))
			{
				System.out.println("Please enter the password");
				for(UserName obj : List)
				{
					if(obj.userName.equals(username))
					{
						if(obj.password.equals(in.nextLine()))
						{
							System.out.println("Hello Admin");
							user_identified = true;
							try {
								adminmode();
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
					if(obj.userName.equals(username))
					{
						System.out.println("Please enter the password");
						if(obj.password.equals(in.nextLine()))
						{   
							user_identified = true;
							System.out.println("Hello"+obj.userName);
							Usermode(obj.c,Priority_List);
							
						}
						else
						{
							System.out.println("Incorrect password");
						}
					}
				}
			}
			if(user_identified== false)
			{
			  System.out.println("No such user, please register with us");	
			}
}
}

private void Usermode(Car c,ArrayList<Car> List)
{
	Scanner in = new Scanner(System.in);
	System.out.println("Please enter the date you are looking to book a slot in dd/mm/yyyy");
	String date = in.nextLine();
	if(ValidateDate(date)==true)
	{
	  	BookSlot(c,date,"TimeslotManager\\Timeslot.txt");
	  	List.add(c);
		
	}
	
}
void BookSlot(Car c, String date, String filepath)
{
	Path p = Paths.get(filepath);
	File f = new File(filepath);
	f.setWritable(true);
	try (BufferedWriter filewrite = Files.newBufferedWriter(p,APPEND);BufferedReader fileread = Files.newBufferedReader(p))
	{

		String line;
		System.out.println("Please choose one of the timeslot");
		System.out.println("1. 12:00 to 13:00 %n2.15:00 to 16:00 %n3. 10:00 to 11:00");
		Scanner in = new Scanner(System.in);
		String time = in.nextLine();
//		int priority = 1;
		//f.setWritable(true);
//		while((line = fileread.readLine())!=null)
//		{
//			if(line.contains(time))
//			{
//				priority++;
//			}
//		}
		filewrite.newLine();
		filewrite.write("Car of id"+c.id+" booked ");
		c.slot_booked = true;
		System.out.println("Your slot has been booked Car ID "+c.id);
		filewrite.write(time,0,time.length());
//		filewrite.write(",");
//		filewrite.write("priority is " + String.valueOf(priority));
		f.setWritable(false);
	}catch(IOException e)
	{
		System.err.println(e);
	}
	
	
}
private void adminmode() throws IOException
{
	System.out.println("Which charging station file would you like to work with?");
	for(int i=0;i<cs.length;i++)
	{
		System.out.println("Timeslot"+i+".txt");
	}
	Scanner in = new Scanner(System.in);
	String filepath = in.nextLine();
	Path p = Paths.get(filepath);
	File f = new File(filepath);
	f.setWritable(true);
    System.out.println("What operation would you like to perform?");
    System.out.println("1.delete file %n2.empty contents");
    switch(in.nextLine()) {
    case "delete file":
        Files.deleteIfExists(p);
        break;
    case "empty contents":
        Files.newBufferedWriter(p, TRUNCATE_EXISTING);
    	break;
        default:
    	System.out.println("Unrecognized operation");
    	}
    if(f!=null)
    {
    	f.setWritable(false);
    }
}
static boolean ValidateDate(String date)
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
	}
    return false;
}

}