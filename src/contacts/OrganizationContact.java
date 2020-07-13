package contacts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrganizationContact extends Contact {
    private String organizationName;
    private String address;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public List<String> getAllData() {
        List<String> display = new ArrayList<>();
        display.add("Organization name: " + getOrganizationName());
        display.add("Address: " + getAddress());
        display.add("Number: " + getPhoneNumber());
        display.add("Time created: " + getCreated().format(GlobalData.formatter));
        display.add("Time last edit: " + getLastEdited().format(GlobalData.formatter));

        return display;
    }

    @Override
    public String getDisplayName() {
        return getOrganizationName();
    }

    @Override
    public List<String> getEditableFields() {
        return Arrays.asList("name", "address", "number");
    }

    @Override
    public void editField(String field) {
        switch (field) {
            case "name":
                String name = Prompter.nextString("Enter organization name: ");
                setOrganizationName(name);
                break;
            case "address":
                String address = Prompter.nextString("Enter address: ");
                setAddress(address);
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
        return getOrganizationName() + "\t" + getAddress() + "\t" + getPhoneNumber();
    }
}
