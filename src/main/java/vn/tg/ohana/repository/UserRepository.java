package vn.tg.ohana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.tg.ohana.repository.model.*;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
           "where upper(u.fullName) like upper(:query) or upper(u.email) like upper(:query) or upper(u.address) like upper(:query) or upper(u.description) like upper(:query) or u.phone like (:query) and u.status = :status")
    List<User> searchAllByStatus(@Param("query") String query, @Param("status") UserStatus status);

    User getByEmailOrPhone(String email, String phone);

    boolean existsByPhoneOrEmail(String phone, String email);

    User findByEmail(String email);

    List<User> findAllByStatus(UserStatus status);

    @Query("SELECT count(u.status) FROM User u")
    Long getQuantityUser(UserStatus userStatus);

    Long countAllByStatus(UserStatus userStatus);

    boolean existsByPhoneOrEmailAndPasswordAndRole(String phone, String email, String password, Role role);

}
