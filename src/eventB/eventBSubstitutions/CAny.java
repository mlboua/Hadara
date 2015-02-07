package eventB.eventBSubstitutions;

import booleanExpression.CAnd;
import booleanExpression.CBooleanExpression;
import booleanExpression.CExist;
import booleanExpression.CForall;
import eventB.eventBVisitor.IEventBVisitor;
import eventB.eventBevents.CEvent;
import generalExpression.CSet;
import generalExpression.CVariable;
import ui.CMutableTreeNode;

import java.util.ArrayList;

/**
 * User: LoW
 * Date: 13/01/13
 * Time: 18:37
 */
public class CAny extends CSubstitution
{
    ArrayList<CVariable> m_Variables;
    CMutableTreeNode m_VariablesNode;
    CBooleanExpression m_Condition;
    CSubstitution m_Substitution;

    public CAny(CBooleanExpression Condition, CSubstitution Substitution, CVariable ... Variables)
    {
        this.m_Variables = new ArrayList<CVariable>();
        this.m_VariablesNode = new CMutableTreeNode("VARIABLES");
        this.add(this.m_VariablesNode);
        for(CVariable thisVariable : Variables)
        {
            this.m_Variables.add(thisVariable);
            this.m_VariablesNode.add(thisVariable);
        }
        this.m_Condition = Condition;
        this.add(this.m_Condition);
        this.m_Substitution = Substitution;
        this.add(this.m_Substitution);
    }
    public CAny(CBooleanExpression Condition, CSubstitution Substitution, ArrayList<CVariable> Variables)
    {
        this.m_Variables = Variables;
        this.m_VariablesNode = new CMutableTreeNode("VARIABLES");
        this.add(this.m_VariablesNode);
        for(CVariable thisVariable : Variables)
        {
            this.m_VariablesNode.add(thisVariable);
        }
        this.m_Condition = Condition;
        this.add(this.m_Condition);
        this.m_Substitution = Substitution;
        this.add(this.m_Substitution);
    }
    public ArrayList<CVariable> GetVariables()
    {
        return this.m_Variables;
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
        CSet<CVariable> newSet = new CSet(X.m_Set);
        newSet.m_Set.addAll(this.m_Variables);

        ArrayList<CVariable> newVariables = new ArrayList<CVariable>();
        for (int i = 0; i < this.m_Variables.size(); i++)
        {
            newVariables.add(this.m_Variables.get(i));
            newVariables.add(new CVariable(this.m_Variables.get(i).toString()+"_prime"));
        }
        return new CExist(new CAnd(this.m_Condition,this.m_Substitution.getPrd(newSet)),newVariables);
    }
    public CBooleanExpression getWP(CBooleanExpression P)
    {
        return new CForall(this.m_Substitution.getWP(P),this.m_Variables);
    }
    public Object AcceptVisitor(IEventBVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        String out = "ANY ";
        for (int i = 0; i < this.m_Variables.size() ; i++)
        {
            if(i !=0)
                out+= ", ";
            out += this.m_Variables.get(i).toString();
        }
        out += " WHERE "+this.m_Condition.toString()+" THEN "+this.m_Substitution+" END";
        return out;
    }
}
