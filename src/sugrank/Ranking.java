package sugrank;

public class Ranking implements Comparable<Ranking> {
    private String query;
    private String sugQuery;
    private double score;

    public Ranking(String query, String sugQuery, double score) {
        this.query = query;
        this.sugQuery = sugQuery;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public String getQuery() {
        return query;
    }

    public String getSugQuery() {
        return sugQuery;
    }

    public void setSugQuery(String sugQuery) {
        this.sugQuery = sugQuery;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(Ranking o) {
        if (score - o.score >= 0.001) {
            return (int)Math.ceil(score - o.score);
        }
        return (int)(score - o.score);
    }
}
