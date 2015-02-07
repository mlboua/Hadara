package eventB.eventBVisitor;

import eventB.eventBSubstitutions.*;
import eventB.eventBevents.CAnyEvent;
import eventB.eventBevents.CGuardedEvent;
import eventB.eventBevents.CNonGuardedEvent;

/**
 * User: LoW
 * Date: 13/02/13
 * Time: 10:50
 */
public interface IEventBVisitor
{
    public Object visit(CSubstitution node, Object data);
    public Object visit(CSkip node, Object data);
    public Object visit(CMultipleAssignment node, Object data);
    public Object visit(CNDChoice node, Object data);
    public Object visit(CGuarded node, Object data);
    public Object visit(CAssignment node, Object data);
    public Object visit(CAny node, Object data);
    public Object visit(CNonGuardedEvent node, Object data);
    public Object visit(CGuardedEvent node, Object data);
    public Object visit(CAnyEvent node, Object data);
    public Object visit(CIf node, Object data);
}
