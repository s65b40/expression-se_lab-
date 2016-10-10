package com.company;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class compute {

    StringBuilder suffix;// 存储转换后的后缀表达式
    StringBuilder com;// 存储计算结果
    String express_just;//存储原装的表达式
    StringBuilder MiddleTolast(String text) {
        /*表达式中缀转后缀*/
        this.express_just = text;
        StringBuilder cal = new StringBuilder();
        cal.append(text);
        cal = TransplateExp(cal);
        Stack<String> sk = new Stack<String>();// 用来转换的栈
        StringBuilder result = new StringBuilder();// 输出结果
        StringBuilder temp = new StringBuilder();// 用来连接多位数字和字母
        StringBuilder tran = new StringBuilder();
        int j = 0;
        //下面是中缀转后缀，利用表达式栈
        for (int i = 0; i < cal.length(); i++) {
            tran.delete(0, tran.length());
            temp.delete(0, temp.length());
            if (cal.charAt(i) == '(') {
                sk.push("(");
            } else if (cal.charAt(i) == ')') {
                while (!sk.isEmpty()) {
                    if (sk.peek().equals("(")) {
                        sk.pop();
                        break;
                    } else {
                        result.append(sk.pop());
                    }
                }
            } else if (cal.charAt(i) == '+' || cal.charAt(i) == '-' || cal.charAt(i) == '*' || cal.charAt(i) == '/') {
                while (!sk.empty() && !sk.peek().equals("(")// 在这里比较优先级，如果栈内的运算符优先级高于外界，那么一直弹栈到低了为止
                        && this.compute_priority(sk.peek()) >= this.compute_priority(cal.charAt(i))) {
                    result.append("#" + sk.pop() + "#");
                }
                sk.push(Character.toString(cal.charAt(i)));
            } else if (Character.isDigit(cal.charAt(i))) {
                j = i;
                while (j < cal.length() && Character.isDigit(cal.charAt(j))) {
                    temp.append(cal.charAt(j));
                    j++;
                }
                result.append("#" + temp.toString() + "#");
                if (j < cal.length() && Character.isLetter(cal.charAt(j))) {
                    sk.push("*");
                }
                i = j - 1;
            } else if (Character.isLetter(cal.charAt(i))) {
                j = i;
                while (j < cal.length() && Character.isLetter(cal.charAt(j))) {
                    temp.append(cal.charAt(j));
                    j++;
                }
                result.append("#" + (temp.toString()) + "#");
                i = j - 1;
            }

        }
        while (!sk.isEmpty()) {
            result.append("#" + sk.pop() + "#");
        }
        this.suffix = result;//把后缀表达式存到this.suffix
        return result;
    }

    StringBuilder TransplateExp(StringBuilder ch) {
		/*
		 * 本方法是用来进行^即乘方的处理，其中参数ch为待处理字符串 采用循环匹配^的方式进行查找，当查找到了^的时候开始向前向后查找起始
		 * 和终结位置，并将这段乘方展开为乘法形式
		 */
        int findindex = 0;
        int startindex = 0;
        int endindex = 0;
        int times = 0;
        StringBuilder temp = new StringBuilder();
        while (ch.indexOf("^") != -1) {
            findindex = ch.indexOf("^");
            startindex = findindex;
            endindex = findindex;
            temp.delete(0, ch.length());
            while (startindex >= 0) {
                // 找起点位置
                if (ch.charAt(startindex) == '+' || ch.charAt(startindex) == '-' || ch.charAt(startindex) == '*'
                        || ch.charAt(startindex) == '/' || ch.charAt(startindex) == '('
                        || Character.isDigit(ch.charAt(startindex))) {
                    break;
                }
                startindex--;
            }
            if (startindex != 0) {
                startindex += 1;
            }
            while (endindex < ch.length()) {
                // 找终点位置
                if (ch.charAt(endindex) == '+' || ch.charAt(endindex) == '-' || ch.charAt(endindex) == '*'
                        || ch.charAt(endindex) == '/' || ch.charAt(endindex) == ')') {
                    break;
                }
                endindex++;
            }
            times = Integer.parseInt(ch.substring(findindex + 1, endindex));// 将乘方次数存储
            for (int i = 0; i < times; i++) {
                temp.append(ch.substring(startindex, findindex));
                temp.append('*');
            }
            temp.setLength(temp.length() - 1);
            ch.replace(startindex, endindex, temp.toString());
        }
        return ch;
    }

    StringBuilder compute_suffix() {
        /*对后缀表达式进行计算，如果是数值就直接计算，如果是字母就接着存到表达式中*/
        Stack<String> sk = new Stack<String>();
        StringBuilder temp = new StringBuilder();
        StringBuilder aBuilder = new StringBuilder();
        StringBuilder bBuilder = new StringBuilder();
        StringBuilder result = new StringBuilder();
        Pattern myPattern = Pattern.compile("#+");//用正则把井号都去掉，并且分割
        if (this.suffix.charAt(0) == '#') {
            this.suffix.delete(0, 1);
        }
        String[] stt = myPattern.split(this.suffix);
        for (int i = 0; i < stt.length; i++) {
            aBuilder.delete(0, aBuilder.length());
            bBuilder.delete(0, bBuilder.length());
            temp.delete(0, temp.length());
            temp.append(stt[i]);
            if (temp.toString().equals("+") && !sk.isEmpty()) {
                aBuilder.append(sk.pop());
                bBuilder.append(sk.pop());
                if (this.isNumeric(aBuilder.toString()) && this.isNumeric(bBuilder.toString())) {
                    sk.push(Integer
                            .toString(Integer.parseInt(aBuilder.toString()) + Integer.parseInt(bBuilder.toString())));

                } else {
                    temp.delete(0, temp.length());
                    temp.append(aBuilder);
                    temp.append("+");
                    temp.append(bBuilder);
                    sk.push(temp.toString());
                }
            } else if (temp.toString().equals("-") && !sk.isEmpty()) {
                aBuilder.append(sk.pop());
                bBuilder.append(sk.pop());
                if (this.isNumeric(aBuilder.toString()) && this.isNumeric(bBuilder.toString())) {
                    sk.push(Integer
                            .toString(Integer.parseInt(bBuilder.toString()) - Integer.parseInt(aBuilder.toString())));

                } else {
                    temp.delete(0, temp.length());
                    temp.append(aBuilder);
                    temp.insert(0, '-');
                    temp.insert(0, bBuilder);
                    sk.push(temp.toString());
                }
            } else if (temp.toString().equals("*") && !sk.isEmpty()) {
                aBuilder.append(sk.pop());
                bBuilder.append(sk.pop());
                if (this.isNumeric(aBuilder.toString()) && this.isNumeric(bBuilder.toString())) {
                    sk.push(Integer
                            .toString(Integer.parseInt(bBuilder.toString()) * Integer.parseInt(aBuilder.toString())));

                } else {
                    temp.delete(0, temp.length());
                    temp.append(aBuilder);
                    temp.append('*');
                    temp.append(bBuilder);
                    sk.push(temp.toString());
                }

            } else if (temp.toString().equals("/") && !sk.isEmpty()) {
                aBuilder.append(sk.pop());
                bBuilder.append(sk.pop());
                if (this.isNumeric(aBuilder.toString()) && this.isNumeric(bBuilder.toString())) {
                    sk.push(Integer
                            .toString(Integer.parseInt(bBuilder.toString()) / Integer.parseInt(aBuilder.toString())));

                } else {
                    temp.delete(0, temp.length());
                    temp.append(bBuilder);
                    temp.insert(0, '/');
                    temp.insert(0, aBuilder);
                    sk.push(temp.toString());
                }
            } else {
                sk.push(temp.toString());
            }
        }
        result.append(sk.pop());
        this.com = result;
        return result;
    }

    StringBuilder simple_conpute(String code) {
        StringBuilder temp = new StringBuilder();
        StringBuilder result = new StringBuilder();
        String[] arr;
        Pattern sourcePattern = Pattern.compile("\\w+=\\d+");
        Matcher sourceMatcher = sourcePattern.matcher(code);
        result.append(this.suffix);
        while (sourceMatcher.find()) {
            temp.delete(0, temp.length());
            arr = sourceMatcher.group().split("=");
            temp.append(result.toString().replaceAll(arr[0], arr[1]));
            result.delete(0, result.length());
            result.append(temp);

        }
        this.suffix = result;
        //this.compute_suffix();
        return result;
    }

    boolean isNumeric(String str) {
        //用来判断是不是数字的字符
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > '9' || str.charAt(i) < '0') {
                return false;
            }
        }
        return true;
    }
    //下面三个方法是同一个方法的重载，都是计算运算符优先级的。
    int compute_priority(StringBuilder p) {
        if (p.equals("+") || p.equals("-")) {
            return 1;
        } else if (p.equals("*") || p.equals("/")) {
            return 2;
        } else if (p.equals("(")) {
            return 3;
        } else {
            return 100;
        }
    }

    int compute_priority(String p) {
        if (p.equals("+") || p.equals("-")) {
            return 1;
        } else if (p.equals("/") || p.equals("/")) {
            return 2;
        } else if (p.equals("(")) {
            return 3;
        } else {
            return 100;
        }
    }

    int compute_priority(Character p) {
        if (p.equals('+') || p.equals('-')) {
            return 1;
        } else if (p.equals('*') || p.equals('/')) {
            return 2;
        } else if (p.equals('(')) {
            return 3;
        } else {
            return 100;
        }
    }
}