package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
//    @Query("select p from Product p where (p.cost <= :maxCost or :maxCost is null) and " +
//            "(p.cost >= :minCost or :minCost is null)")
//    List<Product> filterProducts(@Param("minCost") Long minCost,
//                                 @Param("maxCost") Long maxCost,
//                                 @Param("page") Integer page,
//                                 @Param("size") Integer size);
}
