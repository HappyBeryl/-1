package entiy;

public class Good {
    private int id;
    private String sort;
    private String brand;
    private String style;
    private String match;
    private String bydata;
    private String expdata;
    private double like;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getBydata() {
        return bydata;
    }

    public void setBydata(String bydata) {
        this.bydata = bydata;
    }

    public String getExpdata() {
        return expdata;
    }

    public void setExpdata(String expdata) {
        this.expdata = expdata;
    }

    public double getLike() {
        return like;
    }

    public void setLike(double like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", sort='" + sort + '\'' +
                ", brand='" + brand + '\'' +
                ", style='" + style + '\'' +
                ", match='" + match + '\'' +
                ", bydata='" + bydata + '\'' +
                ", expdata='" + expdata + '\'' +
                ", like=" + like +
                '}';
    }
}
