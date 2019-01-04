package common;

import java.util.Random;
import java.util.Scanner;

public class PublicValues {
    public static final Scanner SCANNER = new Scanner(System.in);
    public static final Random RANDOM = new Random(0);

    public static final int MAX_ARRAY_SIZE = 900000;
    public static final int MAX_BRANCH_FACTOR = 50;
    public static final long TIME_LIMIT = 3*1000;
    public static final long SUCCESSOR_FUNCTION_BOUND = 1000;

    public static final int HUNGRY_HUMAN_SCORE = 2000;
    public static final int WASTE_ENERGY_SCORE = 2;

    public static final int HIGHEST_TEMPERATURE = 50000;

    public static final int POPULATION_NUMBER = 20;
    public static final float MUTATION_POSSIBILITY = (float) 0.05;
}
