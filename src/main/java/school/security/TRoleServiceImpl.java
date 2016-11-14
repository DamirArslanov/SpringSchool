package school.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ArslanovDamir on 09.10.2016.
 */
@Service
public class TRoleServiceImpl implements TRoleService {

    private TRoleDao tRoleDao;
    @Autowired
    public void settRoleDao(TRoleDao tRoleDao) {
        this.tRoleDao = tRoleDao;
    }

    @Override
    @Transactional
    public TRole getTRoleById(int id) {
        return this.tRoleDao.getTRoleById(id);
    }

    @Override
    @Transactional
    public TRole getTRoleByName(String roleName) {
        return this.tRoleDao.getTRoleByName(roleName);
    }

    @Override
    @Transactional
    public List<TRole> T_ROLES_LIST() {
        return this.tRoleDao.T_ROLES_LIST();
    }
}
