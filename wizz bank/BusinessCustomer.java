public class BusinessCustomer extends Customer {

    private String companyName;
    private String taxNumber;

    public BusinessCustomer(String firstName, String lastName, String address,
                            String phone, String companyName, String taxNumber) {
        super(firstName, lastName, address, phone);
        this.companyName = companyName;
        this.taxNumber = taxNumber;
    }

    public String getCompanyName() { return companyName; }
    public String getTaxNumber() { return taxNumber; }
}
