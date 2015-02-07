package abstraction.sts;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: LoW
 * Date: 20/02/13
 * Time: 15:09
 */
public class CModalTransition extends CTransition
{
    EModalities m_Mod;
    ArrayList<CConcreteTransition> m_ConcreteTransition;
    public CModalTransition(String Name, CState Source, CState Target, EModalities Mod)
    {
        super(Name,Source,Target);
        this.m_Mod = Mod;
        this.m_ConcreteTransition = new ArrayList<CConcreteTransition>();
    }
    public void addConcreteTransition(CConcreteTransition cTrans)
    {
        this.m_ConcreteTransition.add(cTrans);
    }
    public EModalities GetMod()
    {
        return this.m_Mod;
    }
    public String toString()
    {
        String ret = new String(this.m_Name);
        if(this.m_Mod == EModalities.MUSTPlusMinus)
        {
            ret += "(+/-)";
        }
        else if(this.m_Mod == EModalities.MUSTPlus)
        {
            ret += "(+)";
        }
        else if(this.m_Mod == EModalities.MUSTMinus)
        {
            ret += "(-)";
        }

        return ret;
    }
}
