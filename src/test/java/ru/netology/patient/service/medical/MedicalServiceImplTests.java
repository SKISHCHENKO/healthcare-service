package ru.netology.patient.service.medical;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoFileRepositoryMock;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceMock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;


@RunWith(MockitoJUnitRunner.class)
public class MedicalServiceImplTests {

    @Captor
    private ArgumentCaptor<String> messageCaptor;

    @Mock
    private PatientInfoFileRepository repository;

    @Mock
    private SendAlertService alertService;

    @InjectMocks
    private MedicalServiceImpl service;

    private PatientInfo patientInfo;

    @Before
    public void setup() {
        patientInfo = new PatientInfo("1", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
    }
    @Test
    public void checkBloodPressureAlertTest(){
        // arrange
        Mockito.when(repository.getById("1")).thenReturn(patientInfo);

        // act
        service.checkBloodPressure("1", new BloodPressure(140, 90));

        // assert
        verify(alertService).send(messageCaptor.capture());
        String message = messageCaptor.getValue();
        String expected = "Warning, patient with id: 1, need help";
        Assert.assertEquals(expected, message);
    }
    @Test
    public void checkTemperatureAlertTest(){
        // arrange
        Mockito.when(repository.getById("1")).thenReturn(patientInfo);

        // act
        service.checkTemperature("1", new BigDecimal("35.0"));

        // assert
        verify(alertService).send(messageCaptor.capture());
        String message = messageCaptor.getValue();
        Assert.assertEquals("Warning, patient with id: 1, need help", message);
    }
    @Test
    public void checkBloodPressureNormalTest(){
        //arrange
        PatientInfoFileRepositoryMock repository = new PatientInfoFileRepositoryMock();
        PatientInfo patientInfo = new PatientInfo("1","Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        repository.addPatientInfo(patientInfo);

        // act
        SendAlertServiceMock alertService = new SendAlertServiceMock();
        MedicalServiceImpl service = new MedicalServiceImpl(repository, alertService);
        service.checkBloodPressure("1", new BloodPressure(120,80));

        // assert
        Assert.assertFalse(alertService.isAlertSent());
    }

    @Test
    public void checkTemperatureNormalTest(){
        //arrange
        PatientInfoFileRepositoryMock repository = new PatientInfoFileRepositoryMock();
        PatientInfo patientInfo = new PatientInfo("1","Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        repository.addPatientInfo(patientInfo);
        // act
        SendAlertServiceMock alertService = new SendAlertServiceMock();
        MedicalServiceImpl service = new MedicalServiceImpl(repository, alertService);
        service.checkTemperature("1", new BigDecimal("36.65"));

        // assert
        Assert.assertFalse(alertService.isAlertSent());
    }

}
