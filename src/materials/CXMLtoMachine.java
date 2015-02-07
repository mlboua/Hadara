package materials;

import abstraction.sts.CModalTransition;
import abstraction.sts.CState;
import abstraction.sts.CTransition;
import abstraction.sts.EModalities;
import arithmeticExpression.CArithmeticExpression;
import arithmeticExpression.CMinus;
import arithmeticExpression.CMult;
import arithmeticExpression.CPlus;
import booleanExpression.*;
import eventB.CMachine;
import eventB.eventBSubstitutions.*;
import eventB.eventBevents.CAnyEvent;
import eventB.eventBevents.CEvent;
import eventB.eventBevents.CGuardedEvent;
import eventB.eventBevents.CNonGuardedEvent;
import generalExpression.CNumber;
import generalExpression.CVariable;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * User: LoW
 * Date: 18/02/13
 * Time: 16:55
 */
public class CXMLtoMachine
{
    public static CMachine getMachineFrom(Document doc) throws Exception
    {
        return getMachineFrom(doc.getRootElement());
    }
    public static CMachine getMachineFrom(Element root) throws Exception
    {
        List<Element> rootChildren = root.getChildren();
        if(rootChildren.size() != 4)
        {
            throw new Exception("4 children excepted : variables, invariant, initialisation, events");
        }
        CMachine ret = new CMachine(root.getAttribute("name").getValue());

        if(!rootChildren.get(0).getName().equals("VARIABLES"))
            throw new Exception(root.getName()+" 0 must be : VARIABLES");

        ret.setVariables(CXMLtoMachine.getVariablesFrom(rootChildren.get(0)));

        if(!rootChildren.get(1).getName().equals("INVARIANT"))
            throw new Exception(root.getName()+" 1 must be : INVARIANT");

        ret.setInvariant(CXMLtoMachine.getBooleanExpressionFrom(rootChildren.get(1).getChildren().get(0)));

        if(!rootChildren.get(2).getName().equals("INITIALISATION"))
            throw new Exception(root.getName()+" 2 must be : INITIALISATION");

        ret.setInit(CXMLtoMachine.getSubstitutionFrom(rootChildren.get(2).getChildren().get(0)));

        if(!rootChildren.get(3).getName().equals("EVENTS"))
            throw new Exception(root.getName()+" 2 must be : EVENTS");


        List<Element> eventsChildren = rootChildren.get(3).getChildren();
        ArrayList<CEvent> events = new ArrayList<CEvent>();
        for (int i = 0; i < eventsChildren.size(); i++)
        {
            events.add(CXMLtoMachine.getEventFrom(eventsChildren.get(i)));
        }
        ret.setEvents(events);

        return ret;
    }
    public static ArrayList<CVariable> getVariablesFrom(Element root) throws Exception
    {
        ArrayList<CVariable> ret = new ArrayList<CVariable>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getVariableFrom(rootChildren.get(i)));
        }
        return ret;
    }
    public static CEvent getEventFrom(Element root) throws Exception
    {
        if(root.getName().equals("CNonGuardedEvent"))
        {
            return CXMLtoMachine.getNonGuardedEventFrom(root);
        }
        else if(root.getName().equals("CGuardedEvent"))
        {
            return CXMLtoMachine.getGuardedEventFrom(root);
        }
        else if(root.getName().equals("CAnyEvent"))
        {
            return CXMLtoMachine.getAnyEventFrom(root);
        }
        else
        {
            throw new Exception("CEvent excepted : "+root.getName());
        }
    }
    public static CAnyEvent getAnyEventFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CAnyEvent"))
            throw new Exception("CAnyEvent excepted : "+root.getName());

        return new CAnyEvent(root.getAttribute("name").getValue(),CXMLtoMachine.getBooleanExpressionFrom(root.getChildren().get(1)),CXMLtoMachine.getSubstitutionFrom(root.getChildren().get(2)),CXMLtoMachine.getVariablesFrom(root.getChildren().get(0)));
    }
    public static CNonGuardedEvent getNonGuardedEventFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CNonGuardedEvent"))
            throw new Exception("CNonGuardedEvent excepted : "+root.getName());

        return new CNonGuardedEvent(root.getAttribute("name").getValue(),CXMLtoMachine.getSubstitutionFrom(root.getChildren().get(0)));
    }
    public static CSubstitution getSubstitutionFrom(Element root) throws Exception
    {
        if(root.getName().equals("CSkip"))
        {
            return new CSkip();
        }
        else if(root.getName().equals("CAssignment"))
        {
            return CXMLtoMachine.getAssignmentFrom(root);
        }
        else if(root.getName().equals("CGuarded"))
        {
            return CXMLtoMachine.getGuardedFrom(root);
        }
        else if(root.getName().equals("CMultipleAssignment"))
        {
            return CXMLtoMachine.getMultipleAssignmentFrom(root);
        }
        else if(root.getName().equals("CNDChoice"))
        {
            return CXMLtoMachine.getNDChoicelFrom(root);
        }
        else if(root.getName().equals("CAny"))
        {
            return CXMLtoMachine.getAnyFrom(root);
        }
        else if(root.getName().equals("CIf"))
        {
            return CXMLtoMachine.getIfFrom(root);
        }
        else if(root.getName().equals("CParallel"))
        {
            return CXMLtoMachine.getParallelFrom(root);
        }
        else
        {
            throw new Exception("CSubstitution excepted : "+root.getName());
        }
    }
    public static CIf getIfFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CIf"))
            throw new Exception("CIf excepted : "+root.getName());

        return new CIf(CXMLtoMachine.getBooleanExpressionFrom(root.getChildren().get(0)),CXMLtoMachine.getSubstitutionFrom(root.getChildren().get(1)),CXMLtoMachine.getSubstitutionFrom(root.getChildren().get(2)));
    }
    public static CAny getAnyFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CAny"))
            throw new Exception("CAny excepted : "+root.getName());

        return new CAny(CXMLtoMachine.getBooleanExpressionFrom(root.getChildren().get(1)),CXMLtoMachine.getSubstitutionFrom(root.getChildren().get(2)),CXMLtoMachine.getVariablesFrom(root.getChildren().get(0)));
    }
    public static CMultipleAssignment getMultipleAssignmentFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CMultipleAssignment"))
            throw new Exception("CMultipleAssignment excepted : "+root.getName());

        ArrayList<CAssignment> ret = new ArrayList<CAssignment>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getAssignmentFrom(rootChildren.get(i)));
        }
        return new CMultipleAssignment(ret);
    }
    public static CParallel getParallelFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CParallel"))
            throw new Exception("CParallel excepted : "+root.getName());

        ArrayList<CSubstitution> ret = new ArrayList<CSubstitution>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getSubstitutionFrom(rootChildren.get(i)));
        }
        return new CParallel(ret);
    }
    public static CNDChoice getNDChoicelFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CNDChoice"))
            throw new Exception("CNDChoice excepted : "+root.getName());

        ArrayList<CSubstitution> ret = new ArrayList<CSubstitution>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getSubstitutionFrom(rootChildren.get(i)));
        }
        return new CNDChoice(ret);
    }
    public static CAssignment getAssignmentFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CAssignment"))
            throw new Exception("CAssignment excepted : "+root.getName());

        return new CAssignment(CXMLtoMachine.getVariableFrom(root.getChildren().get(0)),CXMLtoMachine.getArithmeticExpressionFrom(root.getChildren().get(1)));
    }
    public static CGuarded getGuardedFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CGuarded"))
            throw new Exception("CGuarded excepted : "+root.getName());

        return new CGuarded(CXMLtoMachine.getBooleanExpressionFrom(root.getChildren().get(0)),CXMLtoMachine.getSubstitutionFrom(root.getChildren().get(1)));
    }
    public static CBooleanExpression getBooleanExpressionFrom(Element root) throws Exception
    {
        if(root.getName().equals("CAnd"))
        {
            return CXMLtoMachine.getAndFrom(root);
        }
        else if(root.getName().equals("COr"))
        {
            return CXMLtoMachine.getOrFrom(root);
        }
        else if(root.getName().equals("CGreater"))
        {
            return CXMLtoMachine.getGreaterFrom(root);
        }
        else if(root.getName().equals("CEquals"))
        {
            return CXMLtoMachine.getEqualsFrom(root);
        }
        else if(root.getName().equals("CInDomain"))
        {
            return CXMLtoMachine.getInDomainFrom(root);
        }
        else if(root.getName().equals("CNot"))
        {
            return CXMLtoMachine.getNotFrom(root);
        }
        else if(root.getName().equals("CExist"))
        {
            return CXMLtoMachine.getExistFrom(root);
        }
        else if(root.getName().equals("CForall"))
        {
            return CXMLtoMachine.getForallFrom(root);
        }
        else
        {
            throw new Exception("CBooleanExpression excepted : "+root.getName());
        }
    }


    public static CExist getExistFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CExist"))
            throw new Exception("CExist excepted : "+root.getName());

        return new CExist(CXMLtoMachine.getBooleanExpressionFrom(root.getChildren().get(1)),CXMLtoMachine.getVariablesFrom(root.getChildren().get(0)));
    }
    public static CForall getForallFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CForall"))
            throw new Exception("CForall excepted : "+root.getName());

        return new CForall(CXMLtoMachine.getBooleanExpressionFrom(root.getChildren().get(1)),CXMLtoMachine.getVariablesFrom(root.getChildren().get(0)));
    }
    public static CAnd getAndFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CAnd"))
            throw new Exception("CAnd excepted : "+root.getName());

        ArrayList<CBooleanExpression> ret = new ArrayList<CBooleanExpression>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getBooleanExpressionFrom(rootChildren.get(i)));
        }
        return new CAnd(ret);
    }
    public static COr getOrFrom(Element root) throws Exception
    {
        if(!root.getName().equals("COr"))
            throw new Exception("COr excepted : "+root.getName());

        ArrayList<CBooleanExpression> ret = new ArrayList<CBooleanExpression>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getBooleanExpressionFrom(rootChildren.get(i)));
        }
        return new COr(ret);
    }
    public static CGreater getGreaterFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CGreater"))
            throw new Exception("CGreater excepted : "+root.getName());

        return new CGreater(CXMLtoMachine.getArithmeticExpressionFrom(root.getChildren().get(0)),CXMLtoMachine.getArithmeticExpressionFrom(root.getChildren().get(1)));
    }
    public static CEquals getEqualsFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CEquals"))
            throw new Exception("CEquals excepted : "+root.getName());

        return new CEquals(CXMLtoMachine.getArithmeticExpressionFrom(root.getChildren().get(0)),CXMLtoMachine.getArithmeticExpressionFrom(root.getChildren().get(1)));
    }
    public static CGuardedEvent getGuardedEventFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CGuardedEvent"))
            throw new Exception("CGuardedEvent excepted : "+root.getName());

        return new CGuardedEvent(root.getAttribute("name").getValue(),CXMLtoMachine.getBooleanExpressionFrom(root.getChildren().get(0)),CXMLtoMachine.getSubstitutionFrom(root.getChildren().get(1)));
    }
    public static CInDomain getInDomainFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CInDomain"))
            throw new Exception("CInDomain excepted : "+root.getName());

        return new CInDomain(CXMLtoMachine.getVariableFrom(root.getChildren().get(0)),new Integer(root.getAttribute("type").getValue()));
    }
    public static CNot getNotFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CNot"))
            throw new Exception("CNot excepted : "+root.getName());

        return new CNot(CXMLtoMachine.getBooleanExpressionFrom(root.getChildren().get(0)));
    }


    public static CArithmeticExpression getArithmeticExpressionFrom(Element root) throws Exception
    {
        if(root.getName().equals("CMinus"))
        {
            return CXMLtoMachine.getMinusFrom(root);
        }
        else if(root.getName().equals("CPlus"))
        {
            return CXMLtoMachine.getPlusFrom(root);
        }
        else if(root.getName().equals("CMult"))
        {
            return CXMLtoMachine.getMultFrom(root);
        }
        else if(root.getName().equals("CVariable"))
        {
            return CXMLtoMachine.getVariableFrom(root);
        }
        else if(root.getName().equals("CNumber"))
        {
            return CXMLtoMachine.getNumberFrom(root);
        }
        else
        {
            throw new Exception("CArithmeticExpression excepted : "+root.getName());
        }
    }
    public static CMinus getMinusFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CMinus"))
            throw new Exception("CMinus excepted : "+root.getName());

        ArrayList<CArithmeticExpression> ret = new ArrayList<CArithmeticExpression>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getArithmeticExpressionFrom(rootChildren.get(i)));
        }
        return new CMinus(ret);
    }
    public static CPlus getPlusFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CPlus"))
            throw new Exception("CPlus excepted : "+root.getName());

        ArrayList<CArithmeticExpression> ret = new ArrayList<CArithmeticExpression>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getArithmeticExpressionFrom(rootChildren.get(i)));
        }
        return new CPlus(ret);
    }
    public static CMult getMultFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CMult"))
            throw new Exception("CMult excepted : "+root.getName());

        ArrayList<CArithmeticExpression> ret = new ArrayList<CArithmeticExpression>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getArithmeticExpressionFrom(rootChildren.get(i)));
        }
        return new CMult(ret);
    }

    public static CVariable getVariableFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CVariable"))
            throw new Exception("CVariable excepted : "+root.getName());

        return new CVariable(root.getAttribute("val").getValue());
    }
    public static CNumber getNumberFrom(Element root) throws Exception
    {
        if(!root.getName().equals("CNumber"))
            throw new Exception("CNumber excepted : "+root.getName());

        return new CNumber(new Integer(root.getAttribute("val").getValue()));
    }

    public static ArrayList<CBooleanExpression> getBooleanExpressionsFrom (Element root) throws Exception
    {
        if(!root.getName().equals("AbstractionPredicatsList"))
            throw new Exception("AbstractionPredicatsList excepted : "+root.getName());

        ArrayList<CBooleanExpression> ret = new ArrayList<CBooleanExpression>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getBooleanExpressionFrom(rootChildren.get(i)));
        }
        return ret;
    }
    public static CState getStateFrom (Element root) throws Exception
    {
        if(!root.getName().equals("CState"))
            throw new Exception("CState excepted : "+root.getName());


        return new CState(root.getAttribute("name").getValue(),CXMLtoMachine.getBooleanExpressionFrom(root.getChildren().get(0)));
    }
    public static HashSet<CState> getStatesFrom (Element root) throws Exception
    {
        if(!root.getName().equals("STATES"))
            throw new Exception("STATES excepted : "+root.getName());

        HashSet<CState> ret = new HashSet<CState>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getStateFrom(rootChildren.get(i)));
        }
        return ret;
    }
    public static CTransition getTransitionFrom (Element root, HashSet<CState> states) throws Exception
    {
        if(!root.getName().equals("CTransition"))
            throw new Exception("CTransition excepted : "+root.getName());

        CState[] cs =  states.toArray(new CState[0]);
        CState src = null, dest = null;
        for (int i = 0; i < cs.length; i++)
        {
            if(root.getAttribute("src").getValue().equals(cs[i].GetName()))
            {
                src = cs[i];
            }
            if(root.getAttribute("dest").getValue().equals(cs[i].GetName()))
            {
                dest = cs[i];
            }
        }
        if(root.getAttribute("type") != null)
        {
            if(root.getAttribute("type").getValue().equals("MAY"))
            {
                return new CModalTransition(root.getAttribute("name").getValue(),src,dest, EModalities.MAY) ;
            }
            else if(root.getAttribute("type").getValue().equals("MUSTPlus"))
            {
                return new CModalTransition(root.getAttribute("name").getValue(),src,dest, EModalities.MUSTPlus) ;
            }
            else if(root.getAttribute("type").getValue().equals("MUSTMinus"))
            {
                return new CModalTransition(root.getAttribute("name").getValue(),src,dest, EModalities.MUSTMinus) ;
            }
            else
            {
                return new CModalTransition(root.getAttribute("name").getValue(),src,dest, EModalities.MUSTPlusMinus) ;
            }
        }
        else
        {
            return new CTransition(root.getAttribute("name").getValue(),src,dest) ;
        }
    }
    public static HashSet<CTransition> getTransitionsFrom (Element root, HashSet<CState> states) throws Exception
    {
        if(!root.getName().equals("TRANSITIONS"))
            throw new Exception("TRANSITIONS excepted : "+root.getName());

        HashSet<CTransition> ret = new HashSet<CTransition>();
        List<Element> rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.size(); i++)
        {
            ret.add(CXMLtoMachine.getTransitionFrom(rootChildren.get(i), states));
        }
        return ret;
    }
}
