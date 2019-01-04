package common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static common.PublicValues.*;

import static common.PublicValues.*;

public class Info {
    private int humanNumber = 0;
    private int fruitNumber = 0;
    private int[] fruitEnergy = new int[MAX_ARRAY_SIZE];
    private int[] needEnergy = new int[MAX_ARRAY_SIZE];
    private HashMap<Integer, Set<Integer>> constraint = new HashMap<>();

    private void addConstraint(int s, int d){
        addConstraintOneDirection(s,d);
        addConstraintOneDirection(d,s);
    }

    private void addConstraintOneDirection(int s, int d){
        if (!constraint.containsKey(s)){
            constraint.put(s, new HashSet<>());
        }
        constraint.get(s).add(d);
    }

    public int getHumanNumber() {
        return humanNumber;
    }

    public int getFruitNumber() {
        return fruitNumber;
    }

    public int getNeedEnergy(int humanIndex){
        return needEnergy[humanIndex];
    }

    public int getFruitEnergy(int fruitIndex){
        return fruitEnergy[fruitIndex];
    }

    public boolean isNeighbor (int s, int d){
        if (!constraint.containsKey(s))
            constraint.put(s, new HashSet<>());

        return constraint.get(s).contains(d);
    }

    public Set<Integer> getNeighbors(int s){
        if (!constraint.containsKey(s))
            constraint.put(s, new HashSet<>());
        return constraint.get(s);
    }

    private Info(){}

    public static Info getFromInput (){
        Info info = new Info();

        info.humanNumber = SCANNER.nextInt();
        info.fruitNumber = SCANNER.nextInt();

        for (int i=1 ; i<=info.getHumanNumber() ; i++)
            info.needEnergy[i] = SCANNER.nextInt();

        for (int i=1 ; i<=info.getFruitNumber() ; i++)
            info.fruitEnergy[i] = SCANNER.nextInt();

        int b = SCANNER.nextInt();
        for (int i=0 ;  i<b ; i++)
            info.addConstraint(SCANNER.nextInt(), SCANNER.nextInt());

        return info;
    }
}
