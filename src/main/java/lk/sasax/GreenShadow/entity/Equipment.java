package lk.sasax.GreenShadow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipments")
public class Equipment {
    @Id
    @Column(name = "eqiupment_id", unique = true)
    private String equipmentId;
    @Column(name = "equipment_name")
    private String name;
    @Column(name = "equipment_type")
    private String type;
    @Column(name = "availability_status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "staff_member_id")
//    @Column(name = "assigned_staff_details")
    private Staff assignedStaffDetails;

    @ManyToOne
    @JoinColumn(name = "field_code")
//    @Column(name = "assigned_field_details")
    private Field assignedFieldDetails;

}
