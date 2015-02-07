package eventB.eventBevents;

import booleanExpression.CAnd;
import booleanExpression.CBooleanExpression;
import booleanExpression.CNot;
import booleanExpression.COr;
import booleanExpression.booleanVisitor.CToPrimeVars;
import eventB.eventBSubstitutions.CSubstitution;
import eventB.eventBVisitor.IEventBVisitor;
import generalExpression.CSet;
import generalExpression.CVariable;

/**
 * User: LoW
 * Date: 30/12/12
 * Time: 19:47
 */
public class CGuardedEvent extends CEvent
{
    String m_Name;
    CBooleanExpression m_Condition;
    CSubstitution m_Substitution;
    public CGuardedEvent(String Name, CBooleanExpression Condition, CSubstitution Substitution)
    {
        this.m_Name = Name;
        this.m_Condition = Condition;
        this.add(this.m_Condition);
        this.m_Substitution = Substitution;
        this.add(this.m_Substitution);
    }
    public String GetName()
    {
        return m_Name;
    }
    public CBooleanExpression GetCondition()
    {
        return this.m_Condition;
    }
    public CSubstitution GetSubstitution()
    {
        return this.m_Substitution;
    }
    public CBooleanExpression getPrd(CSet<CVariable> X)
    {
        return new CAnd(this.m_Condition,this.m_Substitution.getPrd(X));
    }
    public CBooleanExpression getWP(CBooleanExpression P)
    {
        CToPrimeVars cToAnte = new CToPrimeVars("_ante");
        CBooleanExpression Q = (CBooleanExpression)new CNot(this.m_Condition).AcceptVisitor(cToAnte,null);
        return new COr(Q,this.m_Substitution.getWP(P));
    }
    public Object AcceptVisitor(IEventBVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return this.m_Name+" ≝ SELECT "+this.m_Condition.toString()+" THEN "+this.m_Substitution.toString()+" END";
    }
}
