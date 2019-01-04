package common;

import java.util.ArrayList;

import static common.PublicValues.MAX_ARRAY_SIZE;

public class State {
    private int[] fruitAssign = new int[MAX_ARRAY_SIZE];
    private ArrayList<Integer>[] humanAssign = new ArrayList[MAX_ARRAY_SIZE];

    public void assign(int human, int fruit, int fruitNumber, int humanNumber) {
        if (fruit > fruitNumber)
            return;
        if (human > humanNumber)
            return;

        int lastHuman = this.getHumanFromFruit(fruit);
        this.fruitAssign[fruit] = human;
        this.getFruitsFromHuman(lastHuman).remove((Integer) fruit);
        this.getFruitsFromHuman(human).add(fruit);
    }

    public ArrayList<Integer> getFruitsFromHuman(int human) {
        if (humanAssign[human] == null)
            humanAssign[human] = new ArrayList<>();

        return humanAssign[human];
    }

    public int getHumanFromFruit(int fruit) {
        return fruitAssign[fruit];
    }

    public Status fitness(Info info) {
        long waste = 0;
        long hungry = 0;
        long remainingEnergy = 0;
        long minNeedEnergy = Long.MAX_VALUE;
        ArrayList<Integer> fruits;

        for (int i = 1; i <= info.getHumanNumber(); i++) {
            fruits = this.getFruitsFromHuman(i);

            int energy = 0;

            for (int fruit : fruits) {
                energy += info.getFruitEnergy(fruit);
            }

            if (energy < info.getNeedEnergy(i)) {
                hungry++;
                remainingEnergy += info.getNeedEnergy(i) - energy;
                minNeedEnergy = Math.min(minNeedEnergy, info.getNeedEnergy(i) - energy);
            } else {
                waste += energy - info.getNeedEnergy(i);
            }
        }
        if (hungry == 0)
            return new Status(hungry, waste, 0, 0);
        return new Status(hungry, waste, remainingEnergy, minNeedEnergy);
    }

}
