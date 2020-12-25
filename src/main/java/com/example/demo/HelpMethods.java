package com.example.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelpMethods {

    //this class exist, to ensure smaller classes, which is good!
    //low coupling good? ikke sikker, men det hjælper med en af de der design regler der larman snakker om.

    public boolean addDeleted(boolean isDeleted, int memberNumber){
        if (isDeleted){
            return memberNumber == 1;
        }
        else {
            return memberNumber == 0;
        }
    }

    public int yearsBetween(Date birthday){
        int yearsBetween;
        Date todayDate = java.util.Calendar.getInstance().getTime(); //current date
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        int d1 = Integer.parseInt(formatter.format(birthday));
        int d2 = Integer.parseInt(formatter.format(todayDate));

        yearsBetween = (d2-d1)/10000;

        return yearsBetween;
    }
}
