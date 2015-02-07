package booleanExpression;


import booleanExpression.booleanVisitor.IBooleanVisitor;
import ui.CMutableTreeNode;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 17:51
 */
public abstract class CBooleanExpression extends CMutableTreeNode
{
    public abstract String toString();
    public abstract Object AcceptVisitor(IBooleanVisitor visitor, Object data);
}
