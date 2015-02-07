package booleanExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;
import arithmeticExpression.CArithmeticExpression;

/**
 * User: LoW
 * Date: 07/01/13
 * Time: 15:18
 */
public class CGreater extends CBooleanExpression
{
    CArithmeticExpression m_lhsElement,m_rhsElement;
    public CGreater(CArithmeticExpression lhsElement,CArithmeticExpression rhsElement)
    {
        super();
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
        return this.m_lhsElement.toString()+" > "+this.m_rhsElement.toString();
    }
}
