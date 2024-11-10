package lk.sasax.GreenShadow.customObj.impl;

import lk.sasax.GreenShadow.customObj.MonitorLogResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitorLogErrorResponse implements MonitorLogResponse , Serializable {
    private int errorCode;
    private String errorMessage;
}
