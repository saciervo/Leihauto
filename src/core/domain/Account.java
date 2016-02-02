package core.domain;

import infrastructure.logging.Log;
import infrastructure.sqlite.DatabaseContext;

public class Account {
    private final static Log log = Log.getInstance();

    private int accountId;
    private AccountType accountType;
    private String name;
    private String street;
    private String zipCode;
    private String city;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static Account find(int id) {
        try (DatabaseContext db = new DatabaseContext()) {
            Object[] obj = db.fetchFirst("SELECT id, type, name, street, zipCode, city FROM accounts WHERE id = ?", Integer.toString(id));
            Account account = new Account();
            account.setAccountId((int) obj[0]);
            account.setAccountType(AccountType.values()[(int) obj[1]]);
            account.setName(obj[2].toString());
            account.setStreet(obj[3].toString());
            account.setZipCode(obj[4].toString());
            account.setCity(obj[5].toString());
            return account;
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }
        return null;
    }
}

