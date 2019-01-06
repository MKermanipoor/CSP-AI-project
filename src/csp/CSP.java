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
        recursiveBackTracking(new State(info));
    }

    private State recursiveBackTracking(State state){
        if (state.isComplete())
            return state;

        int human = state.getNextHumanIndex();
        State temp;
        for (int fruit : state.getAvailableFruitForHuman(human)){
            state.assign(human, fruit);
            temp = recursiveBackTracking(state);
            if (temp != null)
                return temp;
            state.assign(0, fruit);
        }
        return null;
    }

    public static void main(String[] arg){
        new CSP().run();
    }
}
