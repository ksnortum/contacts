package contacts;

import java.time.LocalDateTime;

public class Contact {

    private String phoneNumber = "";
    private final boolean isPerson;
    private final LocalDateTime created = LocalDateTime.now();
    private LocalDateTime lastEdited = LocalDateTime.now();

    public Contact(boolean isThisContactAPerson) {
        this.isPerson = isThisContactAPerson;
    }

    public String getPhoneNumber() {
        return hasNumber() ? phoneNumber : "[no number]";
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = validatePhoneNumber(phoneNumber);
    }

    public boolean hasNumber() {
        return !phoneNumber.isEmpty();
    }

    private String validatePhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.strip();

        if (phoneNumber.isEmpty()) {
            return "";
        }

        String[] parts = phoneNumber.split("\\s+|-");
        //System.out.println(Arrays.toString(parts)); // debug

        for (int groupIndex = 0; groupIndex < parts.length; groupIndex++) {
            if (groupIndex == 0) {
                if (!parts[0].matches("\\+?\\(?[0-9a-zA-Z]+\\)?")) {
                    //System.out.println("Invalid first part"); // debug
                    System.out.println("Wrong number format!");
                    return "";
                }
            } else if (groupIndex == 1) {
                if (!parts[1].matches("\\(?[0-9a-zA-Z]{2,}\\)?")) {
                    //System.out.println("Invalid second part"); // debug
                    System.out.println("Wrong number format!");
                    return "";
                } else if (parts[1].startsWith("(") && parts[0].contains("(")) {
                    //System.out.println("Too many parentheses"); // debug
                    System.out.println("Wrong number format!");
                    return "";
                }
            } else if (!parts[groupIndex].matches("[0-9a-zA-Z]{2,}")) {
                //System.out.println("Invalid third or greater part"); // debug
                System.out.println("Wrong number format!");
                return "";
            }

            if ((parts[groupIndex].contains("(") || parts[groupIndex].contains(")"))
                    && !parts[groupIndex].matches("\\+?\\([^)]*\\)")) {
                //System.out.println("Mismatched parentheses"); // debug
                System.out.println("Wrong number format!");
                return"";
            }
        }

        //System.out.println("Entire phone number matches"); // debug
        return phoneNumber;
    }

    public boolean isPerson() {
        return isPerson;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }
}
