package lk.sasax.GreenShadow.entity.impl;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.entity.SuperEntity;
import lk.sasax.GreenShadow.util.Enum.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "field")
@Entity
public class Field implements SuperEntity {
    @Id
    private String fCode;
    @Column(length = 100, nullable = false, unique = true)
    private String fieldName;
    //@Column(nullable = false)
    private Double fieldSize;
    //@Column(nullable = false)
    private Point fieldLocation;
    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String fieldImage2;
    //@Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;
    @OneToMany(mappedBy = "field")
    private List<Crop> crops;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "field_staff",
            joinColumns = @JoinColumn(name = "field_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private List<Staff> staffs;
    @OneToMany(mappedBy = "field")
    private List<Equipment> equipments;
    @ManyToMany(mappedBy = "fields")
    private List<MonitoringLog> monitoringLogs;
}
