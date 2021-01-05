package datetime;

public class FullDateTime {
    private Date date;
    private QTime time;
    static final int MINPERHR = 60;
    static final int SECPERMIN = 60;

    public FullDateTime(Date date, QTime time) {
        this.date = date;
        this.time = time;
    }

    public QTime getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public void setTime(QTime time) {
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isWithin10Min(Date otherDate, QTime otherTime) {
        int secDiff = 0;
        int minDiff = 0;
        if (!isSameDate(otherDate)) {
            if (isCurDateLater(otherDate)) {
                secDiff = time.getSecond() + (SECPERMIN - otherTime.getSecond());
                minDiff = time.getMinute() + (MINPERHR - otherTime.getMinute());
            } else {
                secDiff = otherTime.getSecond() + (SECPERMIN - time.getSecond());
                minDiff = otherTime.getMinute() + (MINPERHR - time.getMinute());
            }
        }
        if (isSameDate(otherDate)) {
            if (time.getHour() > otherTime.getHour()) {
                secDiff = time.getSecond() + (SECPERMIN - otherTime.getSecond());
                minDiff = time.getMinute() + (MINPERHR - otherTime.getMinute());
            } else if (time.getHour() < otherTime.getHour()) {
                secDiff = otherTime.getSecond() + (SECPERMIN - otherTime.getSecond());
                minDiff = otherTime.getMinute() + (MINPERHR - otherTime.getMinute());
            } else {
                if (time.getMinute() > otherTime.getMinute()) {
                    secDiff = time.getSecond() + (SECPERMIN - otherTime.getSecond());
                    minDiff = time.getMinute() + (MINPERHR - otherTime.getMinute());
                } else if (otherTime.getMinute() < otherTime.getMinute()) {
                    secDiff = otherTime.getSecond() + (SECPERMIN - time.getSecond());
                    minDiff = otherTime.getMinute() + (SECPERMIN - time.getMinute());
                }
                return true;
            }
        }
        if (minDiff > 10) {
            return false;
        } else if (minDiff == 10) {
            if (secDiff == 0) {
                return true;
            }
            return false;
        }
        return true;
    }

    private boolean isSameDate(Date otherdate) {
        if (date.getYear() == otherdate.getYear()) {
            if (date.getMonth() == otherdate.getMonth()) {
                if (date.getDay() == otherdate.getDay()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCurDateLater(Date otherDate) {
        if (date.getYear() < otherDate.getYear()) {
            return false;
        }
        if (date.getYear() > otherDate.getYear()) {
            return true;
        }
        if (date.getMonth() > otherDate.getMonth()) {
            return true;
        }
        if (date.getMonth() < otherDate.getMonth()) {
            return false;
        }
        if (date.getDay() > otherDate.getDay()) {
            return true;
        }
        if (date.getDay() < otherDate.getDay()) {
            return false;
        }
        return false;
    }
}
