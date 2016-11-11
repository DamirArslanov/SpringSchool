package school.security;

import java.util.List;

/**
 * Created by Cheshire on 09.10.2016.
 */
public interface TRoleService {

    public TRole getTRoleById(int id);

    public TRole getTRoleByName(String roleName);

    public List<TRole> T_ROLES_LIST();
}
