import java.util.Scanner;

public class LogInPage {
    public static void main(String[] args) {

        int choice = 1;
        String un, pw;
        User u = new User();
        Scanner Scan = new Scanner(System.in);

        while (choice != 0) {
            if (choice == 1) {
                u.CreateAccount();
            } else if (choice == 2) {
                System.out.println("Plaese enter username: ");
                un = Scan.nextLine();
                System.out.println("Please enter your password");
                pw = Scan.nextLine();
                u.logIn(un, pw);
            }
        }
        Scan.close();
    }
}