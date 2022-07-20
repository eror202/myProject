package ru.smirnov.weatherSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smirnov.weatherSensor.models.Measurements;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {
}
