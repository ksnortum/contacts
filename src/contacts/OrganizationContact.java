package contacts;

public class OrganizationContact extends Contact {
    private String organizationName;
    private String address;

    public OrganizationContact(boolean isThisContactAPerson) {
        super(isThisContactAPerson);
    }

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
}
