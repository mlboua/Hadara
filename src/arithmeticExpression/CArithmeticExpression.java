package arithmeticExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;
import ui.CMutableTreeNode;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 18:37
 */
public abstract class CArithmeticExpression extends CMutableTreeNode
{
    public abstract String toString();
    public abstract Object AcceptVisitor(IBooleanVisitor visitor, Object data);
}
