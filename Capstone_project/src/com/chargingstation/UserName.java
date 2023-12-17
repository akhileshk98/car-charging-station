package com.chargingstation;
import java.util.ArrayList;
import java.util.Scanner;

public class UserName{
	String userName;
	Car c;
	String password;
	static int count=0;

	
	UserName(String userName, Car c)
	{
		this.userName = userName;
		this.c = c;
		
	}
    UserName()
    {
    	
    }
	public void NewUserRegistration(ArrayList<UserName> List)
	{
       
		System.out.println("Please enter the user name with 5 characters");
		Scanner in = new Scanner(System.in);
		this.userName = in.nextLine();
		System.out.println("Please enter the password");
		this.password = in.nextLine();
		this.c = new Car(count++);
		List.add(this);

		
	}
}
