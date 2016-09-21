/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics.database;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import unnbiometrics.model.Student;

/**
 *
 * @author michael-prime
 */
public class Tester {
    public static void main(String[] args) {
//        Database.DEBUGGING = true;
        Logger LOGGER = Logger.getLogger(Tester.class.getName());
        
        try {
            
            Student franklin = Student.load("2011/176524");
            System.out.println("His name is " 
                    + franklin.name + " " + franklin.surname);
            Student.Builder sb = new Student.Builder(franklin);
            sb.name = "Tochukwu";
            sb.midname = "Franklin";
            sb.surname = "Ene";
            sb.gender = Student.Gender.MALE;
            sb.birth = new Date(94, 7, 7);
            sb.address = "No 5A, Still Not Important";
            sb.department = "Electronic Engineering";
            sb.level = 5;
            sb.phone = "08098765476";
            sb.email = "tochukwu.ene.176524@unn.edu.ng";
            Database.helper(Student.class).update(sb.build());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
