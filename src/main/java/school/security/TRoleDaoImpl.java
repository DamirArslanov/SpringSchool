package school.security;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Cheshire on 09.10.2016.
 */
@Repository
public class TRoleDaoImpl implements TRoleDao {

    @Autowired
    public SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public TRole getTRoleById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        TRole tRole = (TRole) session.load(TRole.class, new Integer(id));
        return tRole;
    }

    @Override
    public TRole getTRoleByName(String roleName) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM TRole troles WHERE troles.role = (:roleName)";
        System.out.println("************ФАБРИКА СОЗДАНИЯ ОДНОЙ РОЛИ ОТКРЫТА!***************");
        Query query = session.createQuery(hql).setParameter("roleName", roleName);
        TRole tRole = (TRole) query.getSingleResult();
        System.out.println("************ФАБРИКА ГОВОРИТ!***************");
        System.out.println("РОЛЬ: " + tRole.getRole());
        System.out.println("************ФАБРИКА НЕГОВОРИТ!***************");
        return tRole;
    }

    @Override
    public List<TRole> T_ROLES_LIST() {
        Session session = this.sessionFactory.getCurrentSession();
        List<TRole> tRolesList = session.createQuery("FROM TRole").list();
        return tRolesList;
    }
}
