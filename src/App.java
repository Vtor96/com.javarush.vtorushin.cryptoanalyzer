import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Выберите режим: ");
        System.out.println("1 - шифрование");
        System.out.println("2 - дешифрование");
        System.out.println("3 - brute force");
        Scanner sc = new Scanner(System.in);
        int mode = sc.nextInt();
        sc.nextLine();
    }
}
