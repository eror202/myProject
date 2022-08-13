package ru.smirnov.weatherSensor.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.smirnov.weatherSensor.models.Measurements;
import ru.smirnov.weatherSensor.services.SensorService;
@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurements.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

Measurements measurements = (Measurements) target;
if (measurements.getSensor()==null){
return;
}
if(sensorService.findByName(measurements.getSensor().getName()).isEmpty()){
errors.rejectValue("sensor", "Нет зарегистрированного сенсора с таким именем!");
}
    }
}
