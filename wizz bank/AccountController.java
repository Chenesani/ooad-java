public class AccountController {

    public static boolean openAccount(
            Customer customer,
            String type,
            String branch,
            String openingDepositStr,
            String employerName,
            String employerAddress) {

        if (branch.isBlank() || openingDepositStr.isBlank()) {
            return false;
        }

        double openingDeposit;

        try {
            openingDeposit = Double.parseDouble(openingDepositStr);
        } catch (Exception e) {
            return false;
        }

        Account account;

        switch (type) {

            case "Savings":
                account = new SavingsAccount(openingDeposit, branch, customer);
                break;

            case "Investment":
                if (openingDeposit < 500) {
                    return false;
                }
                account = new InvestmentAccount(openingDeposit, branch, customer);
                break;

            case "Cheque":
                if (employerName.isBlank() || employerAddress.isBlank())
                    return false;

                account = new ChequeAccount(openingDeposit, branch, customer,
                        employerName, employerAddress);
                break;

            default:
                return false;
        }

        customer.addAccount(account);
        return AccountDAO.saveAccount(account, customer);
    }
}
