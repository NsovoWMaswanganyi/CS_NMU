
public class Program {

    Stack<Float> stack = new Stack<>();
    public static void main(String[] args) {
        new Program();
    }

    public Program() {


        rpnCalculator("1 2 3 + +");
        rpnCalculator("1 2 3 * +");
        rpnCalculator("1 2 + 3 *");
        rpnCalculator("5 1 2 + 4 * + 3 -");
        rpnCalculator("3 4 5 * -");
        rpnCalculator("6 2 / 3 +");

    }

    void rpnCalculator(String expression) {
        System.out.println("Using a Stack");
        display(rpnStack(expression));

        System.out.println("Using Recursion");
        display(rpnRecursive(expression));
        System.out.println();
    }

    void display(float result) {
        System.out.println(result);
    }

    public float rpnStack(String expression) {

        for (int i = 0; i < expression.length(); i++) {

            char character = expression.charAt(i);
            if(Character.isWhitespace(character)) continue;

            if(Character.isDigit(character)) {
                stack.push(Float.parseFloat(Character.toString(character)));
            } else {
                float result=0;
                float b = Float.parseFloat(stack.pop().toString());
                float a = Float.parseFloat(stack.pop().toString());

                result = switch (character) {
                    case '+' -> a + b;
                    case '-' -> a - b;
                    case '*' -> a * b;
                    case '/' -> a / b;
                    default -> result;
                };
                stack.push(result);
            }
        }
        return stack.pop();
    }

    public float rpnRecursive(String expression) {
        Stack<Float> stack = new Stack<>();
        return rpnHelper(expression.trim().split("\\s+"), 0, stack);
    }

    private float rpnHelper(String[] tokens, int index, Stack<Float> stack) {
        //Base case: if the stack is empty
        if (index >= tokens.length) {
            return stack.pop();
        }

        String token = tokens[index];

        if (isOperator(token)) {
            float b = stack.pop();
            float a = stack.pop();
            float result = applyOperator(a, b, token.charAt(0));
            stack.push(result);
        } else {
            stack.push(Float.parseFloat(token));
        }

        return rpnHelper(tokens, index + 1, stack);
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private float applyOperator(float a, float b, char op) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new IllegalArgumentException("Invalid operator: " + op);
        };
    }

}
