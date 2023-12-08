package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.FirstWeightDTO;
import com.baylor.diabeticselfed.dto.WeightTrackerReportDTO;
import com.baylor.diabeticselfed.entities.Invitation;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.entities.WeightTracker;
import com.baylor.diabeticselfed.repository.InvitationRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.repository.UserRepository;
import com.baylor.diabeticselfed.service.WeightTrackerService;
import com.baylor.diabeticselfed.token.Token;
import com.baylor.diabeticselfed.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/weight")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
public class WeightTrackerController {

    private final WeightTrackerService weightTrackerService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addReport")
    public ResponseEntity<?> addReport(@RequestBody WeightTrackerReportDTO weightTrackerReportDTO, Principal connectedUser) {

        try {

            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            var patient = patientRepository.findByPatientUser(user).get();

            if (Objects.equals(patient.getId(), weightTrackerReportDTO.getPatientId())) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                if (weightTrackerReportDTO.getWeight() == null || weightTrackerReportDTO.getHeight() == null) {
                    return new ResponseEntity<>("Invalid values", HttpStatus.FORBIDDEN);
                }
                else if (weightTrackerReportDTO.getWeight() < 0 || weightTrackerReportDTO.getHeight() < 0) {
                    return new ResponseEntity<>("Invalid values", HttpStatus.FORBIDDEN);
                }

                if (weightTrackerService.getReportByPatientIdANDDateT(weightTrackerReportDTO.getPatientId(), formatter.parse(weightTrackerReportDTO.getDateT())).isEmpty()) {

                    weightTrackerService.addNewWeightTrackerReport(weightTrackerReportDTO.getPatientId(),
                            formatter.parse(weightTrackerReportDTO.getDateT()),
                            weightTrackerReportDTO.getHeight(),
                            weightTrackerReportDTO.getWeight());

                    return new ResponseEntity<>(null, HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>("Patient already has a report on this date", HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return new ResponseEntity<>("User do Not have access", HttpStatus.I_AM_A_TEAPOT);
            }


        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/fetchReport/{patientId}")
    public List<WeightTracker> fetchReportByPatientId(@PathVariable Integer patientId, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var patient = patientRepository.findByPatientUser(user).get();

        return weightTrackerService.getReportByPatientId(patient.getId());
    }

    @PostMapping("/first/weightReport")
    public ResponseEntity<?> firstWeightReport(@RequestBody FirstWeightDTO firstWeightDTO) {
        Invitation t = invitationRepository.findByToken(firstWeightDTO.getSignUpToken());
        var user = userRepository.findByEmail(t.getEmail())
                .orElseThrow();
        var patient = patientRepository.findByPatientUser(user).get();

        if (firstWeightDTO.getWeight() == null || firstWeightDTO.getHeight() == null) {
            return new ResponseEntity<>("Invalid values", HttpStatus.FORBIDDEN);
        }
        else if (firstWeightDTO.getWeight() < 0 || firstWeightDTO.getHeight() < 0) {
            return new ResponseEntity<>("Invalid values", HttpStatus.FORBIDDEN);
        }

        weightTrackerService.addNewWeightTrackerReport(patient.getId(),
                firstWeightDTO.getDateT(),
                firstWeightDTO.getHeight(),
                firstWeightDTO.getWeight());

        return new ResponseEntity<>(null, HttpStatus.OK);

    }

}
