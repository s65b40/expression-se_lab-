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
		varialist = getVaria(formula); //��ñ�������
		formula = addmul(formula);
		deri = varialist.get(getSVaria(instr, varialist));
		System.out.println("ԭ���ʽ��"+formula);
		System.out.println("��"+deri+"��");
		System.out.println("�󵼽����"+diff(formula, deri));
	}
	 */
	
	
	public static String io (String formula, String instr)
	{
		//��һ�����ʽ���ڶ���ָ��
		String deri;
		ArrayList<String> varialist = new ArrayList<String>();
		varialist = getVaria(formula); //��ñ�������
		formula = addmul(formula);
		deri = varialist.get(getSVaria(instr, varialist));
		System.out.println("ԭ���ʽ��"+formula);
		System.out.println("��"+deri+"��");
		System.out.println("�󵼽����"+diff(formula, deri));
		return diff(formula, deri);
	}
	
	private static ArrayList<String> getVaria (String ori) //����ԭ���ʽ�����б���
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
	
	private static Integer getSVaria (String ins, ArrayList<String> vlist) //���ָ��ʽ�еĺϷ�����
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
	
	private static ret split (String str) //done���ݼӺż���λ�ã�����ַ��������жϲ����Ӽ���ʽ�ӡ�����ֵΪret�࣬�����Ӽ����жϱ�־���Ӽ������顢�������
	{
    	ArrayList<String> stt = new ArrayList<String>();
    	ArrayList<String> sym = new ArrayList<String>();
		ret pack = new ret();
		List<Integer> arr = new ArrayList<Integer>();//�Ӻ�
    	List<Integer> arrm = new ArrayList<Integer>();//����
    	List<Integer> arra = new ArrayList<Integer>();//�Ӽ���
    	char[] inarr = str.toCharArray();
    	int flagh = 0;//���ű�־λ
    	for (int i = 0; i < str.length(); i++)
    	{
    		if (inarr[i] == '(')//�����������ļӼ���
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
        //System.out.println("+:"+arr); //�Ӻ�
        //System.out.println("-:"+arrm);//����
        //System.out.println("+&-:"+arra);//���к�
        if (!arra.isEmpty()) //�纬�ӺŻ����
        {
        	pack.flnon = 0;
	        int flag = 0;
	        for (int j = 0; j < arra.size(); j ++)
	        {
	        	stt.add(str.substring(flag, arra.get(j)));
	        	flag = arra.get(j) + 1;
	        }
	        stt.add(str.substring(arra.get(arra.size()-1) + 1, str.length())); //ͨ���Ӽ���λ�ý�ԭ���ʽ���
	        //System.out.println("parts are"+stt);
	        for (int a = 0; a < arra.size(); a++)
			{
				if(arr.contains(arra.get(a)))//���Ӽ��Ű�˳�����list
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
        	pack.flnon = 1;//�������Ӽ��ţ��ı��־λ
        }
        pack.sym = sym;
        pack.stt = stt;
    	return pack;
	}
		
	private static String addmul (String str) //�����ֺ���ĸ�������֮�����˺ţ������ַ�����
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
		
	private static String diff (String str, String va) //�󵼣�����ԭʽ������
	{
		ArrayList<String> splitRes = new ArrayList<String>();
		ArrayList<String> sym = new ArrayList<String>();
		StringBuilder result = new StringBuilder();
		ret proc = split(str);
		splitRes = proc.stt;
		sym = proc.sym;
		String tp = "";
		if (proc.flnon == 0) //�жϰ�������Ƿ��мӼ��ţ������мӼ���
		{
			int fl = 0;
			for (int i = 0; i < splitRes.size(); i ++)//ѭ���������Ӽ��Ų�ֵĽ��
			{
				if (splitRes.get(i).contains("(")) //����ò��ֺ�������
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
				else //�ò��ֲ�������
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
		else //�������Ӽ���
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
		
	private static String diFir (String str, String va, int fl) //���������ʽ�ӵ������㣬������������㲻���Ӽ�������������
	{
		StringBuilder out =  new StringBuilder();
		ArrayList<String> stl = new ArrayList<String>();
		List<Integer> arrml = new ArrayList<Integer>();
		char[] inarr = str.toCharArray();
		int plfl = 0;
		if(getVaria(str).contains(va)) //�����жϱ��ʽ���Ƿ����󵼵ı���
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
		        stl.add(str.substring(arrml.get(arrml.size()-1) + 1, str.length())); //���ݳ˺Ž��ַ����ֿ�
		        for (int j = 0; j < stl.size(); j ++)
		        {
		        	if (stl.get(j).contains("^"))//���ʽ�к���^��ʱ
		        	{
		        		int temp = stl.get(j).charAt(stl.get(j).indexOf("^") + 1) - '0'; //��á�^����Ĵ���
		        		if (stl.get(j).equals(va))//�Ƚϸ�С��ͱ��������ò��ֲ���^ʱ ע��+++++++++++++++++
		        	    {	
		        			for (int k = 0; k < stl.size(); k ++) //��������������
		        	        {
		        	        	if (k != j)
		        	        	{
		        	        		out.append(stl.get(k) + "*");
		        	        	}
		        	        }
		        			if (out.length() != 0) //�������������ʱ��ȥ��ĩβ�ġ�*�������ӡ�+��
		        			{
		        				out.deleteCharAt(out.length()-1);
		        				out.append('+');
                                plfl = 1;
		        			}
		        	        
		        	    }
		        		else if (stl.get(j).contains(va)) //���ò��ְ��������������к��С�^��ʱ  ע��+++++++++++++++++
		        		{
		        			if (temp == 2)
		        			{
		        				out.append("2" + "*" + va + "*");//�����Ϊ2
		        			}
		        			else //������Ϊ2
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
		        			if (out.length() != 0) //��������������գ���ĩβ��*��ȥ�����ӡ�+��
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
		        	else//���ʽ�в�����^��ʱ ע��+++++++++++++++++
		        	{
		        		if (stl.get(j).equals(va))//�Ƚϸ�С��ͱ���
		        	    {	
		        			for (int k = 0; k < stl.size(); k ++)//ͬ��
		        	        {
		        	        	if (k != j)
		        	        	{
		        	        		out.append(stl.get(k) + "*");
		        	        	}
		        	        }
		        			if (out.length() != 0)//ͬ��
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
                    int temp = str.charAt(str.indexOf("^") + 1) - '0'; //��á�^����Ĵ���
                    if (temp == 2)
                    {
                        out.append("2" + "*" + va);//�����Ϊ2
                    }
                    else //������Ϊ2
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
		if (out.length() > 0 && plfl == 1) //����Ӻű�־λ�ı䣬ȥ��ĩβ��+��
		{
			out.deleteCharAt(out.length()-1);
		}
		return out.toString();
		
	}
	
	private static String diBce (String str, String va, int fl) //���ڴ������ŵ�
	{
		ArrayList<String> stl = new ArrayList<String>();
		StringBuilder outp = new StringBuilder();
		List<Integer> arrml = new ArrayList<Integer>();
		char[] inarr = str.toCharArray();
		int flagh = 0;
		if (getVaria(str).contains(va))//�ж��󵼵ı����Ƿ����
		{
	    	for (int i = 0; i < str.length(); i++)//���ݳ˺Ų𿪣������ڵĲ���
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
	        stl.add(str.substring(arrml.get(arrml.size()-1) + 1, str.length()));//���ݳ˺Ž��ַ����ֿ�
	        //System.out.println("�˺Ų��"+stl);
			for (int j = 0; j < stl.size(); j ++) //ѭ����ֺ���
			{
				String strr;
				if (stl.get(j).contains("(")) //�ж��Ƿ����ţ������ȥ������ȡ�����
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
					//System.out.println("����");
					String tmp = diff(strr, va);//������������
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
	int flnon; //0ʱ���мӼ���1ʱ����
}