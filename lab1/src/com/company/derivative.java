package com.company;

import java.util.*;
//Considering the conflict of different encoding, the annotation will be in English.
//Sorry for the inconvenience

public class derivative
{
    //this method is used for independent test
	/*
     *
	   public static void main(String args[])
	{
		String formula = "(3x^3+4z)*3z^2+5xyd^6+x*4z-(3*z^3+4*y+3*z)*(45+z)*(5*z+2)-z^2-z*6z+z";//"(3x^3+4x)*3x^2+5x^6+x*4x";
		//String formula = "(3x^3+4z)*3z^2";
		String instr = "!d/d xyd";
		String deri;
		ArrayList<String> varialist = new ArrayList<String>();
		varialist = getVaria(formula); //get the variable array
		formula = addmul(formula);
		deri = varialist.get(getSVaria(instr, varialist));
		System.out.println("Original expression:"+formula);
		System.out.println("Take the deviation of "+deri);
		System.out.println("Result:"+diff(formula, deri));
	}
	 */

    /*
     * this method is used to be connected to other classes, almost same as main method

     */
    public static String io (String formula, String instr)
    {
        String deri;
        ArrayList<String> varialist = new ArrayList<String>();
        varialist = getVaria(formula);
        formula = addmul(formula);
        deri = varialist.get(getSVaria(instr, varialist));
        System.out.println("Original expression:"+formula);
        System.out.println("Take the deviation of "+deri);
        System.out.println("Result:"+diff(formula, deri));
        return diff(formula, deri);
    }

    /*
     * "getVaria" method is used to get all the variables in the expression string
       parameter: String ori, expression string
       return: ArrayList<String>, a list of unduplicated variables
     */
    private static ArrayList<String> getVaria (String ori)
    {
        ArrayList<String> varialist = new ArrayList<String>();
        int strlen;
        //if the "ori" starts with '(' and ends with ')', get rid of brackets
        if (ori.charAt(0) == '(' && ori.charAt(ori.length()-1) == ')')
        {
            ori.substring(1, ori.length()-2);
            strlen = ori.length()-2;
        }
        else
        {
            strlen = ori.length();
        }
        String temp = "";
        char[] ori_split = ori.toCharArray();
        //On getting a letter, begin storing and won't stop until getting a non-letter
        for (int i = 0; i < strlen; i ++)
        {
            if((Integer.valueOf(ori_split[i]) >= 65 && Integer.valueOf(ori_split[i]) <= 90) ||(Integer.valueOf(ori_split[i]) >= 97 && Integer.valueOf(ori_split[i]) <= 122))
            {
                temp = temp + ori_split[i];
            }
            else
            {
                //if not vacant, add to ArrayList
                if(temp != "")
                {
                    varialist.add(temp);
                }
                temp = "";
            }
        }
        if(temp != "")
        {
            varialist.add(temp);
        }
        ArrayList<String> vlist = new ArrayList<String>();
        //get rid of duplicated ones
        for (int i=0; i<varialist.size(); i++) {
            if(!vlist.contains(varialist.get(i)))
            {
                vlist.add(varialist.get(i));
            }
        }
        return vlist;
    }

    /*
     * "getSVaria" is used to get the variable in the instruction
       parameter: String ins, the instruction, like "d/d x"
                  ArrayList<String> vlist, the generated list of variable from method "getVaria"
       return: Integer locat, the location of the variable in the instruction in the ArrayList
     */
    private static Integer getSVaria (String ins, ArrayList<String> vlist)
    {
        int varialen = ins.length();
        //cause the instruction is formatted in "d/d " followed by the variable, the fifth will be the start of variable
        ins = ins.substring(5);
        int locat;
        if (vlist.contains(ins))
        {
            locat = vlist.indexOf(ins);
        }
        else
        {
            locat = varialen;
            System.out.println("You haven't inputed valid instruction. The \"" + ins + "\"is not one of the variables");
        }
        return locat;
    }

