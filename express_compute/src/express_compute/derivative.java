package express_compute;

import java.util.*;
public class derivative 
{
	/*
	 * public static void main(String args[])
	{
		String formula = "(3x^3+4z)*3z^2+5xyd^6+x*4z-(3*z^3+4*y+3*z)*(45+z)*(5*z+2)-z^2-z*6z+z";//"(3x^3+4x)*3x^2+5x^6+x*4x"; 
		//String formula = "(3x^3+4z)*3z^2";
		String instr = "!d/d xyd";
		String deri;
		ArrayList<String> varialist = new ArrayList<String>();
		varialist = getVaria(formula); //获得变量数组
		formula = addmul(formula);
		deri = varialist.get(getSVaria(instr, varialist));
		System.out.println("原表达式："+formula);
		System.out.println("对"+deri+"求导");
		System.out.println("求导结果："+diff(formula, deri));
	}
	 */
	
	
	public static String io (String formula, String instr)
	{
		//第一个表达式，第二个指令
		String deri;
		ArrayList<String> varialist = new ArrayList<String>();
		varialist = getVaria(formula); //获得变量数组
		formula = addmul(formula);
		deri = varialist.get(getSVaria(instr, varialist));
		System.out.println("原表达式："+formula);
		System.out.println("对"+deri+"求导");
		System.out.println("求导结果："+diff(formula, deri));
		return diff(formula, deri);
	}
	
