/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics.model;

import java.io.IOException;
import java.util.List;
import unnbiometrics.database.Database;
import unnbiometrics.database.Map;

/**
 *
 * @author michael-prime
 */
public class Faculty {

    public final String name;
    
    public Faculty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public List<Department> loadDepartments() throws IOException {
        return Database.helper(Department.class)
                .getAll(new Map("faculty", name));
    }
    
    public static Faculty load(String name) throws IOException {
        return Database.helper(Faculty.class).get(name);
    }
    
    public static final class Factory 
            extends unnbiometrics.database.Factory<Faculty> {
        public Factory() {
            super(Faculty.class, "name");
        }
        
        @Override
        public Faculty from(Map map) {
            return new Faculty(map.getStr("name"));
        }

        @Override
        public Object get(Faculty f, String name) {
            if (name.equalsIgnoreCase("name")) return f.name;
            throw new IllegalArgumentException(name);
        }
    }
}