    /*
     * "split" method is used to split an expression by '+' or '-' which is outside the brackets and store the operators
       parameter: String str, an expression
       return: ret pack, a custom class, more information in "class ret"
       example: i:String str:(3*x+y)*5+4*y-6*x-(9*x^2-2)
                o:pack.stt:[(3*x+y)*5, 4*y, 6*x, (9*x^2-2)]
                  pack.sym:[+,-,-]
                  pack.flnon:0
     */
    private static ret split (String str)
    {
        ArrayList<String> stt = new ArrayList<String>();
        ArrayList<String> sym = new ArrayList<String>();
        ret pack = new ret();
        List<Integer> arr = new ArrayList<Integer>();//plus operator
        List<Integer> arrm = new ArrayList<Integer>();//minus operator
        List<Integer> arra = new ArrayList<Integer>();//both operators
        char[] inarr = str.toCharArray();
        int flagh = 0;//bracket flag
        for (int i = 0; i < str.length(); i++)
        {
            if (inarr[i] == '(')//the operator inside brackets will be ignored
            {
                flagh = 1;
            }
            else if (inarr[i] == ')')
            {
                flagh = 0;
            }
            if (inarr[i] == '+' && flagh == 0)
            {
                arr.add(i);
                arra.add(i);
            }
            else if(inarr[i] == '-' && flagh == 0)
            {
                arrm.add(i);
                arra.add(i);
            }
        }

        if (!arra.isEmpty()) //if not vacant
        {
            pack.flnon = 0;
            int flag = 0;
            //divide the expression by valid operators
            for (int j = 0; j < arra.size(); j ++)
            {
                stt.add(str.substring(flag, arra.get(j)));
                flag = arra.get(j) + 1;
            }
            stt.add(str.substring(arra.get(arra.size()-1) + 1, str.length())); //boundary issue
            //System.out.println("parts are"+stt);
            for (int a = 0; a < arra.size(); a++)
            {
                if(arr.contains(arra.get(a)))//put the operators into a list in order
                {
                    sym.add("+");
                }
                else
                {
                    sym.add("-");
                }
            }
        }
        else
        {
            pack.flnon = 1;//if there is no operator in the expression, change the operator flag
        }
        pack.sym = sym;
        pack.stt = stt;
        return pack;
    }

    /*
     * "addmul" method is used to add multiplication between num and letter or between brackets
       parameter: String str, original expression
       return: String, expression with all multiplications
     */
    private static String addmul (String str)
    {
        StringBuilder sttb = new StringBuilder(str);
        int k = 0;
        for (int i = 0; i < str.length(); i ++)
        {
            if (Character.isDigit(str.charAt(i))) //a:num+letter or num+'('
            {
                if (i != str.length() - 1) //careful about overflow here
                {
                    if (Character.isLetter(str.charAt(i+1)) || str.charAt(i+1) == '(')
                    {
                        sttb.insert(i + 1 + k, "*");
                        k += 1;
                    }
                }
            }
            else if (str.charAt(i) == ')')//b:')'+'('
            {
                if (i != str.length() - 1)
                {
                    if(str.charAt(i + 1) == '(')
                    {
                        sttb.insert(i + 1 + k, "*");
                        k += 1;
                    }
                }
            }

            else if (Character.isLetter(str.charAt(i)))//c:letter+'('
            {
                if (i != str.length() - 1) //*needed to be noticed(please ignored)
                {
                    if(str.charAt(i + 1) == '(')
                    {
                        sttb.insert(i + 1 + k, "*");
                        k += 1;
                    }
                }
            }
        }
        //System.out.println(sttb);
        return sttb.toString();
    }

