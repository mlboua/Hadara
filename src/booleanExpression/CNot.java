package booleanExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;

import java.util.ArrayList;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 17:55
 */
public class CNot extends CBooleanExpression
{
    CBooleanExpression m_Element;
    public CNot(CBooleanExpression Element)
    {
        super();
        this.m_Element = Element;
        this.add(this.m_Element);
    }
    public CBooleanExpression GetElement()
    {
        return this.m_Element;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return "¬("+this.m_Element.toString()+")";
    }
}