	private static ArrayList<String> getVaria (String ori) //返回原表达式中所有变量
	{
		ArrayList<String> varialist = new ArrayList<String>();
        int strlen;
        if (ori.charAt(0) == '(')
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
		for (int i = 0; i < strlen; i ++)
		{
			if((Integer.valueOf(ori_split[i]) >= 65 && Integer.valueOf(ori_split[i]) <= 90) ||(Integer.valueOf(ori_split[i]) >= 97 && Integer.valueOf(ori_split[i]) <= 122))
			{
				temp = temp + ori_split[i];
			}
			else
			{
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
		for (int i=0; i<varialist.size(); i++) {
			if(!vlist.contains(varialist.get(i)))
			{
				vlist.add(varialist.get(i));
			}
		}
		return vlist;
	}
	
	private static Integer getSVaria (String ins, ArrayList<String> vlist) //获得指令式中的合法变量
	{
		int varialen = ins.length();
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
	
	private static ret split (String str) //done根据加号减号位置，拆分字符串。可判断不含加减号式子。返回值为ret类，包括加减号判断标志、加减号数组、拆分数组
	{
    	ArrayList<String> stt = new ArrayList<String>();
    	ArrayList<String> sym = new ArrayList<String>();
		ret pack = new ret();
		List<Integer> arr = new ArrayList<Integer>();//加号
    	List<Integer> arrm = new ArrayList<Integer>();//减号
    	List<Integer> arra = new ArrayList<Integer>();//加减号
    	char[] inarr = str.toCharArray();
    	int flagh = 0;//括号标志位
    	for (int i = 0; i < str.length(); i++)
    	{
    		if (inarr[i] == '(')//不拆分括号里的加减号
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
        //System.out.println("+:"+arr); //加号
        //System.out.println("-:"+arrm);//减号
        //System.out.println("+&-:"+arra);//所有号
        if (!arra.isEmpty()) //如含加号或减号
        {
        	pack.flnon = 0;
	        int flag = 0;
	        for (int j = 0; j < arra.size(); j ++)
	        {
	        	stt.add(str.substring(flag, arra.get(j)));
	        	flag = arra.get(j) + 1;
	        }
	        stt.add(str.substring(arra.get(arra.size()-1) + 1, str.length())); //通过加减号位置将原表达式拆分
	        //System.out.println("parts are"+stt);
	        for (int a = 0; a < arra.size(); a++)
			{
				if(arr.contains(arra.get(a)))//将加减号按顺序存入list
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
        	pack.flnon = 1;//若不含加减号，改变标志位
        }
        pack.sym = sym;
        pack.stt = stt;
    	return pack;
	}
		
	private static String addmul (String str) //在数字和字母间或括号之间插入乘号，返回字符串型
	{
		StringBuilder sttb = new StringBuilder(str);
		int k = 0;
		for (int i = 0; i < str.length(); i ++)
		{
			if (Character.isDigit(str.charAt(i))) 
			{
				if (i != str.length() - 1)
				{
					if (Character.isLetter(str.charAt(i+1)) || str.charAt(i+1) == '(')
					{
						sttb.insert(i+1+k, "*");
						k+=1;
					}
				}
			}
			else if (str.charAt(i) == ')')
			{
				if (i != str.length() - 1)
				{
					if(str.charAt(i + 1) == '(')
					{
						sttb.insert(i+1+k, "*");
						k+=1;
					}
				}
			}
			
			else if (Character.isLetter(str.charAt(i)))
			{
				if (i != str.length() - 1) //needed to be noticed
				{
					if(str.charAt(i + 1) == '(')
					{
						sttb.insert(i+1+k, "*");
						k+=1;
					}
				}
			}
		}
		//System.out.println(sttb);
		return sttb.toString();
	}
		
	private static String diff (String str, String va) //求导，传入原式及变量
	{
		ArrayList<String> splitRes = new ArrayList<String>();
		ArrayList<String> sym = new ArrayList<String>();
		StringBuilder result = new StringBuilder();
		ret proc = split(str);
		splitRes = proc.stt;
		sym = proc.sym;
		String tp = "";
		if (proc.flnon == 0) //判断按最外层是否含有加减号，若含有加减号
		{
			int fl = 0;
			for (int i = 0; i < splitRes.size(); i ++)//循环按最外层加减号拆分的结果
			{
				if (splitRes.get(i).contains("(")) //如果该部分含有括号
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
					tp = diBce(splitRes.get(i), va, flpm);
				}
				else //该部分不含括号
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
				if (tp.equals(""))
				{
					fl ++;
				}
				else
				{
					result.append(tp);
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
		else //若不含加减号
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
		
	private static String diFir (String str, String va, int fl) //用与最基本式子的求导运算，基本，即最外层不含加减，均不含括号
	{
		StringBuilder out =  new StringBuilder();
		ArrayList<String> stl = new ArrayList<String>();
		List<Integer> arrml = new ArrayList<Integer>();
		char[] inarr = str.toCharArray();
		int plfl = 0;
		if(getVaria(str).contains(va)) //首先判断表达式中是否含有求导的变量
		{
			if (str.contains("*"))//needed to be fixed
			{
				for (int i = 0; i < str.length(); i++)
		    	{
		    		if (inarr[i] == '*')
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
		        stl.add(str.substring(arrml.get(arrml.size()-1) + 1, str.length())); //根据乘号将字符串分开
		        for (int j = 0; j < stl.size(); j ++)
		        {
		        	if (stl.get(j).contains("^"))//表达式中含“^”时
		        	{
		        		int temp = stl.get(j).charAt(stl.get(j).indexOf("^") + 1) - '0'; //获得“^”后的次数
		        		if (stl.get(j).equals(va))//比较各小项和变量，当该部分不含^时 注意+++++++++++++++++
		        	    {	
		        			for (int k = 0; k < stl.size(); k ++) //将其他部分连乘
		        	        {
		        	        	if (k != j)
		        	        	{
		        	        		out.append(stl.get(k) + "*");
		        	        	}
		        	        }
		        			if (out.length() != 0) //当总输出串不空时，去掉末尾的“*”，最后加“+”
		        			{
		        				out.deleteCharAt(out.length()-1);
		        				out.append('+');
                                plfl = 1;
		        			}
		        	        
		        	    }
		        		else if (stl.get(j).contains(va)) //当该部分包含变量，即其中含有“^”时  注意+++++++++++++++++
		        		{
		        			if (temp == 2)
		        			{
		        				out.append("2" + "*" + va + "*");//如次数为2
		        			}
		        			else //次数不为2
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
		        			if (out.length() != 0) //如果输出串结果不空，将末尾“*”去掉，加“+”
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
		        	else//表达式中不含“^”时 注意+++++++++++++++++
		        	{
		        		if (stl.get(j).equals(va))//比较各小项和变量
		        	    {	
		        			for (int k = 0; k < stl.size(); k ++)//同上
		        	        {
		        	        	if (k != j)
		        	        	{
		        	        		out.append(stl.get(k) + "*");
		        	        	}
		        	        }
		        			if (out.length() != 0)//同上
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
			else
			{
                if (str.contains("^"))
                {
                    int temp = str.charAt(str.indexOf("^") + 1) - '0'; //获得“^”后的次数
                    if (temp == 2)
                    {
                        out.append("2" + "*" + va);//如次数为2
                    }
                    else //次数不为2
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
		if (out.length() > 0 && plfl == 1) //如果加号标志位改变，去掉末尾“+”
		{
			out.deleteCharAt(out.length()-1);
		}
		return out.toString();
		
	}
	
	private static String diBce (String str, String va, int fl) //用于处理含括号的
	{
		ArrayList<String> stl = new ArrayList<String>();
		StringBuilder outp = new StringBuilder();
		List<Integer> arrml = new ArrayList<Integer>();
		char[] inarr = str.toCharArray();
		int flagh = 0;
		if (getVaria(str).contains(va))//判断求导的变量是否存在
		{
	    	for (int i = 0; i < str.length(); i++)//根据乘号拆开，括号内的不拆
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
	        stl.add(str.substring(arrml.get(arrml.size()-1) + 1, str.length()));//根据乘号将字符串分开
	        //System.out.println("乘号拆分"+stl);
			for (int j = 0; j < stl.size(); j ++) //循环拆分后结果
			{
				String strr;
				if (stl.get(j).contains("(")) //判断是否含括号，如果含去掉括号取其变量
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
				{   //bug warning
					//System.out.println("对了");
					String tmp = diff(strr, va);//对括号内容求导
					//System.out.println(tmp);
					StringBuilder tmpl = new StringBuilder(tmp);
					//if (tmpl.charAt(tmp.length()-1 '+' || tmpl.charAt(tmp.length()-1) == '-')
					//{
					//	tmpl.deleteCharAt(tmpl.length()-1);
					//}
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
	ArrayList<String> stt = new ArrayList<String>();// attention, stt is occupied
	ArrayList<String> sym = new ArrayList<String>();
	int flnon; //0时含有加减，1时不含
}