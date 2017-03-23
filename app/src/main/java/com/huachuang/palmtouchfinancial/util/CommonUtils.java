package com.huachuang.palmtouchfinancial.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Asuka on 2017/2/28.
 */

public class CommonUtils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

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

    private static CSVReader getReader(Context context, String csvName) {
        try {
            InputStream inputStream = context.getAssets().open(csvName);
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            csvReader = new CSVReader(fileReader, ' ');
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvReader;
    }

    public static List<String[]> getDistricts(Context context, String keyword) {
        CSVReader reader = getReader(context, "district-succinct.csv");
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

    public static String[] getCardType(Context context, String cardNumber) {
        CSVReader reader = getReader(context, "cards-identifier.csv");
        try {
            if (reader != null) {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    if (cardNumber.contains(nextLine[2])) {
                        return nextLine;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
