package eventB.eventBSubstitutions;

import booleanExpression.CAnd;
import booleanExpression.CBooleanAtom;
import booleanExpression.CBooleanExpression;
import booleanExpression.COr;
import eventB.eventBVisitor.IEventBVisitor;
import generalExpression.CBoolean;
import generalExpression.CSet;
import generalExpression.CVariable;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 23:09
 */
public class CNDChoice extends CSubstitution
{
    ArrayList<CSubstitution> m_Choices;
    public CNDChoice(CSubstitution ... Choices)
    {
        this.m_Choices = new ArrayList<CSubstitution>();
        for(CSubstitution thisChoice : Choices)
        {
            this.m_Choices.add(thisChoice);
            this.add(thisChoice);
        }
    }
    public CNDChoice(ArrayList<CSubstitution> Choices)
    {
        this.m_Choices = Choices;
        for(CSubstitution thisChoice : Choices)
        {
            this.add(thisChoice);
        }
    }
    public ArrayList<CSubstitution> GetChoices()
    {
        return this.m_Choices;
    }
    public CBooleanExpression getPrd(CSet<CVariable> X)
    {
        ArrayList<CBooleanExpression> alB = new ArrayList<CBooleanExpression>();
        for (int i = 0; i < this.m_Choices.size(); i++)
        {
            alB.add(this.m_Choices.get(i).getPrd(X));
        }
       return new COr(alB);
    }
    public CBooleanExpression getWP(CBooleanExpression P)
    {
        ArrayList<CBooleanExpression> alB = new ArrayList<CBooleanExpression>();
        for (int i = 0; i < this.m_Choices.size(); i++)
        {
            alB.add(this.m_Choices.get(i).getWP(P));
        }
        return new CAnd(alB);
    }
    public Object AcceptVisitor(IEventBVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        String out = "(";
        for (int i = 0; i < this.m_Choices.size() ; i++)
        {
            if(i !=0)
                out+= " [] ";
            out += this.m_Choices.get(i).toString();
        }
        out += ")";
        return out;
    }
}
