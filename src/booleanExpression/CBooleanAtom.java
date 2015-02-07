package booleanExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 17:50
 */
public class CBooleanAtom<T> extends CBooleanExpression
{
    T m_Element;
    public CBooleanAtom(T Element)
    {
        this.m_Element = Element;
    }
    public T GetElement()
    {
        return this.m_Element;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return this.m_Element.toString();
    }
}
