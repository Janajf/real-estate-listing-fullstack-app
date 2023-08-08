package francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue
    private Integer id;
    private String location;
    private String size;
    private String price;
    @Column(name= "user_id")
    private Integer userId;

    public Listing(String location, String size, String price, Integer userId) {
        this.location = location;
        this.size = size;
        this.price = price;
        this.userId = userId;
    }
}
