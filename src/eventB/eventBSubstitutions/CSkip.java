package eventB.eventBSubstitutions;

import booleanExpression.CBooleanAtom;
import booleanExpression.CBooleanExpression;
import eventB.eventBVisitor.IEventBVisitor;
import generalExpression.CBoolean;
import generalExpression.CSet;
import generalExpression.CVariable;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 23:15
 */
public class CSkip extends CSubstitution
{
    public CBooleanExpression getPrd(CSet<CVariable> X)
    {
        return new CBooleanAtom< CBoolean >(new CBoolean(true));
    }
    public CBooleanExpression getWP(CBooleanExpression P)
    {
        return P;
    }
    public Object AcceptVisitor(IEventBVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return "SKIP";
    }
}
