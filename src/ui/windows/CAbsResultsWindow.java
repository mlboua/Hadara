package ui.windows;

import abstraction.CAbstractInterpretation;
import abstraction.CAbstraction;
import abstraction.CPredicateTriModalsAbstraction;
import abstraction.sts.CState;
import abstraction.sts.CTransition;
import booleanExpression.CBooleanExpression;
import eventB.CMachine;
import generalExpression.CSet;
import generalExpression.CVariable;
import materials.CXMLtoMachine;
import org.jdom2.Document;
import testGeneration.CTestGeneration;
import ui.CMainFrame;
import ui.CModelEditor;
import ui.CPredicatArray;
import utils.CXMLutils;
import utils.ExampleFileFilter;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.ActionEvent;
import java.util.HashSet;

/**
 * User: LoW
 * Date: 06/02/13
 * Time: 11:10
 */
public class CAbsResultsWindow extends CWindow
{
    JTabbedPane m_TabbedPane;
    JMenuItem m_SaveMenuItem;
    CAbstraction m_Abstraction;
    CMainFrame m_MainFrame;

    JMenuItem m_NewAbstractInter;
    JMenuItem m_NewTest;

    public CAbsResultsWindow(CAbstraction cAbstraction, int x, int y, CMainFrame cMainFrame)
    {
        super(cAbstraction.toString(), x, y, 500, 180, true);

        this.m_MainFrame = cMainFrame;

        this.m_Abstraction = cAbstraction;

        this.setFrameIcon(new ImageIcon("img/abs.png"));

        JMenuBar cMenuBar = new JMenuBar();
        JMenu cMenu = new JMenu("File");

        this.m_SaveMenuItem = new JMenuItem("save as...",
                new ImageIcon("img/save.png"));
        this.m_SaveMenuItem.addActionListener(this);
        cMenu.add(this.m_SaveMenuItem);

        if(cAbstraction instanceof CAbstractInterpretation)
        {
            this.m_NewTest = new JMenuItem("Test Generation...",
                    new ImageIcon("img/test.png"));
            this.m_NewTest.addActionListener(this);
            cMenu.add(this.m_NewTest);
        }
        else
        {
            this.m_NewAbstractInter = new JMenuItem("Abstract Interpretation...",
                    new ImageIcon("img/abs.png"));
            this.m_NewAbstractInter.addActionListener(this);
            cMenu.add(this.m_NewAbstractInter);
        }

        cMenuBar.add(cMenu);

        this.setJMenuBar(cMenuBar);

        this.m_TabbedPane = new JTabbedPane();
        this.add(new JScrollPane(this.m_TabbedPane));

        CState[] tStates = cAbstraction.getStates().m_Set.toArray(new CState[0]);
        Object[][] tableState = new Object[tStates.length][2];
        for (int i = 0; i < tStates.length; i++)
        {
            tableState[i][0] = tStates[i].GetName();
            tableState[i][1] = tStates[i].getExpr();
        }
        String[] clumsNames1 = {"Name", "Expression"};
        this.m_TabbedPane.add("Abstract States",new JScrollPane(new CPredicatArray(clumsNames1,tableState)));

        CTransition[] tTransitions = cAbstraction.getTransitions().m_Set.toArray(new CTransition[0]);
        Object[][] tableTransitions = new Object[tTransitions.length][3];
        for (int i = 0; i < tTransitions.length; i++)
        {
            tableTransitions[i][0] = tTransitions[i].GetSource().GetName();
            tableTransitions[i][1] = tTransitions[i].GetTarget().GetName();
            tableTransitions[i][2] = tTransitions[i].toString();
        }
        String[] clumsNames2 = {"Source", "Target", "Label"};
        this.m_TabbedPane.add("Abstract Transitions",new JScrollPane(new CPredicatArray(clumsNames2,tableTransitions)));
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.m_SaveMenuItem)
        {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            chooser.setDialogTitle("Save as ...");

            ExampleFileFilter filter = new ExampleFileFilter();
            filter.addExtension("abs");
            filter.setDescription("Abstraction Model");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                CXMLutils.SaveTo(new Document(this.m_Abstraction.toXML()), chooser.getSelectedFile().getAbsolutePath());
            }
        }
        if(e.getSource() == this.m_NewAbstractInter)
        {
            String s = (String)JOptionPane.showInputDialog(
                    this,
                    "What depth",
                    "Information required",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
            if ((s != null) && (s.length() > 0))
            {
                //this.m_MainFrame.loadAbstraction(new CAbstractInterpretation(this.m_Abstraction.getMachine(), this.m_Abstraction,new Integer(s)));
                CAbstractInterpretationTh cCAbstractInterpretationTh = new CAbstractInterpretationTh(this.m_MainFrame, this.m_Abstraction, new Integer(s));
                cCAbstractInterpretationTh.start();
            }
        }
        if(e.getSource() == this.m_NewTest)
        {
            String s = (String)JOptionPane.showInputDialog(
                    this,
                    "What depth",
                    "Information required",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
            if ((s != null) && (s.length() > 0))
            {
                CTestGenerationTh cCTestGenerationTh = new CTestGenerationTh(this.m_MainFrame, (CAbstractInterpretation)this.m_Abstraction, new Integer(s));
                cCTestGenerationTh.start();
            }
        }
    }
    public void internalFrameClosed(InternalFrameEvent e)
    {
        this.m_MainFrame.GetAbstractions().remove(this.m_Abstraction);
    }
}
class CAbstractInterpretationTh extends Thread
{
    CMainFrame m_MainFrame;
    CAbstraction m_Abs;
    int m_depth;
    public CAbstractInterpretationTh(CMainFrame MainFrame, CAbstraction Abs, int depth)
    {
        this.m_MainFrame = MainFrame;
        this.m_Abs = Abs;
        this.m_depth = depth;
    }
    public void run()
    {
        CAbstractInterpretation cCAbstractInterpretation = new CAbstractInterpretation(this.m_MainFrame,this.m_Abs.getMachine(), this.m_Abs, this.m_depth);
        this.m_MainFrame.loadAbstraction(cCAbstractInterpretation);
    }
}
class CTestGenerationTh extends Thread
{
    CMainFrame m_MainFrame;
    CAbstractInterpretation m_Abs;
    int m_depth;
    public CTestGenerationTh(CMainFrame MainFrame, CAbstractInterpretation Abs, int depth)
    {
        this.m_MainFrame = MainFrame;
        this.m_Abs = Abs;
        this.m_depth = depth;
    }
    public void run()
    {
        CTestGeneration cCTestGeneration = new CTestGeneration(this.m_Abs, this.m_depth);
        this.m_MainFrame.loadTestSuite(cCTestGeneration);
    }
}