package common;

import java.util.*;
import java.util.function.Consumer;

import static common.PublicValues.MAX_ARRAY_SIZE;

public class State {
    private int[] fruitAssign = new int[MAX_ARRAY_SIZE];
    private ArrayList<Integer>[] humanAssign = new ArrayList[MAX_ARRAY_SIZE];
    private HashMap<Integer, Set<Integer>> domains = new HashMap<>();
    private final Info info;


    private State(Info info, boolean isFirst) {
        this.info = info;

        if (isFirst) {
            List<Integer> allHuman = new ArrayList<>();
            for (int i = 1; i <= info.getHumanNumber(); i++)
                allHuman.add(i);

            for (int i = 1; i <= info.getFruitNumber(); i++) {
                domains.put(i, new HashSet<>(allHuman));
            }

            for (int i = 1; i <= info.getFruitNumber(); i++)
                assign(0, i);
        }
    }

    public State(Info info){
        this(info, true);
    }

    public State(State state, int human, int fruit){
        this(state.info, false);
        this.domains = new HashMap<>();
        state.domains.keySet().iterator().forEachRemaining(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                State.this.domains.put(integer, new HashSet<>(state.domains.get(integer)));
            }
        });
        this.fruitAssign = Arrays.copyOf(state.fruitAssign, state.fruitAssign.length);

        for(int i=0 ; i<=this.info.getHumanNumber() ; i++){
             if (state.humanAssign[i] == null)
                 continue;

             this.humanAssign[i] = new ArrayList<>(state.humanAssign[i]);
        }
        assign(human, fruit);
    }


    public void assign(int human, int fruit) {

        if (fruit > info.getFruitNumber())
            return;
        if (human > info.getHumanNumber())
            return;

        int lastHuman = this.getHumanFromFruit(fruit);
        this.fruitAssign[fruit] = human;
        this.getFruitsFromHuman(lastHuman).remove((Integer) fruit);
        this.getFruitsFromHuman(human).add(fruit);

        if (human == 0) {
            return;
        }

        //forward checking
        domains.remove(fruit);
        info.getFruitIndexWithSameEnergy(info.getFruitEnergy(fruit)).iterator().forEachRemaining(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                Set<Integer> temp = domains.get(integer);
                if (temp == null)
                    return;
                temp.removeAll(info.getNeighbors(human));
            }
        });
    }

    public ArrayList<Integer> getFruitsFromHuman(int human) {
        if (humanAssign[human] == null)
            humanAssign[human] = new ArrayList<>();

        return humanAssign[human];
    }

    public int getHumanFromFruit(int fruit) {
        return fruitAssign[fruit];
    }

    public Status fitness() {
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

    public boolean isComplete() {
        // TODO: 1/6/2019 all of human must be survive
        for (int i = 1; i <= info.getFruitNumber(); i++) {
            if (getHumanFromFruit(i) == 0)
                return false;
        }
        return this.fitness().hungry == 0;
    }


    public int getNextFruitIndex() {
        int index = -1;
        int indexSize = Integer.MAX_VALUE;
        for (int i = 1; i <= info.getFruitNumber(); i++) {
            if (domains.get(i) == null || domains.get(i).size() == 0)
                continue;

            if (domains.get(i).size() < indexSize){
                indexSize = domains.get(i).size();
                index = i;
            }
        }
        return index;
    }

    public Integer[] getAvailableHumanForFruit(int fruit) {
        if (!domains.containsKey(fruit))
            return null;

        Integer[] result;
        PriorityQueue<Integer> humans = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return info.getNeighbors(o1).size() - info.getNeighbors(o2).size();
            }
        });

        humans.addAll(domains.get(fruit));

        result = new Integer[humans.size()];
        result = humans.toArray(result);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for(int i=0 ; i<=info.getHumanNumber() ; i++){
            st.append(i).append(" : ");
            for (int fruit : getFruitsFromHuman(i)){
                st.append(fruit).append(" ");
            }
            st.append("\n");
        }
        return st.toString();
    }
}
