package com.cathalob.medtracker.repository;

import com.cathalob.medtracker.model.UserModel;
import com.cathalob.medtracker.model.enums.DAYSTAGE;
import com.cathalob.medtracker.model.enums.USERROLE;
import com.cathalob.medtracker.model.prescription.Medication;
import com.cathalob.medtracker.model.prescription.Prescription;
import com.cathalob.medtracker.model.prescription.PrescriptionScheduleEntry;
import com.cathalob.medtracker.testdata.PrescriptionScheduleEntryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static com.cathalob.medtracker.testdata.PrescriptionBuilder.aPrescription;
import static com.cathalob.medtracker.testdata.PrescriptionScheduleEntryBuilder.aPrescriptionScheduleEntry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PrescriptionScheduleEntryRepositoryTests {
    @Autowired
    private PrescriptionScheduleEntryRepository prescriptionScheduleEntryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void givenPrescriptionScheduleEntry_whenSaved_thenReturnSavedPrescriptionScheduleEntry() {
        //        given
        PrescriptionScheduleEntry prescriptionScheduleEntry = aPrescriptionScheduleEntry().build();
        Prescription prescription = prescriptionScheduleEntry.getPrescription();
        testEntityManager.persist(prescription.getPatient());
        testEntityManager.persist(prescription.getPractitioner());
        testEntityManager.persist(prescription.getMedication());
        testEntityManager.persist(prescription);

        PrescriptionScheduleEntry saved = prescriptionScheduleEntryRepository.save(prescriptionScheduleEntry);
        assertThat(saved.getId()).isEqualTo(1);
    }
}