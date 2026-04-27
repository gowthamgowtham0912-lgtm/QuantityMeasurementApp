public class QuantityMeasurement {

    // Convert method
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid number");
        }

        // Convert to base unit (feet)
        double baseValue = value * source.getFactor();

        // Convert to target unit
        return baseValue / target.getFactor();
    }

    // Main method for testing
    public static void main(String[] args) {

        System.out.println(convert(1.0, LengthUnit.FEET, LengthUnit.INCHES)); // 12
        System.out.println(convert(3.0, LengthUnit.YARDS, LengthUnit.FEET)); // 9
        System.out.println(convert(36.0, LengthUnit.INCHES, LengthUnit.YARDS)); // 1
        System.out.println(convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES)); // ~1
    }
}

// Enum in same file
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