package EISI.SAGEF.Services.Users;

import EISI.SAGEF.Repositories.Users.PermissionRepository;
import EISI.SAGEF.Repositories.Users.RoleRepository;
import EISI.SAGEF.Repositories.Users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user = this.userRepository.findUserEntityByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.getAuthorities().size();
        return user;

    }



}