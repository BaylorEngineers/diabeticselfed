package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.dto.UserDTO;
import com.baylor.diabeticselfed.entities.Role;
import com.baylor.diabeticselfed.repository.UserRepository;
import com.baylor.diabeticselfed.user.ChangePasswordRequest;
import com.baylor.diabeticselfed.entities.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }

    public User getUserData(Long id) throws ChangeSetPersister.NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public List<User> getAllClinicians() {
        return repository.findByRole("CLINICIAN");
    }

//    public Map<User, List<User>> getCliniciansWithPatients() {
//        List<User> clinicians = repository.findByRole(String.valueOf(Role.CLINICIAN));
//        Map<User, List<User>> cliniciansWithPatients = new HashMap<>();
//
//        for (User clinician : clinicians) {
//            List<User> patients = repository.findByRoleAndClinician(Role.CLINICIAN, Role.PATIENT);
//            cliniciansWithPatients.put(clinician, patients);
//        }
//
//        return cliniciansWithPatients;
//    }

}
