package com.example.springsecurityjwt.repository;

import com.example.springsecurityjwt.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    @Query("SELECT c FROM Cart c WHERE c.product=?1 AND c.user=?2")
    Cart findByProductAndUser(Integer productId,Integer Customer);

    @Query("UPDATE Cart c SET c.quantity=?2 WHERE c.product=?1 AND c.user=?3 ")
    void updateQuantity(Integer productId, Integer quantity, Integer customerId);

    @Query("DELETE FROM Cart c WHERE c.product=?1 AND c.user=?2")
    void removeCart(Integer productId,Integer customerId);

}
