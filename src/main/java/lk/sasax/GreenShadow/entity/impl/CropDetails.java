package lk.sasax.GreenShadow.entity.impl;

import jakarta.persistence.*;
import lk.sasax.GreenShadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "crop_details")
public class CropDetails implements SuperEntity {
    @Id
    @Column(name = "log_code", unique = true)
    private String logCode;
    private Date logDate;
    private String logDetails;
    private String observedImage;

    @OneToMany
    @JoinColumn(name = "field_code")
    private List<Field> fields;
    @OneToMany
    @JoinColumn(name = "crop_code")
    private List<Crop> crops;
    @OneToMany
    @JoinColumn(name = "staff_member_id")
    private List<Staff> staff;
}
