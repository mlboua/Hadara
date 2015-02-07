package eventB.eventBSubstitutions;

import booleanExpression.CBooleanExpression;
import booleanExpression.booleanVisitor.IBooleanVisitor;
import eventB.eventBVisitor.IEventBVisitor;
import generalExpression.CSet;
import generalExpression.CVariable;
import ui.CMutableTreeNode;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 22:51
 */
public abstract class CSubstitution extends CMutableTreeNode
{
    public abstract String toString();
    public abstract CBooleanExpression getPrd(CSet<CVariable> X);
    public abstract CBooleanExpression getWP(CBooleanExpression P);
    public abstract Object AcceptVisitor(IEventBVisitor visitor, Object data);
}
