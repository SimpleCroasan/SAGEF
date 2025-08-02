package EISI.SAGEF.Services.Users;

import EISI.SAGEF.Repositories.Users.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


}