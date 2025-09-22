package BBM.repository;

import BBM.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByPhoneOrEmail(String phone, String email);
    Optional<User> findByEmailOrPhone(String email, String phone);
    List<User> findByStateAndDistrictAndBloodGroup(String state, String district, String bloodGroup);
	//User findByUsername(String username);

}
