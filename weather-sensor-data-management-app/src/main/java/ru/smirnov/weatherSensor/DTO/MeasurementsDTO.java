package ru.smirnov.weatherSensor.DTO;

import ru.smirnov.weatherSensor.models.Sensor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MeasurementsDTO {

    @NotNull
    @Max(100)
    @Min(-100)
    private int value;

    @NotNull
    private boolean raining;

    @NotNull
    private Sensor sensor;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
