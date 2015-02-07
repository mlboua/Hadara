package abstraction.sts;

import generalExpression.CVariable;

import java.util.HashMap;

/**
 * User: LoW
 * Date: 16/05/13
 * Time: 20:20
 */
public class CConcreteTransition
{
    HashMap<CVariable,Integer> m_SourceConcreteState;
    HashMap<CVariable,Integer> m_TargetConcreteState;
    HashMap<CVariable,Integer> m_Params;
    public CConcreteTransition(HashMap<CVariable,Integer> SourceConcreteState, HashMap<CVariable,Integer> TargetConcreteState, HashMap<CVariable,Integer> m_Params)
    {
        this.m_SourceConcreteState = SourceConcreteState;
        this.m_TargetConcreteState = TargetConcreteState;
        this.m_Params = m_Params;
    }
    public HashMap<CVariable,Integer> GetConcreteSource()
    {
        return this.m_SourceConcreteState;
    }
    public HashMap<CVariable,Integer> GetConcreteTarget()
    {
        return this.m_TargetConcreteState;
    }
    public HashMap<CVariable,Integer> GetParams()
    {
        return this.m_Params;
    }
}
