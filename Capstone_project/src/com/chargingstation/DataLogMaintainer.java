import java.io.FileOutputStream;
import java.io.IOException;

public class DataLogMaintainer {

    private static final String COMPLETED_FILE_PATH = "C:\\Akhil\\Desktop\\CompletedVehicles.txt";
    private static final String QUEUE_FILE_PATH = "C:\\Akhil\\Desktop\\QueueVehicles.txt";

    public static void chargeFinishedData(String userId, String vehicleNumber, String chargingStation) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(COMPLETED_FILE_PATH, true)) {
            String data = "User ID: " + userId + System.lineSeparator() +
                          "Vehicle Number: " + vehicleNumber + System.lineSeparator() +
                          "Charging Station: " + chargingStation + System.lineSeparator() +
                          System.lineSeparator();

            fileOutputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void queueData(String userId, String vehicleNumber) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(QUEUE_FILE_PATH, true);

            String data = "User ID: " + userId + System.lineSeparator() +
                          "Vehicle Number: " + vehicleNumber + System.lineSeparator() +
                          System.lineSeparator();

            fileOutputStream.write(data.getBytes());
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