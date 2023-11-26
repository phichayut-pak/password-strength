import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        System.out.println(
                """
                        ______  ___   _____ _____  _    _  _________________   _____ ___________ _____ _   _ _____ _____ _   _
                        | ___ \\/ _ \\ /  ___/  ___|| |  | ||  _  | ___ \\  _  \\ /  ___|_   _| ___ \\  ___| \\ | |  __ \\_   _| | | |
                        | |_/ / /_\\ \\\\ `--.\\ `--. | |  | || | | | |_/ / | | | \\ `--.  | | | |_/ / |__ |  \\| | |  \\/ | | | |_| |
                        |  __/|  _  | `--. \\`--. \\| |/\\| || | | |    /| | | |  `--. \\ | | |    /|  __|| . ` | | __  | | |  _  |
                        | |   | | | |/\\__/ /\\__/ /\\  /\\  /\\ \\_/ / |\\ \\| |/ /  /\\__/ / | | | |\\ \\| |___| |\\  | |_\\ \\ | | | | | |
                        \\_|   \\_| |_/\\____/\\____/  \\/  \\/  \\___/\\_| \\_|___/   \\____/  \\_/ \\_| \\_\\____/\\_| \\_/\\____/ \\_/ \\_| |_/
                                                                                                                By Pak and Beam

                        """);

        Scanner s = new Scanner(System.in);
        String password = "";
        while (true) {
            System.out.print("Please insert your password ( type 'exit' to quit ): ");
            password = s.nextLine();

            if (password.toLowerCase().equals("exit")) {
                break;
            }

            PasswordStrength user = new PasswordStrength(password);
            System.out.println(user.checkBirthday(password));

        }

        s.close();
        System.out.println("Thank you for using our program.");

    }
}