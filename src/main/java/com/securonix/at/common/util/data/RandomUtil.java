package com.securonix.at.common.util.data;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

public class RandomUtil {

    private static Random random = new Random();

    public static String removeLeadingZeros(String inValue){

        if(!Objects.equals(inValue,null))
        return inValue.replaceFirst("[^0+(?!$]","");
        else
            return null;
    }

    public static String getAmount(Double min, Double max){
        DecimalFormat df = new DecimalFormat("0.00");
        double amount;
        do{
            amount = random.nextDouble();
        }while(amount==1.00);

        return df.format(min+amount*(max-min));
    }

    public static String getRandomNumeric(int digitsCount){

        if(digitsCount<1){
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digitsCount; i++) {
            sb.append(random.nextInt(9));
        }
        return removeLeadingZeros(sb.toString());
    }

    public static String getRandomString(int wordLength){

        if(wordLength<1){
            return null;
        }
        char[] word = new char[wordLength];
        for (int i = 0; i < wordLength; i++) {
            word[i] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }

    public String getUniqueId(){
        return "veyyon" + LocalDate.now().format(DateTimeFormatter
                .ofPattern("yyyyMMddHHmmssSSS"));
    }
}
