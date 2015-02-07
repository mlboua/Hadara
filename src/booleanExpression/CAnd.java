package booleanExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;

import java.util.ArrayList;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 17:58
 */
public class CAnd extends CBooleanExpression
{
    ArrayList<CBooleanExpression> m_Elements;
    public CAnd(CBooleanExpression ... Elements)
    {
        this.m_Elements = new ArrayList<CBooleanExpression>();
        for(CBooleanExpression thisVariables : Elements)
        {
            this.m_Elements.add(thisVariables);
            this.add(thisVariables);
        }
    }
    public CAnd(ArrayList<CBooleanExpression> Elements)
    {
        this.m_Elements = Elements;
        for (int i = 0; i < this.m_Elements.size(); i++)
        {
            this.add(this.m_Elements.get(i));
        }
    }
    public ArrayList<CBooleanExpression> GetElements()
    {
        return this.m_Elements;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        String ret = new String();
        ret+= "(";
        for (int i = 0; i < this.m_Elements.size(); i++)
        {
            if(i!=0)
            {
                ret += " ∧ ";
            }
            ret += this.m_Elements.get(i).toString();
        }
        return ret+")";
    }
}
