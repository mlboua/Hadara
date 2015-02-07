package smtSolvers;

import booleanExpression.CBooleanExpression;
import generalExpression.CVariable;

import java.util.ArrayList;

/**
 * User: LoW
 * Date: 07/01/13
 * Time: 14:20
 */
public abstract class CSolver
{
    public abstract CAnswer solve(ArrayList<CVariable> variables, CBooleanExpression bExp, boolean bGetModel);
    public abstract void CleanUp();
}
