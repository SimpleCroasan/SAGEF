package EISI.SAGEF.Services.Users;

import EISI.SAGEF.Dtos.UserManualRegistryDTO;
import EISI.SAGEF.Entities.Users.UserEntity;
import EISI.SAGEF.Repositories.Users.RoleRepository;
import EISI.SAGEF.Repositories.Users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserManagementService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Transactional
    public UserEntity createUserManually(UserManualRegistryDTO dto) throws Exception {
        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .roles(Set.of(roleRepository.findById(dto.getRoleId())
                        .orElseThrow(() -> new Exception("Role not found"))))
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        return userRepository.save(user);
    }


    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }


    @Transactional
    public Optional<UserEntity> updateUser(Long id, UserEntity updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            user.setPassword(updatedUser.getPassword());
            user.setEnabled(updatedUser.isEnabled());
            user.setAccountNonExpired(updatedUser.isAccountNonExpired());
            user.setAccountNonLocked(updatedUser.isAccountNonLocked());
            user.setCredentialsNonExpired(updatedUser.isCredentialsNonExpired());
            user.setRoles(updatedUser.getRoles());
            return user;
        });
    }


    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

