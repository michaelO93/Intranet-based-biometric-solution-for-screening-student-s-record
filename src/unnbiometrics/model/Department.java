/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics.model;

import java.io.IOException;
import unnbiometrics.database.Database;
import unnbiometrics.database.Map;

/**
 *
 * @author michael-prime
 */
public class Department {
    public final String name;
    public final int levels;
    public final String faculty;

    public Department(String name, int levels, String facultyName) {
        this.name = name;
        this.levels = levels;
        this.faculty = facultyName;
    }
    
    public Faculty loadFaculty() throws IOException {
        return Database.helper(Faculty.class).get(faculty);
    }

    @Override
    public String toString() {
        return name;
    }
    
    public static final class Factory extends unnbiometrics.database.Factory<Department> {
        public Factory() { super(Department.class, "name", "levels", "faculty"); }

        @Override
        public Department from(Map map) {
            return new Department(
                    map.getStr("name"), 
                    map.getInt("levels"), 
                    map.getStr("faculty"));
        }

        @Override
        public Object get(Department dept, String field) {
            switch (field.toLowerCase()) {
                case "name": return dept.name;
                case "levels": return dept.levels;
                case "faculty": return dept.faculty;
                default: throw new IllegalArgumentException(field);
            }
        }
        
        public static Department load(String name) throws IOException {
            return Database.helper(Department.class).get(name);
        }
    }
}
