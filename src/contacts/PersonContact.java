package contacts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonContact extends Contact {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Gender gender;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String getDisplayName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public List<String> getAllData() {
        List<String> display = new ArrayList<>();
        display.add("Name: " + getFirstName());
        display.add("Surname: " + getLastName());
        String displayBirthday = getBirthday() == null
                ? "[no data]"
                : getBirthday().toString();
        display.add("Birth date: " + displayBirthday);
        String displayGender = getGender() == null
                ? "[no data]"
                : getGender().toString();
        display.add("Gender: " + displayGender);
        display.add("Number: " + getPhoneNumber());
        display.add("Time created: " + getCreated().format(GlobalData.formatter));
        display.add("Time last edit: " + getLastEdited().format(GlobalData.formatter));

        return display;
    }

    @Override
    public List<String> getEditableFields() {
        return Arrays.asList("name", "surname", "birth", "gender", "number");
    }

    @Override
    public void editField(String field) {
        switch (field) {
            case "name":
                String firstName = Prompter.nextString("Enter name: ");
                setFirstName(firstName);
                break;
            case "surname":
                String lastName = Prompter.nextString("Enter surname: ");
                setLastName(lastName);
                break;
            case "birth":
                LocalDate birthday = Prompter.nextDate("Enter the birth date: ");
                setBirthday(birthday);
                break;
            case "gender":
                Gender gender = Prompter.nextGender("Enter the gender: ");
                setGender(gender);
                break;
            case "number":
                String phoneNumber = Prompter.nextString("Enter number: ");
                setPhoneNumber(phoneNumber);
                break;
            default:
                System.out.println("Invalid field");
        }
    }

    @Override
    public String getSearchString() {
        return getFirstName() + "\t" + getLastName() + "\t" + getBirthday() + "\t"
                + getGender() + "\t" + getPhoneNumber();
    }
}
