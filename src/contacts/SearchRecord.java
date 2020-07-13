package contacts;

public class SearchRecord {
    private final Contact contact;
    private final int index;

    public SearchRecord(Contact contact, int index) {
        this.contact = contact;
        this.index = index;
    }

    public Contact getContact() {
        return contact;
    }

    public int getIndex() {
        return index;
    }
}
