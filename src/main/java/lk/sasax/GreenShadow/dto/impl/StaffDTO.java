package lk.sasax.GreenShadow.dto.impl;

import lk.sasax.GreenShadow.customObj.StaffResponse;
import lk.sasax.GreenShadow.customObj.VehicleResponse;
import lk.sasax.GreenShadow.dto.SuperDTO;
import lk.sasax.GreenShadow.entity.impl.Field;
import lk.sasax.GreenShadow.entity.impl.Vehicle;
import lk.sasax.GreenShadow.util.Enum.Designation;
import lk.sasax.GreenShadow.util.Enum.Gender;
import lk.sasax.GreenShadow.util.Enum.StaffStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {
    private String id;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private Designation designation;
    private String role;
    private String email;
    private String mobile;
    private String address;
    private String postalCode;
    private Timestamp joinedDate;
    private StaffStatus status;
    private List<Field> fields;
    private List<Vehicle> vehicles;
}
