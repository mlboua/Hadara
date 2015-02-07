package eventB.eventBSubstitutions;

import booleanExpression.CAnd;
import booleanExpression.CBooleanExpression;
import booleanExpression.CEquals;
import booleanExpression.CNot;
import booleanExpression.booleanVisitor.CReplaceVar;
import eventB.eventBVisitor.IEventBVisitor;
import generalExpression.CSet;
import generalExpression.CVariable;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * User: LoW
 * Date: 04/04/13
 * Time: 20:46
 */
public class CParallel  extends CSubstitution
{
    ArrayList<CSubstitution> m_Substitutions;
    CSubstitution m_Surrogate;
    public CParallel(CSubstitution... Substitutions)
    {
        this.m_Substitutions = new ArrayList<CSubstitution>();
        for(CSubstitution thisChoice : Substitutions)
        {
            this.m_Substitutions.add(thisChoice);
            this.add(thisChoice);
        }
        this.m_Surrogate = retPrimitive(Substitutions);
        //System.out.println(this.m_Surrogate.toString());
    }
    public CParallel( ArrayList<CSubstitution> Substitutions)
    {
        this.m_Substitutions = new ArrayList<CSubstitution>();
        for(CSubstitution thisChoice : Substitutions)
        {
            this.m_Substitutions.add(thisChoice);
            this.add(thisChoice);
        }
        this.m_Surrogate = retPrimitive(Substitutions);
    }
    public CSubstitution retPrimitive( ArrayList<CSubstitution> Substitutions)
    {
        CSubstitution tmp = Substitutions.get(0);
        for (int i = 1; i < Substitutions.size(); i++)
        {
            tmp =  retPrimitive(tmp,Substitutions.get(i));
        }
        return tmp;
    }
    public CSubstitution retPrimitive(CSubstitution ... Substitutions)
    {
        CSubstitution tmp = Substitutions[0];
        for (int i = 1; i < Substitutions.length; i++)
        {
            tmp =  retPrimitive(tmp,Substitutions[i]);
        }
        return tmp;
    }
    public CSubstitution retPrimitive(CSubstitution Substitution1, CSubstitution Substitution2)
    {
        if(Substitution1 instanceof CParallel)
        {
            return retPrimitive((CParallel) Substitution1, Substitution2);
        }
        else if(Substitution2 instanceof CParallel)
        {
            return retPrimitive(Substitution1, (CParallel) Substitution2);
        }
        if(Substitution1 instanceof CSkip)
        {
            return retPrimitive((CSkip) Substitution1, Substitution2);
        }
        else if(Substitution2 instanceof CSkip)
        {
            return retPrimitive(Substitution1, (CSkip) Substitution2);
        }
        if(Substitution1 instanceof CNDChoice)
        {
            return retPrimitive((CNDChoice) Substitution1, Substitution2);
        }
        else if(Substitution2 instanceof CNDChoice)
        {
            return retPrimitive(Substitution1, (CNDChoice) Substitution2);
        }
        if(Substitution1 instanceof CGuarded)
        {
            return retPrimitive((CGuarded) Substitution1, Substitution2);
        }
        else if(Substitution2 instanceof CGuarded)
        {
            return retPrimitive(Substitution1, (CGuarded) Substitution2);
        }
        if(Substitution1 instanceof CAny)
        {
            return retPrimitive((CAny) Substitution1, Substitution2);
        }
        else if(Substitution2 instanceof CAny)
        {
            return retPrimitive(Substitution1, (CAny) Substitution2);
        }
        if(Substitution1 instanceof CIf)
        {
            return retPrimitive((CIf) Substitution1, Substitution2);
        }
        else if(Substitution2 instanceof CIf)
        {
            return retPrimitive(Substitution1, (CIf) Substitution2);
        }
        if(Substitution1 instanceof CMultipleAssignment)
        {
            return retPrimitive((CMultipleAssignment) Substitution1, (CAssignment)Substitution2);
        }
        else if(Substitution2 instanceof CMultipleAssignment)
        {
            return retPrimitive((CAssignment)Substitution1, (CMultipleAssignment) Substitution2);
        }
        return new CMultipleAssignment((CAssignment)Substitution1,(CAssignment)Substitution2);
    }
    public CSubstitution retPrimitive(CSubstitution Substitution, CSkip Skip)
    {
        return Substitution;
    }
    public CSubstitution retPrimitive(CSkip Skip, CSubstitution Substitution)
    {
        return Substitution;
    }
    public CSubstitution retPrimitive(CAssignment Substitution, CMultipleAssignment Choice)
    {
        ArrayList<CAssignment> ar = new ArrayList<CAssignment>();
        ar.add(Substitution);
        for (int i = 0; i < Choice.GetSubstitutions().size(); i++)
        {
            ar.add(Choice.GetSubstitutions().get(i));
        }
        return new CMultipleAssignment(ar);
    }
    public CSubstitution retPrimitive(CMultipleAssignment Choice, CAssignment Substitution)
    {
        ArrayList<CAssignment> ar = new ArrayList<CAssignment>();
        ar.add(Substitution);
        for (int i = 0; i < Choice.GetSubstitutions().size(); i++)
        {
            ar.add(Choice.GetSubstitutions().get(i));
        }
        return new CMultipleAssignment(ar);
    }
    public CSubstitution retPrimitive(CSubstitution Substitution, CParallel Choice)
    {
        Choice.GetSubstitutions().add(Substitution);
        return Choice;
    }
    public CSubstitution retPrimitive(CParallel Choice, CSubstitution Substitution)
    {
        Choice.GetSubstitutions().add(Substitution);
        return Choice;
    }
    public CSubstitution retPrimitive(CSubstitution Substitution, CNDChoice Choice)
        {
            ArrayList<CSubstitution> ar = new ArrayList<CSubstitution>();
            for (int i = 0; i < Choice.GetChoices().size(); i++)
            {
                ar.add(retPrimitive(Substitution,Choice.GetChoices().get(i)));
            }
            return new CNDChoice(ar);
        }
    public CSubstitution retPrimitive(CNDChoice Choice, CSubstitution Substitution)
    {
        ArrayList<CSubstitution> ar = new ArrayList<CSubstitution>();
        for (int i = 0; i < Choice.GetChoices().size(); i++)
        {
            ar.add(retPrimitive(Substitution,Choice.GetChoices().get(i)));
        }
        return new CNDChoice(ar);
    }
    public CSubstitution retPrimitive(CSubstitution Substitution, CGuarded Choice)
    {
        return new CGuarded(Choice.GetCondition(),this.retPrimitive(Substitution,Choice.GetSubstitution()));
    }
    public CSubstitution retPrimitive(CGuarded Choice, CSubstitution Substitution)
    {
        return new CGuarded(Choice.GetCondition(),this.retPrimitive(Choice.GetSubstitution(),Substitution));
    }
    public CSubstitution retPrimitive(CSubstitution Substitution, CAny Choice)
    {
        return new CAny(Choice.GetCondition(),this.retPrimitive(Substitution,Choice.GetSubstitution()),Choice.GetVariables());
    }
    public CSubstitution retPrimitive(CAny Choice, CSubstitution Substitution)
    {
        return new CAny(Choice.GetCondition(),this.retPrimitive(Choice.GetSubstitution(),Substitution),Choice.GetVariables());
    }
    public CSubstitution retPrimitive(CSubstitution Substitution, CIf Choice)
    {
        return new CIf(Choice.GetCondition(),this.retPrimitive(Substitution,Choice.GetThenSubstitution()),this.retPrimitive(Substitution,Choice.GetElseSubstitution()));
    }
    public CSubstitution retPrimitive(CIf Choice, CSubstitution Substitution)
    {
        return new CIf(Choice.GetCondition(),this.retPrimitive(Choice.GetThenSubstitution(),Substitution),this.retPrimitive(Choice.GetElseSubstitution(),Substitution));
    }

    public ArrayList<CSubstitution> GetSubstitutions()
    {
        return this.m_Substitutions;
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
