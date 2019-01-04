package common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static common.PublicValues.*;

public class Info {
    private int humanNumber = 0;
    private int fruitNumber = 0;
    private int[] fruitEnergy = new int[MAX_ARRAY_SIZE];
    private int[] needEnergy = new int[MAX_ARRAY_SIZE];
    private HashMap<Integer, Set<Integer>> constraint = new HashMap<>();

    public int getHumanNumber() {
        return humanNumber;
    }

    public int getFruitNumber() {
        return fruitNumber;
    }

    public void setHumanNumber(int humanNumber) {
        this.humanNumber = humanNumber;
    }

    public void setFruitNumber(int fruitNumber) {
        this.fruitNumber = fruitNumber;
    }

    public int getNeedEnergy(int humanIndex){
        return needEnergy[humanIndex];
    }

    public int getFruitEnergy(int fruitIndex){
        return fruitEnergy[fruitIndex];
    }

    public void addConstraint(int s, int d){
        addConstraintOneDirection(s,d);
        addConstraintOneDirection(d,s);
    }

    private void addConstraintOneDirection(int s, int d){
        if (!constraint.containsKey(s)){
            constraint.put(s, new HashSet<>());
        }
        constraint.get(s).add(d);
    }


}
