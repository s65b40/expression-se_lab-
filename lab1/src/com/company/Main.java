package com.company;
import java.util.Scanner;
import com.company.compute;
import com.company.derivative;
import com.company.Judge;
public class Main {

    public static void main(String[] args) {
	// write your code here
        try
        {
            compute Computer = new compute();
            derivative Derivativer = new derivative();
            Judge judger = new Judge(); //用来判断输入的字符串类型
            Scanner scanner = new Scanner(System.in);
            while(true)
            {
                String s = scanner.nextLine();//读进来字符串存到s
                int res = judger.judge(s);
                if(res == 0)
                {
                    //判断出是求导命令后
                    System.out.println(Derivativer.io(Computer.express_just,s));
                }
                else if(res == 1)
                {
                    //判断出是化简命令
                    //System.out.println("huajian");
                    Computer.simple_conpute(s);
                    System.out.println(Computer.compute_suffix());
                }
                else if(res == -1)
                {
                    //判断出是非法表达式
                    System.out.println("error!");
                }
                else if(res == 2)
                {
                    //判断出是正常表达式
                    //System.out.println("zhengchang");
                    Computer.MiddleTolast(s);
                    System.out.println(Computer.compute_suffix());

                }

            }
        }
        catch (Exception e)
        {
            System.out.println("error!");
        }
    }
}
