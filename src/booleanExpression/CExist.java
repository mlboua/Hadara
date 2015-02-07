package booleanExpression;

import booleanExpression.booleanVisitor.IBooleanVisitor;
import generalExpression.CVariable;
import ui.CMutableTreeNode;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

/**
 * User: LoW
 * Date: 28/12/12
 * Time: 18:08
 */
public class CExist extends CBooleanExpression
{
    ArrayList<CVariable> m_Variables;
    CMutableTreeNode m_VariablesNode;
    CBooleanExpression m_Element;
    public CExist(CBooleanExpression Element,CVariable ... Variables)
    {
        super();
        this.m_Element = Element;
        this.add(this.m_Element);
        this.m_Variables = new ArrayList<CVariable>();
        this.m_VariablesNode = new CMutableTreeNode("VARIABLES");
        this.add(this.m_VariablesNode);
        for(CVariable thisVariables : Variables)
        {
            this.m_Variables.add(thisVariables);
            this.m_VariablesNode.add(thisVariables);
        }
    }
    public CExist(CBooleanExpression Element,ArrayList<CVariable> Variables)
    {
        super();
        this.m_Element = Element;
        this.add(this.m_Element);
        this.m_Variables = Variables;
        this.m_VariablesNode = new CMutableTreeNode("VARIABLES");
        this.add(this.m_VariablesNode);
        for(CVariable thisVariables : Variables)
        {
            this.m_VariablesNode.add(thisVariables);
        }
    }
    public ArrayList<CVariable> GetVariables()
    {
        return this.m_Variables;
    }
    public CBooleanExpression GetElement()
    {
        return this.m_Element;
    }
    public Object AcceptVisitor(IBooleanVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }
    public String toString()
    {
        String out = "∃ ";
        for (int i = 0; i < this.m_Variables.size() ; i++)
        {
            if(i !=0)
                out+= ", ";
            out += this.m_Variables.get(i).toString();
        }
        out += " : ("+this.m_Element.toString()+")";
        return out;
    }
}
