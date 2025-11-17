public class CustomerController {

    public static boolean registerIndividual(
            String first, String last, String address, String phone,
            String username, String password, String nationalId) {

        if (first.isBlank() || last.isBlank() || address.isBlank() ||
            username.isBlank() || password.isBlank() || nationalId.isBlank()) {
            return false;
        }

        IndividualCustomer c = new IndividualCustomer(first, last, address, phone, nationalId);

        return CustomerDAO.saveIndividualCustomer(c, username, password);
    }


    public static boolean registerBusiness(
            String first, String last, String address, String phone,
            String username, String password, String companyName, String taxNo) {

        if (first.isBlank() || last.isBlank() || address.isBlank() ||
            username.isBlank() || password.isBlank() ||
            companyName.isBlank() || taxNo.isBlank()) {
            return false;
        }

        BusinessCustomer c = new BusinessCustomer(first, last, address, phone, companyName, taxNo);

        return CustomerDAO.saveBusinessCustomer(c, username, password);
    }
}
