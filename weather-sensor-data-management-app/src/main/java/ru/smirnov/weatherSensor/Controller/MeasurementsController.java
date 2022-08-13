package ru.smirnov.weatherSensor.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.smirnov.weatherSensor.DTO.MeasurementsDTO;
import ru.smirnov.weatherSensor.DTO.MeasurementsResponse;
import ru.smirnov.weatherSensor.models.Measurements;
import ru.smirnov.weatherSensor.services.MeasurementsService;
import ru.smirnov.weatherSensor.services.SensorService;
import ru.smirnov.weatherSensor.util.MeasurementErrorResponse;
import ru.smirnov.weatherSensor.util.MeasurementValidator;
import ru.smirnov.weatherSensor.util.MeasurementsException;

import javax.validation.Valid;

import java.util.stream.Collectors;

import static ru.smirnov.weatherSensor.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final SensorService sensorService;

    private final MeasurementValidator measurementValidator;

    private final ModelMapper modelMapper;

    public MeasurementsController(MeasurementsService measurementsService, SensorService sensorService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.sensorService = sensorService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementsDTO measurementsDTO,
                                          BindingResult bindingResult){
Measurements measurementsToAdd = convertToMeasurements(measurementsDTO);

measurementValidator.validate(measurementsToAdd, bindingResult);
if (bindingResult.hasErrors()){
    returnErrorsToClient(bindingResult);
}
measurementsService.addMeasurements(measurementsToAdd);
return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public MeasurementsResponse getMeasurements(){
return new MeasurementsResponse(measurementsService.findAll().stream().map(this::convertToMeasurementsDTO).collect(Collectors.toList()));
    }
@GetMapping("/rainyDaysCount")
public Long getRainyDaysCount(){
return measurementsService.findAll().stream().filter(Measurements::isRaining).count();
}

private MeasurementsDTO convertToMeasurementsDTO(Measurements measurements){
        return modelMapper.map(measurements, MeasurementsDTO.class);
}
private Measurements convertToMeasurements(MeasurementsDTO measurementsDTO){
        return modelMapper.map(measurementsDTO, Measurements.class);
}
@ExceptionHandler
private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementsException e){
    MeasurementErrorResponse errorResponse = new MeasurementErrorResponse(
            e.getMessage(),
            System.currentTimeMillis()
    );
return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
}
}
