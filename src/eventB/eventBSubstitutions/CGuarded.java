package eventB.eventBSubstitutions;

import booleanExpression.CAnd;
import booleanExpression.CBooleanExpression;
import booleanExpression.CNot;
import booleanExpression.COr;
import booleanExpression.booleanVisitor.CToPrimeVars;
import eventB.eventBVisitor.IEventBVisitor;
import generalExpression.CSet;
import generalExpression.CVariable;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 23:17
 */
public class CGuarded extends CSubstitution
{
    CBooleanExpression m_Condition;
    CSubstitution m_GuardedSubstitution;
    public CGuarded(CBooleanExpression Condition, CSubstitution GuardedSubstitution)
    {
        this.m_Condition = Condition;
        this.add(this.m_Condition);
        this.m_GuardedSubstitution = GuardedSubstitution;
        this.add(this.m_GuardedSubstitution);
    }
    public CBooleanExpression GetCondition()
    {
        return this.m_Condition;
    }
    public CSubstitution GetSubstitution()
    {
        return this.m_GuardedSubstitution;
    }
    public CBooleanExpression getPrd(CSet<CVariable> X)
    {
        return new CAnd(this.m_Condition,this.m_GuardedSubstitution.getPrd(X));
    }
    public CBooleanExpression getWP(CBooleanExpression P)
    {
        CToPrimeVars cToAnte = new CToPrimeVars("_ante");
        CBooleanExpression Q = (CBooleanExpression)new CNot(this.m_Condition).AcceptVisitor(cToAnte,null);
        return new COr(Q,this.m_GuardedSubstitution.getWP(P));
    }
    public Object AcceptVisitor(IEventBVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return this.m_Condition.toString()+" ⇒ "+this.m_GuardedSubstitution.toString();
    }
}
