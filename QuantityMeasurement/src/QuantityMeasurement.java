public class QuantityMeasurement{

    // Enum for units
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // QuantityLength class
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

            // Compare using tolerance for double precision
            double EPSILON = 0.0001;
            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
        }

        @Override
        public int hashCode() {
            return Double.valueOf(toBaseUnit()).hashCode();
        }
    }

    // Main method (acts like demo + simple tests)
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q3 = new QuantityLength(2.0, LengthUnit.FEET);
        QuantityLength q4 = new QuantityLength(1.0, LengthUnit.INCH);

        System.out.println("1 foot == 12 inches: " + q1.equals(q2)); // true
        System.out.println("1 foot == 2 feet: " + q1.equals(q3));    // false
        System.out.println("1 inch == 1 inch: " + q4.equals(new QuantityLength(1.0, LengthUnit.INCH))); // true
        System.out.println("Same reference: " + q1.equals(q1)); // true
        System.out.println("Compare with null: " + q1.equals(null)); // false
    }
}