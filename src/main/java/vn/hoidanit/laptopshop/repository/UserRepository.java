package vn.hoidanit.laptopshop.repository;

import java.util.List;
import java.lang.String;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hoidanit); // hibernate tu dong convert thanh cau truy van sql

    List<User> findByEmail(String email);

    List<User> findAll();
}