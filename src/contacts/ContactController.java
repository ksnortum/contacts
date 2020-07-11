package contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactController {
    private final List<Contact> phoneBook = new ArrayList<>();

    void run() {
        boolean thereIsMoreToDo = true;

        do {
            String action = Prompter.nextString("Enter action (add, remove, edit, count, list, exit): ");

            switch (action) {
                case "add":
                    addAContact();
                    break;
                case "remove":
                    removeAContact();
                    break;
                case "edit":
                    editAContact();
                    break;
                case "count":
                    countPhoneBook();
                    break;
                case "list":
                    listPhoneBook();
                    break;
                case "exit":
                    thereIsMoreToDo = false;
                    break;
                default:
                    System.out.println("Invalid action");
            }
        } while(thereIsMoreToDo);
    }

    private void addAContact() {
        String firstName = Prompter.nextString("Enter the name: ");
        String lastName = Prompter.nextString("Enter the surname: ");
        String phoneNumber = Prompter.nextString("Enter the number: ");
        Contact contact = new Contact(firstName, lastName, phoneNumber);
        phoneBook.add(contact);
        System.out.println("The record added.");
    }

    private void removeAContact() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to remove!");
            return;
        }

        listPhoneBook();
        int index = Prompter.nextInt("Select a record: ");
        phoneBook.remove(index - 1);
        System.out.println("The record removed!");
    }

    private void editAContact() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to edit!");
            return;
        }

        listPhoneBook();
        int index = Prompter.nextInt("Select a record: ");
        String field = Prompter.nextString("Select a field (name, surname, number): ");

        switch (field) {
            case "name":
                String firstName = Prompter.nextString("Enter name: ");
                phoneBook.get(index - 1).setFirstName(firstName);
                break;
            case "surname":
                String lastName = Prompter.nextString("Enter surname: ");
                phoneBook.get(index - 1).setLastName(lastName);
                break;
            case "number":
                String phoneNumber = Prompter.nextString("Enter number: ");
                phoneBook.get(index - 1).setPhoneNumber(phoneNumber);
                break;
            default:
                System.out.println("Invalid field");
                return;
        }

        System.out.println("The record updated!");
    }

    private void countPhoneBook() {
        System.out.printf("The Phone Book has %d records.%n", phoneBook.size());
    }

    private void listPhoneBook() {
        for (int i = 0; i < phoneBook.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, phoneBook.get(i));
        }
    }
}
