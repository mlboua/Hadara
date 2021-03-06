package booleanExpression.booleanVisitor;

import arithmeticExpression.*;
import booleanExpression.*;
import generalExpression.CNumber;
import generalExpression.CSet;
import generalExpression.CVariable;

import java.util.ArrayList;

/**
 * User: LoW
 * Date: 06/03/13
 * Time: 13:06
 */
public class CReplaceVar implements IBooleanVisitor
{
    CVariable m_toReplace;
    public CReplaceVar(CVariable var)
    {
        this.m_toReplace = var;
    }
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
        ArrayList<CBooleanExpression> Elements = new ArrayList<CBooleanExpression>();
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            Elements.add((CBooleanExpression)node.GetElements().get(i).AcceptVisitor(this, data));
        }
        return new CAnd(Elements);
    }
    public Object visit(CBooleanAtom node, Object data)
    {
        return node;
    }
    public Object visit(CEquals node, Object data)
    {
        return new CEquals((CArithmeticExpression)node.GetLhsElement().AcceptVisitor(this, data),(CArithmeticExpression)node.GetRhsElement().AcceptVisitor(this, data));
    }
    public Object visit(CExist node, Object data)
    {
        ArrayList<CVariable> Elements = new ArrayList<CVariable>();
        for (int i = 0; i < node.GetVariables().size(); i++)
        {
            Elements.add((CVariable)node.GetVariables().get(i).AcceptVisitor(this, data));
        }

        return new CExist((CBooleanExpression)node.GetElement().AcceptVisitor(this, data),Elements);
    }
    public Object visit(CForall node, Object data)
    {
        ArrayList<CVariable> Elements = new ArrayList<CVariable>();
        for (int i = 0; i < node.GetVariables().size(); i++)
        {
            Elements.add((CVariable)node.GetVariables().get(i).AcceptVisitor(this, data));
        }

        return new CForall((CBooleanExpression)node.GetElement().AcceptVisitor(this, data),Elements);
    }
    public Object visit(CGreater node, Object data)
    {
        return new CGreater((CArithmeticExpression)node.GetLhsElement().AcceptVisitor(this, data),(CArithmeticExpression)node.GetRhsElement().AcceptVisitor(this, data));
    }
    public Object visit(CInDomain node, Object data)
    {
        return new COr(new CEquals((CArithmeticExpression)node.GetElement().AcceptVisitor(this, data), new CNumber(0)), new CGreater((CArithmeticExpression)node.GetElement().AcceptVisitor(this, data), new CNumber(0)));
    }
    public Object visit(CNot node, Object data)
    {
        return new CNot((CBooleanExpression)node.GetElement().AcceptVisitor(this, data));
    }
    public Object visit(COr node, Object data)
    {
        ArrayList<CBooleanExpression> Elements = new ArrayList<CBooleanExpression>();
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            Elements.add((CBooleanExpression)node.GetElements().get(i).AcceptVisitor(this, data));
        }
        return new COr(Elements);
    }
    public Object visit(CArithmeticTerm node, Object data)
    {
        return node;
    }
    public Object visit(CMinus node, Object data)
    {
        ArrayList<CArithmeticExpression> Elements = new ArrayList<CArithmeticExpression>();
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            Elements.add((CArithmeticExpression)node.GetElements().get(i).AcceptVisitor(this, data));
        }
        return new CMinus(Elements);
    }
    public Object visit(CPlus node, Object data)
    {
        ArrayList<CArithmeticExpression> Elements = new ArrayList<CArithmeticExpression>();
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            Elements.add((CArithmeticExpression)node.GetElements().get(i).AcceptVisitor(this, data));
        }
        return new CPlus(Elements);
    }
    public Object visit(CMult node, Object data)
    {
        ArrayList<CArithmeticExpression> Elements = new ArrayList<CArithmeticExpression>();
        for (int i = 0; i < node.GetElements().size(); i++)
        {
            Elements.add((CArithmeticExpression)node.GetElements().get(i).AcceptVisitor(this, data));
        }
        return new CMult(Elements);
    }
    public Object visit(CVariable node, Object data)
    {
        if(node.equals(this.m_toReplace))
        {
            return data;
        }
        else
        {
            return node;
        }
    }
    public Object visit(CNumber node, Object data)
    {
        return node;
    }
}
