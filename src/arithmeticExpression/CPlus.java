package arithmeticExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;

import java.util.ArrayList;

/**
 *
 * User: LoW
 * Date: 28/12/12
 * Time: 18:48
 */
public class CPlus extends CArithmeticExpression
{
    ArrayList<CArithmeticExpression> m_Elements;
    public CPlus(CArithmeticExpression ... Elements)
    {
        this.m_Elements = new ArrayList<CArithmeticExpression>();
        for(CArithmeticExpression thisVariables : Elements)
        {
            this.m_Elements.add(thisVariables);
            this.add(thisVariables);
        }
    }
    public CPlus(ArrayList<CArithmeticExpression> Elements)
    {
        this.m_Elements = Elements;
        for (int i = 0; i < this.m_Elements.size(); i++)
        {
            this.add(this.m_Elements.get(i));
        }
    }
    public ArrayList<CArithmeticExpression>  GetElements()
    {
        return this.m_Elements;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        String out = "(";
        for (int i = 0; i < this.m_Elements.size() ; i++)
        {
            if(i !=0)
                out+= " + ";
            out += this.m_Elements.get(i).toString();
        }
        out += ")";
        return out;
    }
}
