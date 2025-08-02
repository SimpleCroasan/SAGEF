package EISI.SAGEF.Services.Users;

import EISI.SAGEF.Dtos.AuthResponse;
import EISI.SAGEF.Dtos.LoginRequest;
import EISI.SAGEF.Dtos.RegisterRequest;
import EISI.SAGEF.Entities.Users.UserEntity;
import EISI.SAGEF.Repositories.Users.RoleRepository;
import EISI.SAGEF.Repositories.Users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails user = this.userRepository.findUserEntityByUsername(loginRequest.getUsername())
                .orElseThrow(()->new UsernameNotFoundException("No existe ese usuario"));
        UserEntity userWithId = this.userRepository.findUserEntityByUsername(loginRequest.getUsername()).get();

        String token = this.jwtService.getToken(user, userWithId.getId());


        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) throws Exception {

        UserEntity user = UserEntity.builder().username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(this.roleRepository.findById(Long.valueOf(2)).orElseThrow(() -> new Exception("Role not found"))))
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        UserEntity userWithId=this.userRepository.save(user);

        return AuthResponse.builder()
                .token(this.jwtService.getToken(user, userWithId.getId()))
                .build();
    }

}