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

    public static boolean validateInvitationCode(String verificationCode) {
        String regExp = "^[0-9A-Z]{6}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(verificationCode);
        return m.matches();
    }

    public static boolean validatePassword(String password) {
        String regExp = "^.{6,}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean validateNumber(String number) {
        String regExp = "^\\d+(?:\\.\\d+)?$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public static boolean validateYear(String year) {
        String regExp = "^\\d{4}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(year);
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

    public static String generateRandomString(int length) {
        return null;
    }

    public static String mosaicIdentityCard(String identityCard) {
        return identityCard.substring(0, 8) + "******" + identityCard.substring(14);
    }

    public static String mosaicBankCard(String bankCard) {
        return bankCard.substring(0, bankCard.length() - 10) + "******" + bankCard.substring(bankCard.length() - 4);
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
        return GlobalParams.SERVER_URL_HEAD + "/header/" + UserManager.getUserID() + ".jpg";
    }

    public static String getHeaderUrl(long userID) {
        return GlobalParams.SERVER_URL_HEAD + "/header/" + userID + ".jpg";
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

    public static boolean checkBankCard(String bankCard) {
        if(bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        return bit != 'N' && bankCard.charAt(bankCard.length() - 1) == bit;
    }

    private static char getBankCardCheckCode(String nonCheckCodeBankCard){
        if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

    public static void main(String[] args) {
        System.out.println(validateNumber("100"));
        System.out.println(validateNumber("100.0"));
        System.out.println(validateNumber(".1"));
        System.out.println(validateNumber("0.1"));
        System.out.println(validateNumber("1.1.1"));
        System.out.println(validateNumber("1."));
        System.out.println(validateNumber("1..1"));
    }
}
