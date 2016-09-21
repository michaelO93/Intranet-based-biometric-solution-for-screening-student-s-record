package unnbiometrics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Charles
 */

import javax.swing.*;
import java.util.regex.*;
import java.text.*;

public class InformationChecker {
    private static final String GSMNO = "[[234]{3},[0]{1}][7-8]{1}[0-9]{9}|[0-9]{11}";
    private static final String USERNAME="([ ]{0,5}[A-Z]{2,50}[ ]{0,5}[A-Z]{0,25}[ ]{0,5}[A-Z,.]{0,25})";
    private static final String LGA="([A-Z,0-9,-,_]{2,35})";
    private static final String LOCATION="[A-Z,0-9,-_,;,.,[ ]{0,5}]{3,60}";
    private static final String MATRIC = "[0-9]{4}/[0-9]{4}";
    private static final String EMAIL="[a-z]{2}[a-z,0-9,_.]{1,15}[@]{1}[a-z,0-9,_.]{3,20}[.]{1}[a-z]{3}";
  
    private static final String SURNAME="([A-Z]{1,250}[-]{0,1}[A-Z]{0,250})";
    private static final String MIDNAME="[A-Z,[.]{0,1}]{0,200}";
    private static final String STAFFNUM="[A-Z,0-9]{1,2}[ ]{0,1}[0-9]{1,3}";
    private static final String DEPART = "[A-Z,0-9,-,[ ],.&{0,5}]{3,80}";
    private static final String BLOODGROUP = "[A-Z]{1,2}";
    private static final String ADDRESS="([A-Z,0-9,-_,;.#[ ]{0,7}]{3,400})[.]{0,1}";
    private static final String TITLE="([A-Z,a-z,.,[ ]{0,4}{3,30})[.]{0,1}";
    private static final String CO_NAME_CITY="([A-Z,a-z,.;,-,[ ]{0,4}]{3,500})[.]{0,1}";
    
    private Matcher matcher;
    private Pattern pattern;
    /** Creates a new instance of PatternCheck */
    public InformationChecker() {
    }
    
    
    
