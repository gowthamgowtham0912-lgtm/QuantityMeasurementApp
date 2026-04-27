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

    // Convert method (UC5)
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid number");
        }

        double baseValue = value * source.getFactor(); // to feet
        return baseValue / target.getFactor();
    }

    // Add method (UC6)
    public QuantityMeasurement add(QuantityMeasurement other) {
        if (other == null) {
            throw new IllegalArgumentException("Other value cannot be null");
        }

        // Convert both to base unit (feet)
        double base1 = this.value * this.unit.getFactor();
        double base2 = other.value * other.unit.getFactor();

        // Add
        double sumBase = base1 + base2;

        // Convert back to first object's unit
        double resultValue = sumBase / this.unit.getFactor();

        return new QuantityMeasurement(resultValue, this.unit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }

    // Main method
    public static void main(String[] args) {

        QuantityMeasurement q1 = new QuantityMeasurement(1.0, LengthUnit.FEET);
        QuantityMeasurement q2 = new QuantityMeasurement(12.0, LengthUnit.INCHES);

        QuantityMeasurement result = q1.add(q2);

        System.out.println(result); // Quantity(2.0, FEET)

        // More examples
        System.out.println(new QuantityMeasurement(1, LengthUnit.YARDS)
                .add(new QuantityMeasurement(3, LengthUnit.FEET)));

        System.out.println(new QuantityMeasurement(12, LengthUnit.INCHES)
                .add(new QuantityMeasurement(1, LengthUnit.FEET)));
    }
}

// Enum (same file)
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