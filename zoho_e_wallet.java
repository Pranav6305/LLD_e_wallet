
import java.util.*;

public class zoho_e_wallet {

    static String log_email;
    static String log_username;
    static Map<String, user> new_user_map = new HashMap<>();
    static Map<String, bank> user_bank_map = new HashMap<>();

    static class user {

        protected String fullname;
        protected String mobile_number;
        String email;
        String username;
        protected String password;

        user(String fullname, String mobile_number, String email, String username, String password) {
            this.fullname = fullname;
            this.mobile_number = mobile_number;
            this.email = email;
            this.username = username;
            this.password = password;
        }
    }

    static class bank {

        String username;
        String email;
        protected String bank_name;
        protected String acc_no;
        protected String ifsc_code;
        protected Double account_balance;
        protected Double zoho_amount;

        bank(String username, String email, String bank_name, String acc_no, String ifsc_code, Double account_balance, Double zoho_amount) {
            this.username = username;
            this.email = email;
            this.bank_name = bank_name;
            this.acc_no = acc_no;
            this.ifsc_code = ifsc_code;
            this.account_balance = account_balance;
            this.zoho_amount = zoho_amount;
        }
    }

    public void signup(String fullname, String mobile_number, String u_email, String username, String password) {
        //System.out.println(fullname + " " + mobile_number + " " + u_email + " " + username + " " + password);
        if (new_user_map.containsKey(u_email)) {
            System.out.println("user already exists");
            System.out.println("login or create another account using different email");
        } else {
            user create_user = new user(fullname, mobile_number, u_email, username, password);
            new_user_map.put(u_email, create_user);
            System.out.println("User created Successfully!");
        }
    }

    public void login(String u_email, String username, String password) {
        //System.out.println(u_email + " " + username + " " + password);
        if (new_user_map.containsKey(u_email)) {
            if (new_user_map.get(u_email).username.equals(username) && new_user_map.get(u_email).password.equals(password)) {
                System.out.println("User logged in successfully!");
                log_email = u_email;
                log_username = username;
            } else {
                System.out.println("username or password is not valid...");
                System.out.println("try again...");
            }
        } else {
            System.out.println("user not found...");
            log_email = null;
            log_username = null;
        }
    }

    public void userprofile(String email) {
        System.out.println("UserName:" + new_user_map.get(email).username);
        System.out.println("Mobile Number:" + new_user_map.get(email).mobile_number);
        System.out.println();
    }

    static boolean checkAuthentication() {
        if (log_email == null || log_username == null) {
            System.out.println("login first...");
            return false;
        }
        return true;
    }

    public void enter_bankdetails(String email, String mobile_number, String username, Scanner sc) {
        if (user_bank_map.containsKey(mobile_number)) {
            System.out.println("user has his bank account linked to his wallet already...");
        } else {
            System.out.println("Enter bank name:");
            String bank_name = sc.nextLine();
            sc.nextLine();
            System.out.println("Enter account number:");
            String acc_no = sc.nextLine();
            System.out.println("Enter IFSC code:");
            String ifsc_code = sc.nextLine();
            System.out.println("Enter account balance:");
            Double account_balance = sc.nextDouble();
            System.out.println("Enter zoho-wallet amount:");
            Double zoho_amount = sc.nextDouble();
            while (account_balance < zoho_amount) {
                System.out.println("Enter zoho-wallet amount less than or equal to account balance:");
                zoho_amount = sc.nextDouble();
                if (zoho_amount <= account_balance) {
                    break;
                }
            }
            account_balance = account_balance - zoho_amount;
            bank user_bank = new bank(username, email, bank_name, acc_no, ifsc_code, account_balance, zoho_amount);
            user_bank_map.put(mobile_number, user_bank);
            System.out.println("Bank Details added successfully!");
        }
    }

