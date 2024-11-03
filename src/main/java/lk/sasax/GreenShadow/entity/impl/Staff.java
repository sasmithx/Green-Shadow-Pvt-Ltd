package lk.sasax.GreenShadow.entity.impl;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.entity.SuperEntity;
import lk.sasax.GreenShadow.util.Enum.Gender;
import lk.sasax.GreenShadow.util.Enum.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class Staff implements SuperEntity {
    @Id
    @Column(name = "staff_member_id", unique = true)
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "designation")
    private String designation;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "joined_date")
    private Date joinDate;
    @Column(name = "date_of_birth")
    private Date dob;
    @Column(name = "address_line_1")
    private String addressLine1;
    @Column(name = "address_line_2")
    private String addressLine2;
    @Column(name = "address_line_3")
    private String addressLine3;
    @Column(name = "address_line_4")
    private String addressLine4;
    @Column(name = "address_line_5")
    private String addressLine5;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private StaffRole role;
    @ManyToMany(mappedBy = "staff")
    //    @Column(name = "field")
    private List<Field> field;
    @OneToMany(mappedBy = "allocatedStaffMemberDetails")
//    @Column(name = "vehicle")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "assignedStaffDetails")
    private List<Equipment> equipment;

    @ManyToOne
    @JoinColumn(name = "log_code")
    private CropDetails cropDetails;
}
