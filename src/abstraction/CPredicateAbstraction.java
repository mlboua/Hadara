package abstraction;

import abstraction.sts.CConstructedState;
import abstraction.sts.CState;
import abstraction.sts.CTransition;
import booleanExpression.CAnd;
import booleanExpression.CBooleanExpression;
import booleanExpression.CNot;
import booleanExpression.booleanVisitor.CToPrimeVars;
import eventB.CMachine;
import eventB.eventBVisitor.CToXMLVisitor;
import generalExpression.CSet;
import generalExpression.CVariable;
import org.jdom2.Attribute;
import org.jdom2.Element;
import smtSolvers.CSolver;
import smtSolvers.CZ3;
import smtSolvers.EAnswer;
import ui.CMainFrame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * User: LoW
 * Date: 10/01/13
 * Time: 00:40
 */
public class CPredicateAbstraction extends CAbstraction
{
    CSolver m_Solver;
    CToPrimeVars m_ToPrimeVars;
    CMainFrame m_MainFrame;
    CMachine m_Machine;

    CSet<CBooleanExpression> m_Predicates;
    CSet<CState> m_States;

    CSet<CTransition> m_Transitions;

    public CPredicateAbstraction(CMachine Machine, Set<CState> states,Set<CTransition> trans)
    {
        this.m_Machine = Machine;
        this.m_States = new CSet<CState>(states);
        this.m_Transitions = new CSet<CTransition>(trans);
    }

