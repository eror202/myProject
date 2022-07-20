package ru.smirnov.weatherSensor.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.smirnov.weatherSensor.models.Sensor;
import ru.smirnov.weatherSensor.services.SensorService;
@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
Sensor sensor = (Sensor) target;

if(sensorService.findByName(sensor.getName()).isPresent()){
errors.rejectValue("name", "Уже есть сенсор с таким именем!");
}
    }
}
