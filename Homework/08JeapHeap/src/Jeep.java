package src;

class Jeep implements Comparable<Jeep> {

    private final String name;
    private final float weight, horsepower;
    private static final String DEFAULT_NAME = "JeepyMcJeepFace";
    private static final float DEFAULT_WEIGHT = 6431.3114f; // f for FATTY

    Jeep(float horsepower) {
        this(DEFAULT_NAME, DEFAULT_WEIGHT, horsepower);
    }

    Jeep (String givenName, float givenWeight, float givenHorsepower) {
        name = givenName;
        weight = givenWeight;
        horsepower = givenHorsepower;
    }

    @Override
    public String toString() {
        return "Jeep{" +
            "name='" + name + '\'' +
            ", weight=" + weight +
            ", horsepower=" + horsepower +
            '}';
    }

    @Override
    public int compareTo(Jeep jeep) {
        return (int) (horsepower - jeep.horsepower) * 10000;
    }
}