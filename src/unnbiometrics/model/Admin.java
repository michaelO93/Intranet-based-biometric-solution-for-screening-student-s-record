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
public class Admin {
    public final String username;
    public final String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public boolean matchesPassword(String password) {
        return this.password.equals(password);
    }
    
     public static Admin load(String username) throws IOException {
        return Database.helper(Admin.class).get(username);
    }
    public static final class Factory extends unnbiometrics.database.Factory<Admin> {
        public Factory() { super(Admin.class, "username", "password"); }

        @Override
        public Admin from(Map map) {
            return new Admin(
                    map.getStr("username"), 
                    map.getStr("password"));
        }

        @Override
        public Object get(Admin admin, String field) {
            switch(field.toLowerCase()) {
                case "username": return admin.username;
                case "password": return admin.password;
                default: throw new IllegalArgumentException(field);
            }
        }
    }
}
