import java.io.FileOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataLogMaintainer {

    private static final String COMPLETED_FILE_PATH = "C:\\Akhil\\Desktop\\CompletedVehicles.txt";
    private static final String QUEUE_FILE_PATH = "C:\\Akhil\\Desktop\\QueueVehicles.txt";

    public static void chargeFinishedData(String userId, String vehicleNumber, String chargingStation) {
        try (FileWriter fileWriter = new FileWriter(COMPLETED_FILE_PATH, true)) {
            fileWriter.write("User ID: " + userId + System.lineSeparator());
            fileWriter.write("Vehicle Number: " + vehicleNumber + System.lineSeparator());
            fileWriter.write("Charging Station: " + chargingStation + System.lineSeparator());
            fileWriter.write(System.lineSeparator());
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void queueData(String userId, String vehicleNumber) {
        FileOutputStream fileOutputStream = null;
        try {BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(QUEUE_FILE_PATH, true))) {
            String data = "User ID: " + userId + System.lineSeparator() +
                          "Vehicle Number: " + vehicleNumber + System.lineSeparator() +
                          System.lineSeparator();

            bufferedWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
