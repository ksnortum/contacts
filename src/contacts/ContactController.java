package contacts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ContactController {
    private static final int BACK_TO_MENU_VALUE = -1;

    private List<Contact> phoneBook;

    void run(String[] args) {
        String fileName = parseArgs(args);
        phoneBook = loadFromDisk(fileName);
        boolean thereIsMoreToDo = true;

        do {
            String action = Prompter.nextString("[menu] Enter action (add, list, search, count, exit): ");

            switch (action) {
                case "add":
                    addAContact();
                    break;
                case "list":
                    listContacts();
                    break;
                case "search":
                    search();
                    break;
                case "count":
                    countPhoneBook();
                    break;
                case "exit":
                    thereIsMoreToDo = false;
                    break;
                default:
                    System.out.println("Invalid action");
                    System.out.println();
            }
        } while(thereIsMoreToDo);

        saveToDisk(phoneBook, fileName);
    }

    private List<Contact> loadFromDisk(String fileName) {
        if (!fileName.isEmpty()) {
            Object obj = PhoneBookDAO.deserialize(fileName);
            System.out.println("open " + fileName);
            System.out.println();

            if (obj instanceof List) {
                //noinspection unchecked
                return (List<Contact>) obj;
            }
        }

        return new ArrayList<>();
    }

    private void saveToDisk(List<Contact> phoneBook, String fileName) {
        if (!fileName.isEmpty()) {
            PhoneBookDAO.serialize(phoneBook, fileName);
        }
    }

    private String parseArgs(String[] args) {
        String fileName = "";

        if (args.length > 0) {
            fileName = args[0];
        }

        return fileName;
    }

    private void addAContact() {
        String type = Prompter.nextString("Enter the type (person, organization): ");
        Contact contact;

        if ("person".equals(type)) {
            contact = new PersonContact();
        } else if ("organization".equals(type)) {
            contact = new OrganizationContact();
        } else {
            System.out.println("Bad type");
            System.out.println();
            return;
        }

        editAllFields(contact);
    }

    private void listContacts() {
        listPhoneBook();
        int index = listMenu();

        if (index != BACK_TO_MENU_VALUE) {
            recordMenu(index);
        }
    }

    private void search() {
        boolean searchAgain;

        do {
            String queryIn = Prompter.nextString("Enter search query: ");
            Pattern queryPattern = Pattern.compile(queryIn, Pattern.CASE_INSENSITIVE);
            List<SearchRecord> queryList = new ArrayList<>();

            for (int i = 0; i < phoneBook.size(); i++) {
                Contact contact = phoneBook.get(i);

                if (queryPattern.matcher(contact.getSearchString()).find()) {
                    queryList.add(new SearchRecord(contact, i));
                }
            }

            System.out.printf("Found %d results:%n", queryList.size());
            printSearchRecords(queryList);
            searchAgain = searchMenu(queryList);
        } while (searchAgain);
    }

    private void printSearchRecords(List<SearchRecord> records) {
        for (int i = 0; i < records.size(); i++) {
            Contact contact = records.get(i).getContact();
            System.out.printf("%d. %s%n", i + 1, contact.getDisplayName());
        }

        System.out.println();
    }

    private boolean searchMenu(List<SearchRecord> queryList) {
        boolean searchAgain = false;
        String action = Prompter.nextString("[search] Enter action ([number], back, again): ");

        if (action.matches("\\d+")) {
            int queryIndex = Integer.parseInt(action);
            SearchRecord record = queryList.get(queryIndex - 1);
            int contactIndex = record.getIndex() + 1;
            displayThisContact(contactIndex);
            recordMenu(contactIndex);
         } else if ("again".equals(action)) {
            searchAgain = true;
        } else if ("back".equals(action)) {
            System.out.println();
        } else {
            System.out.println("Invalid action");
            System.out.println();
        }

        return searchAgain;
    }

    private void editAllFields(Contact contact) {
        for (String field : contact.getEditableFields()) {
            contact.editField(field);
        }

        phoneBook.add(contact);
        System.out.println("The record added.");
        System.out.println();
    }

    private void removeAContact(int index) {
        phoneBook.remove(index - 1);
        System.out.println("The record removed!");
        System.out.println();
    }

    private void editAContact(int index) {
        Contact contact = phoneBook.get(index - 1);
        String fieldsPrompt = String.join(", ", contact.getEditableFields());
        String field = Prompter.nextString("Select a field (" + fieldsPrompt + "): ");
        contact.editField(field);
        contact.setLastEdited(LocalDateTime.now());
        phoneBook.remove(index - 1);
        phoneBook.add(index - 1, contact);
        System.out.println("Saved");
        displayThisContact(index);
    }

    private void countPhoneBook() {
        System.out.printf("The Phone Book has %d records.%n%n", phoneBook.size());
    }

    private void displayThisContact(int index) {
        if (index > 0 && index <= phoneBook.size()) {
            Contact contact = phoneBook.get(index - 1);
            contact.getAllData().forEach(System.out::println);
        }

        System.out.println();
    }

    private void listPhoneBook() {
        for (int i = 0; i < phoneBook.size(); i++) {
            Contact contact = phoneBook.get(i);
            System.out.printf("%d. %s%n", i + 1, contact.getDisplayName());
        }

        System.out.println();
    }

    private void recordMenu(int index) {
        boolean moreToDo = true;

        do {
            String action = Prompter.nextString("[record] Enter action (edit, delete, menu): ");

            switch(action) {
                case "edit":
                    editAContact(index);
                    break;
                case "delete":
                    removeAContact(index);
                    break;
                case "menu":
                    moreToDo = false;
                    break;
                default:
                    System.out.println("Invalid action");
            }
        } while (moreToDo);

        System.out.println();
    }

    private int listMenu() {
        int index = BACK_TO_MENU_VALUE;
        String action = Prompter.nextString("[list] Enter action ([number], back): ");

        if (action.matches("\\d+")) {
            index = Integer.parseInt(action);
            displayThisContact(index);
        } else if ("back".equals(action)) {
            System.out.println();
        } else {
            System.out.println("Invalid action");
            System.out.println();
        }

        return index;
    }
}
