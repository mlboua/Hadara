package abstraction;

import abstraction.sts.CModalTransition;
import abstraction.sts.CState;
import abstraction.sts.CTransition;
import abstraction.sts.EModalities;
import booleanExpression.*;
import eventB.CMachine;
import generalExpression.CNumber;
import generalExpression.CSet;
import generalExpression.CVariable;
import smtSolvers.EAnswer;
import ui.CMainFrame;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * User: LoW
 * Date: 07/02/13
 * Time: 23:10
 */
public class CPred3ModExtended extends CPredicateAbstraction
{
    public CPred3ModExtended(CMainFrame MainFrame, CMachine Machine,CBooleanExpression... Predicates)
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
        double totalTransPossible = this.m_States.m_Set.size()*this.m_Machine.GetEvents().size()*this.m_States.m_Set.size();
        double evaluatedTrans = 0;
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
                    if(this.isMay(arStates[i],arStates[j],Prd))
                    {
                        boolean bMustPlus = false;
                        if(this.isMustPlus(arStates[i], arStates[j], Prd))
                        {
                            bMustPlus = true;
                        }
                        boolean bMustMinus = false;
                        if(this.isMustMinus(arStates[i], arStates[j], Prd))
                        {
                            bMustMinus = true;
                        }
                        if(bMustPlus && bMustMinus)
                        {
                            this.m_Transitions.m_Set.add(new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),arStates[i],arStates[j], EModalities.MUSTPlusMinus));
                        }
                        else if(bMustPlus)
                        {
                            this.m_Transitions.m_Set.add(new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),arStates[i],arStates[j], EModalities.MUSTPlus));
                        }
                        else if(bMustMinus)
                        {
                            this.m_Transitions.m_Set.add(new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),arStates[i],arStates[j], EModalities.MUSTMinus));
                        }
                        else
                        {
                            this.m_Transitions.m_Set.add(new CModalTransition(this.m_Machine.GetEvents().get(e).GetName(),arStates[i],arStates[j], EModalities.MAY));
                        }
                    }
                    evaluatedTrans++;
                    System.out.println("Eval : "+(evaluatedTrans/totalTransPossible)*100);
                }
            }
        }

        /*CVariable var_Mago = new CVariable("Mago");
        CVariable var_Balance = new CVariable("Balance");
        CVariable var_CofeeLeft = new CVariable("CofeeLeft");
        CVariable var_Statut = new CVariable("Statut");
        CBooleanExpression Pinit  = new CAnd(new CEquals(var_Statut,new CNumber(0)),new CEquals(var_Mago,new CNumber(0)),new CEquals(var_Balance,new CNumber(0)),new CEquals(var_CofeeLeft,new CNumber(100)));

        CState cInitState = new CState("init",Pinit);
        CState[] arStates = this.m_States.m_Set.toArray(new CState[0]);
        for (int e = 0; e < this.m_Machine.GetEvents().size(); e++)
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
                        prdTest,cInitState.getExpr(),
                        (CBooleanExpression)this.m_ToPrimeVars.visit(arStates[j].getExpr(),null),
                        this.m_Machine.GetInvariant(),
                        (CBooleanExpression)this.m_ToPrimeVars.visit(this.m_Machine.GetInvariant(),null)
                );
                if(this.m_Solver.solve(arSet,mayTest) == EAnswer.SAT)
                {
                    //System.out.println(this.m_Machine.GetEvents().get(e).GetName()+" : "+arStates[i].GetName()+" -> "+arStates[j].GetName()+" <-> "+mayTest);
                    //
                    arSet.clear();
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
                            prdTest,
                            (CBooleanExpression)this.m_ToPrimeVars.visit(arStates[j].getExpr(),null),
                            (CBooleanExpression)this.m_ToPrimeVars.visit(this.m_Machine.GetInvariant(),null)
                    );
                    CBooleanExpression cExistmustplusTest = new CExist(cAndmustplusTest,VariablesPrime);                              CBooleanExpression cNotmustplusTest = new CNot(cExistmustplusTest);
                    CBooleanExpression mustplusTest = new CAnd(cInitState.getExpr(),cNotmustplusTest,this.m_Machine.GetInvariant());
                    boolean bMustPlus = false;
                    if(this.m_Solver.solve(arSet,mustplusTest) == EAnswer.UNSAT)
                    {
                        bMustPlus = true;
                    }
                    arSet.clear();
                    for (int k = 0; k < this.m_Machine.GetVariables().size(); k++)
                    {
                        arSet.add(new CVariable(this.m_Machine.GetVariables().get(k).toString()+"_prime")) ;
                    }
                    CBooleanExpression cAndMustminus = new CAnd(prdTest,cInitState.getExpr(),this.m_Machine.GetInvariant());

                    CBooleanExpression cExistMustminus = new CExist(cAndMustminus,this.m_Machine.GetVariables());
                    CBooleanExpression cNotMustminus = new CNot(cExistMustminus);

                    CBooleanExpression mustminusTest = new CAnd((CBooleanExpression)this.m_ToPrimeVars.visit(arStates[j].getExpr(),null),cNotMustminus,(CBooleanExpression)this.m_ToPrimeVars.visit(this.m_Machine.GetInvariant(),null));
                    boolean bMustMinus = false;
                    if(this.m_Solver.solve(arSet,mustminusTest) == EAnswer.UNSAT)
                    {
                        bMustMinus = true;
                    }
                    if(bMustPlus && bMustMinus)
                    {
                        this.m_Transitions.m_Set.add(new CTransition(this.m_Machine.GetEvents().get(e).GetName()+"(Must+/Must-)",cInitState,arStates[j]));
                    }
                    else if(bMustPlus)
                    {
                        this.m_Transitions.m_Set.add(new CTransition(this.m_Machine.GetEvents().get(e).GetName()+"(Must+)",cInitState,arStates[j]));
                    }
                    else if(bMustMinus)
                    {
                        this.m_Transitions.m_Set.add(new CTransition(this.m_Machine.GetEvents().get(e).GetName()+"(Must-)",cInitState,arStates[j]));
                    }
                    else
                    {
                        this.m_Transitions.m_Set.add(new CTransition(this.m_Machine.GetEvents().get(e).GetName()+"(May)",cInitState,arStates[j]));
                    }
                    //this.m_Transitions.m_Set.add(new CTransition(this.m_Machine.GetEvents().get(e).GetName()+"(May)",arStates[i],arStates[j]));

                }
            }
        }
        this.m_States.m_Set.add(cInitState);*/
    }
    boolean isMay(CState src, CState dest, CBooleanExpression Prd)
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
        if(this.m_Solver.solve(arSet,mayTest,false).GetAnswer() == EAnswer.SAT)
        {
            return true;
        }
        else
        {
            return false;
        }
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
}
