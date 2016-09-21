/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics;

/**
 *
 * @author michael-prime
 */
public class fillDepartment {

    private int dept_id;
    private String dept_name;
    private String department_lc;
    private int faculty_id;
    private String facN;

    
    public fillDepartment() {
    }

    public fillDepartment(int dept_id, String dept_name, String facultyName) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
        //this.department_lc = department_lc;
        //this.faculty_id = faculty_id;
        this.facN  = facultyName;

    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDepartment_lc() {
        return department_lc;
    }

    public void setDepartment_lc(String department_lc) {
        this.department_lc = department_lc;
    }

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }

    @Override
    public String toString() {
        return "fillDepartment{" + "dept_id=" + dept_id + ", dept_name=" + dept_name + ", department_lc=" + department_lc + ", faculty_id=" + faculty_id + '}';
    }

    public String getFacN() {
        return facN;
    }

    public void setFacN(String facN) {
        this.facN = facN;
    }


}
