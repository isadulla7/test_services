package uz.fido.email.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.fido.email.entity.Role;
import uz.fido.email.entity.emun.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

   Role findByRoleName(RoleName roleName);
}
