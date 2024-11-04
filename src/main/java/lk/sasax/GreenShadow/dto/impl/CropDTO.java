package lk.sasax.GreenShadow.dto.impl;

import lk.sasax.GreenShadow.customObj.CropResponse;
import lk.sasax.GreenShadow.dto.SuperDTO;
import lk.sasax.GreenShadow.entity.impl.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO implements SuperDTO, CropResponse {
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String cropImage;
    private String category;
    private String cropSeason;
    private String fieldCode;
}
