public class Palindrome {
    Stack<Character> stack = new Stack<>();
    public boolean isPalindrome(String clause) {

        String temp = "";
        clause = clause.toLowerCase();

        for (int i = 0; i < clause.length(); i++) {
            if (clause.charAt(i) != ' ') {
                temp += clause.charAt(i);
                stack.push(clause.charAt(i));
            }
        }

        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) != stack.pop()) {
                return false;
            }
        }
        return true;
    }

    public void checkPalindrome(String clause) {
        System.out.println("'" + clause + "' is a palindrome? "
                + isPalindrome(clause));
    }

    public Palindrome() {
        checkPalindrome("hello");
        checkPalindrome("Madmam");
        checkPalindrome("madam");
        checkPalindrome("Madam in Eden Im Adam");
    }

    public static void main(String[] args) {
        new Palindrome();
    }
}
