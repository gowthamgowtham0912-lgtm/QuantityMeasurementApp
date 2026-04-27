public class QuantityMeasurement {

    private final double value;
    private final LengthUnit unit;

    // Constructor
    public QuantityMeasurement(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid number");
        }
        this.value = value;
        this.unit = unit;
    }

    // UC5: Convert
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid number");
        }

        double base = value * source.getFactor(); // to feet
        return base / target.getFactor();
    }

    // UC6: Add (result in first operand unit)
    public QuantityMeasurement add(QuantityMeasurement other) {
        return add(other, this.unit);
    }

    // UC7: Add with target unit
    public QuantityMeasurement add(QuantityMeasurement other, LengthUnit targetUnit) {

        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        // Convert both to base unit (feet)
        double base1 = this.value * this.unit.getFactor();
        double base2 = other.value * other.unit.getFactor();

        // Add
        double sumBase = base1 + base2;

        // Convert to target unit
        double result = sumBase / targetUnit.getFactor();

        return new QuantityMeasurement(result, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }

    // Main method
    public static void main(String[] args) {

        QuantityMeasurement q1 = new QuantityMeasurement(1.0, LengthUnit.FEET);
        QuantityMeasurement q2 = new QuantityMeasurement(12.0, LengthUnit.INCHES);

        // UC7 Examples
        System.out.println(q1.add(q2, LengthUnit.FEET));    // 2 FEET
        System.out.println(q1.add(q2, LengthUnit.INCHES));  // 24 INCHES
        System.out.println(q1.add(q2, LengthUnit.YARDS));   // ~0.667 YARDS

        System.out.println(new QuantityMeasurement(1, LengthUnit.YARDS)
                .add(new QuantityMeasurement(3, LengthUnit.FEET), LengthUnit.YARDS));

        System.out.println(new QuantityMeasurement(36, LengthUnit.INCHES)
                .add(new QuantityMeasurement(1, LengthUnit.YARDS), LengthUnit.FEET));
    }
}

// Enum
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}