package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContactController {
    private final List<Contact> phoneBook = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'mm:ss");

    void run() {
        boolean thereIsMoreToDo = true;

        do {
            String action = Prompter.nextString("Enter action (add, remove, edit, count, info, exit): ");

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
                case "info":
                    displayInfo();
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
        String type = Prompter.nextString("Enter the type (person, organization): ");

        if ("person".equals(type)) {
            addAPerson();
        } else if ("organization".equals(type)) {
            addAnOrganization();
        } else {
            System.out.println("Bad type");
            System.out.println();
        }
    }

    private void addAPerson() {
        PersonContact contact = new PersonContact(true);
        String firstName = Prompter.nextString("Enter the name: ");
        contact.setFirstName(firstName);
        String lastName = Prompter.nextString("Enter the surname: ");
        contact.setLastName(lastName);
        LocalDate birthday = Prompter.nextDate("Enter the birth date: ");
        contact.setBirthday(birthday);
        Gender gender = Prompter.nextGender("Enter the gender (M, F): ");
        contact.setGender(gender);
        String phoneNumber = Prompter.nextString("Enter the number: ");
        contact.setPhoneNumber(phoneNumber);
        phoneBook.add(contact);
        System.out.println("The record added.");
        System.out.println();
    }

    private void addAnOrganization() {
        OrganizationContact contact = new OrganizationContact(false);
        String name = Prompter.nextString("Enter the organization name: ");
        contact.setOrganizationName(name);
        String address = Prompter.nextString("Enter the address: ");
        contact.setAddress(address);
        String phoneNumber = Prompter.nextString("Enter the number: ");
        contact.setPhoneNumber(phoneNumber);
        phoneBook.add(contact);
        System.out.println("The record added.");
        System.out.println();
    }

    private void removeAContact() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to remove!");
            System.out.println();
            return;
        }

        listPhoneBook();
        int index = Prompter.nextInt("Select a record: ");
        phoneBook.remove(index - 1);
        System.out.println("The record removed!");
        System.out.println();
    }

    private void editAContact() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to edit!");
            System.out.println();
            return;
        }

        listPhoneBook();
        int index = Prompter.nextInt("Select a record: ");
        Contact contact = phoneBook.get(index - 1);

        if (contact.isPerson()) {
            PersonContact personContact = (PersonContact) contact;
            editAPersonContact(personContact, index);
        } else {
            OrganizationContact organizationContact = (OrganizationContact) contact;
            editAnOrganization(organizationContact, index);
        }
    }

    private void editAPersonContact(PersonContact personContact, int index) {
        String field = Prompter.nextString("Select a field (name, surname, birth, gender, number): ");

        switch (field) {
            case "name":
                String firstName = Prompter.nextString("Enter name: ");
                personContact.setFirstName(firstName);
                break;
            case "surname":
                String lastName = Prompter.nextString("Enter surname: ");
                personContact.setLastName(lastName);
                break;
            case "birth":
                LocalDate birthday = Prompter.nextDate("Enter the birth date: ");
                personContact.setBirthday(birthday);
                break;
            case "gender":
                Gender gender = Prompter.nextGender("Enter the gender: ");
                personContact.setGender(gender);
                break;
            case "number":
                String phoneNumber = Prompter.nextString("Enter number: ");
                personContact.setPhoneNumber(phoneNumber);
                break;
            default:
                System.out.println("Invalid field");
                System.out.println();
                return;
        }

        personContact.setLastEdited(LocalDateTime.now());
        phoneBook.remove(index - 1);
        phoneBook.add(index - 1, personContact);
        System.out.println("The record updated!");
        System.out.println();
    }

    private void editAnOrganization(OrganizationContact organizationContact, int index) {
        String field = Prompter.nextString("Select a field (name, address, number): ");

        switch (field) {
            case "name":
                String name = Prompter.nextString("Enter organization name: ");
                organizationContact.setOrganizationName(name);
                break;
            case "address":
                String address = Prompter.nextString("Enter address: ");
                organizationContact.setAddress(address);
                break;
            case "number":
                String phoneNumber = Prompter.nextString("Enter number: ");
                organizationContact.setPhoneNumber(phoneNumber);
                break;
            default:
                System.out.println("Invalid field");
                return;
        }

        organizationContact.setLastEdited(LocalDateTime.now());
        phoneBook.remove(index - 1);
        phoneBook.add(index - 1, organizationContact);
        System.out.println("The record updated!");
        System.out.println();
    }

    private void countPhoneBook() {
        System.out.printf("The Phone Book has %d records.%n%n", phoneBook.size());
    }

    private void displayInfo() {
        listPhoneBook();
        int index = Prompter.nextInt("Enter index to show info: ");
        Contact contact = phoneBook.get(index - 1);

        if (contact.isPerson()) {
            PersonContact personContact = (PersonContact) contact;
            displayPerson(personContact);
        } else {
            OrganizationContact organizationContact = (OrganizationContact) contact;
            displayOrganization(organizationContact);
        }
    }

    private void displayPerson(PersonContact personContact) {
        System.out.println("Name: " + personContact.getFirstName());
        System.out.println("Surname: " + personContact.getLastName());
        String displayBirthday = personContact.getBirthday() == null
                ? "[no data]"
                : personContact.getBirthday().toString();
        System.out.println("Birth date: " + displayBirthday);
        String displayGender = personContact.getGender() == null
                ? "[no data]"
                : personContact.getGender().toString();
        System.out.println("Gender: " + displayGender);
        System.out.println("Number: " + personContact.getPhoneNumber());
        System.out.println("Time created: " + personContact.getCreated().format(formatter));
        System.out.println("Time last edit: " + personContact.getLastEdited().format(formatter));
        System.out.println();
    }

    private void displayOrganization(OrganizationContact organizationContact) {
        System.out.println("Organization name: " + organizationContact.getOrganizationName());
        System.out.println("Address: " + organizationContact.getAddress());
        System.out.println("Number: " + organizationContact.getPhoneNumber());
        System.out.println("Time created: " + organizationContact.getCreated().format(formatter));
        System.out.println("Time last edit: " + organizationContact.getLastEdited().format(formatter));
        System.out.println();
    }

    private void listPhoneBook() {
        for (int i = 0; i < phoneBook.size(); i++) {
            Contact contact = phoneBook.get(i);

            if (contact.isPerson()) {
                PersonContact personContact = (PersonContact) contact;
                System.out.printf("%d. %s %s%n", i + 1, personContact.getFirstName(), personContact.getLastName());
            } else {
                OrganizationContact organizationContact = (OrganizationContact) contact;
                System.out.printf("%d. %s%n", i + 1, organizationContact.getOrganizationName());
            }
        }
    }
}
