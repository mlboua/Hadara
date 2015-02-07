package smtSolvers;

import booleanExpression.CBooleanExpression;
import booleanExpression.booleanVisitor.CToSmtLibVisitor;
import generalExpression.CVariable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: LoW
 * Date: 07/01/13
 * Time: 14:19
 */
public class CZ3 extends CSolver
{
    Runtime m_Runtime;
    String m_z3Path;
    CToSmtLibVisitor m_ToSMTLIB;
    BufferedReader m_BufferedReader;
    PrintWriter m_PrintWriter;
    Process m_Process;
    public CZ3(String z3Path)                
    {
        this.m_Runtime = Runtime.getRuntime();
        this.m_z3Path = z3Path;
        this.m_ToSMTLIB = new CToSmtLibVisitor();

        try
        {
            String[] cmds = { this.m_z3Path, "/smt2", "/in", "/t:10" };
            this.m_Process = this.m_Runtime.exec(cmds);
            this.m_PrintWriter = new PrintWriter(this.m_Process.getOutputStream());
            this.m_BufferedReader = new BufferedReader(new InputStreamReader(this.m_Process.getInputStream()));

            //this.m_PrintWriter.print("(set-option :elim-quantifiers true)\n");
            this.m_PrintWriter.println("(set-option :mbqi true)");
            this.m_PrintWriter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public CAnswer solve(ArrayList<CVariable> variables, CBooleanExpression bExp, boolean bGetModel)
    {
        CAnswer result = new CAnswer();
        try
        {
            /*DEBUG*/
            /*FileWriter writer = new FileWriter("smt-input.txt");
            writer.write("(set-option :elim-quantifiers true)\n");
            writer.write("(set-option :mbqi true)\n");
            for (int i = 0; i < variables.size(); i++)
            {
                writer.write("(declare-fun "+variables.get(i).toString()+" () Int) \n");
            }
            writer.write("(assert ");
            writer.write((String) this.m_ToSMTLIB.visit(bExp,new String("")));
            writer.write(")\n");
            writer.write("(check-sat)");
            writer.close();    */

            this.m_PrintWriter.println("(reset)");
            this.m_PrintWriter.flush();
            for (int i = 0; i < variables.size(); i++)
            {
                this.m_PrintWriter.write("(declare-fun "+variables.get(i).toString()+" () Int) \n");
            }
            this.m_PrintWriter.print("(assert ");
            this.m_PrintWriter.print((String) this.m_ToSMTLIB.visit(bExp,new String("")));
            this.m_PrintWriter.print(")\n");
            this.m_PrintWriter.println("(check-sat)");
            this.m_PrintWriter.flush();
            String tmp = this.m_BufferedReader.readLine();
            if(tmp == null)
            {
                result.SetAnswer(EAnswer.UNKNOW);
            }
            else if(tmp.equals("sat"))
            {
                result.SetAnswer(EAnswer.SAT);
                if(bGetModel)
                {
                    this.m_PrintWriter.println("(get-model)");
                    this.m_PrintWriter.flush();
                    tmp = this.m_BufferedReader.readLine();
                    if(tmp.equals("(model "))
                    {
                        HashMap<String,Integer> model = new HashMap<String,Integer>();
                        tmp = this.m_BufferedReader.readLine();
                        while(!tmp.equals(")"))
                        {
                            //System.out.println(tmp);
                            String tab[] = tmp.split("\\s+");
                            //System.out.println(tab[2]);
                            tmp = this.m_BufferedReader.readLine();
                           // System.out.println(tmp);
                            String tab2[] = tmp.split("\\s+");
                            //System.out.println(tab2[1]);
                            Integer val = new Integer(tab2[1].substring(0,tab2[1].length()-1));
                            //System.out.println(tmp.substring(0,tmp.length()-1));
                            model.put(tab[2],val);
                            tmp = this.m_BufferedReader.readLine();
                        }

                        result.SetModel(model);
                    }
                    else
                    {
                        System.out.println("error while parsing model : "+tmp);
                    }
                }
            }
            else if(tmp.equals("unsat"))
            {
                result.SetAnswer(EAnswer.UNSAT);
            }
            else
            {
                System.out.println("error : "+tmp);
                result.SetAnswer(EAnswer.UNKNOW);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public void CleanUp()
    {
        try
        {
            this.m_BufferedReader.close();
            this.m_PrintWriter.close();
            this.m_Process.destroy();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
