package generalExpression;


import booleanExpression.booleanVisitor.IBooleanVisitor;
import arithmeticExpression.CArithmeticExpression;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 23:01
 */
public class CVariable extends CArithmeticExpression
{
    String m_Label;
    public CVariable(String Label)
    {
        this.m_Label = Label;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public boolean equals(Object aThat)
    {
        if ( this == aThat ) return true;
        if ( !(aThat instanceof CVariable) ) return false;
        CVariable that = (CVariable)aThat;
        return this.m_Label.equals(that.toString());
    }
    public String toString()
    {
        return m_Label;
    }
}
