package com.example.animarol;

public class FormControl {
    public static final String  EMPTYSTRING = "";


    public static boolean IsANumber(String formNumber){
        boolean isANumber = true;
        try{
            int number = Integer.parseInt(formNumber) ;
        }catch (Exception e){
            isANumber = false;
        }

        return isANumber;
    }
}
