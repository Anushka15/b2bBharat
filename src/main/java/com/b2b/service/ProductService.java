package com.b2b.service;

import com.b2b.exception.FileStorageException;
import com.b2b.exception.MyFileNotFoundException;
import com.b2b.model.Category;
import com.b2b.model.Product;
import com.b2b.model.Type;
import com.b2b.model.User;
import com.b2b.repository.ProductRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Product storeProductAsDemand(MultipartFile file,Product product) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            //DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
            product.setFileName(fileName);
            product.setFileType(file.getContentType());
            product.setData(file.getBytes());
            product.setType("DEMAND");
            //return dbFileRepository.save(dbFile);
            return productRepository.save(product);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Product storeProductAsSupply(MultipartFile file,Product product) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            //DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
            product.setFileName(fileName);
            product.setFileType(file.getContentType());
            product.setData(file.getBytes());
            product.setType("SUPPLY");
            System.out.println("STORE PRODUCT AS SUPPLY CALLED*******************");
            //return dbFileRepository.save(dbFile);
            return productRepository.save(product);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /*public Product getProduct(String userEmail) {
        return productRepository.findByUserEmail(userEmail);
    }*/

    public List<Product> getDemandList(String userEmail)
    {
        return jdbcTemplate.query("SELECT * FROM products WHERE user_email=? and type=?", new RowMapper<Product>() {

            public Product mapRow(ResultSet rs,int arg1) throws SQLException{

                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setCapabilities(rs.getString("capabilities"));
                product.setCategory(rs.getString("category"));
                product.setComments(rs.getString("comments"));
                product.setFileName(rs.getString("file_name"));
                product.setFileType(rs.getString("file_type"));
                product.setPriceRange(rs.getString("price_range"));
                product.setProductDescription(rs.getString("product_description"));
                product.setQuantity(rs.getString("quantity"));
                product.setRelatedProducts(rs.getString("related_products"));
                product.setTimeToProduce(rs.getString("time_to_produce"));
                product.setWebsite(rs.getString("website"));
                product.setUserEmail(userEmail);
                product.setType("DEMAND");
                byte[] data =rs.getBytes("data");
                String base64Encoded = Base64.getEncoder().encodeToString(data);
                product.setBase64Image(base64Encoded);
                System.out.println("*******************"+"l"+product.getBase64Image());
                return product;
            }
        },userEmail,"DEMAND");
    }

    public List<Product> getSupplyList(String userEmail)
    {
        return jdbcTemplate.query("SELECT * FROM products WHERE user_email=? and type=?", new RowMapper<Product>() {

            @SneakyThrows
            public Product mapRow(ResultSet rs, int arg1) throws SQLException{

                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setCapabilities(rs.getString("capabilities"));
                product.setCategory(rs.getString("category"));
                product.setComments(rs.getString("comments"));
                product.setFileName(rs.getString("file_name"));
                product.setFileType(rs.getString("file_type"));
                product.setPriceRange(rs.getString("price_range"));
                product.setProductDescription(rs.getString("product_description"));
                product.setQuantity(rs.getString("quantity"));
                product.setRelatedProducts(rs.getString("related_products"));
                product.setTimeToProduce(rs.getString("time_to_produce"));
                product.setWebsite(rs.getString("website"));
                product.setUserEmail(userEmail);
                product.setType("SUPPLY");
                byte[] data =rs.getBytes("data");
                String base64Encoded = Base64.getEncoder().encodeToString(data);
                product.setBase64Image(base64Encoded);
                System.out.println("*******************"+"l"+product.getBase64Image());
                return product;
            }
        }, userEmail,"SUPPLY");
    }




}
