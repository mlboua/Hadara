package generalExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;
import arithmeticExpression.CArithmeticExpression;

/**
 * User: LoW
 * Date: 09/01/13
 * Time: 23:33
 */
public class CNumber extends CArithmeticExpression
{
    int m_Val;
    public CNumber(int Val)
    {
        this.m_Val = Val;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return ""+m_Val;
    }
}
