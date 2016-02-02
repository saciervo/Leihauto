package core.domain;

import infrastructure.logging.Log;
import infrastructure.sqlite.DatabaseContext;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private final static Log log = Log.getInstance();

    private int memberId;
    private Account account;
    private String name;
    private String postalAddress;
    private String emailAddress;
    private String drivingLicenceNumber;
    private String homePhoneNumber;
    private String mobilePhoneNumber;
    private String businessPhoneNumber;
    private String faxPhoneNumber;
    private String pinCode;
    private Location defaultLocation;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDrivingLicenceNumber() {
        return drivingLicenceNumber;
    }

    public void setDrivingLicenceNumber(String drivingLicenceNumber) {
        this.drivingLicenceNumber = drivingLicenceNumber;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getBusinessPhoneNumber() {
        return businessPhoneNumber;
    }

    public void setBusinessPhoneNumber(String businessPhoneNumber) {
        this.businessPhoneNumber = businessPhoneNumber;
    }

    public String getFaxPhoneNumber() {
        return faxPhoneNumber;
    }

    public void setFaxPhoneNumber(String faxPhoneNumber) {
        this.faxPhoneNumber = faxPhoneNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Location getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(Location defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    private static Member ConvertToMember(Object[] obj) {
        Member member = new Member();
        member.setMemberId((int) obj[0]);
        member.setAccount(Account.find((int) obj[1]));
        member.setName(obj[2].toString());
        member.setDefaultLocation(Location.find((int) obj[3]));
        member.setEmailAddress(obj[4].toString());
        member.setPinCode(obj[5].toString());
        member.setDrivingLicenceNumber(obj[6].toString());
        return member;
    }

    public static Member find(int id) {
        try (DatabaseContext db = new DatabaseContext()) {
            Object[] obj = db.fetchFirst("SELECT id, accountId, name, defaultLocationId, emailAddress, pinCode, drivingLicenceNumber FROM members WHERE id = ?", Integer.toString(id));
            return ConvertToMember(obj);
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }
        return null;
    }

    public static List<Member> findAll() {
        try (DatabaseContext db = new DatabaseContext()) {
            List<Member> result = new ArrayList<>();
            List<Object[]> fetchResult = db.fetch("SELECT id, accountId, name, defaultLocationId, emailAddress, pinCode, drivingLicenceNumber FROM members");
            for (Object[] obj : fetchResult) {
                result.add(ConvertToMember(obj));
            }

            return result;
        } catch (Exception ex) {
            log.error(ex, "Could not connect to database.");
        }

        return null;
    }
}
