import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class PasswordChecker {

    private String password;
    private int score;

    public PasswordChecker(String password) {
        this.password = password;
    }

    public int checkLength() {
        return password.length();
    }

    public boolean checkCombination() {
        int countInteger = 0;
        int countUpper = 0;
        int countLower = 0;
        int countSpecial = 0;

        for (int i = 0; i < password.length(); i++) {

            if (checkRange(password.charAt(i), 33, 126)) {
                if (password.charAt(i) >= 97 && password.charAt(i) <= 122) {
                    countLower++;
                }

                else if (password.charAt(i) >= 65 && password.charAt(i) <= 90) {
                    countUpper++;
                }

                else if (password.charAt(i) >= 48 && password.charAt(i) <= 57) {
                    countInteger++;
                }

                else {
                    countSpecial++;
                }
            }
        }

        return (countSpecial + countInteger + countUpper + countLower >= 4);

    }

    private boolean checkRange(char c, int min, int max) {
        return (c >= min && c <= max);
    }

    public boolean checkConsecutive() {

        if (this.password.length() <= 1) {
            return false;
        }

        char comparedLetter = this.password.charAt(0);

        for (int i = 1; i < this.password.length(); i++) {
            char selectedLetter = this.password.charAt(i);
            if (comparedLetter == selectedLetter) {
                return true;
            }
            comparedLetter = selectedLetter;
        }

        return false;
    }

    public boolean checkCommon() {
        File passList = new File("common_words.txt");
        boolean match = false;
        try (Scanner x = new Scanner(passList)) {
            x.useDelimiter(" ");
            while (x.hasNext()) {
                if (password.contains(x.next())) {
                    match = true;
                    break;
                }
            }
        } catch (IOException mem) {
            mem.printStackTrace();
        }

        return match;

    }
}

// 1. A password should be 16 characters or more
// 2. A password should include a combination of letters, numbers, and
// characters. ( upper / lower )
// 3. A password shouldn’t contain any consecutive letters or numbers.
// 4. A password shouldn’t be the word “password” or the same letter or number
// repeated.
// 5.