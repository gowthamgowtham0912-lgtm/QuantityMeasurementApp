public class QuantityMeasurement {

    private final double value;
    private final LengthUnit unit;

    // Constructor
    public QuantityMeasurement(double value, LengthUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    // Convert using enum responsibility
    public QuantityMeasurement convertTo(LengthUnit targetUnit) {
        double base = unit.toBase(value);
        double result = targetUnit.fromBase(base);
        return new QuantityMeasurement(result, targetUnit);
    }

    // UC7: Add with target unit
    public QuantityMeasurement add(QuantityMeasurement other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = this.unit.toBase(this.value);
        double base2 = other.unit.toBase(other.value);

        double sum = base1 + base2;

        double result = targetUnit.fromBase(sum);

        return new QuantityMeasurement(result, targetUnit);
    }

    // UC6: Add (default first unit)
    public QuantityMeasurement add(QuantityMeasurement other) {
        return add(other, this.unit);
    }

    // Equality check
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuantityMeasurement)) return false;

        QuantityMeasurement other = (QuantityMeasurement) obj;

        double base1 = this.unit.toBase(this.value);
        double base2 = other.unit.toBase(other.value);

        return Math.abs(base1 - base2) < 1e-6;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }

    // Main method
    public static void main(String[] args) {

        QuantityMeasurement q1 = new QuantityMeasurement(1.0, LengthUnit.FEET);
        QuantityMeasurement q2 = new QuantityMeasurement(12.0, LengthUnit.INCHES);

        // Conversion
        System.out.println(q1.convertTo(LengthUnit.INCHES));

        // Addition
        System.out.println(q1.add(q2, LengthUnit.FEET));
        System.out.println(q1.add(q2, LengthUnit.YARDS));

        // Equality
        System.out.println(
                new QuantityMeasurement(36, LengthUnit.INCHES)
                        .equals(new QuantityMeasurement(1, LengthUnit.YARDS))
        );
    }
}

// Standalone-style enum (but kept in same file)
enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    // Convert to base unit (feet)
    public double toBase(double value) {
        return value * factor;
    }

    // Convert from base unit (feet)
    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}