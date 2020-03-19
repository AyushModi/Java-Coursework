package uk.ac.ucl.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Analytics {
    private final Model model;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate now = LocalDate.now();
    private int[] numInEachDecade = new int[12];
    private LocalDate latestBday = LocalDate.parse("1600-01-01", formatter);
    private LocalDate earliestBday = LocalDate.now();
    private Patient oldest = null, youngest = null;
    private int ageSum = 0;

    public Analytics(Model model) {
        this.model = model;
    }

    public AnalyticsData getAnalytics() {
        reset();
        for (int i = 0; i < model.getDataFrame().getRowCount(); i++) {
            LocalDate bDay = LocalDate.parse(model.getDataFrame().getValue("BIRTHDATE", i), formatter);
            String dDayString = model.getDataFrame().getValue("DEATHDATE", i);
            if (dDayString.equals("")) {
                setYoungOld(i, bDay);
                ageCalc(bDay);
            } else {
                LocalDate dDay = LocalDate.parse(dDayString, formatter);
                ageSum += Period.between(bDay, dDay).getYears();
            }
        }
        return new AnalyticsData(youngest, oldest, ageSum / model.getDataFrame().getRowCount(), numInEachDecade);
    }

    private void setYoungOld(int i, LocalDate bDay) {
        if (bDay.isBefore(earliestBday)) {
            oldest = model.getPatient(i);
            earliestBday = bDay;
        } else if (bDay.isAfter(latestBday)) {
            youngest = model.getPatient(i);
            latestBday = bDay;
        }
    }

    private void ageCalc(LocalDate bDay) {
        int age = Period.between(bDay, now).getYears();
        if (age < 120)
            numInEachDecade[age / 10] += 1;
        ageSum += age;
    }

    private void reset() {
        numInEachDecade = new int[12];
        latestBday = LocalDate.parse("1600-01-01", formatter);
        earliestBday = LocalDate.now();
        oldest = null;
        youngest = null;
        ageSum = 0;

    }
}