package arithmeticExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 18:37
 */
public class CArithmeticTerm<T> extends CArithmeticExpression
{
    T m_Element;
    public CArithmeticTerm(T Element)
    {
        this.m_Element = Element;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public T  GetElement()
    {
        return this.m_Element;
    }
    public String toString()
    {
        return this.m_Element.toString();
    }
}
