import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

public class MiniBank {
    private Scanner sc;
    private Map<String, User> users;

    public MiniBank() {
        this.users = new HashMap<>();
        sc = new Scanner(System.in);
    }


    public static boolean person_num(String personnummer) {
        String siffrorStr = personnummer.replace("-", "").substring(0, 9);
        int summa = 0;

        boolean multipliceraMedTva = true;
        for (int i = 0; i < siffrorStr.length(); i++) {
            int siffra = Character.getNumericValue(siffrorStr.charAt(i));

            if (multipliceraMedTva) {
                siffra *= 2;
            }

            if (siffra > 9) {
                summa += (siffra / 10) + (siffra % 10);
            } else {
                summa += siffra;
            }

            multipliceraMedTva = !multipliceraMedTva;
        }

        int tial = (summa + 9) / 10 * 10;  // Nästa högre tiotal
        int kontrollsiffra = tial - summa;

        int angivenKontrollsiffra = Character.getNumericValue(personnummer.charAt(10));

        return kontrollsiffra == angivenKontrollsiffra;
    }


    public void menu() {
        boolean active = true;
        while (active) {
            System.out.println("\n -- Välkommen till Mini-Bank Huvudmeny--");
            System.out.println("1. Skapa användare");
            System.out.println("2. Logga in ");
            System.out.println("3. Avsluta");
            System.out.println("Välj ett av fyra alternativ: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    createUser();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    System.out.println("Tack för du valde mini-bank.\n");
                    System.out.println("Välkommen åter");
                    active = false;
                    break;
                default:
                    System.out.println("Valet finns inte med försök igen tack!");
            }

        }
    }

    private boolean incorrectPin(String pin) {
        return pin.length() != 4 && !pin.matches(("\\d+"));
    }

    private void createUser() {
        System.out.println("Ange personnumer: ");
        String persNum = sc.nextLine();

        if (users.containsKey(persNum)) {
            System.out.println(persNum + " finns redan reggat vid banken försök igen!");
            return;
        }

        if (person_num(persNum)) {
            System.out.println("Ange pin");
            String pin = sc.nextLine();
            if (!incorrectPin(pin)) {
                users.put(persNum, new User(persNum, pin));
                System.out.println("Användar-konton skapades framgångsrikt");
            }
        } else {
            System.out.println("Person-numret eller Pin-koden är ogilitiga!");

        }


    }

    private void login() {
        System.out.println("Ange personnummer: ");
        String personalNum = sc.nextLine();
        User user = users.get(personalNum);
        if (user == null) {
            System.out.println("Användaren existerar inte: ");
            return;
        }
        int atem = 4;
        while (atem > 0) {
            System.out.println("Vänligen ange pin: ");
            String pin = sc.nextLine();
            if (user.verifyPin(pin)) {
                System.out.println("PIN-koden är godkänd");
                userM(user);
                return;
            } else {
                atem = atem - 1;
                System.out.println("Fel svar vänligen försök igen");
            }
        }
        System.out.println("Du har bara 4x försök på dig. Nu avslutas programmet!");
    }

    private void userM(User user) {
        boolean active = true;
        while (active) {
            System.out.println("\n-- Mina sidor --");
            System.out.println("1. Visa mina existerande-konton");
            System.out.println("2. Gör en transaktion");
            System.out.println("3. Logga ut ");
            System.out.println("Välj ett av de tre alternativen: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    showAccounts(user);
                    break;
                case "2":
                    makeTrans(user);
                    break;
                case "3":
                    active = false;
                    System.out.println("Du loggas ut nu ifrån banken!");
                    break;
                default:
                    System.out.println("Ditt val är ogiltigt gör ett nytt försök!");
            }
        }
    }

    private void showAccounts(User user) {
        System.out.println("\n-- Dina konton har följande belopp");
        System.out.println("Löne-konto " + user.getSalary().getAccountNum() + " " + user.getSalary().getBalance_amount());
        System.out.println("Spar-konto " + user.getSaving().getAccountNum() + " " + user.getSaving().getBalance_amount());
    }

    public void makeTrans(User user) {
        System.out.println("\n-- Överföring");
        System.out.println("1. Från Löne-konto till Spar-konto");
        System.out.println("2. Från Spar-konto till Löne-konto");
        System.out.println("Välj ett av de två alternativen: ");
        String choice_input = sc.nextLine();
        int choice;
        try {
            choice = sc.nextLine();
        } catch (NumberFormatException e) {
            System.out.println("Ogiltig inmatning");
            return;
        }
        if (choice != 1 && choice != 2) {
            System.out.println("Ogilitigt försök");
            return;
        }
        System.out.println("Ange belopp att överföra: ");
        double amount;
        try {
            amount = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ogilitgt belopp");
            return;
        }

        if (choice == 1) {
            if (user.getSalary().removeFromAccount(amount)) {
                user.getSaving().addToAccount(amount);
                System.out.println("Överföringen lyckades");
            } else {
                System.out.println("Överföringen misslyckades. Kolla ditt saldo");
            }
            if (choice == 2) {
                if (user.getSaving().removeFromAccount(amount)) {
                    user.getSalary().addToAccount(amount);
                    System.out.println("Överföringen lyckades");
                } else {
                    System.out.println("Överföringen misslyckades");
                }
                System.out.println("Valet e ogiltigt");
            }

        }
    }
}
