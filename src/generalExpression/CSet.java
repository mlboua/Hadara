package generalExpression;

import ui.CMutableTreeNode;

import java.util.Set;

/**
 * User: LoW
 * Date: 30/12/12
 * Time: 19:22
 */
public class CSet<T> extends CMutableTreeNode
{
    public static final int NATURAL = 0;
    public static final int Z = 1;

    public Set<T> m_Set;
    public int m_Type;
    public CSet(int Type)
    {
        m_Type = Type;
    }
    public CSet(Set<T> Set )
    {
        m_Type = -1;
        this.m_Set = Set;
    }
    public int GetType()
    {
        return this.m_Type;
    }
    public String toString()
    {
        if(m_Type == CSet.NATURAL)
            return "ℕ";
        if(m_Type == CSet.Z)
            return "ℤ";
        else
            return this.m_Set.toString();
    }
}
