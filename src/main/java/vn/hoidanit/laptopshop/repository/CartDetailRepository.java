package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.laptopshop.domain.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    List<CartDetail> findByCart(Cart cart);

    CartDetail findByCartAndProduct(Cart cart, Product product);

    void deleteByCartAndProduct(Cart cart, Product product);

    void deleteByCart(Cart cart);

}
