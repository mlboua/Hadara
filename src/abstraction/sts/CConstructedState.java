package abstraction.sts;

import booleanExpression.CBooleanExpression;

import java.util.ArrayList;

/**
 * User: LoW
 * Date: 16/05/13
 * Time: 21:28
 */
public class CConstructedState extends CState
{
    ArrayList<String> m_Plan;
    public CConstructedState(String name, CBooleanExpression bExpr, ArrayList<String> Plan)
    {
        super(name,bExpr);
        this.m_Plan = Plan;
    }
    public ArrayList<String> GetPlan()
    {
        return this.m_Plan;
    }
    public String GetName()
    {
        return this.toString();
    }
    public String toString()
    {
        String ret = new String();
        if(this.m_Plan != null)
        {
            for (int i = 0; i < this.m_Plan.size(); i++)
            {
                 ret += this.m_Plan.get(i);
            }
        }
        return ret;
    }
}