    public boolean checkSurname(String name2)
    {
        pattern=Pattern.compile(SURNAME,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(name2);
        if(matcher.matches())
        {
           // System.out.println("surname is  good");
            return true;
        }
        //System.out.println("surname is  bad");
         return false;
    }
    
    public boolean checkMidname(String Mname2)
    {
        pattern=Pattern.compile(MIDNAME,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(Mname2);
        if(matcher.matches())
        {
            System.out.println("It is good");
            return true;
        }
        System.out.println("It is bad");
         return false;
    }
     public boolean checkTitle(String ti2)
    {
        pattern=Pattern.compile(TITLE,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(ti2);
        if(matcher.matches())
        {
         //  System.out.println("It is good");
            return true;
        }
        //System.out.println("It is bad");
         return false;
    }
    
    
    public boolean checkstaffnum(String num2)
    {
        pattern=Pattern.compile(STAFFNUM,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(num2);
        if(matcher.matches())
        {
            return true;
        }
         return false;
    }
    
     public boolean checkDepart(String depart2)
    {
        pattern=Pattern.compile(DEPART,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(depart2);
        if(matcher.matches())
        {
            return true;
        }
         return false;
    }
    
    
     public boolean checkGsm(String gsm)
    {
        pattern=Pattern.compile(GSMNO);
        matcher=pattern.matcher(gsm);
        if(matcher.matches())
        {
          return true;
        }
         return false;
    }
    
     public boolean checkBloodGroup(String group2)
    {
        pattern=Pattern.compile(BLOODGROUP,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(group2);
        if(matcher.matches())
        {
          return true;
        }
         return false;
    }
    
     public boolean checkAddress(String add2)
    {
        pattern=Pattern.compile(ADDRESS,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(add2);
        if(matcher.matches())
        {
           // System.out.println("it is true ifor good");
          return true;
         
        }
         //System.out.println("it is true in is bad");
         return false;
         
    }
    
    
    
    public boolean checkMatNo (String matric){
        pattern = Pattern.compile(MATRIC,Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(matric);
        if (matcher.matches()){
          
           return true;
        }else{
          
            return false;  
        }
   }
     
     
     
     
     public boolean checksetup_infor(String co_name2)
    {
        pattern=Pattern.compile(CO_NAME_CITY,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(co_name2);
        if(matcher.matches())
        {
           // System.out.println("surname is  good");
            return true;
        }
        //System.out.println("surname is  bad");
         return false;
    }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
    
    
    
    
    
    
   
   
    
    public boolean checkUserName(String username,String label,String format)
    {
        pattern=Pattern.compile(USERNAME,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(username);
        if(matcher.matches())
        {
            //display("Valid "+label+" .Your "+label+"\n should be in this format:\n"+format+"\nThank You");
            return true;
        }
        //display("Invalid "+label+" .Your "+label+"\n should be in this format:\n"+format+"\nThank You");
        return false;
    }
    public boolean checklGA(String username,String label,String format)
    {
        pattern=Pattern.compile(LGA,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(username);
        if(matcher.matches())
        {
            //display("Valid "+label+" .Your "+label+"\n should be in this format:\n"+format+"\nThank You");
            return true;
        }
        //display("Invalid "+label+" .Your "+label+"\n should be in this format:\n"+format+"\nThank You");
        return false;
    }
    public boolean checkLocation(String loc,String label,String format)
    {
        pattern=Pattern.compile(LOCATION,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(loc);
        if(matcher.matches())
        {
           // display("Valid "+label+". Your "+label+"\n should be in this format:\n"+format+"\nThank You");
            return true;
        }
        //display("Invalid "+label+". Your "+label+"\n should be in this format:\n"+format+"\nThank You");
        return false;
    }
    public boolean checkEmail(String email,String label,String format)
    {
        pattern=Pattern.compile(EMAIL);
        matcher=pattern.matcher(email);
        if(matcher.matches())
        {
             //display("Valid "+label+" .Your "+label+"\n should be in this format:\n"+format+"\nThank You");
             return true;
        }
         //display("Invalid "+label+" .Your "+label+"\n should be in this format:\n"+format+"\nThank You");
         return false;
    }
    
    
     public boolean checkYear(String input) {
        //pattern = Pattern.compile(YEAR);
        matcher = pattern.matcher(input);
        if (matcher.matches()){
           // display ("Valid Year Number: Congratulations!!!.");
           return true;
        }else{
            //display ("Invalid Year Number: Enter Valid Year Number.");
            return false; 
            
        }
    }
     
     public boolean checkNext(String input) {
       // pattern = Pattern.compile(YEAR);
        matcher = pattern.matcher(input);
        if (matcher.matches()){
           return true;
        }else{
            return false;  
        }
    }
     
   
   
    public boolean checkSession(String yearA,String yearB){
        int yearAInt;
        int yearBInt;
        boolean check = false;
        if (checkYear (yearA) && checkYear(yearB)){
            try{
                //ensure year difer by 1,interface B-A=1
                yearAInt = Integer.parseInt(yearA);
                yearBInt = Integer.parseInt(yearB);
                int dif = yearBInt - yearAInt;
                if(dif == 1){
                    check = true;
                }else {
                   //display ("Invalid session: Enter valid session like 2005 / 2006") ;
                }
            }catch(NumberFormatException exc){
               // display(exc.toString());
            }
                         
        }else{
            //display ("Invalid session format");
        }
        return check;
      
    }
    public String getSession(String yearA,String yearB){
        String session = yearA + "/" + yearB;
        return session;
    }
   private void display (String msg){
       JOptionPane.showMessageDialog(null,msg,"INVALID INPUT",2);
   }
   public boolean checkMatch (String topic,String option){
       pattern = Pattern.compile(topic,Pattern.CASE_INSENSITIVE);
       matcher = pattern.matcher(option);
       boolean b=Pattern.matches(topic, EMAIL);
       boolean result = matcher.find();
       return result;
       
   }
   public boolean BreakTopic (String srcTopic, String dbTopic){
       
         JOptionPane.showMessageDialog(null,srcTopic +" " +dbTopic);
       boolean select = false;
       pattern = Pattern.compile("[\\s]+");
       //split topic into individualword
       String[] result = pattern.split(srcTopic);
       
       //check each word againt the topic from database
       for (int i = 0; i <result.length; i++){
         if(checkMatch(result[i],dbTopic)) {
             JOptionPane.showMessageDialog(null,result +" " +dbTopic);
             select = true;
             i++;
         }
      }
       return select;
   }




   public static void main(String []args)
    {
       InformationChecker pc=new InformationChecker();
        //pc.checkMatNo("14163");
        //pc.checkYear("2006");
        //pc.checkSession("2001", "2002");
        //pc.checkCode("PHY 101");
        //pc.checkEmail("info@teledominternational.net");
       // pc.checkGsm("08064500095","GSM Number","2348064500095");
       // pc.checkUserName("Okonkwo Afam Simon", "Client Name","Surname FirstName LastName");
        //pc.checkLocation("5 Sule Abuka Crescent,Opebi,Ikeja", "Location", "No 5 Opi,Nsukka,Enugu");
       // pc.checkAddress("# 5 Sule Abuka, Crescent,Opebi,Ikeja.");
       
        // pc.checkAddress("3 Shogule street Oshodi");
       pc.checkTitle("Lt. Col(rtd)");
      // pc.checkMidname("Ogeneifoghaletochi");

    }
    
}