    /*
     * "diff" method is used to take the deviation of the variable
        parameter: String str, expression
                   String va, the variable
     */
    private static String diff (String str, String va)
    {
        ArrayList<String> splitRes = new ArrayList<String>();
        ArrayList<String> sym = new ArrayList<String>();
        StringBuilder result = new StringBuilder();
        ret proc = split(str);
        splitRes = proc.stt; //split result, list from the split
        sym = proc.sym; //operational symbols, list from the split
        String tp = "";
        if (proc.flnon == 0) //judge by flag
        {
            int fl = 0;
            for (int i = 0; i < splitRes.size(); i ++)
            {
                if (splitRes.get(i).contains("(")) //if there are brackets in this part
                {
                    int flpm;
                    if (i == 0)//if this is the first part of the expression or the operator before this part is '+'
                    {
                        flpm = 0;
                    }
                    else if (sym.get(i-1) == "+")
                    {
                        flpm = 0;
                    }
                    else //if the operator before this part is '-'
                    {
                        flpm = 1;
                    }
                    tp = diBce(splitRes.get(i), va, flpm);
                }
                else //with no bracket
                {
                    int flpm;
                    if (i == 0)
                    {
                        flpm = 0;
                    }
                    else if (sym.get(i-1) == "+")
                    {
                        flpm = 0;
                    }
                    else
                    {
                        flpm = 1;
                    }
                    tp = diFir(splitRes.get(i), va, flpm);
                }
                if (tp.equals("")) //emphasis!!!!!! if result is void, the array index of operator array goes by 1
                {
                    fl ++;
                }
                else
                {
                    result.append(tp);//connect the deviation result and the operator stored before
                    if (fl < sym.size())
                    {
                        result.append(sym.get(fl));
                        fl ++;
                    }
                }

            }
            if(result.charAt(result.length()-1) == '+')
            {
                result.deleteCharAt(result.length()-1);
            }
        }
        else //with no operator
        {
            if(!diFir(str, va, 0).equals(""))
            {
                if(str.contains("("))
                {
                    tp = diBce(str, va, 0);
                    result.append(tp);
                }
                else
                {
                    tp = diFir(str, va, 0);
                    result.append(tp);
                }
            }
        }
        return result.toString();
    }

    /*
     * "diFir" method is used to get the deviation of elementary expression, aka with no operator in the outlayer or bracket
        example: for like "3*x" or "3*y^2*x", not for "(4*y-3)*y" or "5+4y"
        parameter: String str, elementary expression
                   String va, variable in the deviation
                   int fl, operator flag, 0 for '+' and 1 for '-'
        return: String, result of deviation
     */
    private static String diFir (String str, String va, int fl)
    {
        StringBuilder out =  new StringBuilder();
        ArrayList<String> stl = new ArrayList<String>();
        List<Integer> arrml = new ArrayList<Integer>();
        char[] inarr = str.toCharArray();
        int plfl = 0;
        if(getVaria(str).contains(va)) //first to judge if expression has 'the' variable
        {
            if (str.contains("*"))
            {
                for (int i = 0; i < str.length(); i++)
                {
                    if (inarr[i] == '*')//if has '*'
                    {
                        arrml.add(i);
                    }
                }
                int flag = 0;
                for (int j = 0; j < arrml.size(); j ++)
                {
                    stl.add(str.substring(flag, arrml.get(j)));
                    flag = arrml.get(j) + 1;
                }
                stl.add(str.substring(arrml.get(arrml.size()-1) + 1, str.length())); //split the string by multiplication
                for (int j = 0; j < stl.size(); j ++)
                {
                    if (stl.get(j).contains("^"))//if has '^'
                    {
                        int temp = stl.get(j).charAt(stl.get(j).indexOf("^") + 1) - '0'; //get the times after the '^'
                        if (stl.get(j).equals(va))
                        {
                            for (int k = 0; k < stl.size(); k ++) //connect other parts with '*'
                            {
                                if (k != j)
                                {
                                    out.append(stl.get(k) + "*");
                                }
                            }
                            if (out.length() != 0) //if not vacant, delete the ending '*' and add a '+'
                            {
                                out.deleteCharAt(out.length()-1);
                                out.append('+');
                                plfl = 1;
                            }

                        }
                        else if (stl.get(j).contains(va))
                        {
                            if (temp == 2)
                            {
                                out.append("2" + "*" + va + "*");//if times is '2'
                            }
                            else //if greater than '2'
                            {
                                out.append(Integer.toString(temp) + "*" + va + "^" + Integer.toString(temp-1) + "*");
                            }
                            for (int k = 0; k < stl.size(); k ++)
                            {
                                if (k != j)
                                {
                                    out.append(stl.get(k) + "*");
                                }
                            }
                            if (out.length() != 0) //if not vacant, delete the ending '*' and add a '+'
                            {
                                out.deleteCharAt(out.length()-1);
                                if (fl == 0)
                                {
                                    out.append("+");
                                }
                                else
                                {
                                    out.append("-");
                                }

                                plfl = 1;
                            }
                        }
                    }
                    else
                    {
                        if (stl.get(j).equals(va))//same as above
                        {
                            for (int k = 0; k < stl.size(); k ++)
                            {
                                if (k != j)
                                {
                                    out.append(stl.get(k) + "*");
                                }
                            }
                            if (out.length() != 0)
                            {
                                out.deleteCharAt(out.length()-1);
                                if (fl == 0)
                                {
                                    out.append("+");
                                }
                                else
                                {
                                    out.append("-");
                                }

                                plfl = 1;
                            }
                        }
                    }
                }
            }
            else //if with no '*' but '^'
            {
                if (str.contains("^"))
                {
                    int temp = str.charAt(str.indexOf("^") + 1) - '0';
                    if (temp == 2)
                    {
                        out.append("2" + "*" + va);
                    }
                    else
                    {
                        out.append(Integer.toString(temp) + "*" + va + "^" + Integer.toString(temp-1));
                    }
                }
                else
                {
                    out.append("1");
                }

            }
        }
        if (out.length() > 0 && plfl == 1) //if flag changes, delete the ending '+'
        {
            out.deleteCharAt(out.length()-1);
        }
        return out.toString();

    }

