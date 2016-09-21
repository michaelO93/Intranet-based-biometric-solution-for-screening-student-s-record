/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics.model;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import unnbiometrics.database.Database;
import unnbiometrics.database.Map;

/**
 *
 * @author michael-prime
 */
public class Student {
    public enum Gender { MALE, FEMALE }
    public final String regNumber;
	public final String name;
	public final String midname;
	public final String surname;
	public final String department;
	public final int level;
	public final String address;
	public final String email;
	public final String phone;
	public final Date birth;
	public final Gender gender;

    public Student(String regNumber, 
            String name, String midname, String surname, 
            String department, int level, String address, 
            String email, String phone, Date birth, Gender gender) {
        Objects.requireNonNull(regNumber);
        Objects.requireNonNull(name);
        
        this.regNumber = regNumber;
        this.name = name;
        this.midname = midname;
        this.surname = surname;
        this.department = department;
        this.level = level;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.birth = birth;
        this.gender = gender;
    }
    
    public Department loadDepartment() throws IOException {
        return Database.helper(Department.class).get(department);
    }
    
    public static Student load(String regNumber) throws IOException {
        return Database.helper(Student.class).get(regNumber);
    }
    
    public static final class Builder {
        public String regNumber;
        public String name;
        public String midname;
        public String surname;
        public String department;
        public int level;
        public String address;
        public String email;
        public String phone;
        public Date birth;
        public Gender gender;
        
        public Builder(Student s) {
            this.regNumber = s.regNumber;
            this.name = s.name;
            this.midname = s.midname;
            this.surname = s.surname;
            this.department = s.department;
            this.level = s.level;
            this.address = s.address;
            this.email = s.email;
            this.phone = s.phone;
            this.birth = s.birth;
            this.gender = s.gender;
        }

        public Builder() {
        }
        
        public Student build() {
            return new Student(regNumber, name, midname, surname, 
                    department, level, address, 
                    email, phone, birth, gender);
        }
    }
	
    public static final class Factory extends unnbiometrics.database.Factory<Student> {
        public Factory() {
            super(Student.class,
                    "regNumber", "name", "midname", "surname",
                    "department", "level", "address", "email", 
                    "phone", "birth", "gender");
        }

        @Override
        public Student from(Map map) {
            boolean male = map.getStr("gender").toLowerCase().startsWith("m");
            return new Student(map.getAny("regNumber"),
                    map.getStr("name"),
                    map.getStr("midname"),
                    map.getStr("surname"),
                    map.getStr("department"),
                    map.getInt("level"),
                    map.getStr("address"),
                    map.getStr("email"),
                    map.getStr("phone"),
                    map.getAny("birth"),
                    male ? Gender.MALE : Gender.FEMALE);
        }

        @Override
        public Object get(Student s, String field) {
            switch(field.toLowerCase()) {
                case "regnumber": return s.regNumber;
				case "name": return s.name;
				case "midname": return s.midname;
				case "surname": return s.surname;
				case "department": return s.department;
				case "level": return s.level;
				case "address": return s.address;
				case "email": return s.email;
				case "phone": return s.phone;
				case "birth": return s.birth;
				case "gender": return s.gender == Gender.MALE ? "m" : "f";
                default: throw new IllegalArgumentException(field);
            }
        }
        
        
    }
}
