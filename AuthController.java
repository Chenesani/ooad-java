public class AuthController {

    public static Customer login(String username, String password) {

        if (username == null || username.isBlank() ||
            password == null || password.isBlank()) {
            return null;
        }

        return CustomerDAO.loginCustomer(username, password);
    }
}
