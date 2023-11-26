public class PasswordStrength {
    private int baseScore = 0;
    private int score = 0;
    private int minPasswordLength = 16;

    private int upper = 0; // A-Z
    private int lower = 0; // a-z
    private int number = 0; // 0-9
    private int symbol = 0; // !,@,#,$,%,^,&,*,?,_,~
    private int excess = 0; // excess characters after subtracting with minPasswordLength
    private int uniqueChars = 0;

    private int bonusExcess = 3;
    private int bonusUpper = 4;
    private int bonusNumber = 5;
    private int bonusSymbol = 5;
    private int bonusCombo = 0;
    private int bonusOnlyLower = 0;
    private int bonusOnlyNumber = 0;
    private int bonusUniqueChars = 0;
    private int bonusRepetition = 0;

    public PasswordStrength(String password) {
        if (password.length() >= minPasswordLength) {
            this.baseScore = 50;
            this.analyzeString(password);
            this.calcComplexity();
        } else {
            this.baseScore = 0;
        }
    }

    private boolean inRange(char c, int min, int max) {
        return (c >= min && c <= max);
    }

    private void analyzeString(String password) {
        for (int i = 0; i < password.length(); i++) {

            if (inRange(password.charAt(i), 33, 126)) {
                if (inRange(password.charAt(i), 97, 122)) {
                    this.lower++;
                }

                else if (inRange(password.charAt(i), 65, 90)) {
                    this.upper++;
                }

                else if (inRange(password.charAt(i), 48, 57)) {
                    this.number++;
                }

                else if (password.charAt(i) == 33 || inRange(password.charAt(i), 35, 38) || password.charAt(i) == 63
                        || password.charAt(i) == 64 || password.charAt(i) == 94 || password.charAt(i) == 95
                        || password.charAt(i) == 126) {
                    this.symbol++;
                }
            }
        }

        this.excess = password.length() - minPasswordLength;

        if (this.upper + this.number + this.symbol >= 3) {
            this.bonusCombo = 25;
        } else if ((this.upper + this.number >= 1) ||
                (this.upper + this.symbol >= 1) ||
                (this.number + this.symbol >= 1)) {
            this.bonusCombo = 15;
        }

        if (this.upper == 0 && this.lower >= 1 && this.number == 0 && this.symbol == 0) {
            this.bonusOnlyLower = -15;
        }

        if (this.upper == 0 && this.lower == 0 && this.number >= 1 && this.symbol == 0) {
            this.bonusOnlyNumber = -15;
        }

        if (checkRepetition(password)) {
            this.bonusRepetition = -50;
        } else {
            this.bonusRepetition = 0;
        }

        this.uniqueChars = checkUniqueChars(password);

        if (this.uniqueChars <= 3) {
            this.bonusUniqueChars = -10;
        } else if (this.uniqueChars >= 3 && this.uniqueChars < 6) {
            this.bonusUniqueChars = -5 * (36 - this.uniqueChars * this.uniqueChars);
        }
    }

    // checks if there are more than or equal to 3 repeated characters in a password
    private boolean checkRepetition(String password) {
        if (password == null || password.length() < 3) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            int count = 0;

            for (int j = 0; j < password.length(); j++) {
                if (password.charAt(j) == currentChar) {
                    count++;
                    if (count >= 3) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public int checkUniqueChars(String password) {
        String lowerPassword = password.toLowerCase();

        for (int i = 0; i < lowerPassword.length(); i++) {
            char currentChar = lowerPassword.charAt(i);

            for (int j = i + 1; j < lowerPassword.length(); j++) {
                if (lowerPassword.charAt(j) == currentChar) {
                    lowerPassword = lowerPassword.substring(0, j) + lowerPassword.substring(j + 1);
                }
            }

        }

        return lowerPassword.length();

    }

    private void calcComplexity() {
        this.score = this.baseScore +
                this.excess * this.bonusExcess +
                this.upper * this.bonusUpper +
                this.number * this.bonusNumber +
                this.symbol * this.bonusSymbol +
                this.bonusCombo +
                this.bonusOnlyLower +
                this.bonusOnlyNumber +
                this.bonusRepetition +
                this.bonusUniqueChars;
    }

    public String toString() {
        String message = "";

        if (score < 25) {
            message = "Very Weak!";
        } else if (score >= 25 && score < 50) {
            message = "Weak!";
        } else if (score >= 50 && score < 75) {
            message = "Average!";
        } else if (score >= 75 && score < 100) {
            message = "Strong!";
        } else if (score >= 100) {
            message = "Secure!";
        }
        return ("The Password is " + message + "\n" +
                "=================================" + "\n" +
                "Score Breakdown" + "\n" +
                "Base Score: " + this.baseScore + "\n" +
                "Length Bonus: " + (this.excess * this.bonusExcess) + "[" + this.excess + " x " + this.bonusExcess + "]"
                + "\n" +
                "Upper Case Bonus: " + (this.upper * this.bonusUpper) + "[" + this.upper + " x " + this.bonusUpper + "]"
                + "\n" +
                "Number Bonus: " + (this.number * this.bonusNumber) + "[" + this.number + " x " + this.bonusNumber + "]"
                + "\n" +
                "Symbol Bonus: " + (this.symbol * this.bonusSymbol) + "[" + this.symbol + " x " + this.bonusSymbol + "]"
                + "\n" +
                "Combination Bonus: " + this.bonusCombo + "\n" +
                "Lower case only penalty: " + this.bonusOnlyLower + "\n" +
                "Numbers only penalty: " + this.bonusOnlyNumber + "\n" +
                "Repeating pattern only penalty: " + this.bonusRepetition + "\n" +
                "Total Score: " + this.score);
    }
}
