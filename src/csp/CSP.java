package csp;

import common.Info;
import common.State;
import static common.PublicValues.*;

public class CSP {
    private final Info info;
    private int visitNodeCount = 0;
    private long startTime;
    private State lastSurviveState;
    private long lastSurviveStateHungry = Long.MAX_VALUE;

    public CSP(Info info) {
        this.info = info;
    }

    public CSP(){
        this(Info.getFromInput());
    }

    public void run(){
        startTime = System.currentTimeMillis();
        State re = recursiveBackTracking(new State(info));
        System.out.println(re);
        System.out.println(visitNodeCount);
    }

    private State recursiveBackTracking(State state){
        if (System.currentTimeMillis() - startTime > TIME_LIMIT)
            return lastSurviveState;

        visitNodeCount ++;
        if (visitNodeCount % 100 == 0)
            System.out.println(visitNodeCount);
        if (state.isComplete())
            return state;

        if (state.fitness().hungry < lastSurviveStateHungry) {
            lastSurviveState = state;
            lastSurviveStateHungry = state.fitness().hungry;
        }

        int fruit = state.getNextFruitIndex();
        if (fruit == -1)
            return null;
        State temp, result;
        Integer[] available = state.getAvailableHumanForFruit(fruit);
        for (int human : available){
            result = new State(state, human, fruit);
            temp = recursiveBackTracking(result);
            if (System.currentTimeMillis() - startTime > TIME_LIMIT)
                return lastSurviveState;
            if (temp != null && temp.isComplete())
                return temp;
        }
        return null;
    }

    public static void main(String[] arg){
        new CSP().run();
    }
}
