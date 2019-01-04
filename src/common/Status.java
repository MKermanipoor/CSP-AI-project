package common;


import static common.PublicValues.HUNGRY_HUMAN_SCORE;
import static common.PublicValues.WASTE_ENERGY_SCORE;

public class Status implements Comparable<Status> {
    public final long hungry;
    public final long waste;
    public final long needEnergy;
    public final long minNeedEnergy;

    public Status(long hungry, long waste, long needEnergy, long minNeedEnergy) {
        this.hungry = hungry;
        this.waste = waste;
        this.needEnergy = needEnergy;
        this.minNeedEnergy = minNeedEnergy;
    }

    @Override
    public int compareTo(Status b) {
        long aScore ,bScore;
        aScore = this.getScore();
        bScore = b.getScore();
        return (int) (aScore - bScore);
    }

    @Override
    public String toString() {
        return "Hungry : " + hungry+
                "\nwaste : " + waste+
                "\nneedEnergy : " + needEnergy;
    }

    public long getScore(){
        return this.hungry * HUNGRY_HUMAN_SCORE +
                this.waste * WASTE_ENERGY_SCORE +
                this.minNeedEnergy;
    }
}
