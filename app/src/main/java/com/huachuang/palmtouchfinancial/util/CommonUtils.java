package com.huachuang.palmtouchfinancial.util;

import android.content.Context;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Asuka on 2017/2/28.
 */

public class CommonUtils {

    public static boolean validatePhone(String phoneNumber) {
        String regExp = "^[1][0-9]{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNumber);
        return m.find();
    }

    public static boolean validateEmail(String email) {
        String regExp = "^[\\w\\.]+@[\\w\\.]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(email);
        return m.find();
    }

    public static boolean validateNumber(String name) {
        String regExp = "^\\d+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(name);
        return m.find();
    }

    private static CSVReader csvReader = null;

    private static CSVReader getReader(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("district-succinct.csv");
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            csvReader = new CSVReader(fileReader, ' ');
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvReader;
    }

    public static List<String[]> getDistricts(Context context, String keyword) {
        CSVReader reader = getReader(context);
        List<String[]> districts = new ArrayList<>();
        try {
            if (reader != null) {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    if (nextLine.length > 3 && nextLine[2].equals(keyword)) {
                        districts.add(nextLine);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return districts;
    }
}
