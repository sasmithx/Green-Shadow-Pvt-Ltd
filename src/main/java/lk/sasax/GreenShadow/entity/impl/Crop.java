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
@Table(name = "crops")
public class Crop implements SuperEntity {
    @Id
    @Column(name = "crop_code", unique = true)
    private String cropCode;
    @Column(name = "crop_common_name")
    private String cropCommonName;
    @Column(name = "crop_scientific_name")
    private String cropScientificName;
    @Column(name = "crop_image", columnDefinition = "LONGTEXT")
    private String cropImage;
    @Column(name = "category")
    private String category;
    private String cropSeason;
    @ManyToOne
    @JoinColumn(name = "field_code")
    private Field field;

    @ManyToOne
    @JoinColumn(name = "log_code")
    private CropDetails cropDetails;
}
