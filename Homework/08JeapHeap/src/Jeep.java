import java.util.Objects;

class Jeep {

    final String name;
    final float weight, horsepower;

    Jeep (String givenName, float givenWeight, float givenHorsepower) {
        name = givenName;
        weight = givenWeight;
        horsepower = givenHorsepower;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Jeep{" +
            "name='" + name + '\'' +
            ", weight=" + weight +
            ", horsepower=" + horsepower +
            '}';
    }

    public float getWeight() {
        return weight;
    }

    public float getHorsepower() {
        return horsepower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jeep jeep = (Jeep) o;
        return Float.compare(jeep.getWeight(), getWeight()) == 0 &&
            Float.compare(jeep.getHorsepower(), getHorsepower()) == 0 &&
            Objects.equals(getName(), jeep.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getWeight(), getHorsepower());
    }
}
