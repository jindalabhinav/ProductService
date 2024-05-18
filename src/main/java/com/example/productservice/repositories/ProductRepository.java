package com.example.productservice.repositories;

import com.example.productservice.models.Product;
import com.example.productservice.projections.ProductWithIdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    // region Declarative Queries

    // Optional<Product> findById(Long id);
    // Product save(Product product);

    // endregion

    // region HQL Queries

    // We don't have a '*' after SELECT, but 'p', since this is following HQL convention
    // Also, here the method name doesn't matter, but the query does
    @Query("select p from Product p where p.title = :title")
    List<Product> foo(@Param ("title") String title);

    // Now if we don't want the entire data from the Product class, we can use a Partial Projection
    // Partial Projections are used to select only a few fields from the table
    @Query("select p.id, p.title from Product p where p.title = :title")
    List<ProductWithIdAndTitle> foo2(@Param ("title") String title);

    // endregion

    // region Native Queries

    @Query(value = "select * from Product p where p.title = :title", nativeQuery = true)
    List<Product> fooNative(@Param ("title") String title);

    // Now if we don't want the entire data from the Product class, we can use a Partial Projection
    // Partial Projections are used to select only a few fields from the table
    @Query(value = "select p.id, p.title from Product p where p.title = :title", nativeQuery = true)
    List<ProductWithIdAndTitle> foo2Native(@Param ("title") String title);

    // endregion
}
