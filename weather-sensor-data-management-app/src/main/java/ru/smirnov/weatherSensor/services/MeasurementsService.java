package ru.smirnov.weatherSensor.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smirnov.weatherSensor.models.Measurements;
import ru.smirnov.weatherSensor.repositories.MeasurementsRepository;
import ru.smirnov.weatherSensor.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorService sensorService;

    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorService sensorService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorService = sensorService;
    }

    @Transactional(readOnly = false)
    public void addMeasurements(Measurements measurements){
        enrichMeasurement(measurements);
        measurementsRepository.save(measurements);
    }

    public List<Measurements> findAll(){
        return measurementsRepository.findAll();
    }

    public void enrichMeasurement(Measurements measurements){
measurements.setSensor(sensorService.findByName(measurements.getSensor().getName()).get());

measurements.setMeasurementsDateTime(LocalDateTime.now());
    }
}
