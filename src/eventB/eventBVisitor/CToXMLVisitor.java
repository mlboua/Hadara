package eventB.eventBVisitor;

import arithmeticExpression.*;
import arithmeticExpression.arithmeticVisitor.IArithmeticVisitor;
import booleanExpression.*;
import booleanExpression.booleanVisitor.IBooleanVisitor;
import eventB.eventBSubstitutions.*;
import eventB.eventBevents.CAnyEvent;
import eventB.eventBevents.CGuardedEvent;
import eventB.eventBevents.CNonGuardedEvent;
import generalExpression.CNumber;
import generalExpression.CVariable;

import org.jdom2.*;

/**
 * User: LoW
 * Date: 13/02/13
 * Time: 10:58
 */
public class CToXMLVisitor implements IEventBVisitor,IArithmeticVisitor,IBooleanVisitor
{
    public Object visit(CSubstitution node, Object data)
    {
        if(node instanceof CSkip)
        {
            return this.visit((CSkip)node,data);
        }
        else if(node instanceof CMultipleAssignment)
        {
            return this.visit((CMultipleAssignment)node,data);
        }
        else if(node instanceof CNDChoice)
        {
            return this.visit((CNDChoice)node,data);
        }
        else if(node instanceof CGuarded)
        {
            return this.visit((CGuarded)node,data);
        }
        else if(node instanceof CAssignment)
        {
            return this.visit((CAssignment)node,data);
        }
        else if(node instanceof CAny)
        {
            return this.visit((CAny)node,data);
        }
        else if(node instanceof CNonGuardedEvent)
        {
            return this.visit((CNonGuardedEvent)node,data);
        }
        else if(node instanceof CGuardedEvent)
        {
            return this.visit((CGuardedEvent)node,data);
        }
        else if(node instanceof CAnyEvent)
        {
            return this.visit((CAnyEvent)node,data);
        }
        else if(node instanceof CIf)
        {
            return this.visit((CIf)node,data);
        }
        else if(node instanceof CParallel)
        {
            return this.visit((CParallel)node,data);
        }
        return null;
    }
    public Object visit(CSkip node, Object data)
    {
        return new Element("CSkip");
    }

    public Object visit(CMultipleAssignment node, Object data)
    {
        Element cParallel = new Element("CMultipleAssignment");
        for(CAssignment assign : node.GetSubstitutions())
        {
            cParallel.addContent((Element) assign.AcceptVisitor(this, null));
        }
        return cParallel;
    }
    public Object visit(CParallel node, Object data)
    {
        Element cParallel = new Element("CParallel");
        for(CSubstitution assign : node.GetSubstitutions())
        {
            cParallel.addContent((Element) assign.AcceptVisitor(this, null));
        }
        return cParallel;
    }

    public Object visit(CNDChoice node, Object data)
    {
        Element cCNDChoice = new Element("CNDChoice");
        for(CSubstitution sub : node.GetChoices())
        {
            cCNDChoice.addContent((Element)sub.AcceptVisitor(this,null));
        }
        return cCNDChoice;
    }

    public Object visit(CGuarded node, Object data)
    {
        Element cCGuarded = new Element("CGuarded");
        cCGuarded.addContent((Element) node.GetCondition().AcceptVisitor(this, null));
        cCGuarded.addContent((Element) node.GetSubstitution().AcceptVisitor(this, null));
        return cCGuarded;
    }

    public Object visit(CAssignment node, Object data)
    {
        Element cCAssignment = new Element("CAssignment");
        cCAssignment.addContent((Element)node.GetVariable().AcceptVisitor(this,null));
        cCAssignment.addContent((Element)node.GetExpression().AcceptVisitor(this,null));
        return cCAssignment;
    }

    public Object visit(CAny node, Object data)
    {
        Element cCAny = new Element("CAny");
        Element VariablesList = new Element("VariablesList");
        for(CVariable var : node.GetVariables())
        {
            VariablesList.addContent((Element)var.AcceptVisitor(this,null));
        }
        cCAny.addContent(VariablesList);
        cCAny.addContent((Element)node.GetCondition().AcceptVisitor(this,null));
        cCAny.addContent((Element)node.GetSubstitution().AcceptVisitor(this,null));
        return cCAny;
    }

    public Object visit(CNonGuardedEvent node, Object data)
    {
        Element cCNonGuardedEvent = new Element("CNonGuardedEvent");
        cCNonGuardedEvent.setAttribute(new Attribute("name",node.GetName()));
        cCNonGuardedEvent.addContent((Element)node.GetSubstitution().AcceptVisitor(this,null));
        return cCNonGuardedEvent;
    }

    public Object visit(CGuardedEvent node, Object data)
    {
        Element cCGuardedEvent = new Element("CGuardedEvent");
        cCGuardedEvent.setAttribute(new Attribute("name",node.GetName()));
        cCGuardedEvent.addContent((Element)node.GetCondition().AcceptVisitor(this,null));
        cCGuardedEvent.addContent((Element)node.GetSubstitution().AcceptVisitor(this,null));
        return cCGuardedEvent;
    }

