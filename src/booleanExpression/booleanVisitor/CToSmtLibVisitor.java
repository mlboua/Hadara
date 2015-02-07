package booleanExpression.booleanVisitor;

import booleanExpression.*;
import generalExpression.CNumber;
import generalExpression.CVariable;
import arithmeticExpression.CArithmeticTerm;
import arithmeticExpression.CMinus;
import arithmeticExpression.CMult;
import arithmeticExpression.CPlus;

/**
 * User: LoW
 * Date: 11/01/13
 * Time: 00:10
 */
public  class CToSmtLibVisitor implements IBooleanVisitor
{
    public Object visit(CBooleanExpression node, Object data)
    {
        if(node instanceof CAnd)
        {
            return this.visit((CAnd)node,data);
        }
        else if(node instanceof CBooleanAtom)
        {
            return this.visit((CBooleanAtom)node,data);
        }
        else if(node instanceof CEquals)
        {
            return this.visit((CEquals)node,data);
        }
        else if(node instanceof CExist)
        {
            return this.visit((CExist)node,data);
        }
        else if(node instanceof CForall)
        {
            return this.visit((CForall)node,data);
        }
        else if(node instanceof CGreater)
        {
            return this.visit((CGreater)node,data);
        }
        else if(node instanceof CInDomain)
        {
            return this.visit((CInDomain)node,data);
        }
        else if(node instanceof CNot)
        {
            return this.visit((CNot)node,data);
        }
        else if(node instanceof COr)
        {
            return this.visit((COr)node,data);
        }
        return "(does not happen or it's bad)";
    }
    public Object visit(CAnd node, Object data)
    {
        String sData = "(and";
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            sData = sData + " "+node.GetElements().get(i).AcceptVisitor(this, null);
        }
        sData = sData+")";
        return sData;
    }
    public Object visit(CBooleanAtom node, Object data)
    {
        return node.toString();
    }
    public Object visit(CEquals node, Object data)
    {
        String sData = "(= ";
        sData = sData + node.GetLhsElement().AcceptVisitor(this,null);
        sData = sData+" ";
        sData = sData + node.GetRhsElement().AcceptVisitor(this,null);
        sData = sData+")";
        return sData;
    }
    public Object visit(CExist node, Object data)
    {
        String sData = "(exists (";
        for (int i = 0; i < node.GetVariables().size(); i++)
        {
            sData = sData + "("+node.GetVariables().get(i).toString()+" Int)";
        }
        sData = sData+") ";
        sData = sData + node.GetElement().AcceptVisitor(this,null);
        sData = sData+")";
        return sData;
    }
    public Object visit(CForall node, Object data)
    {
        String sData = "(forall (";
        for (int i = 0; i < node.GetVariables().size(); i++)
        {
            sData = sData + "("+node.GetVariables().get(i).toString()+" Int)";
        }
        sData = sData+")";
        sData = sData + node.GetElement().AcceptVisitor(this,null);
        sData = sData+")";
        return sData;
    }
    public Object visit(CGreater node, Object data)
    {
        String sData = "(> ";
        sData = sData + node.GetLhsElement().AcceptVisitor(this,null);
        sData = sData+" ";
        sData = sData + node.GetRhsElement().AcceptVisitor(this,null);
        sData = sData+")";
        return sData;
    }
    public Object visit(CInDomain node, Object data)
    {
        return "(>= "+node.GetElement().AcceptVisitor(this,null)+" 0)";
    }
    public Object visit(CNot node, Object data)
    {
        return "(not "+node.GetElement().AcceptVisitor(this,null)+")";
    }
    public Object visit(COr node, Object data)
    {
        String sData = "(or";
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            sData = sData + " "+node.GetElements().get(i).AcceptVisitor(this, null);
        }
        sData = sData+")";
        return sData;
    }
    public Object visit(CArithmeticTerm node, Object data)
    {
        return node.toString();
    }
    public Object visit(CMinus node, Object data)
    {
        String sData = "(-";
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            sData = sData + " "+node.GetElements().get(i).AcceptVisitor(this, null);
        }
        sData = sData+")";
        return sData;
    }
    public Object visit(CPlus node, Object data)
    {
        String sData = "(+";
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            sData = sData + " "+node.GetElements().get(i).AcceptVisitor(this, null);
        }
        sData = sData+")";
        return sData;
    }
    public Object visit(CMult node, Object data)
    {
        String sData = "(*";
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            sData = sData + " "+node.GetElements().get(i).AcceptVisitor(this, null);
        }
        sData = sData+")";
        return sData;
    }
    public Object visit(CVariable node, Object data)
    {
        return node.toString();
    }
    public Object visit(CNumber node, Object data)
    {
        return node.toString();
    }
}