    private int iIndex;
    public CPredicateAbstraction(CMainFrame MainFrame, CMachine Machine,CBooleanExpression ... Predicates)
    {
        this.m_MainFrame =  MainFrame;
        this.m_Solver = new CZ3("C:/Users/LoW/Desktop/tools/z3/Debug/z3.exe");
        this.m_ToPrimeVars = new CToPrimeVars();
        this.m_Predicates = new CSet<CBooleanExpression>(new HashSet<CBooleanExpression>());
        this.m_States = new CSet<CState>(new HashSet<CState>());

        this.m_Transitions = new CSet<CTransition>(new HashSet<CTransition>());
        this.m_Machine = Machine;

        for(CBooleanExpression bExp : Predicates)
        {
            this.m_Predicates.m_Set.add(bExp);
        }
        long startTime   = System.currentTimeMillis();
        this.calculStatesTransitions();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Abstraction was done in : "+totalTime);
        this.m_Solver.CleanUp();
    }
    protected void calculStatesTransitions()
    {
        this.calculStates();
        this.calculTransitions();
    }
    protected void calculStates()
    {
        CBooleanExpression[] arPredicates = this.m_Predicates.m_Set.toArray(new CBooleanExpression[0]);
        this.iIndex = 1;
        if(arPredicates.length == 1)
        {
            if(this.m_Solver.solve(this.m_Machine.GetVariables(),new CAnd(this.m_Machine.GetInvariant(),arPredicates[0]),false).GetAnswer() == EAnswer.SAT)
            {
                this.m_States.m_Set.add(new CState("s" + this.iIndex, arPredicates[0]));
                this.iIndex++;
            }
            if(this.m_Solver.solve(this.m_Machine.GetVariables(),new CAnd(this.m_Machine.GetInvariant(),new CNot(arPredicates[0])),false).GetAnswer() == EAnswer.SAT)
            {
                this.m_States.m_Set.add(new CState("s" + this.iIndex, new CNot(arPredicates[0])));
                this.iIndex++;
            }
        }
        else
        {
            this.addStates(arPredicates[0],1,arPredicates);
            this.addStates(new CNot(arPredicates[0]),1,arPredicates);
        }
    }
    private void addStates(CBooleanExpression bExpr, int n, CBooleanExpression[] arPredicates)
    {
        if(n == arPredicates.length)
        {
            if(this.m_Solver.solve(this.m_Machine.GetVariables(),new CAnd(this.m_Machine.GetInvariant(),bExpr),false).GetAnswer() == EAnswer.SAT)
            {
                this.m_States.m_Set.add(new CState("s" + iIndex, bExpr));
                this.iIndex++;
            }
        }
        else
        {
            this.addStates(new CAnd(bExpr,arPredicates[n]),n+1,arPredicates);
            this.addStates(new CAnd(bExpr,new CNot(arPredicates[n])),n+1,arPredicates);
        }
    }
    protected void calculTransitions()
    {
        System.out.println("bad");
        for (int e = 0; e < this.m_Machine.GetEvents().size(); e++)
        {
            CState[] arStates = this.m_States.m_Set.toArray(new CState[0]);
            for (int i = 0; i < arStates.length; i++)
            {
                for (int j = 0; j < arStates.length; j++)
                {
                    ArrayList<CVariable> arSet = new ArrayList<CVariable>();
                    for (int k = 0; k < this.m_Machine.GetVariables().size(); k++)
                    {
                        arSet.add(new CVariable(this.m_Machine.GetVariables().get(k).toString())) ;
                        arSet.add(new CVariable(this.m_Machine.GetVariables().get(k).toString()+"_prime")) ;
                    }
                    HashSet<CVariable> cPrsSet = new HashSet<CVariable>();
                    for (int v = 0; v < this.m_Machine.GetVariables().size(); v++)
                    {
                        cPrsSet.add(this.m_Machine.GetVariables().get(v));
                    }
                    CBooleanExpression prdTest = this.m_Machine.GetEvents().get(e).getPrd(new CSet<CVariable>(cPrsSet));
                    CBooleanExpression mayTest = new CAnd(
                                                                    prdTest,arStates[i].getExpr(),
                                                                    (CBooleanExpression)this.m_ToPrimeVars.visit(arStates[j].getExpr(),null),
                                                                    this.m_Machine.GetInvariant(),
                                                                    (CBooleanExpression)this.m_ToPrimeVars.visit(this.m_Machine.GetInvariant(),null)
                                                            );
                    if(this.m_Solver.solve(arSet,mayTest,true).GetAnswer() == EAnswer.SAT)
                    {
                        this.m_Transitions.m_Set.add(new CTransition(this.m_Machine.GetEvents().get(e).GetName(),arStates[i],arStates[j]));
                    }
                }
            }
        }
    }
    public CSet<CState> getStates()
    {
        return m_States;
    }
    public CSet<CTransition> getTransitions()
    {
        return m_Transitions;
    }
    public CMachine getMachine()
    {
        return this.m_Machine;
    }
    public Element toXML()
    {
        CToXMLVisitor cCToXMLVisitor = new CToXMLVisitor();

        Element root = new Element("CPredicateAbstraction");
        root.setAttribute(new Attribute("model",this.m_Machine.toString()));

        Element StatesRoot = new Element("STATES");
        CState[] arStates = this.m_States.m_Set.toArray(new CState[0]);
        for (int i = 0; i < arStates.length; i++)
        {
            Element newState = new Element("CTransition");
            newState.setAttribute(new Attribute("name",arStates[i].GetName()));
            newState.addContent((Element)cCToXMLVisitor.visit(arStates[i].getExpr(),null));

            StatesRoot.addContent(newState);
        }
        Element TransRoot = new Element("TRANSITIONS");
        CTransition[] arTransitiond = this.m_Transitions.m_Set.toArray(new CTransition[0]);
        for (int i = 0; i < arTransitiond.length; i++)
        {
            Element newState = new Element("CTransition");
            newState.setAttribute(new Attribute("src",arTransitiond[i].GetSource().GetName()));
            newState.setAttribute(new Attribute("dest",arTransitiond[i].GetTarget().GetName()));
            newState.setAttribute(new Attribute("name",arTransitiond[i].GetName()));

            TransRoot.addContent(newState);
        }

        root.addContent(StatesRoot);
        root.addContent(TransRoot);

        return root;
    }
    public String toDOT()
    {
        String ret = new String("digraph G { \n");
        for (CTransition cTrans : this.m_Transitions.m_Set)
        {
              ret += cTrans.GetSource().GetName()+" -> "+cTrans.GetTarget().GetName()+"[ label = \""+cTrans.toString()+"\" ];\n";
        }
        ret += "}";
        return ret;
    }
    public String toString()
    {
        return this.m_Machine.toString()+" : CPredicateAbstraction";
    }
}
