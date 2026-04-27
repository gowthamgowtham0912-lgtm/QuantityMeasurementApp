public class QuantityMeasurement{

    // Enum with all units (base unit = FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084); // 1 cm = 0.0328084 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Generic QuantityLength class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        public double toBaseUnit() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double EPSILON = 0.0001;
            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
        }

        @Override
        public int hashCode() {
            return Double.valueOf(toBaseUnit()).hashCode();
        }
    }

    // Main method (demo + test cases)
    public static void main(String[] args) {

        // Basic tests
        System.out.println("1 foot == 12 inches: " +
                new QuantityLength(1.0, LengthUnit.FEET)
                        .equals(new QuantityLength(12.0, LengthUnit.INCH))); // true

        // Yard tests
        System.out.println("1 yard == 3 feet: " +
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET))); // true

        System.out.println("1 yard == 36 inches: " +
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(36.0, LengthUnit.INCH))); // true

        // Centimeter tests
        System.out.println("1 cm == 0.393701 inches: " +
                new QuantityLength(1.0, LengthUnit.CENTIMETERS)
                        .equals(new QuantityLength(0.393701, LengthUnit.INCH))); // true

        // Same unit equality
        System.out.println("2 yards == 2 yards: " +
                new QuantityLength(2.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(2.0, LengthUnit.YARDS))); // true

        // Different values
        System.out.println("1 yard != 2 feet: " +
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(2.0, LengthUnit.FEET))); // false

        // Transitive property
        QuantityLength a = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength b = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength c = new QuantityLength(36.0, LengthUnit.INCH);

        System.out.println("Transitive (yard == feet && feet == inch): " +
                (a.equals(b) && b.equals(c) && a.equals(c))); // true

        // Null comparison
        System.out.println("Compare with null: " +
                new QuantityLength(1.0, LengthUnit.FEET).equals(null)); // false

        // Same reference
        QuantityLength q = new QuantityLength(5.0, LengthUnit.FEET);
        System.out.println("Same reference: " + q.equals(q)); // true
    }
}