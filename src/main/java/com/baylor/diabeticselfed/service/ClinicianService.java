package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.*;
import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.model.ViewPatientSummary;
import com.baylor.diabeticselfed.repository.*;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


@Service
public class ClinicianService {
    private final ClinicianNoteRepository clinicianNoteRepository;

    private final ClinicianRepository clinicianRepository;

    private final PatientRepository patientRepository;

    private final WeightTrackerRepository weightTrackerRepository;

    private final SurveyRepository surveyRepository;

    private final ModuleProgressRepository moduleProgressRepository;

    public ClinicianService(ClinicianNoteRepository clinicianNoteRepository, ClinicianRepository clinicianRepository, PatientRepository patientRepository, WeightTrackerRepository weightTrackerRepository, SurveyRepository surveyRepository, ModuleProgressRepository moduleProgressRepository) {
        this.clinicianNoteRepository = clinicianNoteRepository;
        this.clinicianRepository = clinicianRepository;
        this.patientRepository = patientRepository;
        this.weightTrackerRepository = weightTrackerRepository;
        this.surveyRepository = surveyRepository;
        this.moduleProgressRepository = moduleProgressRepository;
    }

    public List<Clinician> getAllClinicians() {
        return clinicianRepository.findAll();
    }

    public List<ViewPatientSummary> getViewPatientSummary() {
        List<Patient> allPatients = patientRepository.findAll();
        List<ViewPatientSummary> summaries = new ArrayList<>();

        for (Patient patient : allPatients) {
            ViewPatientSummary summary = new ViewPatientSummary();
            // patient information
            summary.setPatientId(patient.getId());
            summary.setPatientName(patient.getName());
            summary.setPatientDOB(patient.getDOB());

            //patient survey question and its answer
            List<Survey> surveys = surveyRepository.findByPatient(patient);
            List<Boolean> answerList = new ArrayList<>();
            List<Date> answerDateList = new ArrayList<>();
            List<String> questionDescriptionList = new ArrayList<>();

            for (Survey survey : surveys) {
                answerList.add(survey.getAnswer());
                answerDateList.add(survey.getDateT());
                questionDescriptionList.add(survey.getQuestion().getDescription());
            }

            summary.setAnswerList(answerList);
            summary.setAnswerDateList(answerDateList);
            summary.setQuestionDescriptionList(questionDescriptionList);

            // patient height weight_list and BMI_list to keep change
            List<WeightTracker> weightRecords = weightTrackerRepository.findByPatientId(patient.getId());
            List<Integer> weights = new ArrayList<>();
            List<Date> dates = new ArrayList<>();
            List<Double> bmiList = new ArrayList<>();
            for (WeightTracker record : weightRecords) {
                weights.add(record.getWeight());
                dates.add(record.getDateT());

                // Assuming height is in inches and weight is in pounds for BMI calculation
                double heightInMeters = record.getHeight() * 0.0254; // Convert height from inches to meters
                double weightInKilograms = record.getWeight() * 0.453592; // Convert weight from pounds to kilograms
                double bmi = weightInKilograms / (heightInMeters * heightInMeters); // Calculate BMI
                bmiList.add(bmi);
            }
            if (!weightRecords.isEmpty()) {
                // Assuming the height doesn't change, take the height from the first record
                int height = weightRecords.get(0).getHeight();
                summary.setHeight(height);
            }
            summary.setWeightList(weights);
            summary.setWeightTimeList(dates);
            summary.setBmiList(bmiList);

            //patient module, modulelog and moduleprogress
            List<ModuleProgress> moduleProgressList = moduleProgressRepository.findByPatient(patient);
            List<String> moduleNames = new ArrayList<>();
            List<String> moduleDescriptions = new ArrayList<>();
            List<Integer> moduleProgressPercentages = new ArrayList<>();
            List<LocalDateTime> moduleStartTimes = new ArrayList<>();
            List<LocalDateTime> moduleEndTimes = new ArrayList<>();

            for (ModuleProgress progress : moduleProgressList) {
                Module module = progress.getModule();
                moduleNames.add(module.getName());
                moduleDescriptions.add(module.getDescription());
                moduleProgressPercentages.add(progress.getCompleted_percentage());

                // Assuming ModuleLog contains the start and end times for each module progress
                List<ModuleLog> moduleLogs = progress.getModuleLogList();
                LocalDateTime startTime = moduleLogs.stream()
                        .min(Comparator.comparing(ModuleLog::getStartT))
                        .map(ModuleLog::getStartT)
                        .orElse(null);
                LocalDateTime endTime = moduleLogs.stream()
                        .max(Comparator.comparing(ModuleLog::getEndT))
                        .map(ModuleLog::getEndT)
                        .orElse(null);

                moduleStartTimes.add(startTime);
                moduleEndTimes.add(endTime);
            }

            summary.setModuleNames(moduleNames);
            summary.setModuleDescriptions(moduleDescriptions);
            summary.setModuleProgressPercentages(moduleProgressPercentages);
            summary.setModuleStartTimes(moduleStartTimes);
            summary.setModuleEndTimes(moduleEndTimes);

            //Display patient clinician summaries
            List<ClinicianNote> notes = getNotesByPatientId(patient.getId());
            List<String> clinicianNotes = new ArrayList<>();
            List<LocalDateTime> noteTimes = new ArrayList<>();

            for (ClinicianNote note : notes) {
                clinicianNotes.add(note.getNote());
                noteTimes.add(note.getCreatedAt());
            }

            summary.setClinicianNotes(clinicianNotes);
            summary.setNoteTimes(noteTimes);

            summaries.add(summary);
        }

        return summaries;
    }

    public List<ClinicianNote> getNotesByPatientId(Integer patientId) {
        return clinicianNoteRepository.findByPatientId(patientId);
    }

    public ClinicianNote addNote(Integer patientId, String note) {
        System.out.println("clinician save note into: ");
        ClinicianNote clinicianNote = new ClinicianNote();
        clinicianNote.setPatientId(patientId);
        clinicianNote.setNote(note);
        clinicianNote.setCreatedAt(LocalDateTime.now());
        return clinicianNoteRepository.save(clinicianNote);
    }
}


