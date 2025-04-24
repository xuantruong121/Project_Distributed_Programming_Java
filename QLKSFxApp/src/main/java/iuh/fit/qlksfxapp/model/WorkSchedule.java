package iuh.fit.qlksfxapp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class WorkSchedule {
    private LocalDate date;
    private String shift;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private String status;
    private String notes;

    public WorkSchedule(LocalDate date, String shift, LocalTime startTime, LocalTime endTime, String location, String status, String notes) {
        this.date = date;
        this.shift = shift;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.status = status;
        this.notes = notes;
    }

}
