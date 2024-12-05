package com.cathalob.medtracker.repository;

import com.cathalob.medtracker.model.UserModel;
import com.cathalob.medtracker.model.enums.DAYSTAGE;
import com.cathalob.medtracker.model.enums.USERROLE;
import com.cathalob.medtracker.model.tracking.BloodPressureReading;
import com.cathalob.medtracker.model.tracking.DailyEvaluation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class BloodPressureReadingRepositoryTests {
@Autowired
    private BloodPressureReadingRepository bloodPressureReadingRepository;

@Test
public void givenBloodPressureReading_whenSaved_thenReturnSavedBloodPressureReading(){
//    given
    UserModel userModel = new UserModel();
    userModel.setUsername("name");
    userModel.setPassword("abc");
    userModel.setRole(USERROLE.USER);

    DailyEvaluation dailyEvaluation = new DailyEvaluation();
    dailyEvaluation.setUserModel(userModel);
    dailyEvaluation.setRecordDate(LocalDate.now());

    BloodPressureReading bloodPressureReading = new BloodPressureReading();
    bloodPressureReading.setReadingTime(LocalDateTime.now());
    bloodPressureReading.setDiastole(80);
    bloodPressureReading.setSystole(120);
    bloodPressureReading.setHeartRate(60);
    bloodPressureReading.setDayStage(DAYSTAGE.MIDDAY);
    bloodPressureReading.setDailyEvaluation(dailyEvaluation);

//    when
    BloodPressureReading saved = bloodPressureReadingRepository.save(bloodPressureReading);

//    then
    assertThat(saved.getId()).isGreaterThan(0);
}
}