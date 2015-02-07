package abstraction.sts;

import abstraction.sts.CState;

/**
 * User: LoW
 * Date: 11/01/13
 * Time: 17:51
 */
public class CTransition
{
    CState m_Source, m_Target;
    String m_Name;
    public CTransition(String Name, CState Source, CState Target)
    {
        this.m_Name = Name;
        this.m_Source = Source;
        this.m_Target = Target;
    }
    public String GetName()
    {
        return this.m_Name;
    }
    public CState GetTarget()
    {
        return this.m_Target;
    }
    public CState GetSource()
    {
        return this.m_Source;
    }
    public String toString()
    {
        return this.m_Name;
    }
    public boolean equals(Object that)
    {
        if ( this == that ) return true;

        if ( !(that instanceof CTransition) ) return false;

        CTransition typedThat = (CTransition)that;

        return this.GetName().equals(typedThat.GetName()) && this.GetSource().equals(typedThat.GetSource()) && this.GetTarget().equals(typedThat.GetTarget());
    }
}