    public void updatebankdetails(String mobile_number, Scanner sc) {
        System.out.println("1.update mobile number" + "\n" + "2.update email" + "\n" + "3.update bank_name" + "\n" + "4.update account number" + "\n" + "5.update ifsc code" + "\n" + "6.Remove Bank account" + "\n" + "7.Exit");
        System.out.println();
        System.out.println("Enter your choice:");
        int choice = sc.nextInt();
        while (true) {
            if (choice == 1) {
                System.out.println("Enter new mobile number:");
                String new_mobile_number = sc.next();
                sc.nextLine();
                // System.out.println(user_bank_map);
                // System.out.println("m:" + mobile_number + " " + "nm:" + new_mobile_number);
                bank details = user_bank_map.remove(mobile_number);
                user_bank_map.put(new_mobile_number, details);
                new_user_map.get(log_email).mobile_number = new_mobile_number;
                // System.out.println(user_bank_map);
                System.out.println("Mobile number updated successfully!");
                break;
            }
            if (choice == 2) {
                System.out.println("Enter new email:");
                String new_email = sc.next();
                sc.nextLine();
                System.out.println("Enter mobile number:");
                String new_mobile_number = sc.next();
                sc.nextLine();
                // System.out.println(new_email);
                if (user_bank_map.containsKey(new_mobile_number)) {
                    user info = new_user_map.remove(log_email);
                    log_email = new_email;
                    new_user_map.put(new_email, info);
                    user_bank_map.get(new_mobile_number).email = new_email;
                    System.out.println("Email updated successfully!");
                } else {
                    System.out.println("Bank account Mobile number linked with the wallet is removed successfully!");
                }
                break;
            }
            if (choice == 3) {
                System.out.println("Enter new bank name:");
                String new_bank_name = sc.nextLine();
                sc.nextLine();
                System.out.println("Enter mobile number:");
                String new_mobile_number = sc.nextLine();
                if (user_bank_map.containsKey(new_mobile_number)) {
                    user_bank_map.get(new_mobile_number).bank_name = new_bank_name;
                    System.out.println("Bank Name updated successfully!");
                } else {
                    System.out.println("Bank account Mobile number linked with the wallet is removed successfully!");
                }
                break;
            }
            if (choice == 4) {
                System.out.println("Enter Account number to be modified:");
                String new_acc_no = sc.nextLine();
                sc.nextLine();
                System.out.println("Enter mobile number:");
                String new_mobile_number = sc.nextLine();
                if (user_bank_map.containsKey(new_mobile_number)) {
                    user_bank_map.get(new_mobile_number).acc_no = new_acc_no;
                    System.out.println("Account number updated successfully!");
                } else {
                    System.out.println("Bank account Mobile number linked with the wallet is removed successfully!");
                }
                break;
            }
            if (choice == 5) {
                System.out.println("Enter IFSC code to be modified:");
                String new_ifsc_code = sc.nextLine();
                sc.nextLine();
                System.out.println("Enter mobile number:");
                String new_mobile_number = sc.nextLine();
                if (user_bank_map.containsKey(new_mobile_number)) {
                    user_bank_map.get(new_mobile_number).ifsc_code = new_ifsc_code;
                    System.out.println("IFSC code updated successfully!");
                } else {
                    System.out.println("Mobile number is not linked to the wallet");
                }
                break;
            }
            if (choice == 6) {
                System.out.println("Enter mobile number:");
                String new_mobile_number = sc.next();
                sc.nextLine();
                if (user_bank_map.containsKey(new_mobile_number)) {
                    user_bank_map.remove(new_mobile_number);
                    System.out.println("Bank account Mobile number linked with the wallet is removed successfully!");
                } else {
                    System.out.println("Mobile number is not linked to the wallet");
                }
                break;
            }
            if (choice == 7) {
                break;
            }
        }
    }

    public void menu() {
        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("0. User signup/login/logout");
        System.out.println("1. Add/Update Bank Details");
        System.out.println("2. Wallet Details");
        System.out.println("--------------------------------------");
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // for testing: 
        // new_user_map.put("pranav@", new user("pranavji", "12345", "pranav@", "pranav", "123"));
        zoho_e_wallet zoho = new zoho_e_wallet();
        while (true) {
            zoho.menu();
            System.out.print("Enter Choice:");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 0) {
                System.out.println("1.Signup" + "\n" + "2.Login" + "\n" + "3.Profile Management" + "\n" + "4.Logout" + "\n" + "5.Exit");
                System.out.println();
                int option = sc.nextInt();
                if (option == 1) {
                    System.out.println("User signup\n");
                    System.out.println();
                    System.out.println("Enter fullname:");
                    String fullname = sc.nextLine();
                    sc.nextLine();
                    System.out.println("Enter mobile number:");
                    String mobile_number = sc.nextLine();
                    System.out.println("Enter email:");
                    String u_email = sc.nextLine();
                    System.out.println("Enter username:");
                    String username = sc.nextLine();
                    System.out.println("Enter password:");
                    String password = sc.nextLine();
                    System.out.println("Enter confirm password:");
                    String confirm_password = sc.nextLine();
                    while (!password.equals(confirm_password)) {
                        System.out.println("Enter valid confirm password:");
                        confirm_password = sc.nextLine();
                        if (password.equals(confirm_password)) {
                            break;
                        }
                    }
                    zoho.signup(fullname, mobile_number, u_email, username, password);
                    continue;

                } else if (option == 2) {
                    System.out.println("User login\n");
                    System.out.println();
                    System.out.println("Enter email:");
                    log_email = sc.next();
                    sc.nextLine();
                    System.out.println("Enter username:");
                    log_username = sc.nextLine();
                    System.out.println("Enter password:");
                    String password = sc.nextLine();
                    zoho.login(log_email, log_username, password);
                    continue;
                } else if (option == 3) {
                    System.out.println("Profile Management:");
                    System.out.println();
                    System.out.println("Enter email:");
                    String email = sc.next();
                    sc.nextLine();
                    //System.out.println(email);
                    if (!new_user_map.containsKey(email)) {
                        System.out.println("User does not exist!");
                    } else {
                        System.out.println("User Details:");
                        zoho.userprofile(email);
                    }
                    continue;
                } else if (option == 4) {
                    if (!checkAuthentication()) {
                        continue;
                    }
                    System.out.println("Logout...");
                    log_email = null;
                    log_username = null;
                } else {
                    continue;
                }
            }
            if (choice == 1) {
                if (!checkAuthentication()) {
                    continue;
                }
                // for testing: 
                // user_bank_map.put("12345", new bank(log_username, log_email, "sbi", "ajwd", "sjwbn", 10000.0, 1000.0));
                System.out.println("email:" + log_email);
                System.out.println("username:" + log_username);
                System.out.println();
                System.out.println("1.Add bank account with the wallet" + "\n" + "2.Update Bank details" + "\n" + "3.Exit");
                int option = sc.nextInt();
                if (option == 1) {
                    zoho.enter_bankdetails(log_email, new_user_map.get(log_email).mobile_number, log_username, sc);
                    continue;
                } else if (option == 2) {
                    zoho.updatebankdetails(new_user_map.get(log_email).mobile_number, sc);
                    continue;
                } else {
                    continue;
                }
            }
            if (choice == 2) {

            }
        }
    }
}
