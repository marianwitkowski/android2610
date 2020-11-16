package pl.alx.winko2020;

public class Wine {
    String pimId, name, country, color, image, descr;
    double price;
    int votes, rating;

    public Wine(String pimId, String name, String country, String color,
                String image, String descr, double price, int votes, int rating) {
        this.pimId = pimId;
        this.name = name;
        this.country = country;
        this.color = color;
        this.image = image;
        this.descr = descr;
        this.price = price;
        this.votes = votes;
        this.rating = rating;
    }

    public String getPimId() {
        return pimId;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getColor() {
        return color;
    }

    public String getImage() {
        return image;
    }

    public String getDescr() {
        return descr;
    }

    public double getPrice() {
        return price;
    }

    public int getVotes() {
        return votes;
    }

    public int getRating() {
        return rating;
    }
}
