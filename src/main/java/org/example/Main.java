package org.example;

import org.example.data.UserDb;
import org.example.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean running = true;

    public static void main(String[] args) {
        try {
            while (running) {
                displayMenu();
                int choice = getUserChoice();
                processChoice(choice);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void displayMenu() {
        System.out.println("\n===== User Management System =====");
        System.out.println("1. Show all users");
        System.out.println("2. Add new user");
        System.out.println("3. View user details");
        System.out.println("4. Update user");
        System.out.println("5. Delete user");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return choice;
    }

    private static void processChoice(int choice) throws SQLException {
        switch (choice) {
            case 0:
                System.out.println("Exiting program. Goodbye!");
                running = false;
                break;
            case 1:
                showAllUsers();
                break;
            case 2:
                addNewUser();
                break;
            case 3:
                viewUserDetails();
                break;
            case 4:
                updateUser();
                break;
            case 5:
                deleteUser();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void showAllUsers() throws SQLException {
        System.out.println("\n----- User List -----");
        List<User> users = UserDb.findAll();
        System.out.println("Total users: " + users.size());

        for (User user : users) {
            System.out.println(user.getUserId() + " " +
                    user.getFirstName() + " " +
                    user.getLastName() + " " +
                    user.getEmail());
        }

        pressEnterToContinue();
    }

    private static void addNewUser() throws SQLException {
        System.out.println("\n----- Add New User -----");
        User newUser = new User();

        System.out.print("Enter first name: ");
        newUser.setFirstName(scanner.nextLine());

        System.out.print("Enter last name: ");
        newUser.setLastName(scanner.nextLine());

        System.out.print("Enter email: ");
        newUser.setEmail(scanner.nextLine());

        UserDb.insert(newUser);
        System.out.println("User added successfully!");

        pressEnterToContinue();
    }

    private static void viewUserDetails() throws SQLException{
        System.out.println("\n---- View User Details ----");
        System.out.println("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = UserDb.findById(userId);
        if (user != null) {
            System.out.println("\n User Details:");
            System.out.println("ID: " + user.getUserId());
            System.out.println("First name: " + user.getFirstName());
            System.out.println("Last name: " + user.getLastName());
            System.out.println("Email: " + user.getEmail());
        } else {
            System.out.println("User not found with ID: " + userId);
        }
        pressEnterToContinue();

    }

    private static void updateUser() throws SQLException{
        System.out.println("\n---- Update User ----");
        System.out.print("Enter user ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User existingUser = UserDb.findById(userId);
        if (existingUser == null) {
            System.out.print("User not found with ID: " + userId);
            pressEnterToContinue();
            return;
        }

        User updatedUser = new User();
        updatedUser.setUserId(userId);

        System.out.print("Enter new first name (current: " + existingUser.getFirstName() + "): ");
        updatedUser.setFirstName(scanner.nextLine());

        System.out.print("Enter new last name (current: " + existingUser.getLastName() + "): ");
        updatedUser.setLastName(scanner.nextLine());

        System.out.print("Enter new email (current: " + existingUser.getEmail() + "): ");
        updatedUser.setEmail(scanner.nextLine());

        boolean success = UserDb.update(updatedUser);
        if(success) {
            System.out.println("User updated successfully");
        } else {
            System.out.println("Failed to update user.");
        }
        pressEnterToContinue();

    }

    private static void deleteUser() throws SQLException{
        System.out.println("\n---- Delete User ----");
        System.out.print("Enter user ID to Delete");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Are you sure you want to delete this user? (y/n)");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            boolean success = UserDb.delete(userId);
            if (success) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Failed to delete user or user not found.");
            }
        } else {
                System.out.println("Deleting cancelled");
            }
            pressEnterToContinue();
            }

    private static void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}