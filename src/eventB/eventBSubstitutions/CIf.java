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
 * Date: 04/04/13
 * Time: 16:51
 */
public class CIf extends CSubstitution
{
    CBooleanExpression m_Condition;
    CSubstitution m_ThenSubstitution;
    CSubstitution m_ElseSubstitution;
    CSubstitution m_Surrogate;

    public CIf(CBooleanExpression Condition, CSubstitution ThenSubstitution, CSubstitution ElseSubstitution)
    {
        this.m_Condition = Condition;
        this.add(this.m_Condition);
        this.m_ThenSubstitution = ThenSubstitution;
        this.add(this.m_ThenSubstitution);
        this.m_ElseSubstitution = ElseSubstitution;
        this.add(this.m_ElseSubstitution);
        this.m_Surrogate = new CNDChoice(new CGuarded(this.m_Condition,this.m_ThenSubstitution),new CGuarded(new CNot(this.m_Condition),this.m_ElseSubstitution));
    }
    public CBooleanExpression GetCondition()
    {
        return this.m_Condition;
    }
    public CSubstitution GetThenSubstitution()
    {
        return this.m_ThenSubstitution;
    }
    public CSubstitution GetElseSubstitution()
    {
        return this.m_ElseSubstitution;
    }
    public CBooleanExpression getPrd(CSet<CVariable> X)
    {
        return this.m_Surrogate.getPrd(X);
    }
    public CBooleanExpression getWP(CBooleanExpression P)
    {
       return this.m_Surrogate.getWP(P);
    }
    public Object AcceptVisitor(IEventBVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return "IF "+this.m_Condition.toString()+" THEN "+this.m_ThenSubstitution.toString()+" ELSE "+this.m_ElseSubstitution.toString()+" END";
    }
}
