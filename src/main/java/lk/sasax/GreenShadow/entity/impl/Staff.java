package lk.sasax.GreenShadow.entity.impl;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.entity.SuperEntity;
import lk.sasax.GreenShadow.util.Enum.Designation;
import lk.sasax.GreenShadow.util.Enum.Gender;
import lk.sasax.GreenShadow.util.Enum.StaffStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "staff")
@Entity
public class Staff implements SuperEntity {
    @Id
    private String id;
    //@Column(length = 150, nullable = false)
    private String name;
    //@Column(nullable = false)
    private LocalDate dob;
    //@Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    //@Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private Designation designation;
    //@Column(length = 20, nullable = false)
    private String role;
    //@Column(nullable = false, unique = true)
    private String email;
    //@Column(length = 15, nullable = false)
    private String mobile;
    //@Column(nullable = false)
    private String address;
    //@Column(length = 10, nullable = false)
    private String postalCode;
    @CreationTimestamp
    private Timestamp joinedDate;
    //@Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private StaffStatus status;
    @ManyToMany(mappedBy = "staffs", fetch = FetchType.LAZY)
    private List<Field> fields;
    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY)
    private List<Vehicle> vehicles;
    @ManyToMany(mappedBy = "staffs", fetch = FetchType.LAZY)
    private List<MonitoringLog> monitoringLogs;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_equipment_id", referencedColumnName = "equipmentId")
    private Equipment equipment;
}
