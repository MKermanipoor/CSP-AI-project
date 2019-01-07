package csp;

import common.Info;
import common.State;

public class CSP {
    private final Info info;

    public CSP(Info info) {
        this.info = info;
    }

    public CSP(){
        this(Info.getFromInput());
    }

    public void run(){
        State re = recursiveBackTracking(new State(info));
        System.out.println("done");
    }

    private State recursiveBackTracking(State state){
        if (state.isComplete())
            return state;

//        int human = state.getNextHumanIndex();
//        State temp;
//        for (int fruit : state.getAvailableFruitForHuman(human)){
//            state.assign(human, fruit);
//            temp = recursiveBackTracking(state);
//            if (temp != null)
//                return temp;
//            state.assign(0, fruit);
//        }

        int fruit = state.getNextFruitIndex();
        if (fruit == -1)
            return null;
        State temp, result;
        Integer[] available = state.getAvailableHumanForFruit(fruit);
        for (int human : available){
            result = new State(state, human, fruit);
            temp = recursiveBackTracking(result);
            if (temp != null && temp.isComplete())
                return temp;
        }
        return null;
    }

    public static void main(String[] arg){
        new CSP().run();
    }
}
