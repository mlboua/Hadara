package eventB.eventBevents;

import booleanExpression.CBooleanExpression;
import eventB.eventBSubstitutions.CSubstitution;
import eventB.eventBVisitor.IEventBVisitor;
import eventB.eventBevents.CEvent;
import generalExpression.CSet;
import generalExpression.CVariable;

/**
 * User: LoW
 * Date: 30/12/12
 * Time: 19:40
 */
public class CNonGuardedEvent extends CEvent
{
    String m_Name;
    CSubstitution m_Substitution;
    public CNonGuardedEvent(String Name, CSubstitution Substitution)
    {
        this.m_Name = Name;
        this.m_Substitution = Substitution;
        this.add(this.m_Substitution);
    }
    public String GetName()
    {
        return m_Name;
    }
    public CSubstitution GetSubstitution()
    {
        return this.m_Substitution;
    }
    public CBooleanExpression getPrd(CSet<CVariable> X)
    {
        return this.m_Substitution.getPrd(X);
    }
    public CBooleanExpression getWP(CBooleanExpression P)
    {
        return this.m_Substitution.getWP(P);
    }
    public Object AcceptVisitor(IEventBVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return this.m_Name+" ≝ BEGIN "+this.m_Substitution.toString()+" END";
    }
}