    /*
     * "diBce" method is used to get the deviation of expressions with brackets
     */
    private static String diBce (String str, String va, int fl) //用于处理含括号的
    {
        ArrayList<String> stl = new ArrayList<String>();
        StringBuilder outp = new StringBuilder();
        List<Integer> arrml = new ArrayList<Integer>();
        char[] inarr = str.toCharArray();
        int flagh = 0;
        if (getVaria(str).contains(va))//similar to "diFir"
        {
            for (int i = 0; i < str.length(); i++)
            {

                if (inarr[i] == '(')
                {
                    flagh = 1;
                }
                else if (inarr[i] == ')')
                {
                    flagh = 0;
                }
                else if (inarr[i] == '*' && flagh == 0)
                {
                    arrml.add(i);
                }
            }
            int flag = 0;
            for (int j = 0; j < arrml.size(); j ++)
            {
                stl.add(str.substring(flag, arrml.get(j)));
                flag = arrml.get(j) + 1;
            }
            stl.add(str.substring(arrml.get(arrml.size()-1) + 1, str.length()));//split by '*'
            //System.out.println("乘号拆分"+stl);
            for (int j = 0; j < stl.size(); j ++)
            {
                String strr;
                if (stl.get(j).contains("(")) //if starting with '(' and ending with ')', get rid of them
                {
                    strr = stl.get(j).substring(1, stl.get(j).length()-1);
                }
                else
                {
                    strr = stl.get(j);
                }
                //System.out.println("eeee"+stl.get(
                //System.out.println("dddd"+getVaria(stl.get(j)));
                if (getVaria(strr).contains(va))
                {
                    String tmp = diff(strr, va);//take the deviation of parts in the expression
                    StringBuilder tmpl = new StringBuilder(tmp);
                    tmp = tmpl.toString();
                    if(tmp.contains("-") || tmp.contains("+"))
                    {
                        outp.append("(" + tmp + ")*");
                    }
                    else
                    {
                        outp.append(tmp + "*");
                    }
                    //System.out.println(jj);
                    //System.out.println(tmp);
                    for (int k = 0; k < stl.size(); k ++)
                    {
                        if (k != j)
                        {
                            outp.append(stl.get(k) + "*");
                        }
                    }
                    if (outp.charAt(outp.length()-1) == '*')
                    {
                        outp.deleteCharAt(outp.length()-1);
                    }
                    if (!outp.equals(""))
                    {
                        if (fl == 0)
                        {
                            outp.append("+");
                        }
                        else
                        {
                            outp.append("-");
                        }
                    }
                }
            }
            if (outp.length() != 0)
            {
                outp.deleteCharAt(outp.length()-1);
            }
        }
        return outp.toString();
    }

	/*
	 * private static String demul (String str)
	{
		StringBuilder fin = new StringBuilder(str);
		char tp[] = str.toCharArray();
		for (int i = 0; i < str.length(); i ++)
		{
			if (i < str.length()-1)
			{
				if (Character.isDigit(str.charAt(i)))
				{

				}
			}
		}

		return ;
	}
	 */

}
class ret
{
    ArrayList<String> stt = new ArrayList<String>();//store the results after split
    ArrayList<String> sym = new ArrayList<String>();//store the operators
    int flnon;//flag for operators
}