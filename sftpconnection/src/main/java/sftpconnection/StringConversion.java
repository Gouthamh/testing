package sftpconnection;

public class StringConversion {
    public static void main(String[] args) {
        String inputString = "usage_units,usage_discount,id,testdataone,holiday_date";
        String endofline = ";";
        String Delimiter = "|";
        
        
        String[] elements = inputString.split(",");
       
        StringBuilder stringBuilder = new StringBuilder();

        for (String element : elements) {
            stringBuilder.append(element).append(Delimiter);
            
            //System.out.println("element--> "+element);
        }

        // Remove the extra "-" at the end of the string
        if (stringBuilder.length() > 0) {
           stringBuilder.setLength(stringBuilder.length()-Delimiter.length());
        }

        String convertedString = stringBuilder.toString();
        if(inputString=="") {
        	System.out.println("");
        }
        else {
        System.out.println(convertedString+endofline);
        }
    }
}
