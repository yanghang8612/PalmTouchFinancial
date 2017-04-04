package com.huachuang.palmtouchfinancial.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.opencsv.CSVReader;

import java.io.ByteArrayOutputStream;
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
        return m.matches();
    }

    public static boolean validateVerificationCode(String verificationCode) {
        String regExp = "^[0-9]{6}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(verificationCode);
        return m.matches();
    }

    public static boolean validatePassword(String password) {
        String regExp = "^\\d{6,}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean validateNumber(String number) {
        String regExp = "^\\d+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public static boolean validateChineseName(String name) {
        String regExp = "^[\\u4e00-\\u9fa5]{2,}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    public static String mosaicIdentityCard(String identityCard) {
        return identityCard.substring(0, 8) + "******" + identityCard.substring(14);
    }

    public static String mosaicBankCard(String bankCard) {
        return bankCard;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp,
                                        final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 80, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getHeaderUrl() {
        return GlobalParams.SERVER_URL_HEAD + "/header/" + UserManager.getCurrentUser().getUserId() + ".jpg";
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
