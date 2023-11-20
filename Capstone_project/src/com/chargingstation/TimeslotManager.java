package com.chargingstation;
import static java.nio.file.StandardOpenOption.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Scanner;

public class TimeslotManager {
	boolean userexists=false;
	Path filepath;
	TimeslotManager()
	{
		
	}
	TimeslotManager(Path filepath)
	{
		this.filepath = filepath;
		try {
		Files.createFile(this.filepath);
	    UserPrincipal owner1 = (this.filepath).getFileSystem().getUserPrincipalLookupService()
	            .lookupPrincipalByName("NINJA520\\91968");
	    Files.setOwner(this.filepath, owner1);
		}
		catch(IOException e)
		{
			
		}
	}
    public void mode(UserName[]Userlist, ChargingStation[] cs)
    {
        boolean no_match = true;
        UserName u = null;
    	System.out.println("Would you like to open in user mode or admin mode");
    	Scanner in = new Scanner(System.in);
    	switch(in.nextLine())
    	{
    	case "user mode":
    		System.out.println("Please enter the user name");
    		String username = in.nextLine();
        	for(int i =0;i<Userlist.length;i++)
        	{
        		if(username.equals(Userlist[i].userName))
        		{
        		   u = Userlist[i];
        		   no_match = false;
        		   break;
        		}          		
        	}
        	if(no_match == true)
        	{
        		System.out.println("Invalid user.Please register with us");
        	}
        	else
        	{
            	System.out.println("Please enter the charging station number from 1 to 5");
            	int number = Integer.parseInt(in.nextLine());
            	try {
            		   bookslot(u, cs[number]);
            	}
            	catch(IOException e)
            	{
            		
            	}
        	}
        	break;
    	case "admin mode":
    		System.out.println("Please enter admin username");
    		String adminname = in.nextLine();
    		System.out.println("Which file would you like to work with?");
    		String filename = in.nextLine();
    		
    		try {
    		adminmode(adminname,filename);
    		}
    		catch(IOException e)
    		{
    			
    		}
    		
    		
    		
    	}
    }
	public void bookslot(UserName UserName, ChargingStation cs) throws IOException
	{

			String filepath = "Timeslot"+cs.id+".txt";
			Path p = Paths.get(filepath);
			File f = new File(filepath);
			try (BufferedWriter filewrite = Files.newBufferedWriter(p,APPEND);BufferedReader fileread = Files.newBufferedReader(p))
			{

				String line;
				System.out.println("Please enter the time slot of one hour and date");
				Scanner in = new Scanner(System.in);
				String time = in.nextLine();
				int priority = 1;
				f.setWritable(true);
				while((line = fileread.readLine())!=null)
				{
					if(line.contains(time))
					{
						priority++;
					}
				}
				filewrite.newLine();
				filewrite.write(UserName.userName+" of car id "+String.valueOf(UserName.c.getid())+" booked ");
				filewrite.write(time,0,time.length());
				filewrite.write(",");
				filewrite.write("priority is " + String.valueOf(priority));
				f.setWritable(true, true);
			}catch(IOException e)
			{
				System.err.println(e);
			}
	}
	public void adminmode(String s1, String s2) throws IOException
	{
		String filepath = s2;
		Path p = Paths.get(filepath);
		FileOwnerAttributeView foav = Files.getFileAttributeView(p,FileOwnerAttributeView.class);
        UserPrincipal owner = foav.getOwner();

	    if(owner.getName().equals(s1))
	    {
	       System.out.println("What operation would you like to perform?");
	       System.out.println("1.delete file \n2.empty contents");
	       Scanner in_admin = new Scanner(System.in);
	       String operation = in_admin.nextLine();
	       switch(operation) {
	        case "delete file":
	            Files.deleteIfExists(p);
	            break;
	        case "empty contents":
	            Files.newBufferedWriter(p, TRUNCATE_EXISTING);
	        	break;
	            default:
	        	System.out.println("Unrecognized operation");
	        	}
	    }
	    else
	    {
	    	System.out.println("Unauthorized access");
	    }
		
	}

}
