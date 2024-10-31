package lk.sasax.GreenShadow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "fields")
public class Field {
    @Id
    @Column(name = "field_code", unique = true)
    private String fieldCode;
    @Column(name = "field_name")
    private String fieldName;
    @Column(name = "field_location")
    private Point fieldLocation;
    @Column(name = "extent_size")
    private Double extentSize;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Crop> crops;
    @ManyToMany
    @Column(name = "staff")
    private List<Staff> staff;
    @Column(name = "image_1", columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(name = "image_2", columnDefinition = "LONGTEXT")
    private String fieldImage2;

    @OneToMany(mappedBy = "assignedFieldDetails")
    private List<Equipment> equipment;

    @ManyToOne
    @JoinColumn(name = "log_code")
    private CropDetails cropDetails;
}
