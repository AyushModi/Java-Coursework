package uk.ac.ucl.model;

public class AnalyticsData {
    private Patient youngest;
    private Patient oldest;
    private int averageAge;
    private int[] numInEachDecade = new int[12];

    public AnalyticsData(Patient youngest, Patient oldest, int averageAge, int[] numInEachDecade) {
        this.youngest = youngest;
        this.oldest = oldest;
        this.averageAge = averageAge;
        this.numInEachDecade = numInEachDecade;
    }

    public Patient getYoungest() {
        return youngest;
    }

    public Patient getOldest() {
        return oldest;
    }

    public int getAverageAge() {
        return averageAge;
    }

    public int[] getNumInEachDecade() {
        return numInEachDecade;
    }
}
