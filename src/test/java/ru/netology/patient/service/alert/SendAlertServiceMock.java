package ru.netology.patient.service.alert;

public class SendAlertServiceMock implements SendAlertService {

    private boolean isAlertSent = false;

    @Override
    public void send(String message) {
        isAlertSent = true;
    }

    public boolean isAlertSent() {
        return isAlertSent;
    }
}