package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DTO.RegistrationDTO;

public interface RegistrationBO extends SuperBO {
    boolean save(RegistrationDTO registrationDTO);
}
