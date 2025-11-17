public class IndividualCustomer extends Customer {

    private String nationalId;

    public IndividualCustomer(String firstName, String lastName, String address,
                              String phone, String nationalId) {
        super(firstName, lastName, address, phone);
        this.nationalId = nationalId;
    }

    public String getNationalId() {
        return nationalId;
    }
}
