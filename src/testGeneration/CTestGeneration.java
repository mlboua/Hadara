package testGeneration;

import abstraction.CAbstractInterpretation;
import abstraction.sts.CModalTransition;
import abstraction.sts.EModalities;

import java.util.ArrayList;

/**
 * User: LoW
 * Date: 12/04/13
 * Time: 12:14
 */
public class CTestGeneration
{
    CAbstractInterpretation m_Abs;
    ArrayList<ArrayList<CModalTransition>> m_testSuite;
    int m_Depth;
    public CTestGeneration(CAbstractInterpretation cAbs, int depth)
    {
        this.m_Abs = cAbs;
        this.m_Depth =  depth;
        this.m_testSuite = new ArrayList<ArrayList<CModalTransition>>();
        for(CModalTransition tmp : this.m_Abs.getTransitions().m_Set.toArray(new CModalTransition[0]))
        {
            ArrayList<CModalTransition> newTest = new  ArrayList<CModalTransition>();
            newTest.add(tmp);
            if(tmp.GetMod() == EModalities.MUSTPlusMinus || tmp.GetMod() == EModalities.MUSTMinus)
            {
                this.travelMustMinus(newTest);
            }
            else
            {
                this.travelMustPlus(newTest);
            }
        }
    }
    private int containsAsMustMinus(ArrayList<CModalTransition> cTest, CModalTransition trans)
    {
        int ret = 0;
        for (int i = 0; i < cTest.size(); i++)
        {
            if(cTest.get(i).GetMod() == EModalities.MUSTMinus || cTest.get(i).GetMod() == EModalities.MUSTPlusMinus)
            {
                if(trans.equals(cTest.get(i)))
                {
                    ret++;
                }
            }
            else
            {
                return ret;
            }
        }
        return ret;
    }
    private void travelMustMinus(ArrayList<CModalTransition> curTest)
    {
        boolean bEndOfTest = true;
        for(CModalTransition tmp : this.m_Abs.GetRelatedAbs().getTransitions().m_Set.toArray(new CModalTransition[0]))
        {
            if(this.containsAsMustMinus(curTest, tmp) < this.m_Depth)
            {
                if(tmp.GetSource().equals(curTest.get(curTest.size()-1).GetTarget()))
                {
                    bEndOfTest = false;
                    ArrayList<CModalTransition> newTest = (ArrayList<CModalTransition>)curTest.clone();
                    newTest.add(tmp);
                    if(tmp.GetMod() == EModalities.MUSTPlusMinus || tmp.GetMod() == EModalities.MUSTMinus)
                    {
                        this.travelMustMinus(newTest);
                    }
                    else
                    {
                        this.travelMustPlus(newTest);
                    }
                }
            }
        }
        if(bEndOfTest)
        {
            this.m_testSuite.add(curTest);
        }
    }
    private int containsAsMustPlus(ArrayList<CModalTransition> cTest, CModalTransition trans)
    {
        int ret = 0;
        boolean tmp = false;
        for (int i = 0; i < cTest.size(); i++)
        {
            if(!tmp)
            {
                if( !(cTest.get(i).GetMod() == EModalities.MUSTMinus || cTest.get(i).GetMod() == EModalities.MUSTPlusMinus))
                {
                    tmp = true;
                }
            }
            else if(trans.equals(cTest.get(i)))
            {
                ret++;
            }
        }
        return ret;
    }
    private void travelMustPlus(ArrayList<CModalTransition> curTest)
    {
        boolean bEndOfTest = true;
        for(CModalTransition tmp : this.m_Abs.GetRelatedAbs().getTransitions().m_Set.toArray(new CModalTransition[0]))
        {
            if(this.containsAsMustPlus(curTest, tmp) < this.m_Depth)
            {
                if(tmp.GetSource().equals(curTest.get(curTest.size()-1).GetTarget()))
                {
                    if(tmp.GetMod() == EModalities.MUSTPlusMinus || tmp.GetMod() == EModalities.MUSTPlus)
                    {
                        bEndOfTest = false;
                        ArrayList<CModalTransition> newTest = (ArrayList<CModalTransition>)curTest.clone();
                        newTest.add(tmp);
                        this.travelMustPlus(newTest);
                    }
                }
            }
        }
        if(bEndOfTest)
        {
            this.m_testSuite.add(curTest);
        }
    }
    public CAbstractInterpretation GetAbs()
    {
        return this.m_Abs;
    }
    public String[] toStringTab()
    {
        String tab[] = new String[this.m_testSuite.size()];
        for (int i = 0; i < this.m_testSuite.size(); i++)
        {
            String ret = new String();
            boolean  tmp = false;
            ret += this.m_testSuite.get(i).get(0).GetSource().GetName();
            if(this.m_testSuite.get(i).get(0).GetMod() == EModalities.MUSTMinus || this.m_testSuite.get(i).get(0).GetMod() == EModalities.MUSTPlusMinus)
            {
                ret += " -"+this.m_testSuite.get(i).get(0).GetName()+"(-)-> ";
            }
            else
            {
                tmp = true;
                ret += " -"+this.m_testSuite.get(i).get(0).GetName()+"-> ";
            }
            ret += this.m_testSuite.get(i).get(0).GetTarget().GetName();
            for (int j = 1; j < this.m_testSuite.get(i).size(); j++)
            {
                if(!tmp)
                {
                    if(this.m_testSuite.get(i).get(j).GetMod() == EModalities.MUSTMinus || this.m_testSuite.get(i).get(j).GetMod() == EModalities.MUSTPlusMinus)
                    {
                        ret += " -"+this.m_testSuite.get(i).get(j).GetName()+"(-)-> "+this.m_testSuite.get(i).get(j).GetTarget().GetName();
                    }
                    else
                    {
                        tmp = true;
                        ret += " -"+this.m_testSuite.get(i).get(j).GetName()+"-> "+this.m_testSuite.get(i).get(j).GetTarget().GetName();
                    }
                }
                else
                {
                    ret += " -"+this.m_testSuite.get(i).get(j).GetName()+"(+)-> "+this.m_testSuite.get(i).get(j).GetTarget().GetName();
                }

            }
            tab[i] = ret;
        }
        return tab;
    }
    public String toString()
    {
        String ret = new String();
        for (int i = 0; i < this.m_testSuite.size(); i++)
        {
            boolean  tmp = false;
            ret += this.m_testSuite.get(i).get(0).GetSource().GetName();
            if(this.m_testSuite.get(i).get(0).GetMod() == EModalities.MUSTMinus || this.m_testSuite.get(i).get(0).GetMod() == EModalities.MUSTPlusMinus)
            {
                 // ret += " -"+this.m_testSuite.get(i).get(0).GetName()+"(-)-> ";
                ret += "<math|<long-arrow|\\<rubber-rightarrow\\>|"+this.m_testSuite.get(i).get(0).GetName()+"->> ";
            }
            else
            {
                tmp = true;
                //ret += " -"+this.m_testSuite.get(i).get(0).GetName()+"-> ";
                ret += "<math|<long-arrow|\\<rubber-rightarrow\\>|"+this.m_testSuite.get(i).get(0).GetName()+">> ";
            }
            ret += this.m_testSuite.get(i).get(0).GetTarget().GetName();
            for (int j = 1; j < this.m_testSuite.get(i).size(); j++)
            {
                if(!tmp)
                {
                    if(this.m_testSuite.get(i).get(j).GetMod() == EModalities.MUSTMinus || this.m_testSuite.get(i).get(j).GetMod() == EModalities.MUSTPlusMinus)
                    {
                        //ret += " -"+this.m_testSuite.get(i).get(j).GetName()+"(-)-> "+this.m_testSuite.get(i).get(j).GetTarget().GetName();
                        ret += "<math|<long-arrow|\\<rubber-rightarrow\\>|"+this.m_testSuite.get(i).get(j).GetName()+"->> "+this.m_testSuite.get(i).get(j).GetTarget().GetName();
                    }
                    else
                    {
                        tmp = true;
                        //ret += " -"+this.m_testSuite.get(i).get(j).GetName()+"-> "+this.m_testSuite.get(i).get(j).GetTarget().GetName();
                        ret += "<math|<long-arrow|\\<rubber-rightarrow\\>|"+this.m_testSuite.get(i).get(j).GetName()+">> "+this.m_testSuite.get(i).get(j).GetTarget().GetName();
                    }
                }
                else
                {
                    //ret += " -"+this.m_testSuite.get(i).get(j).GetName()+"(+)-> "+this.m_testSuite.get(i).get(j).GetTarget().GetName();
                    ret += "<math|<long-arrow|\\<rubber-rightarrow\\>|"+this.m_testSuite.get(i).get(j).GetName()+"+>> "+this.m_testSuite.get(i).get(j).GetTarget().GetName();
                }

            }
            ret += "\n";
        }
        return ret;
    }
}
