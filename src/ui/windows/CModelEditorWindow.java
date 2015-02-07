package ui.windows;

import abstraction.CPredicateTriModalsAbstraction;
import booleanExpression.CBooleanExpression;
import eventB.CMachine;
import materials.CXMLtoMachine;
import org.jdom2.Document;
import ui.CConsole;
import ui.CMainFrame;
import ui.CModelEditor;
import utils.CXMLutils;
import utils.ExampleFileFilter;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.ActionEvent;

/**
 * User: LoW
 * Date: 06/02/13
 * Time: 09:36
 */
public class CModelEditorWindow extends CWindow
{
    CMainFrame m_MainFrame;
    CModelEditor m_ModelEditor;
    CMachine m_Machine;
    JMenuItem m_SaveMenuItem;
    JMenuItem m_NewAbsItem;
    public CModelEditorWindow(CMachine cMachine,int x, int y, CMainFrame cMainFrame)
    {
        super(cMachine.toString()+" : Model", x, y, 500, 300,true);

        this.m_MainFrame = cMainFrame;

        this.m_Machine = cMachine;
        this.m_ModelEditor = new CModelEditor(this.m_Machine);

        JMenuBar cMenuBar = new JMenuBar();
        JMenu cMenu = new JMenu("File");
        this.m_SaveMenuItem = new JMenuItem("save as...",
                new ImageIcon("img/save.png"));

        this.m_SaveMenuItem.addActionListener(this);

        this.m_NewAbsItem = new JMenuItem("Abstraction from...",
                new ImageIcon("img/abs.png"));

        this.m_NewAbsItem.addActionListener(this);

        cMenu.add(this.m_SaveMenuItem);
        cMenu.add(this.m_NewAbsItem);
        cMenuBar.add(cMenu);

        this.setJMenuBar(cMenuBar);

        this.setFrameIcon(new ImageIcon("img/editor.png"));

        this.add(new JScrollPane(this.m_ModelEditor));

        this.moveToFront();
    }
    public void actionPerformed(ActionEvent e)
    {
         if(e.getSource() == this.m_SaveMenuItem)
         {
             JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
             chooser.setDialogType(JFileChooser.SAVE_DIALOG);
             chooser.setDialogTitle("Save as ...");

             ExampleFileFilter filter = new ExampleFileFilter();
             filter.addExtension("ebm");
             filter.setDescription("Event-B Model");
             chooser.setFileFilter(filter);

             int returnVal = chooser.showOpenDialog(this);
             if(returnVal == JFileChooser.APPROVE_OPTION)
             {
                 CXMLutils.SaveTo(new Document(this.m_Machine.toXMLElement()), chooser.getSelectedFile().getAbsolutePath());
             }
         }
        if(e.getSource() == this.m_NewAbsItem)
        {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            ExampleFileFilter filter = new ExampleFileFilter();
            filter.addExtension("ap");
            filter.setDescription("Abstraction Predicates");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    //this.m_MainFrame.loadAbstraction(new CPredicateTriModalsAbstraction(this.m_Machine, CXMLtoMachine.getBooleanExpressionsFrom(CXMLutils.loadFrom(chooser.getSelectedFile().getAbsolutePath()).getRootElement()).toArray(new CBooleanExpression[0])));
                    CPredicateTriModalsAbstractionTh cCPredicateTriModalsAbstractionTh = new CPredicateTriModalsAbstractionTh(this,this.m_Machine, CXMLtoMachine.getBooleanExpressionsFrom(CXMLutils.loadFrom(chooser.getSelectedFile().getAbsolutePath()).getRootElement()).toArray(new CBooleanExpression[0]));
                    cCPredicateTriModalsAbstractionTh.start();
                }
                catch(Exception cException)
                {
                    this.m_MainFrame.GetConsoleWindow().GetConsole().addError(cException.getMessage());
                }
            }
        }
    }
    public CMainFrame GetMainFrame()
    {
        return this.m_MainFrame;
    }
    public void internalFrameClosed(InternalFrameEvent e)
    {
        this.m_MainFrame.GetMachines().remove(this.m_Machine);
    }
}
class CPredicateTriModalsAbstractionTh extends Thread
{
    CModelEditorWindow m_CModelEditorWindow;
    CMachine m_Machine;
    CBooleanExpression[] m_Predicates;
    public CPredicateTriModalsAbstractionTh(CModelEditorWindow cCModelEditorWindow, CMachine Machine, CBooleanExpression... Predicates)
    {
        this.m_CModelEditorWindow = cCModelEditorWindow;
        this.m_Machine = Machine;
        this.m_Predicates = Predicates;
    }
    public void run()
    {
        CPredicateTriModalsAbstraction cPredicateTriModalsAbstraction = new CPredicateTriModalsAbstraction(this.m_CModelEditorWindow.GetMainFrame(), this.m_Machine,this.m_Predicates);
        this.m_CModelEditorWindow.GetMainFrame().loadAbstraction(cPredicateTriModalsAbstraction);
    }
}
