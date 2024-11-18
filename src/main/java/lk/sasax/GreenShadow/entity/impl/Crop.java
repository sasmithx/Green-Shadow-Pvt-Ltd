package lk.sasax.GreenShadow.entity.impl;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.entity.SuperEntity;
import lk.sasax.GreenShadow.util.Enum.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "crop")
@Entity
public class Crop implements SuperEntity {
    @Id
    private String cropCode;
    @Column(nullable = false)
    private String commonName;
    @Column(nullable = false, unique = true)
    private String scientificName;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String season;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;
    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String cropImage;
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
    @ManyToMany(mappedBy = "crops")
    private List<MonitoringLog> monitoringLogs;
}
