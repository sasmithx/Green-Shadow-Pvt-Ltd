package lk.sasax.GreenShadow.entity.impl;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.entity.SuperEntity;
import lk.sasax.GreenShadow.util.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vehicle")
@Entity
public class Vehicle implements SuperEntity {
    @Id
    private String vehicleCode;
    //@Column(nullable = false)
    private String licensePlateNo;
    //@Column(nullable = false)
    private String category;
    //@Column(nullable = false)
    private String fuelType;
    //@Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    //@Column(nullable = false)
    private String remark;
    @ManyToOne
    @JoinColumn(name = "assigned_staff_id")
    private Staff staff;
}
