package datetime;

public class QTime {
    private int hour;
    private int minute;
    private int second;

    public QTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(hour);
        sb.append(":");
        sb.append(minute);
        sb.append(":");
        sb.append(second);
        return sb.toString();
    }
}
