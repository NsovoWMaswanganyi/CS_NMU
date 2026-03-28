import java.util.ArrayList;

public class Polynomial {

    /***
     * 1. What if when a person creates a Polynomial, they also provide us
     * with the degree of the polynomial. What would we do with that number?
     */

    private ArrayList<Double> coefficients;

    public Polynomial() {
        coefficients = new ArrayList<>();
    }

    public void setCoefficient(int exponent, double value) {
        ensureCoefficientSize(exponent);
        coefficients.set(exponent, value);
    }

    public double getCoefficient(int exponent) {
        if(exponent < coefficients.size()) {
            return coefficients.get(exponent);
        }
        return 0;
    }

    //A method to derive the degree of a polynomial
    public int getDegree() {
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            if(coefficients.get(i) != 0) {
                return i;
            }
        }
        return 0;
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < coefficients.size(); i++) {
            result += coefficients.get(i) * Math.pow(x, i);
        }

        return result;
    }

    public Polynomial add(double scalar,Polynomial g) {
        Polynomial result = new Polynomial();

        //Using the get degree method to check the highest degree
        //between f and g

        int highestDegree = Math.max(this.getDegree(), g.getDegree());

        for(int i = 0; i <= highestDegree; i++) {
            double fCoefficient = this.getCoefficient(i);
            double gCoefficient = g.getCoefficient(i);

            double newCoefficient = fCoefficient + scalar * gCoefficient;

            if(newCoefficient != 0) {
                result.setCoefficient(i, newCoefficient);
            }
        }

        return result;
    }

    public Polynomial derivative() {
        Polynomial result = new Polynomial();

        for (int i = 1; i < coefficients.size(); i++) {
            double newCoefficient = coefficients.get(i) * i;

            if(newCoefficient != 0) {
                result.setCoefficient(i - 1, newCoefficient);
            }
        }

        return result;
    }

    @Override
    public String toString() {

        if (getDegree() == 0 && getCoefficient(0) == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = getDegree(); i >= 0; i--) {

            double coef = getCoefficient(i);

            if (coef == 0) continue;

            if (!sb.isEmpty()) {
                if (coef > 0) {
                    sb.append(" + ");
                } else {
                    sb.append(" - ");
                    coef = Math.abs(coef);
                }
            } else if (coef < 0) {
                sb.append("-");
                coef = Math.abs(coef);
            }

            if (i == 0) {
                sb.append(coef);
            } else if (i == 1) {
                sb.append(coef).append("x");
            } else {
                sb.append(coef).append("x^").append(i);
            }
        }

        return sb.toString();
    }

    //A method that ensures that exponents and coefficients aren't
    //out of sync
    private void ensureCoefficientSize(int exponent) {
        while (coefficients.size() <= exponent) {
            coefficients.add(0.0);
        }
    }


//    int polynomialDegree;
//    ArrayList<Integer> exponents;
//    ArrayList<Double> coefficients;
//
//    Polynomial(int polynomialDegree) {
//        this.polynomialDegree = polynomialDegree;
//        exponents = new ArrayList<>();
//        coefficients = new ArrayList<>();
//
//        for (int i = 1; i <= polynomialDegree; i++) {
//            exponents.add(i);
//
//        }
//    }
//
//    public void setCoefficient(int coefficientNumber, double coefficient) {
//        for (int i = 0; i <= coefficients.size(); i++) {
//            if (i == coefficientNumber) {
//                coefficients.add(coefficient);
//                break;
//            }
//        }
//    }
//
//    public double getCoefficient(int coefficientNumber) {
//
//        for (int i = 0; i <= coefficientNumber; i++) {
//            if (i == coefficientNumber) {
//                return coefficients.get(i);
//            }
//        }
//
//        return 0;
//    }
//
//    public double evaluate(double x) {
//
//        double expoResult = 1;
//        double evalSum = coefficients.getFirst();
//
//        for (int i = 1; i <= polynomialDegree; i++) {
//            double coefficient = coefficients.get(i);
//
//            if(i > 1) {
//
//                for (int j = 1; j <= i; j++) {
//                    expoResult *= x;
//                }
//                evalSum += coefficient*expoResult;
//            } else {
//                evalSum = evalSum + (coefficient*x);
//            }
//            expoResult = 1;
//        }
//        return evalSum;
//    }
//
//    public Polynomial add(double x, Polynomial g) {
//        ArrayList<Double> newCoefficients = new ArrayList<>();
//        Polynomial h = new Polynomial(polynomialDegree);
//
//        for (int i = 1; i <= g.polynomialDegree; i++) {
//
//        }
//
//        return null;
//    }
//
//    public Polynomial derivative(Polynomial g) {
//        return null;
//    }
//
//
//    @Override
//    public String toString() {
//        StringBuilder result = new StringBuilder();
//
//        for (int i = 0; i < polynomialDegree; i++) {
//
//            double currCoefficient = coefficients.get(i);
//            double currExponent = exponents.get(i);
//
//            result.append(currCoefficient);
//
//            if(currCoefficient != 0){
//                result.append(" x^ ").append(currExponent);
//            }
//
//        }
//
//        return result.toString();
//    }
}
