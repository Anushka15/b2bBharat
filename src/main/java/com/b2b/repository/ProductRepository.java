package com.b2b.repository;

import com.b2b.model.Product;
import com.b2b.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByUserEmail(String userEmail);
    //Product findByProductId(Integer productId);
}
