package ru.netology.patient.repository;

import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;

import java.util.HashMap;
import java.util.Map;

public class PatientInfoFileRepositoryMock implements PatientInfoRepository {

    private final Map<String, PatientInfo> patientInfos = new HashMap<>();

    public void addPatientInfo(PatientInfo patientInfo) {
        patientInfos.put(patientInfo.getId(), patientInfo);
    }

    @Override
    public PatientInfo getById(String id) {
        return patientInfos.get(id);
    }

    @Override
    public String add(PatientInfo patientInfo) {
        return "";
    }

    @Override
    public PatientInfo remove(String id) {
        return null;
    }

    @Override
    public PatientInfo update(PatientInfo patientInfo) {
        return null;
    }
}