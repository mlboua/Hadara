package eventB.eventBSubstitutions;

import booleanExpression.CAnd;
import booleanExpression.CBooleanExpression;
import booleanExpression.booleanVisitor.CReplaceVar;
import eventB.eventBVisitor.IEventBVisitor;
import generalExpression.CSet;
import generalExpression.CVariable;
import arithmeticExpression.CArithmeticExpression;
import booleanExpression.CEquals;

import java.util.ArrayList;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 22:57
 */
public class CAssignment extends CSubstitution
{
    CVariable m_Variable;
    CArithmeticExpression  m_Expression;
    public CAssignment(CVariable Variable, CArithmeticExpression Expression)
    {
        this.m_Variable = Variable;
        this.add(this.m_Variable);
        this.m_Expression = Expression;
        this.add(this.m_Expression);
    }
    public CVariable GetVariable()
    {
        return this.m_Variable;
    }
    public CArithmeticExpression GetExpression()
    {
        return this.m_Expression;
    }
    public CBooleanExpression getPrd(CSet<CVariable> X)
    {
        ArrayList<CBooleanExpression> alB = new ArrayList<CBooleanExpression>();
        for(CVariable thisVar : X.m_Set)
        {
            if(thisVar.equals(this.m_Variable))
            {
                CVariable tmpprime = new CVariable(this.m_Variable.toString()+"_prime");
                CBooleanExpression bExp = new CEquals(tmpprime,this.m_Expression);
                alB.add(bExp);
            }
            else
            {
                 alB.add(new CEquals(new CVariable(thisVar.toString()+"_prime"),thisVar)) ;
            }
        }
        return new CAnd(alB);
    }
    public CBooleanExpression getWP(CBooleanExpression P)
    {
        CReplaceVar cReplaceVar = new CReplaceVar(new CVariable(this.m_Variable.toString()+"_ante"));
        return (CBooleanExpression)P.AcceptVisitor(cReplaceVar,this.m_Expression);
    }
    public Object AcceptVisitor(IEventBVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        return this.m_Variable.toString() + " := "  + this.m_Expression.toString();
    }
}
