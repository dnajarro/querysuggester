package shared;

public class Freq {
    private int count;  // number of times this modification happens in the query log
    private Query sugQuery;  // the suggested query

    public Freq(Query sq) {
        this.sugQuery = sq;
        this.count = 1;
    }

    public Query getSugQuery() {
        return sugQuery;
    }

    public String getSugQueryAsStr() { return sugQuery.toString(); }

    public boolean hasFreq(String sq) {
        if (sq.equals(sugQuery.toString())) {
            return true;
        }
        return false;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSugQuery(Query sugQuery) {
        this.sugQuery = sugQuery;
    }
}
