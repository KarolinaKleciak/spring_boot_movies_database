package karkaprogrammer.springbootmoviesdatabase;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Movie_id")
    private Integer id;


    @Column(name = "Title", unique = true, nullable = false, length = 260)
    private String title;

    @Column(name = "Production_year", unique = false, nullable = false, columnDefinition = "integer")
    private int productionYear;

    @Column(name = "Category", nullable = false, length = 100)
    private String category;

    @Column(name = "Description", nullable = false, length = 500)
    @Lob
    private String description;

    @Column(name = "Rating", nullable = false)
    private double rating;

    @Column(name = "Awards", nullable = false, columnDefinition = "TEXT")
    @Lob
    private String awards;

    @Override
    public String toString(){
        return this.title + " , " + this.productionYear + " , " +  this.category + " , "  + this.description + " , " +  this.rating + " , "  + this.awards;
    }

    public Movie(String title, int productionYear, String category, String description, double rating,String awards){
        this.title = title;
        this.productionYear = productionYear;
        this.category = category;
        this.description = description;
        this.rating = rating;
        this.awards = awards;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public String getAwards() {
        return awards;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }
}