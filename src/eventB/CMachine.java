package eventB;

import abstraction.CAbstraction;
import booleanExpression.CBooleanExpression;
import eventB.eventBSubstitutions.CSubstitution;
import eventB.eventBVisitor.CToXMLVisitor;
import eventB.eventBevents.CEvent;
import generalExpression.CVariable;
import org.jdom2.Attribute;
import org.jdom2.Element;
import ui.CMutableTreeNode;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

/**
 * User: LoW
 * Date: 30/12/12
 * Time: 19:02
 */
public class CMachine extends CMutableTreeNode
{
    String m_Name;
    ArrayList<CVariable> m_Variables;
    ArrayList<CEvent> m_Events;
    CSubstitution m_Init;
    CBooleanExpression m_Invariant;
    DefaultMutableTreeNode m_VariablesNode,m_InitNode,m_EventsNode,m_InvariantNode;

    public CMachine(String Name)
    {
        super();
        this.m_Name = Name;
        this.m_Variables = new ArrayList<CVariable>();
        this.m_Events = new ArrayList<CEvent>();

        this.m_VariablesNode = new CMutableTreeNode("VARIABLES");
        this.add(this.m_VariablesNode);
        this.m_InvariantNode = new CMutableTreeNode("INVARIANT");
        this.add(this.m_InvariantNode);
        this.m_InitNode = new CMutableTreeNode("INITIALISATION");
        this.add(this.m_InitNode);
        this.m_EventsNode = new CMutableTreeNode("EVENTS");
        this.add(this.m_EventsNode);

    }
    public void setVariables(CVariable ... Variables)
    {
        this.m_VariablesNode.removeAllChildren();
        this.m_Variables.clear();
        for(CVariable thisVariable : Variables)
        {
            this.m_Variables.add(thisVariable);
            this.m_VariablesNode.add(thisVariable);
        }
    }
    public void setVariables(ArrayList<CVariable> Variables)
    {
        this.m_VariablesNode.removeAllChildren();
        this.m_Variables = Variables;
        for (int i = 0; i < this.m_Variables.size(); i++)
        {
            this.m_VariablesNode.add(this.m_Variables.get(i));
        }
    }
    public void setInit(CSubstitution init)
    {
        this.m_InitNode.removeAllChildren();
        this.m_Init = init;
        this.m_InitNode.add(this.m_Init);
    }
    public void setInvariant(CBooleanExpression Invariant)
    {
        this.m_InvariantNode.removeAllChildren();
        this.m_Invariant = Invariant;
        this.m_InvariantNode.add(this.m_Invariant);
    }
    public void setEvents(CEvent ... Events)
    {
        this.m_EventsNode.removeAllChildren();
        this.m_Events.clear();
        for(CEvent thisEvent : Events)
        {
            this.m_Events.add(thisEvent);
            this.m_EventsNode.add(thisEvent);
        }
    }
    public void setEvents(ArrayList<CEvent> Events)
    {
        this.m_EventsNode.removeAllChildren();
        this.m_Events = Events;
        for (int i = 0; i < this.m_Events.size(); i++)
        {
            this.m_EventsNode.add(this.m_Events.get(i));
        }
    }
    public Element toXMLElement()
    {
        CToXMLVisitor cCToXMLVisitor = new CToXMLVisitor();

        Element cMachine = new Element("MACHINE");
        cMachine.setAttribute(new Attribute("name",this.m_Name));

        Element Variables = new Element("VARIABLES");
        for (int i = 0; i < this.m_Variables.size() ; i++)
        {
            Variables.addContent((Element) cCToXMLVisitor.visit(this.m_Variables.get(i),null));
        }
        Element Invariant = new Element("INVARIANT");
        Invariant.addContent((Element) cCToXMLVisitor.visit(this.m_Invariant,null));
        Element Init = new Element("INITIALISATION");
        Init.addContent((Element) cCToXMLVisitor.visit(this.m_Init,null));
        Element Events = new Element("EVENTS");
        for (int i = 0; i < this.m_Events.size() ; i++)
        {
            Events.addContent((Element) cCToXMLVisitor.visit(this.m_Events.get(i),null));
        }

        cMachine.addContent(Variables);
        cMachine.addContent(Invariant);
        cMachine.addContent(Init);
        cMachine.addContent(Events);

        return  cMachine;
    }
    public String toOutputString()
    {
        String out = "MACHINE \n\t"+this.m_Name+"\n";
        out += "VARIABLES \n\t";
        for (int i = 0; i < this.m_Variables.size() ; i++)
        {
            if(i !=0)
                out+= ", ";
            out += this.m_Variables.get(i).toString();
        }
        out += "\n";
        out += "INVARIANT \n\t"+this.m_Invariant.toString()+"\n";
        out += "INITIALISATION \n\t"+this.m_Init.toString()+"\n";
        out += "EVENTS \n\t";
        for (int i = 0; i < this.m_Events.size() ; i++)
        {
            if(i !=0)
                out+= "\n\t";
            out += this.m_Events.get(i).toString();
        }
        return out;
    }
    public CSubstitution GetInit()
    {
        return this.m_Init;
    }
    public ArrayList<CVariable> GetVariables()
    {
        return this.m_Variables;
    }
    public ArrayList<CEvent> GetEvents()
    {
        return this.m_Events;
    }
    public CBooleanExpression GetInvariant()
    {
        return this.m_Invariant;
    }
    public String toString()
    {
        return this.m_Name;
    }
}
