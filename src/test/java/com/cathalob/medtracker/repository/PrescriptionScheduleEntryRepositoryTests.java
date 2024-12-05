package com.cathalob.medtracker.repository;

import com.cathalob.medtracker.model.UserModel;
import com.cathalob.medtracker.model.enums.DAYSTAGE;
import com.cathalob.medtracker.model.enums.USERROLE;
import com.cathalob.medtracker.model.prescription.Medication;
import com.cathalob.medtracker.model.prescription.Prescription;
import com.cathalob.medtracker.model.prescription.PrescriptionScheduleEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PrescriptionScheduleEntryRepositoryTests {
    @Autowired
    private PrescriptionScheduleEntryRepository prescriptionScheduleEntryRepository;

    @Test
    public void givenPrescriptionScheduleEntry_whenSaved_thenReturnSavedPrescriptionScheduleEntry() {
        //        given
        UserModel patient = new UserModel();
        patient.setUsername("name");
        patient.setPassword("abc");
        patient.setRole(USERROLE.USER);

        UserModel practitioner = new UserModel();
        practitioner.setUsername("name");
        practitioner.setPassword("abc");
        practitioner.setRole(USERROLE.USER);

        Medication medication = new Medication();
        medication.setName("med");


        Prescription prescription = new Prescription();
        prescription.setDoseMg(15);
        prescription.setPatient(patient);
        prescription.setPractitioner(practitioner);
        prescription.setBeginTime(LocalDateTime.now());
        prescription.setMedication(medication);

        PrescriptionScheduleEntry prescriptionScheduleEntry = new PrescriptionScheduleEntry();
        prescriptionScheduleEntry.setPrescription(prescription);
        prescriptionScheduleEntry.setDayStage(DAYSTAGE.MIDDAY);
        PrescriptionScheduleEntry saved = prescriptionScheduleEntryRepository.save(prescriptionScheduleEntry);
        assertThat(saved.getId()).isGreaterThan(0);
    }
}