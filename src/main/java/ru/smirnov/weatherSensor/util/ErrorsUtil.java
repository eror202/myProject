package ru.smirnov.weatherSensor.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.smirnov.weatherSensor.models.Measurements;
import java.util.List;


public class ErrorsUtil {

    public static void returnErrorsToClient(BindingResult bindingResult){
        StringBuilder msg = new StringBuilder();

        List<FieldError>errors =bindingResult.getFieldErrors();
for (FieldError error: errors){
msg.append(error.getField())
        .append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
        .append(";");
}
throw new MeasurementsException(msg.toString());
    }
}
