public class WellFormed {

    public boolean wellFormed(String expr) {
        Stack<Character> stack = new Stack<>();
        char cur;

        for (int i = 0; i < expr.length(); i++) {
            switch (expr.charAt(i)) {
                case '(':
                case '[':
                case '{':
                    stack.push(expr.charAt(i));
                    break;
                case ')':
                    if (stack.isEmpty()) return false;
                    cur = stack.pop();
                    if (cur != '(') return false;
                    break;
                case ']':
                    if (stack.isEmpty()) return false;
                    cur = stack.pop();
                    if (cur != '[') return false;
                    break;
                case '}':
                    if (stack.isEmpty()) return false;
                    cur = stack.pop();
                    if (cur != '{') return false;
                    break;
            }
        }

        return stack.isEmpty();
    }

    public void checkIfWellFormed(String expr) {
        System.out.println("'" + expr + "' is well formed? " + wellFormed(expr));
    }

    public WellFormed() {
        checkIfWellFormed("{6+[4-(6+7)-3+(4+2)]-3*4}");
        checkIfWellFormed("{6+[4-(6+7-3+(4+2))]-3*4}");
        checkIfWellFormed("{6+[4-(6+7-3+(4+2)]-3*4})");
        checkIfWellFormed("6+[4-(6+7-3+(4+2))]-3*4}");
    }

    public static void main(String[] args) {
        new WellFormed();
    }
}
