package com.baylor.diabeticselfed.Service;

import com.baylor.diabeticselfed.Entity.Admin;
import com.baylor.diabeticselfed.Entity.Clinician;
import com.baylor.diabeticselfed.Entity.Patient;
import com.baylor.diabeticselfed.Entity.User;
import com.baylor.diabeticselfed.Repository.AdminRepository;
import com.baylor.diabeticselfed.Repository.ClinicianRepository;
import com.baylor.diabeticselfed.Repository.PatientRepository;
import com.baylor.diabeticselfed.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicianRepository clinicianRepository;

    @Autowired
    private AdminRepository adminRepository;
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

        public Patient savePatient(Patient user) {
        return patientRepository.save(user);
    }

    public Clinician saveClinician(Clinician user) {
        return clinicianRepository.save(user);
    }

    public Admin saveAdmin(Admin user) {
        return adminRepository.save(user);
    }


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
