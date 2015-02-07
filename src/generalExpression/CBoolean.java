package generalExpression;

import ui.CMutableTreeNode;

/**
 * User: LoW
 * Date: 05/01/13
 * Time: 00:19
 */
public class CBoolean extends CMutableTreeNode
{
    boolean m_Val;
    public CBoolean(boolean Val)
    {
        this.m_Val = Val;
    }
    public String toString()
    {
        return ""+m_Val;
    }
}