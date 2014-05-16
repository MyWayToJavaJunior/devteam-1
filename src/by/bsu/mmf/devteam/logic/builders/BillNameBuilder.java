package by.bsu.mmf.devteam.logic.builders;

import java.util.Calendar;

public class BillNameBuilder {
    private static final String INVOICE = "INVOICE-";

    public static String createBillName(String name) {
        int number = 1;
        if(!name.equals("")){
            name = name.replaceFirst("-", "+");
            number += Integer.parseInt(name.substring(name.indexOf("+") + 1, name.indexOf("-")));
        }
        Calendar now = Calendar.getInstance();
        return INVOICE + number + "-" + now.get(Calendar.YEAR);
    }

}
