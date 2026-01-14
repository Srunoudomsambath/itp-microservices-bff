package co.istad.sambath.cart.repository;


import co.istad.sambath.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(String userId);

    void deleteByUserId(String userId);

    boolean existsByUserId(String userId);
}