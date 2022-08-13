package ru.smirnov.weatherSensor.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.smirnov.weatherSensor.DTO.SensorDTO;
import ru.smirnov.weatherSensor.models.Measurements;
import ru.smirnov.weatherSensor.models.Sensor;
import ru.smirnov.weatherSensor.services.SensorService;
import ru.smirnov.weatherSensor.util.MeasurementErrorResponse;
import ru.smirnov.weatherSensor.util.MeasurementsException;
import ru.smirnov.weatherSensor.util.SensorValidator;

import javax.validation.Valid;

import static ru.smirnov.weatherSensor.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorController {
private final SensorService sensorService;
private final ModelMapper modelMapper;
private final SensorValidator sensorValidator;

    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult){
Sensor sensorToAdd = convertToSensor(sensorDTO);

sensorValidator.validate(sensorToAdd,bindingResult);
if (bindingResult.hasErrors()){
    returnErrorsToClient(bindingResult);
}
sensorService.register(sensorToAdd);
return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementsException e){
        MeasurementErrorResponse errorResponse = new MeasurementErrorResponse(
        e.getMessage(),
        System.currentTimeMillis()
    );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO,Sensor.class);
    }
}
