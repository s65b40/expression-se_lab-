package com.company;
import  java.util.regex.*;
/**
 * Created by 56363 on 2016/9/20.
 */
public class Judge {
    public int judge(String s)
    {

        Pattern pattern1 = Pattern.compile("^!d/d\\s[a-zA-Z]+");
        Matcher matcher1 = pattern1.matcher(s);
        boolean b= matcher1.matches();
        if(b)
        {
            return 0;  //返回0代表是表达式求导
        }
        Pattern pattern2 = Pattern.compile("\\!simplify.*");
        Matcher matcher2 = pattern2.matcher(s);
        b = matcher2.matches();
        if(b)
        {
            return 1;//返回1代表表达式化简
        }
        Pattern pattern3 = Pattern.compile("\\s|\\.|@|#|$|%|&|,|:|\"");
        Matcher matcher3 = pattern3.matcher(s);
        //b = matcher3.find();
        b = s.matches("\\s|\\.|@|#|$|%|&|,|:");
        if(b)
        {
            return -1;  //返回-1代表表达式非法
        }
        return 2; //返回2代表正常输入表达式

    }

}
