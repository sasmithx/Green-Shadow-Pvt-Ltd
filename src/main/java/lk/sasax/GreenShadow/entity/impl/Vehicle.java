package lk.sasax.GreenShadow.entity.impl;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicles")
public class Vehicle implements SuperEntity {
    @Id
    @Column(name = "vehicle_code", unique = true)
    private String vehicleCode;
    @Column(name = "licence_plate_number")
    private String licensePlateNumber;
    @Column(name = "vehicle_category")
    private String vehicleCategory;
    @Column(name = "fuel_type")
    private String fuelType;
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "staff_member_id")
    //@Column(name = "allocated_staff_members")
    private Staff allocatedStaffMemberDetails;
    @Column(name = "remarks")
    private String remarks;
}
