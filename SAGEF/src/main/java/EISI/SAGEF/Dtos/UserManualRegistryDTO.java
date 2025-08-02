package EISI.SAGEF.Dtos;

import lombok.Data;

@Data
public class UserManualRegistryDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private Long roleId;
}