package eventB.eventBSubstitutions;

import booleanExpression.CAnd;
import booleanExpression.CBooleanAtom;
import booleanExpression.CBooleanExpression;
import booleanExpression.CEquals;
import booleanExpression.booleanVisitor.CReplaceVar;
import eventB.eventBVisitor.IEventBVisitor;
import generalExpression.CBoolean;
import generalExpression.CSet;
import generalExpression.CVariable;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * User: LoW
 * Date: 30/12/12
 * Time: 20:17
 */
public class CMultipleAssignment extends CSubstitution
{
    ArrayList<CAssignment> m_Substitutions;
    public CMultipleAssignment(CAssignment... Substitutions)
    {
        this.m_Substitutions = new ArrayList<CAssignment>();
        for(CAssignment thisChoice : Substitutions)
        {
            this.m_Substitutions.add(thisChoice);
            this.add(thisChoice);
        }
    }
    public CMultipleAssignment(ArrayList<CAssignment> Substitutions)
    {
        this.m_Substitutions = Substitutions;
        for(CAssignment thisChoice : Substitutions)
        {
            this.add(thisChoice);
        }
    }
    public ArrayList<CAssignment> GetSubstitutions()
    {
        return this.m_Substitutions;
    }
    public CBooleanExpression getPrd(CSet<CVariable> X)
    {
        ArrayList<CBooleanExpression> arPrd = new  ArrayList<CBooleanExpression>();
        ArrayList<CVariable> arDeclared = new  ArrayList<CVariable>();
        for (int i = 0; i <this.m_Substitutions.size(); i++)
        {
            HashSet<CVariable> cPrsSet = new HashSet<CVariable>();
            cPrsSet.add(this.m_Substitutions.get(i).m_Variable);
            arPrd.add(this.m_Substitutions.get(i).getPrd(new CSet<CVariable>(cPrsSet)));
            arDeclared.add(this.m_Substitutions.get(i).m_Variable);
        }
        for(CVariable var : X.m_Set)
        {
            if(!arDeclared.contains(var))
                arPrd.add(new CEquals(new CVariable(var.toString()+"_prime"),var));
        }
        return new CAnd(arPrd);
    }
    public CBooleanExpression getWP(CBooleanExpression P)
    {
        CBooleanExpression ret = P;
        for (int i = 0; i <this.m_Substitutions.size(); i++)
        {
            CReplaceVar cReplaceVar = new CReplaceVar(new CVariable(this.m_Substitutions.get(i).GetVariable()+"_ante"));
            ret = (CBooleanExpression)ret.AcceptVisitor(cReplaceVar,this.m_Substitutions.get(i).GetExpression());
        }
        return ret;
    }
    public Object AcceptVisitor(IEventBVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        String out = "(";
        for (int i = 0; i < this.m_Substitutions.size() ; i++)
        {
            if(i !=0)
                out+= " || ";
            out += this.m_Substitutions.get(i).toString();
        }
        out += ")";
        return out;
    }
}
