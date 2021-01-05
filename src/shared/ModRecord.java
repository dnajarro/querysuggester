package shared;

import java.util.*;

public class ModRecord {
    private Map<String, Mod> mods;
    private int mostMod;

    public ModRecord() {
        mods = new HashMap<>();
        mostMod = 0;
    }

    public int getMostMod() {
        return mostMod;
    }

    public boolean contains(Query q, Query sq) {
        if (mods.containsKey(q.toString() + " " + sq.toString())) {
            return true;
        }
        return false;
    }

    public void add(Query query, Query sugQuery) {
        if (!query.toString().isEmpty() && !sugQuery.toString().isEmpty()) {
            String key = query.toString() + " " + sugQuery.toString();
            if (mods.containsKey(key)) {
                int count = mods.get(key).getCount() + 1;
                if (count > mostMod) {
                    mostMod = count;
                }
                Mod m = mods.get(key);
                m.setCount(count);
                mods.put(key, m);
            } else {
                mods.put(key, new Mod(query, sugQuery));
            }
        }
    }

    public Set<Query> getAllSQ() {
        Set<Query> sugQueries = new HashSet<>();
        for (Mod entry : mods.values()) {
            sugQueries.add(entry.getSugQuery());
        }
        return sugQueries;
    }

    public Set<String> getQueries() {
        Set<String> queries = new HashSet<>();
        for (Mod mod : mods.values()) {
            if (!mod.getQueryAsStr().isEmpty()) {
                queries.add(mod.getQueryAsStr());
            }
            if (!mod.getSugQueryAsStr().isEmpty()) {
                queries.add(mod.getSugQueryAsStr());
            }
        }

        return queries;
    }

    public Mod getMod(String q, String sq) { return mods.get(q + " " + sq); }

    public int getMod(Query q, Query sq) {
        Mod m = mods.get(q.toString() + " " + sq.toString());
        if (m != null) {
            return m.getCount();
        }
        return -1;
    }

    public Query getQuery(String q) {
        for (Mod mod : mods.values()) {
            if (mod.getQueryAsStr().equals(q)) {
                return mod.getQuery();
            }
            if (mod.getSugQueryAsStr().equals(q)) {
                return mod.getSugQuery();
            }
        }
        return null;
    }
}
