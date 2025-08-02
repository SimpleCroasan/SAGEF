package EISI.SAGEF.Repositories.Users;

import EISI.SAGEF.Entities.Users.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
}