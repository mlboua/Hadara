package booleanExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;
import arithmeticExpression.CArithmeticExpression;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 18:55
 */
public class CEquals extends CBooleanExpression
{
    CArithmeticExpression m_lhsElement, m_rhsElement;
    public CEquals(CArithmeticExpression lhsElement, CArithmeticExpression rhsElement)
    {
        this.m_lhsElement = lhsElement;
        this.add(this.m_lhsElement);
        this.m_rhsElement = rhsElement;
        this.add(this.m_rhsElement);
    }
    public CArithmeticExpression GetLhsElement()
    {
        return this.m_lhsElement;
    }
    public CArithmeticExpression GetRhsElement()
    {
        return this.m_rhsElement;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return this.m_lhsElement.toString()+" = "+this.m_rhsElement.toString();
    }
}
