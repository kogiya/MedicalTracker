package com.georgebrown.comp3074.a2100984638;

/**
 * Created by Owner on 11/8/2017.
 */

public class Validation {
    //default constructor

    public Validation() {
    }

    //to find if the inputed value exists or not
    public static boolean isMissing(String data){
        if(data.equals("") || data.equals(null)){
            return true;
        }
        else{
            return false;
        }
    }

    //check if the inputed value is all alphabet
    public static boolean isAlphabet (String data){
		/*validate isMissing() first otherwise empty value returns true.
		 * or enable following code
		 *
		if(!isMissing(data)){
			return false;
		}
		*/
        char[] chars = data.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
    //Refeence: https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters


    //check if the inputed value is all numeric
    public static boolean isNumeric (String data){
        String pattern = "[0-9]+";
        return data.matches(pattern);
    }
}
