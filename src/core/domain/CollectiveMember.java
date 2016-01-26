package core.domain;

import java.util.Calendar;
import java.util.Date;

public class CollectiveMember extends Member {
    private int personnelNumber;
    private Date lastBadgeCheckDate;

    public boolean isBadgeValid() {
        Date now = Calendar.getInstance().getTime();
        return lastBadgeCheckDate.compareTo(now) < 0;
    }
}
