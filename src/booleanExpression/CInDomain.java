package booleanExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;
import generalExpression.CSet;
import generalExpression.CVariable;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Set;

/**
 * User: LoW
 * Date: 30/12/12
 * Time: 19:13
 */
public class CInDomain extends CBooleanExpression
{
    CVariable m_Element;
    CSet<CVariable> m_Set;
    public CInDomain(CVariable Element, int type)
    {
        super();
        this.m_Element = Element;
        this.add(this.m_Element);
        this.m_Set = new CSet(type);
        this.add(this.m_Set);
    }
    public CInDomain(CVariable Element,Set<CVariable> Set )
    {
        super();
        this.m_Element = Element;
        this.m_Set = new CSet(Set);
    }
    public CVariable GetElement()
    {
        return m_Element;
    }
    public CSet<CVariable> GeSet()
    {
        return m_Set;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return this.m_Element.toString()+" ∈ "+this.m_Set.toString();
    }
}
