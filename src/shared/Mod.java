package shared;

public class Mod {
    private int count;  // number of times this modification happens in the query log
    private Query query;   // the query
    private Query sugQuery;  // the suggested query

    public Mod(Query q, Query sq) {
        this.query = q;
        this.sugQuery = sq;
        this.count = 1;
    }

    public Query getQuery() {
        return query;
    }

    public String getQueryAsStr() { return query.toString(); }

    public Query getSugQuery() {
        return sugQuery;
    }

    public String getSugQueryAsStr() { return sugQuery.toString(); }

    public boolean hasMod(String q, String sq) {
        if (q.equals(query.toString()) && sq.equals(sugQuery.toString())) {
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

    public void setQuery(Query query) {
        this.query = query;
    }

    public void setSugQuery(Query sugQuery) {
        this.sugQuery = sugQuery;
    }
}