    public Object visit(CAnyEvent node, Object data)
    {
        Element cCAnyEvent = new Element("CAnyEvent");
        cCAnyEvent.setAttribute(new Attribute("name",node.GetName()));
        Element VariablesList = new Element("VariablesList");
        for(CVariable var : node.GetVariables())
        {
            VariablesList.addContent((Element)var.AcceptVisitor(this,null));
        }
        cCAnyEvent.addContent(VariablesList);
        cCAnyEvent.addContent((Element)node.GetCondition().AcceptVisitor(this,null));
        cCAnyEvent.addContent((Element)node.GetSubstitution().AcceptVisitor(this,null));
        return cCAnyEvent;
    }
    public Object visit(CIf node, Object data)
    {
        Element cCIf = new Element("CIf");

        cCIf.addContent((Element)node.GetCondition().AcceptVisitor(this,null));
        cCIf.addContent((Element)node.GetThenSubstitution().AcceptVisitor(this,null));
        cCIf.addContent((Element)node.GetElseSubstitution().AcceptVisitor(this,null));
        return cCIf;
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
        return null;
    }

    public Object visit(CAnd node, Object data)
    {
        Element cCAnd = new Element("CAnd");
        for(CBooleanExpression exp : node.GetElements())
        {
            cCAnd.addContent((Element)exp.AcceptVisitor(this,null));
        }
        return cCAnd;
    }

    public Object visit(CBooleanAtom node, Object data)
    {
        return new Element("CBooleanAtom not supported");
    }

    public Object visit(CEquals node, Object data)
    {
        Element cCEquals = new Element("CEquals");
        cCEquals.addContent((Element) node.GetLhsElement().AcceptVisitor(this, null));
        cCEquals.addContent((Element)node.GetRhsElement().AcceptVisitor(this, null));
        return cCEquals;
    }

    public Object visit(CExist node, Object data)
    {
        Element cCExist = new Element("CExist");
        Element VariablesList = new Element("VariablesList");
        for(CVariable var : node.GetVariables())
        {
            VariablesList.addContent((Element)var.AcceptVisitor(this,null));
        }
        cCExist.addContent(VariablesList);
        cCExist.addContent((Element)node.GetElement().AcceptVisitor(this,null));
        return cCExist;
    }

    public Object visit(CForall node, Object data)
    {
        Element cCForall = new Element("CForall");
        Element VariablesList = new Element("VariablesList");
        for(CVariable var : node.GetVariables())
        {
            VariablesList.addContent((Element)var.AcceptVisitor(this,null));
        }
        cCForall.addContent(VariablesList);
        cCForall.addContent((Element)node.GetElement().AcceptVisitor(this,null));
        return cCForall;
    }

    public Object visit(CGreater node, Object data)
    {
        Element cCGreater = new Element("CGreater");
        cCGreater.addContent((Element)node.GetLhsElement().AcceptVisitor(this,null));
        cCGreater.addContent((Element)node.GetRhsElement().AcceptVisitor(this,null));
        return cCGreater;
    }

    public Object visit(CInDomain node, Object data)
    {
        Element cCInDomain = new Element("CInDomain");
        cCInDomain.setAttribute(new Attribute("type",new Integer(node.GeSet().GetType()).toString()));
        cCInDomain.addContent((Element)node.GetElement().AcceptVisitor(this,null));
        return cCInDomain;
    }

    public Object visit(CNot node, Object data)
    {
        Element cCNot = new Element("CNot");
        cCNot.addContent((Element)node.GetElement().AcceptVisitor(this,null));
        return cCNot;
    }

    public Object visit(COr node, Object data)
    {
        Element cCOr = new Element("COr");
        for(CBooleanExpression exp : node.GetElements())
        {
            cCOr.addContent((Element)exp.AcceptVisitor(this,null));
        }
        return cCOr;
    }

    public Object visit(CArithmeticTerm node, Object data)
    {
        return new Element("CArithmeticTerm not supported");
    }

    public Object visit(CMinus node, Object data)
    {
        Element cCMinus = new Element("CMinus");
        for(CArithmeticExpression exp : node.GetElements())
        {
            cCMinus.addContent((Element)exp.AcceptVisitor(this,null));
        }
        return cCMinus;
    }

    public Object visit(CPlus node, Object data)
    {
        Element cCPlus = new Element("CPlus");
        for(CArithmeticExpression exp : node.GetElements())
        {
            cCPlus.addContent((Element)exp.AcceptVisitor(this,null));
        }
        return cCPlus;
    }

    public Object visit(CMult node, Object data)
    {
        Element cCMult = new Element("CMult");
        for(CArithmeticExpression exp : node.GetElements())
        {
            cCMult.addContent((Element)exp.AcceptVisitor(this,null));
        }
        return cCMult;
    }

    public Object visit(CVariable node, Object data)
    {
        Element cCVariable = new Element("CVariable");
        cCVariable.setAttribute(new Attribute("val",node.toString()));
        return cCVariable;
    }

    public Object visit(CNumber node, Object data)
    {
        Element cCNumber = new Element("CNumber");
        cCNumber.setAttribute(new Attribute("val",node.toString()));
        return cCNumber;
    }
}
