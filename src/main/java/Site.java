public class Site implements Comparable<Site>{


    public static enum Criteria {
        ID,
        NAME
    }

    private String id;
    private String name;
    public static Criteria criteria = Criteria.NAME;


    public Site() { // el constructor por defecto lo usa el compilador si no creo ningun constructor
    }

    public Site(String id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public int compareTo(Site o) {
        switch (criteria) {
            case NAME:
                return this.name.compareTo(o.getName());
            case ID:
                return this.id.compareTo(o.id);
        }
        return 0;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}