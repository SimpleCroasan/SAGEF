package EISI.SAGEF.Controllers;

import EISI.SAGEF.Entities.Users.UserEntity;
import EISI.SAGEF.Services.Users.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserManagementService userManagementService;

    @Autowired
    public void setUserManagementService(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UserEntity>> listUsers() {
        if(this.userManagementService.getAllUsers().isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(this.userManagementService.getAllUsers());
    }

}
