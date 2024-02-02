package com.chargingstation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;



class Logging {

    public static void logTheEvents(int id, String message) {
    	// Here we are logging the events that are happening in the charging station
    	String datestamp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    	String fileName = "Charging_Station_" + id + "_Log_" + datestamp + ".txt";
    	LogWriter(message, fileName);
           
    } 
    
    public static void logTheEnergyManagementEvents(String message) {
    	// This section of the code is logging the events that are happening in the Energy Management
    	String datestamp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    	String fileName = "EnergyManagement_Log_" + datestamp +".txt";
    	LogWriter(message, fileName);		
        
    }
    
    public static void LogWriter(String message, String fileName)
	{
		// This section is used to write the log messages to the corresponding files
    	String timing = new SimpleDateFormat("HH:mm:ss").format(new Date());

        try {
            File logsDirectory = new File("logs");
            if (!logsDirectory.exists()) {
                logsDirectory.mkdirs(); 
            }
            File outputFile = new File(logsDirectory, fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
                
                writer.write( message + " at " + timing + "\n");
            }
            
            // this section creates a new file if the existing file is greater than 10MB
            if (outputFile.length() > 19485760) { // 10MB = 10485760 bytes
            	// the previously existing file will be stored under new name "old_" + previousfilename
            	File newLogFile = new File(logsDirectory, "old_" + fileName);
            	outputFile.renameTo(newLogFile);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }


	}  
    // this method will delete the log files which are older than 30 days
    public static void DeleteOldLogFiles() throws IOException  {
        String logFileName = "deleted_log_files.txt";
    	File logsDirectory = new File("logs");
		if (!logsDirectory.exists()) {
		    logsDirectory.mkdirs(); 
		}
		File outputFile = new File(logsDirectory, logFileName);
		String directoryPath = outputFile.getParent();
        int daysThreshold = 30;

            try {
                BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFileName, true));

                Files.walkFileTree(Paths.get(directoryPath), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (ChronoUnit.DAYS.between(attrs.creationTime().toInstant(), Instant.now()) > daysThreshold) {
                            String deletedFileName = file.getFileName().toString();

                            // Append the deleted file name to the log file
                            logWriter.write("Deleted file: " + deletedFileName + "\n");

                            // Delete the file
                            Files.delete(file);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });

                logWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    	// This function is called for fetching the log files 
    	public static void FetchLogFiles() {
            Scanner scanner = new Scanner(System.in);
            String name1=null;
            String name2=null;
            String regexPattern=null;
            int k=0;     
            System.out.println("Enter which option number would you like access to, 1 or 2?");
            System.out.println("1.Charging Station \n2.Energy Management");
            try {
            int i = Integer.parseInt(scanner.nextLine());
            if(i==1)
            {
            	name1 = "Charging_Station";
            	System.out.println("Which charging station you like access to?");
            	k = Integer.parseInt(scanner.nextLine());
            	System.out.println("To which date would you like access to in dd-mm-yyyy");
            	name2= scanner.nextLine();
                if(ValidateDate(name2)==true)
                {
                	regexPattern = name1+"_"+k+"_Log_"+name2+".txt";
                }
                else
                {
                	regexPattern = null;
                }
            }
            else if(i==2)
            {
            	name1 = "EnergyManagement";
            	System.out.println("To which date would you like access to in dd-mm-yyyy");
            	name2= scanner.nextLine();
                if(ValidateDate(name2)==true)
                {
                	regexPattern = name1+"_Log_"+name2+".txt";
                }
                else
                {
                	regexPattern = null;
                }
            }
            else
            {
            	System.out.println("Invalid option");
            	regexPattern = null;
            }
            if(regexPattern!= null)
            {
            Pattern pattern = Pattern.compile(regexPattern);
            //String fileName = scanner.nextLine();
            String directoryPath = "logs";
            File directory = new File(directoryPath);
            if (directory.exists() && directory.isDirectory()) {
                // List all files in the directory
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        // Get the file name
                        String retrievefileName = file.getName();
                        // Create a Matcher object to match the pattern in the file name
                        Matcher matcher = pattern.matcher(retrievefileName);
                        // Check if the pattern is found in the file name
                        if(matcher.find()==true) {
                            // Do something with the matching file
                        	String fileContent = readLogFile("logs\\"+retrievefileName);
                            System.out.println("Log file contents:\n" + fileContent);
                            break;
                            
                        }
                        
                    }
                }
            }
            }
            }catch(IOException e)
            {
            	
            }
            finally {
            	scanner.close();
            }
    	}

    	// This segment will read the log files
        private static String readLogFile(String filePath) throws IOException {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            return content.toString();
        }
        
        // This part validates the date format entered
        static boolean ValidateDate(String date)
        {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");

        	try
        	{
        		formatter.parse(date);
        		return true;
        	}
        	catch(Exception e)
        	{
        		System.out.println("Invalid Date/Date format entered");
        	}
            return false;
        }

}
