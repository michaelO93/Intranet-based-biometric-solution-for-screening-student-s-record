/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import unnbiometrics.model.Admin;
import unnbiometrics.model.Course;
import unnbiometrics.model.Department;
import unnbiometrics.model.Faculty;
import unnbiometrics.model.Identity;
import unnbiometrics.model.Student;

/**
 *
 * @author michael-prime
 * @param <T>
 */
public abstract class Factory<T> {
    private static final java.util.Map<Class<?>, Factory<?>> 
            FACTORIES = new HashMap<>();
    
    @SuppressWarnings("unchecked")
    public static <T> Factory<T> get(Class<? extends T> c) {
        return (Factory<T>) FACTORIES.get(c);
    }
    
    static {
        include(new Faculty.Factory());
        include(new Department.Factory());
        include(new Course.Factory());
        include(new Student.Factory());
        include(new Identity.Factory());
        include(new Admin.Factory());
    }
    
    public final List<String> fields;
    public final Class<? extends T> clazz;

    public abstract T from(Map map);
    public abstract Object get(T t, String field);

    public Factory(Class<? extends T> clazz, String... keys) {
        this.clazz = clazz;
        this.fields = Arrays.asList(keys);
    }
    
    public static void include(Factory factory) {
        FACTORIES.put(factory.clazz, factory);
    }

    public final String className() {
        return clazz.getSimpleName();
    }

    public final List<String> fields() {
        return fields;
    }

    public final String primary() {
        return fields.get(0);
    }

    public final List<T> fromMaps(List<Map> maps) {
        List<T> t = new ArrayList<>(maps.size());
        for (Map map : maps) {
            t.add(from(map));
        }
        return t;
    }

    public final Map map(T t) {
        Map map = new Map();
        for (String field : fields) {
            map.put(field, get(t, field));
        }
        return map;
    }

    public final Map map(T t, Collection<String> wanted) {
        Map map = new Map();
        for (String field : fields) {
            if (wanted.contains(field)) {
                map.put(field, get(t, field));
            }
        }
        return map;
    }

    public final List<Map> maps(List<T> list) {
        List<Map> maps = new ArrayList<>(list.size());
        for (T t : list) {
            maps.add(map(t));
        }
        return maps;
    }

    public final List<Map> maps(List<T> list, String... keys) {
        return maps(list, Arrays.asList(keys));
    }

    public final List<Map> maps(List<T> list, Collection<String> wanted) {
        List<Map> maps = new ArrayList<>(list.size());
        for (T t : list) {
            maps.add(map(t, wanted));
        }
        return maps;
    }
}
