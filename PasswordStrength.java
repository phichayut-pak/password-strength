public class PasswordStrength {
    private int baseScore = 0;
    private int score = 0;
    private int minPasswordLength = 16;

    private int upper = 0; // A-Z
    private int lower = 0; // a-z
    private int number = 0; // 0-9
    private int symbol = 0; // !,@,#,$,%,^,&,*,?,_,~
    private int excess = 0; // excess characters after subtracting with minPasswordLength

    private int onlyLower = 0;
    private int onlyNumber = 0;
    // private int uniqueChars = 0;
    private int repetition = 0;

    private int bonusExcess = 3;
    private int bonusUpper = 4;
    private int bonusNumber = 5;
    private int bonusSymbol = 5;
    private int bonusCombo = 0;
    private int bonusOnlyLower = 0;
    private int bonusOnlyNumber = 0;
    // private int bonusUniqueChars = 0;
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
            this.onlyLower = -15;
        }

        if (this.upper == 0 && this.lower == 0 && this.number >= 1 && this.symbol == 0) {
            this.onlyNumber = -15;
        }

        if (checkRepetition(password)) {
            this.bonusRepetition = -50;
        } else {
            this.bonusRepetition = 0;
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

    private void calcComplexity() {
        this.score = this.baseScore +
                this.excess * this.bonusExcess +
                this.upper * this.bonusUpper +
                this.number * this.bonusNumber +
                this.symbol * this.bonusSymbol +
                this.bonusCombo +
                this.bonusOnlyLower +
                this.bonusOnlyNumber +
                this.bonusRepetition;
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
        return ("The Password is " + message);
    }
}
