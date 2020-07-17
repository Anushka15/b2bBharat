package com.b2b.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import com.b2b.model.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productId")
    private Integer productId;

    @Column(name = "productName")
    @NotEmpty(message = "*Please provide the name of your Product")
    private String productName;

    @Column(name = "productDescription")
    @NotEmpty(message = "*Please provide description for your product")
    private String productDescription;

    @Column(name = "relatedProducts")
    private String relatedProducts;

    @Column(name = "category")
    private String Category;

    @Column(name = "timeToProduce")
    @NotEmpty(message = "*Please enter the approximate time to produce/required for your product")
    private String timeToProduce;

    @Column(name = "quantity")
    @NotEmpty(message = "*Please enter quantity in proper units")
    private String quantity;

    @Column(name = "priceRange")
    @NotEmpty(message = "*Please enter price range for your product")
    private String priceRange;

    @Column(name = "website")
    private String website;

    @Column(name = "comments")
    private String comments;

    @Column(name = "capabilities")
    @NotEmpty(message = "*Please enter capabilities of your product")
    private String capabilities;

    @Column(name = "type")
    private String type;


    private String userEmail;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @Transient
    private String base64Image;
}