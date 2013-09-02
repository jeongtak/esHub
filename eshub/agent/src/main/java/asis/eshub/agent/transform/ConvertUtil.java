package asis.eshub.agent.transform;

import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: jeongtak
 * Date: 13. 7. 1
 * Time: 오후 1:35
 */
public class ConvertUtil {

    public static <T extends Object> T mapToPojo(Map map, Class<T> type) {

        T t = null;
        try {
            t = type.newInstance();
            BeanUtils.populate(t, map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    public static <T extends Object> List<T> mapToPojoList(List<Map> list, Class<T> type) {

        List<T> result = new ArrayList<T>();

        try {

            for (Map map : list) {
                T t = null;
                t = type.newInstance();
                BeanUtils.populate(t, map);
                result.add(t);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
