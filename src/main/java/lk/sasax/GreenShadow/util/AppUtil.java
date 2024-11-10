package lk.sasax.GreenShadow.util;

import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Base64;
import java.util.UUID;

public class AppUtil {

    private static int userCounter = 1;
    private static int vehicleCounter = 1;
    private static int equipmentCounter = 1;
    private static int cropCounter = 1;
    private static int staffCounter= 1;

    public static String createUserId() {
        String id = String.format("U%03d", userCounter); // Generates "U001", "U002", etc.
        userCounter++;
        return id;
    }

    public static String createVehicleId() {
        String id = String.format("V%03d", vehicleCounter); // Generates "V001", "V002", etc.
        vehicleCounter++;
        return id;
    }

    public static String createStaffId() {
        String id = String.format("ST%03d", staffCounter); // Generates "ST001", "ST002", etc.
        staffCounter++;
        return id;
    }

    public static String createEquipmentId() {
        String id = String.format("EQ%03d", equipmentCounter); // Generates "EQ001", "EQ002", etc.
        equipmentCounter++;
        return id;
    }

    public static String createCropId() {
        String id = String.format("CR%03d", cropCounter); // Generates "CR001", "CR002", etc.
        cropCounter++;
        return id;
    }

    public static String createFieldId() {
        String id = String.format("F%03d", cropCounter); // Generates "F001", "F002", etc.
        cropCounter++;
        return id;
    }

    //Convert to Base64
    public static String toBase64(MultipartFile file) {
        try {
            byte[] fileByteCollection = file.getBytes();
            return Base64.getEncoder().encodeToString(fileByteCollection);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Point convertToPoint(String fieldLocation) {
        try {
            String[] parts = fieldLocation.split(",");
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            return new Point(x, y);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid fieldLocation format. Expected format: 'x,y'");
        }
    }


    //Generate ID
    /*public static String createUserId(){return "USER-"+UUID.randomUUID();}
    public static String createVehicleId(){return "VEHICLE-"+ UUID.randomUUID();}
    public static String createEquipmentId(){return "EQUIPMENT-"+ UUID.randomUUID();}
    public static String createCropId(){return "CROP-"+ UUID.randomUUID();}*/
}
