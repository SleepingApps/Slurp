package it.sofk.slurp.dto;

import java.time.LocalDate;

public class WeekListItem {

    private LocalDate startDate, endDate;

    public WeekListItem(LocalDate startDate) {
        this.startDate = startDate;
        if(startDate != null)
            this.endDate = startDate.plusDays(6);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.endDate = startDate.plusDays(6);
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = startDate.plusDays(6);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
