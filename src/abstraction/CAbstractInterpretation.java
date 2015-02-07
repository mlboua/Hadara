package abstraction;

import abstraction.sts.*;
import booleanExpression.*;
import booleanExpression.booleanVisitor.CToPrimeVars;
import eventB.CMachine;
import eventB.eventBSubstitutions.CSubstitution;
import eventB.eventBVisitor.CToXMLVisitor;
import generalExpression.CSet;
import generalExpression.CVariable;
import org.jdom2.Attribute;
import org.jdom2.Element;
import smtSolvers.CAnswer;
import smtSolvers.CSolver;
import smtSolvers.CZ3;
import smtSolvers.EAnswer;
import ui.CMainFrame;
import ui.windows.CProgressWindow;

import java.util.*;

/**
 * User: LoW
 * Date: 06/03/13
 * Time: 13:24
 */
public class CAbstractInterpretation extends CAbstraction
{
    CProgressWindow m_ProgressWindow;

    CSolver m_Solver;
    CToPrimeVars m_ToPrimeVars;
    CMachine m_Machine;
    CAbstraction m_RelatedAbs;
    CMainFrame m_MainFrame;

    CSet<CState> m_States;

    int m_Depth;

    CSet<CTransition> m_Transitions;
    CState m_absInit;

    public CAbstractInterpretation(CMainFrame MainFrame, CMachine Machine, CAbstraction Abs, Set<CState> states,Set<CTransition> trans)
    {
        this.m_MainFrame = MainFrame;
        this.m_Machine = Machine;
        this.m_RelatedAbs = Abs;
        this.m_States = new CSet<CState>(states);
        this.m_Transitions = new CSet<CTransition>(trans);
    }

