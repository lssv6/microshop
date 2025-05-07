package com.microshop.repository;

import com.microshop.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * This method will return all the products that belongs only to category of the given id.
     *
     * @param id It's the id of the category you want to get the products. {@return a page of
     *     products}
     */
    public Page<Product> findByCategoryId(Long id, Pageable pageable);

    /**
     * This method will return all the products of a category subtree. Let's say that you have cats
     * A-G in the given fashion: A / | \ B C D / /\ E F G
     *
     * <p>If you give id equals the id of C(that's a category), this function will return a page of
     * products that belongs to both C, F and G.
     */
    @Query("SELECT p FROM Product p WHERE p.category IN :categories")
    public Page<Product> findByCategoryIdNested(
            @Param("categories") List<Long> categories, Pageable pageable);
}
