package org.example.backend.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.example.backend.dto.reponse.UserRegisterReponse;
import org.example.backend.dto.request.UserRegisterRequest;
import org.example.backend.persitence.entity.Role;
import org.example.backend.persitence.repository.UserRepository;

import org.example.backend.persitence.repository.RoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.backend.persitence.entity.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);

    @Transactional
    public UserRegisterReponse register(UserRegisterRequest request)  {
        log.info("In method register user");
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        if (request.getPassword()==null) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        User user=new User();
        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setCompany(request.getCompany());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        user.setCreatedAt(request.getCreatedAt());
        user.setUpdatedAt(request.getCreatedAt());
        Set<Role> roleSet=request.getRoles().stream().map(roleName->roleRepository.findRoleByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName))).collect(Collectors.toSet())
                ;
        user.setRoles(roleSet);
        userRepository.save(user);

        UserRegisterReponse userRegisterReponse=new UserRegisterReponse(request);
        userRegisterReponse.setId(request.getId());
        userRegisterReponse.setUsername(request.getUsername());
        userRegisterReponse.setEmail(request.getEmail());
        userRegisterReponse.setRoles(request.getRoles());
        userRegisterReponse.setActive(true);
        userRegisterReponse.setPhone(request.getPhone());
        userRegisterReponse.setCompany(request.getCompany());
        userRegisterReponse.setCreatedAt(request.getCreatedAt());
        userRegisterReponse.setUpdateAt(request.getCreatedAt());
        log.info("User registered and save successfully", userRegisterReponse);
        return userRegisterReponse;
    }
   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Username not found"+username));

        List<GrantedAuthority> grantedAuthorities=user.getRoles().stream().map(roleName->
                new SimpleGrantedAuthority("ROLE"+roleName.getRoleName())).collect(Collectors.toList());
        return org.springframework.security.core.userdetails.
                User.withUsername(user.getUsername()).
                password(user.getPasswordHash()).
                authorities(grantedAuthorities)
                .accountExpired(false).
                accountLocked(false).
                credentialsExpired(false).
                disabled(!user.isActive())
                .build();
    }
    public UserRepository forgetPassword()
    {

    }

}
