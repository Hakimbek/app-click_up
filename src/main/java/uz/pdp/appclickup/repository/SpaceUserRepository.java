package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appclickup.entity.space.SpaceUser;

import java.util.UUID;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, UUID> {

    @Transactional
    @Modifying
    void deleteAllBySpaceId(UUID space_id);
}
