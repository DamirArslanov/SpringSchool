package school.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cheshire on 14.10.2016.
 */
public class ArrayPost {

    List<Integer> integers;

    public ArrayPost() {
    }

    public List<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(List<Integer> integers) {
        this.integers = integers;
    }

    Map<Integer, Integer> rat = new HashMap<>();

    public Map<Integer, Integer> getRat() {
        return rat;
    }

    public void setRat(Map<Integer, Integer> rat) {
        this.rat = rat;
    }
}
