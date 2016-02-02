package core.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * The collective member.
 */
public class CollectiveMember extends Member {
    private int personnelNumber;
    private Date lastBadgeCheckDate;

    public int getPersonnelNumber() {
        return personnelNumber;
    }

    public void setPersonnelNumber(int personnelNumber) {
        this.personnelNumber = personnelNumber;
    }

    public Date getLastBadgeCheckDate() {
        return lastBadgeCheckDate;
    }

    public void setLastBadgeCheckDate(Date lastBadgeCheckDate) {
        this.lastBadgeCheckDate = lastBadgeCheckDate;
    }

    /**
     * Check whether the badge is valid at the moment.
     *
     * @return True if the badge is valid. False if not.
     */
    public boolean isBadgeValid() {
        Date now = Calendar.getInstance().getTime();
        return lastBadgeCheckDate.compareTo(now) < 0;
    }
}
