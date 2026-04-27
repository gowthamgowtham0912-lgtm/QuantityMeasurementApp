public class QuantityMeasurement {

    // ================= LENGTH =================
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
            this.value = value;
            this.unit = unit;
        }

        public QuantityLength convertTo(LengthUnit target) {
            double base = unit.toBase(value);
            return new QuantityLength(target.fromBase(base), target);
        }

        public QuantityLength add(QuantityLength other, LengthUnit target) {
            double sum = unit.toBase(value) + other.unit.toBase(other.value);
            return new QuantityLength(target.fromBase(sum), target);
        }

        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof QuantityLength)) return false;
            QuantityLength o = (QuantityLength) obj;
            return Math.abs(unit.toBase(value) - o.unit.toBase(o.value)) < 1e-6;
        }

        public String toString() {
            return "Length(" + value + ", " + unit + ")";
        }
    }

    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor;

        LengthUnit(double factor) { this.factor = factor; }

        public double toBase(double v) { return v * factor; }      // feet
        public double fromBase(double b) { return b / factor; }
    }

    // ================= WEIGHT =================
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
            this.value = value;
            this.unit = unit;
        }

        public QuantityWeight convertTo(WeightUnit target) {
            double base = unit.toBase(value);
            return new QuantityWeight(target.fromBase(base), target);
        }

        public QuantityWeight add(QuantityWeight other, WeightUnit target) {
            double sum = unit.toBase(value) + other.unit.toBase(other.value);
            return new QuantityWeight(target.fromBase(sum), target);
        }

        public QuantityWeight add(QuantityWeight other) {
            return add(other, this.unit);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof QuantityWeight)) return false;
            QuantityWeight o = (QuantityWeight) obj;
            return Math.abs(unit.toBase(value) - o.unit.toBase(o.value)) < 1e-6;
        }

        public String toString() {
            return "Weight(" + value + ", " + unit + ")";
        }
    }

    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) { this.factor = factor; }

        public double toBase(double v) { return v * factor; }      // kg
        public double fromBase(double b) { return b / factor; }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        // Length
        QuantityLength l1 = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12, LengthUnit.INCHES);

        System.out.println(l1.add(l2));                 // 2 feet
        System.out.println(l1.convertTo(LengthUnit.INCHES)); // 12 inches

        // Weight
        QuantityWeight w1 = new QuantityWeight(1, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000, WeightUnit.GRAM);

        System.out.println(w1.equals(w2));              // true
        System.out.println(w1.add(w2));                 // 2 kg
        System.out.println(w1.convertTo(WeightUnit.POUND)); // ~2.20462 lb
    }
}