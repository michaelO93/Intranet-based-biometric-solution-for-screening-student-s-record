package unnbiometrics.model;

import java.io.IOException;
import unnbiometrics.database.Database;
import unnbiometrics.database.Map;

/**
 *
 * @author michael-prime
 */
public class Course {
    public final String code;
    public final String name;
    public final int units;

    public Course(String code, String name, int units) {
        this.code = code;
        this.name = name;
        this.units = units;
    }

    @Override
    public String toString() {
        return code;
    }
    
    public static Course load(String code) throws IOException {
        return Database.helper(Course.class).get(code);
    }
    
    public static final class Factory extends unnbiometrics.database.Factory<Course> {
        public Factory() { super(Course.class, "code", "name", "units"); }

        @Override
        public Course from(Map map) {
            return new Course(map.getStr("code"), 
                    map.getStr("name"), 
                    map.getInt("units"));
        }

        @Override
        public Object get(Course c, String field) {
            switch (field.toLowerCase()) {
                case "code": return c.code;
                case "name": return c.name;
                case "units": return c.units;
                default: throw new IllegalArgumentException(field);
            }
        }
        
        
    }
}
