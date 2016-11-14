package school.dao.interfaces;



import school.entity.Children;

import java.util.List;

/**
 * Created by ArslanovDamir on 23.09.2016.
 */
public interface ChildrenDao {

    public void addChildren(Children children);

    public void updateChildren(Children children);

    public void removeChildren(int id);

    public Children getChildrenById(int id);

    public List<Children> listChildrens();
}
