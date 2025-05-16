package com.STD22073;

import com.STD22073.enums.TransactionType;
import com.STD22073.models.Offer;
import com.STD22073.models.User;
import com.STD22073.services.OfferService;
import com.STD22073.services.TransactionService;
import com.STD22073.services.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final TransactionService transactionService = new TransactionService();
    private final OfferService offerService = new OfferService();
    private User currentUser;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        System.out.println("=== USSD OPERATEUR ===");
        System.out.print("Entrez votre numéro: ");
        String phoneNumber = scanner.nextLine();

        currentUser = userService.getUser(phoneNumber);

        if (currentUser == null) {
            createNewUser(phoneNumber);
        } else {
            authenticateUser(phoneNumber);
        }
        showMainMenu();
    }

    private void createNewUser(String phoneNumber) {
        System.out.print("Créez votre mot de passe: ");
        String password = scanner.nextLine();
        currentUser = userService.createUser(phoneNumber, password);
        System.out.println("Compte créé avec succès!");
    }

    private void authenticateUser(String phoneNumber) {
        boolean authenticated = false;
        while (!authenticated) {
            System.out.print("Mot de passe: ");
            String password = scanner.nextLine();
            currentUser = userService.authenticate(phoneNumber, password);
            if (currentUser != null) {
                authenticated = true;
            } else {
                System.out.println("Mot de passe incorrect!");
            }
        }
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("1. Mobile Money");
            System.out.println("2. Afficher mon numéro");
            System.out.println("3. Quitter");
            System.out.print("Choix: ");

            switch (getIntInput()) {
                case 1 -> showMobileMoneyMenu();
                case 2 -> displayPhoneNumber();
                case 3 -> System.exit(0);
                default -> System.out.println("Choix invalide!");
            }
        }
    }

    private void showMobileMoneyMenu() {
        while (true) {
            System.out.println("\nMOBILE MONEY:");
            System.out.println("1. Solde actuel");
            System.out.println("2. Transférer argent");
            System.out.println("3. Dépôt argent");
            System.out.println("4. Historique");
            System.out.println("5. Acheter offre");
            System.out.println("6. Retour");
            System.out.print("Choix: ");

            switch (getIntInput()) {
                case 1 -> checkBalance();
                case 2 -> transferMoney();
                case 3 -> depositMoney();
                case 4 -> showTransactionHistory();
                case 5 -> showOfferMenu();
                case 6 -> { return; }
                default -> System.out.println("Choix invalide!");
            }
        }
    }

    private void checkBalance() {

        int attempts = 3;
        boolean authenticated = false;

        while (attempts > 0 && !authenticated) {
            System.out.print("Confirmez avec votre mot de passe (" + attempts + " tentatives restantes): ");
            String password = scanner.nextLine();

            if (userService.authenticate(currentUser.getPhoneNumber(), password) != null) {
                authenticated = true;
            } else {
                attempts--;
                System.out.println("Mot de passe incorrect!");
            }
        }

        if (!authenticated) {
            System.out.println("Trop de tentatives échouées");
            return;
        }

        System.out.println("------------");
        System.out.printf("Solde actuel: %.2f MGA%n", currentUser.getBalance());
        System.out.println("------------");
    }

    private void transferMoney() {
        System.out.print("Numéro destinataire: ");
        String recipientNumber = scanner.nextLine();

        System.out.print("Montant (MGA): ");
        double amount = getDoubleInput();

        int attempts = 3;
        boolean authenticated = false;

        while (attempts > 0 && !authenticated) {
            System.out.print("Confirmez avec votre mot de passe (" + attempts + " tentatives restantes): ");
            String password = scanner.nextLine();

            if (userService.authenticate(currentUser.getPhoneNumber(), password) != null) {
                authenticated = true;
            } else {
                attempts--;
                System.out.println("Mot de passe incorrect!");
            }
        }

        if (!authenticated) {
            System.out.println("Transfert annulé: trop de tentatives échouées");
            return;
        }

        if (currentUser.getBalance() >= amount) {
            transactionService.addTransaction(
                    currentUser,
                    TransactionType.TRANSFER,
                    amount,
                    "Transfert vers " + recipientNumber
            );
            currentUser.setBalance(currentUser.getBalance() - amount);
            System.out.println("------------");
            System.out.println("Transfert réussi vers " + recipientNumber);
            System.out.println("------------");
        } else {
            System.out.println("------------");
            System.out.println("Solde insuffisant!");
            System.out.println("------------");
        }
    }
    private void depositMoney() {
        System.out.print("Montant à déposer: ");
        double amount = getDoubleInput();
        transactionService.addTransaction(currentUser, TransactionType.DEPOSIT, amount, "Dépôt cash");
        System.out.println("------------");
        System.out.println("Dépôt effectué!");
        System.out.println("------------");
    }

    private void showTransactionHistory() {
        System.out.println("\nDERNIÈRES TRANSACTIONS:");
        System.out.println("==============");
        currentUser.getTransactions().stream()
                .limit(10)
                .forEach(System.out::println);
    }

    private void showOfferMenu() {
        List<Offer> offers = offerService.getAllOffers();
        System.out.println("\nOFFRES DISPONIBLES:");
        for (int i = 0; i < offers.size(); i++) {
            Offer offer = offers.get(i);
            System.out.printf("%d. %s - %.2f MGA%n",
                    i+1, offer.getName(), offer.getPrice());
        }
        System.out.print("Choix: ");

        int choice = getIntInput();
        if (choice > 0 && choice <= offers.size()) {
            Offer selectedOffer = offers.get(choice - 1);
            if (currentUser.getBalance() >= selectedOffer.getPrice()) {
                transactionService.addTransaction(currentUser,
                        TransactionType.OFFER_PURCHASE,
                        selectedOffer.getPrice(),
                        "Achat: " + selectedOffer.getName());
                System.out.println("Offre activée!");
            } else {
                System.out.println("Solde insuffisant!");
            }
        }
    }

    private void displayPhoneNumber() {
        System.out.println("------------");
        System.out.println("Votre numéro: " + currentUser.getPhoneNumber());
        System.out.println("------------");
    }

    private int getIntInput() {
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }

    private double getDoubleInput() {
        try {
            return scanner.nextDouble();
        } finally {
            scanner.nextLine();
        }
    }
}