    public CAbstractInterpretation(CMainFrame MainFrame, CMachine Machine, CAbstraction Abs, int depth)
    {
        this.m_MainFrame = MainFrame;
        this.m_Solver = new CZ3("C:/Users/LoW/Desktop/tools/z3/Debug/z3.exe");
        this.m_ToPrimeVars = new CToPrimeVars();

        this.m_States = new CSet<CState>(new HashSet<CState>());
        this.m_Transitions = new CSet<CTransition>(new HashSet<CTransition>());
        this.m_Machine = Machine;
        this.m_RelatedAbs = Abs;
        this.m_Depth = depth;

        long startTime   = System.currentTimeMillis();
        this.calculStatesTransitions();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("CAbstractInterpretation was done in : "+totalTime);
        //System.out.println(cTestGeneration.toString());
    }
    protected void calculStatesTransitions()
    {
        this.m_ProgressWindow = new CProgressWindow(this.toString(),2+(int)Math.pow((double)this.m_Machine.GetEvents().size()+this.m_RelatedAbs.getStates().m_Set.size(),this.m_Depth));
        this.m_MainFrame.add(this.m_ProgressWindow);

        ArrayList<String> path = new ArrayList<String>();
        CConstructedState init = new CConstructedState("init",this.getSP(this.m_Machine.GetInit(),this.m_Machine.GetInvariant()),path);
        if(this.m_Solver.solve(this.m_Machine.GetVariables(),init.getExpr(),false).GetAnswer() == EAnswer.SAT)
        {
            this.m_States.m_Set.add(init);
            if(this.m_Depth > 0)
             this.addStatesHelper(this.m_Depth-1,init.getExpr(), path);
        }
        else
        {
            System.out.println("no reachable state");
            return;
        }

        ArrayList<CBooleanExpression> arTotal = new ArrayList<CBooleanExpression>();
        for (CState cState : this.m_States.m_Set)
        {
            arTotal.add(cState.getExpr());
        }
        CBooleanExpression BtotalState = new COr(arTotal);
        this.m_absInit = new CState("absInit",BtotalState);

        for (int e = 0; e < this.m_Machine.GetEvents().size(); e++)
        {
            HashSet<CVariable> cPrsSet = new HashSet<CVariable>();
            for (int v = 0; v < this.m_Machine.GetVariables().size(); v++)
            {
                cPrsSet.add(this.m_Machine.GetVariables().get(v));
            }
            CBooleanExpression Prd = this.m_Machine.GetEvents().get(e).getPrd(new CSet<CVariable>(cPrsSet));

            CState[] arStates = this.m_RelatedAbs.getStates().m_Set.toArray(new CState[0]);
            for (int j = 0; j < arStates.length; j++)
            {
                /*if(this.isInclude(arStates[j],this.m_absInit))
                {
                    this.m_Transitions.m_Set.add(new CModalTransition("self",this.m_absInit,arStates[j], EModalities.MUSTMinus));
                }                */
                CAnswer isMay = this.May(this.m_absInit, arStates[j], Prd);
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
                    CModalTransition cCModalTransition = null;
                    boolean bMustPlus = false;
                    if(this.isMustPlus(this.m_absInit, arStates[j], Prd))
                    {
                        bMustPlus = true;
                    }
                    boolean bMustMinus = false;
                    if(this.isMustMinus(this.m_absInit, arStates[j], Prd))
                    {
                        bMustMinus = true;
                    }
                    if(bMustPlus && bMustMinus)
                    {
                        cCModalTransition = new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),this.m_absInit,arStates[j], EModalities.MUSTPlusMinus);
                    }
                    else if(bMustPlus)
                    {
                        cCModalTransition = new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),this.m_absInit,arStates[j], EModalities.MUSTPlus);
                    }
                    else if(bMustMinus)
                    {
                        cCModalTransition = new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),this.m_absInit,arStates[j], EModalities.MUSTMinus);
                    }
                    else
                    {
                        cCModalTransition = new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),this.m_absInit,arStates[j], EModalities.MAY);
                    }
                    cCModalTransition.addConcreteTransition(new CConcreteTransition(SourceConcreteState,TargetConcreteState,Params));
                    this.m_Transitions.m_Set.add(cCModalTransition);
                }
                this.m_ProgressWindow.setProgress(this.m_ProgressWindow.getProgress()+1);
            }
        }
        this.m_ProgressWindow.dispose();
    }
    void addStatesHelper(int n, CBooleanExpression lastExpr, ArrayList<String> path)
    {
        for (int e = 0; e < this.m_Machine.GetEvents().size(); e++)
        {
            CBooleanExpression cTest = this.getSP(this.m_Machine.GetEvents().get(e),lastExpr);
            if(this.m_Solver.solve(this.m_Machine.GetVariables(),cTest,false).GetAnswer() == EAnswer.SAT)
            {
                ArrayList<String> newpath = ( ArrayList<String>)path.clone();
                newpath.add(this.m_Machine.GetEvents().get(e).GetName());
                this.m_States.m_Set.add(new CConstructedState(this.m_Machine.GetEvents().get(e).GetName(),cTest,newpath));
                if(n > 0)
                {
                    this.addStatesHelper(n - 1, cTest, newpath);
                }
                this.m_ProgressWindow.setProgress(this.m_ProgressWindow.getProgress()+1);
            }
            else
            {
                this.m_ProgressWindow.setProgress(this.m_ProgressWindow.getProgress()+(int)Math.pow(this.m_Machine.GetEvents().size(),n));
            }

        }
    }
    CBooleanExpression getSP(CSubstitution cSubstitution, CBooleanExpression cBooleanExpression)
    {
        ArrayList<CBooleanExpression> xxp = new ArrayList<CBooleanExpression>();
        for (int i = 0; i < this.m_Machine.GetVariables().size(); i++)
        {
            xxp.add(new CEquals(new CVariable(this.m_Machine.GetVariables().get(i).toString()),new CVariable(this.m_Machine.GetVariables().get(i).toString()+"_ante")));
        }
        CBooleanExpression tmp = new CNot(cSubstitution.getWP(new CNot(new CAnd(xxp))));
        CToPrimeVars cToAnte = new CToPrimeVars("_ante");

        CBooleanExpression Q = (CBooleanExpression)cBooleanExpression.AcceptVisitor(cToAnte,null);
        ArrayList<CVariable> cAnteVars = new ArrayList<CVariable>();
        for (int i = 0; i < this.m_Machine.GetVariables().size(); i++)
        {
            cAnteVars.add(new CVariable(this.m_Machine.GetVariables().get(i).toString()+"_ante"));
        }

        return new CExist(new CAnd(Q,tmp),cAnteVars);
    }
    boolean isInclude(CState test, CState in)
    {
        ArrayList<CVariable> arSet = new ArrayList<CVariable>();
        for (int k = 0; k < this.m_Machine.GetVariables().size(); k++)
        {
            arSet.add(new CVariable(this.m_Machine.GetVariables().get(k).toString())) ;
        }

        CBooleanExpression tmp = new CAnd(test.getExpr(),new CNot(in.getExpr()));
        if(this.m_Solver.solve(arSet,tmp,false).GetAnswer() == EAnswer.UNSAT)
        {
            return true;
        }
        else
        {
            return false;
        }
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
        CBooleanExpression cExistmustplusTest = new CExist(cAndmustplusTest,VariablesPrime);
        CBooleanExpression cNotmustplusTest = new CNot(cExistmustplusTest);
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
    public CAbstraction GetRelatedAbs()
    {
        return m_RelatedAbs;
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
        return this.m_Machine.toString()+" : CAbstractInterpretation("+this.m_Depth+")";
    }
}