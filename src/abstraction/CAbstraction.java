package abstraction;

import abstraction.sts.CConstructedState;
import abstraction.sts.CState;
import abstraction.sts.CTransition;
import eventB.CMachine;
import generalExpression.CSet;
import org.jdom2.Element;

/**
 * User: LoW
 * Date: 10/01/13
 * Time: 00:38
 */
public abstract class CAbstraction
{
    public abstract CMachine getMachine();
    public abstract CSet<CState> getStates();
    public abstract CSet<CTransition> getTransitions();
    public abstract Element toXML();
    public abstract String toDOT();
}
