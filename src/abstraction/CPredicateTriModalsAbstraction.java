package abstraction;

import abstraction.sts.*;
import booleanExpression.CAnd;
import booleanExpression.CBooleanExpression;
import booleanExpression.CExist;
import booleanExpression.CNot;
import eventB.CMachine;
import eventB.eventBVisitor.CToXMLVisitor;
import generalExpression.CSet;
import generalExpression.CVariable;
import org.jdom2.Attribute;
import org.jdom2.Element;
import smtSolvers.CAnswer;
import smtSolvers.EAnswer;
import ui.CMainFrame;
import ui.windows.CProgressWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * User: LoW
 * Date: 13/01/13
 * Time: 17:34
 */
public class CPredicateTriModalsAbstraction extends CPredicateAbstraction
{
    CProgressWindow m_ProgressWindow;
    public CPredicateTriModalsAbstraction(CMainFrame MainFrame,CMachine Machine,CBooleanExpression... Predicates)
    {
        super(MainFrame,Machine,Predicates);

    }
    protected void calculStatesTransitions()
    {
        this.calculStates();
        this.calculTransitions();
    }
    protected void calculTransitions()
    {
        this.m_ProgressWindow = new CProgressWindow(this.toString(),this.m_States.m_Set.size()*this.m_Machine.GetEvents().size()*this.m_States.m_Set.size());
        this.m_MainFrame.add(this.m_ProgressWindow);

        int nbMay = 0;
        int nbMustplus = 0;
        int nbMustmoins = 0;

        for (int e = 0; e < this.m_Machine.GetEvents().size(); e++)
        {
            HashSet<CVariable> cPrsSet = new HashSet<CVariable>();
            for (int v = 0; v < this.m_Machine.GetVariables().size(); v++)
            {
                cPrsSet.add(this.m_Machine.GetVariables().get(v));
            }
            CBooleanExpression Prd = this.m_Machine.GetEvents().get(e).getPrd(new CSet<CVariable>(cPrsSet));

            CState[] arStates = this.m_States.m_Set.toArray(new CState[0]);
            for (int i = 0; i < arStates.length; i++)
            {
                for (int j = 0; j < arStates.length; j++)
                {
                    CAnswer isMay = this.May(arStates[i], arStates[j], Prd);
                    if(isMay.GetAnswer() == EAnswer.SAT)
                    {
                        HashMap<CVariable,Integer> SourceConcreteState = new HashMap<CVariable,Integer>();
                        HashMap<CVariable,Integer> TargetConcreteState = new HashMap<CVariable,Integer>();
                        HashMap<CVariable,Integer> Params = new HashMap<CVariable,Integer>();
                        for (Map.Entry<String, Integer> entry : isMay.GetModel().entrySet())
                        {
                            String key = entry.getKey();
                            Integer value = entry.getValue();
                            if(key.contains("_prime"))
                            {
                                TargetConcreteState.put(new CVariable(key.substring(0,key.length()-6)),value);
                            }
                            else
                            {
                                CVariable cvar =  new CVariable(key);
                                if(this.m_Machine.GetVariables().contains(cvar))
                                {
                                    SourceConcreteState.put(cvar, value);
                                }
                                else
                                {
                                    Params.put(cvar,value);
                                }
                            }
                        }
                       // System.out.println("->"+SourceConcreteState.toString()+TargetConcreteState.toString()+Params.toString());
                        CModalTransition cCModalTransition = null;
                        nbMay++;
                        boolean bMustPlus = false;
                        if(this.isMustPlus(arStates[i], arStates[j], Prd))
                        {
                            nbMustplus++;
                            bMustPlus = true;
                        }
                        boolean bMustMinus = false;
                        if(this.isMustMinus(arStates[i], arStates[j], Prd))
                        {
                            nbMustmoins++;
                            bMustMinus = true;
                        }
                        if(bMustPlus && bMustMinus)
                        {
                            cCModalTransition = new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),arStates[i],arStates[j], EModalities.MUSTPlusMinus);
                        }
                        else if(bMustPlus)
                        {
                            cCModalTransition = new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),arStates[i],arStates[j], EModalities.MUSTPlus);
                        }
                        else if(bMustMinus)
                        {
                            cCModalTransition = new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),arStates[i],arStates[j], EModalities.MUSTMinus);
                        }
                        else
                        {
                            cCModalTransition = new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),arStates[i],arStates[j], EModalities.MAY);
                        }
                        cCModalTransition.addConcreteTransition(new CConcreteTransition(SourceConcreteState,TargetConcreteState,Params));
                        this.m_Transitions.m_Set.add(cCModalTransition);
                    }
                    this.m_ProgressWindow.setProgress(this.m_ProgressWindow.getProgress()+1);
                }
            }
        }
        System.out.println("May : "+nbMay+" / Must+ : "+nbMustplus+"/ Must- : "+nbMustmoins);
        this.m_ProgressWindow.dispose();
    }
    CAnswer May(CState src, CState dest, CBooleanExpression Prd)
    {
        ArrayList<CVariable> arSet = new ArrayList<CVariable>();
        for (int k = 0; k < this.m_Machine.GetVariables().size(); k++)
        {
            arSet.add(new CVariable(this.m_Machine.GetVariables().get(k).toString())) ;
            arSet.add(new CVariable(this.m_Machine.GetVariables().get(k).toString()+"_prime")) ;
        }
        CBooleanExpression mayTest = new CAnd(
                Prd,src.getExpr(),
                (CBooleanExpression)this.m_ToPrimeVars.visit(dest.getExpr(),null),
                this.m_Machine.GetInvariant(),
                (CBooleanExpression)this.m_ToPrimeVars.visit(this.m_Machine.GetInvariant(),null)
        );
        return this.m_Solver.solve(arSet,mayTest,true);
    }
    boolean isMustPlus(CState src, CState dest, CBooleanExpression Prd)
    {
        ArrayList<CVariable> arSet = new ArrayList<CVariable>();
        for (int k = 0; k < this.m_Machine.GetVariables().size(); k++)
        {
            arSet.add(new CVariable(this.m_Machine.GetVariables().get(k).toString())) ;
        }
        ArrayList<CVariable> VariablesPrime = new ArrayList<CVariable>();
        for (int u = 0; u < this.m_Machine.GetVariables().size(); u++)
        {
            VariablesPrime.add(new CVariable(this.m_Machine.GetVariables().get(u).toString()+"_prime"));
        }
        CBooleanExpression cAndmustplusTest = new CAnd(
                Prd,
                (CBooleanExpression)this.m_ToPrimeVars.visit(dest.getExpr(),null),
                (CBooleanExpression)this.m_ToPrimeVars.visit(this.m_Machine.GetInvariant(),null)
        );
        CBooleanExpression cExistmustplusTest = new CExist(cAndmustplusTest,VariablesPrime);                              CBooleanExpression cNotmustplusTest = new CNot(cExistmustplusTest);
        CBooleanExpression mustplusTest = new CAnd(src.getExpr(),cNotmustplusTest,this.m_Machine.GetInvariant());
        if(this.m_Solver.solve(arSet,mustplusTest,false).GetAnswer() == EAnswer.UNSAT)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    boolean isMustMinus(CState src, CState dest, CBooleanExpression Prd)
    {
        ArrayList<CVariable> arSet = new ArrayList<CVariable>();
        for (int k = 0; k < this.m_Machine.GetVariables().size(); k++)
        {
            arSet.add(new CVariable(this.m_Machine.GetVariables().get(k).toString()+"_prime")) ;
        }
        CBooleanExpression cAndMustminus = new CAnd(Prd,src.getExpr(),this.m_Machine.GetInvariant());

        CBooleanExpression cExistMustminus = new CExist(cAndMustminus,this.m_Machine.GetVariables());
        CBooleanExpression cNotMustminus = new CNot(cExistMustminus);

        CBooleanExpression mustminusTest = new CAnd((CBooleanExpression)this.m_ToPrimeVars.visit(dest.getExpr(),null),cNotMustminus,(CBooleanExpression)this.m_ToPrimeVars.visit(this.m_Machine.GetInvariant(),null));
        if(this.m_Solver.solve(arSet,mustminusTest,false).GetAnswer() == EAnswer.UNSAT)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Element toXML()
    {
        CToXMLVisitor cCToXMLVisitor = new CToXMLVisitor();

        Element root = new Element("CPredicateTriModalsAbstraction");
        root.setAttribute(new Attribute("model",this.m_Machine.toString()));

        Element StatesRoot = new Element("STATES");
        CState[] arStates = this.m_States.m_Set.toArray(new CState[0]);
        for (int i = 0; i < arStates.length; i++)
        {
            Element newState = new Element("CState");
            newState.setAttribute(new Attribute("name",arStates[i].GetName()));
            newState.addContent((Element)cCToXMLVisitor.visit(arStates[i].getExpr(),null));

            StatesRoot.addContent(newState);
        }
        Element TransRoot = new Element("TRANSITIONS");
        CModalTransition[] arTransitiond = this.m_Transitions.m_Set.toArray(new CModalTransition[0]);
        for (int i = 0; i < arTransitiond.length; i++)
        {
            Element newState = new Element("CTransition");
            newState.setAttribute(new Attribute("src",arTransitiond[i].GetSource().GetName()));
            newState.setAttribute(new Attribute("dest",arTransitiond[i].GetTarget().GetName()));
            newState.setAttribute(new Attribute("name",arTransitiond[i].GetName()));
            if(arTransitiond[i].GetMod() == EModalities.MAY)
            {
                newState.setAttribute(new Attribute("type","MAY"));
            }
            else if(arTransitiond[i].GetMod() == EModalities.MUSTPlus)
            {
                newState.setAttribute(new Attribute("type","MUSTPlus"));
            }
            else if(arTransitiond[i].GetMod() == EModalities.MUSTMinus)
            {
                newState.setAttribute(new Attribute("type","MUSTMinus"));
            }
            else if(arTransitiond[i].GetMod() == EModalities.MUSTPlusMinus)
            {
                newState.setAttribute(new Attribute("type","MUSTPlusMinus"));
            }
            TransRoot.addContent(newState);
        }

        root.addContent(StatesRoot);
        root.addContent(TransRoot);

        return root;
    }
    public String toString()
    {
        return this.m_Machine.toString()+" : CPredicateTriModalsAbstraction";
    }
}