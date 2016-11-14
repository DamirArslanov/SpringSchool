package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.ChildrenDao;
import school.entity.Children;
import school.service.interfaces.ChildrenService;

import java.util.List;

/**
 * Created by ArslanovDamir on 23.09.2016.
 */
@Service
public class ChildrenServiceImpl implements ChildrenService {

    private ChildrenDao childrenDao;
    @Autowired
    public void setChildrenDao(ChildrenDao childrenDao) {
        this.childrenDao = childrenDao;
    }



    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void addChildren(Children children) {
        this.childrenDao.addChildren(children);
    }

    @Override
    @Transactional
//    @PreAuthorize("hasRole('ROLE_ADMIN') or #children.teacher.username == principal.getUsername()")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void updateChildren(@P("children") Children children) {
        this.childrenDao.updateChildren(children);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void removeChildren(int id) {
        this.childrenDao.removeChildren(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_USER')")
    public Children getChildrenById(int id) {
        return this.childrenDao.getChildrenById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Children> listChildrens() {
        return this.childrenDao.listChildrens();
    }
}
