package ui;

import abstraction.CAbstraction;
import abstraction.CPredicateAbstraction;
import abstraction.sts.CState;
import arithmeticExpression.arithmeticVisitor.IArithmeticVisitor;
import eventB.CMachine;
import materials.CXMLtoMachine;
import org.jdom2.Element;
import parser.noeud.AfterParserException;
import parser.noeud.BParserException;
import parser.noeud.BToXMLVisiteur;
import testGeneration.CTestGeneration;
import ui.windows.*;
import utils.CXMLutils;
import utils.ExampleFileFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static parser.grammaire.BParser.analyse;

/**
 * User: LoW
 * Date: 20/01/13
 * Time: 14:27
 */
public class CMainFrame  extends JFrame implements ActionListener
{
    JDesktopPane m_DesktopPane;
    CConsoleWindow m_ConsoleWindow;
    JMenuItem m_LoadMachineMenuItem;
    JMenuItem m_LoadBSystemItem;
    JMenuItem m_LoadAbstraction;

    int m_WindowXabs, m_WindowYabs;

    ArrayList<CMachine> m_Machines;
    ArrayList<CAbstraction> m_Abstractions;

    public CMainFrame()
    {
        super("Hadara UI (Hadara (ALPHA) By LoW) ");

        this.setIconImage(new ImageIcon("img/hadara.png").getImage());

        this.m_Machines = new ArrayList<CMachine>();
        this.m_Abstractions = new ArrayList<CAbstraction>();

        JMenuBar cMenuBar = new JMenuBar();
        JMenu cMenu = new JMenu("File");
        this.m_LoadMachineMenuItem = new JMenuItem("Load XML machine...",
                new ImageIcon("img/editor.png"));

        this.m_LoadMachineMenuItem.addActionListener(this);

        this.m_LoadBSystemItem = new JMenuItem("Load B machine ...",
                new ImageIcon("img/icon-b.png"));

        this.m_LoadBSystemItem.addActionListener(this);

        this.m_LoadAbstraction = new JMenuItem("Load abstraction...",
                new ImageIcon("img/abs.png"));

        this.m_LoadAbstraction.addActionListener(this);


        cMenu.add(this.m_LoadMachineMenuItem);
        cMenu.add(this.m_LoadBSystemItem);
        cMenu.add(this.m_LoadAbstraction);

        cMenuBar.add(cMenu);

        this.setJMenuBar(cMenuBar);

        this.m_DesktopPane = new JDesktopPane();
        this.m_DesktopPane.setDragMode(JDesktopPane.DRAG_LAYER);
        this.setContentPane(this.m_DesktopPane);

        this.m_ConsoleWindow = new CConsoleWindow();
        this.m_DesktopPane.add(this.m_ConsoleWindow);

        this.m_WindowXabs = 10;
        this.m_WindowYabs = 10;


        this.setSize(new Dimension(1000,700));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void loadMachineXML(CMachine cMachine)
    {
        //System.out.println(cMachine.toOutputString());
        this.m_DesktopPane.add(new CModelEditorWindow(cMachine,this.m_WindowXabs,m_WindowYabs,this));
        this.m_WindowXabs = (this.m_WindowXabs+50)%200;
        this.m_WindowYabs = (this.m_WindowYabs+50)%200;
        this.m_Machines.add(cMachine);
        this.m_ConsoleWindow.GetConsole().addSuccess(cMachine.toString()+" model has been loaded.");
    }

    public void loadMachineB(CMachine cMachine)
    {
        //System.out.println(cMachine.toOutputString());
        this.m_DesktopPane.add(new CModelEditorWindow(cMachine,this.m_WindowXabs,m_WindowYabs,this));
        this.m_WindowXabs = (this.m_WindowXabs+50)%200;
        this.m_WindowYabs = (this.m_WindowYabs+50)%200;
        this.m_Machines.add(cMachine);
        this.m_ConsoleWindow.GetConsole().addSuccess(cMachine.toString()+" model has been loaded.");
    }


    public void loadAbstraction(CAbstraction cAbs)
    {
        //System.out.println(cAbs.toDOT());
        this.m_DesktopPane.add(new CAbsResultsWindow(cAbs,this.m_WindowXabs,m_WindowYabs,this));
        this.m_WindowXabs = (this.m_WindowXabs+50)%200;
        this.m_WindowYabs = (this.m_WindowYabs+50)%200;
        this.m_Abstractions.add(cAbs);
        this.m_ConsoleWindow.GetConsole().addSuccess(cAbs.toString()+" has been loaded.");
    }
    public void loadTestSuite(CTestGeneration cTestGeneration)
    {
        //System.out.println(cTestGeneration.toString());
        this.m_DesktopPane.add(new CTestListWindow(cTestGeneration,this.m_WindowXabs,m_WindowYabs,this));
        this.m_WindowXabs = (this.m_WindowXabs+50)%200;
        this.m_WindowYabs = (this.m_WindowYabs+50)%200;
        this.m_ConsoleWindow.GetConsole().addSuccess(cTestGeneration.GetAbs().toString()+" test suite has been loaded.");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.m_LoadMachineMenuItem)
        {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            ExampleFileFilter filter = new ExampleFileFilter();
            filter.addExtension("ebm");
            filter.setDescription("Event-B Model");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    this.loadMachineXML(CXMLtoMachine.getMachineFrom(CXMLutils.loadFrom(chooser.getSelectedFile().getAbsolutePath())));
                }
                catch(Exception cException)
                {
                    this.m_ConsoleWindow.GetConsole().addError(cException.getMessage());
                }
            }
        }
        if(e.getSource() == this.m_LoadBSystemItem)
        {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            ExampleFileFilter filter = new ExampleFileFilter();
            filter.addExtension("mch");
            filter.setDescription("Event-B Model");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                try {

                    BToXMLVisiteur visitor = new BToXMLVisiteur();
                    Element mch = (Element) visitor.visit(analyse(new File(chooser.getSelectedFile().getAbsolutePath())));
                    this.loadMachineB(CXMLtoMachine.getMachineFrom(CXMLutils.loadFrom(mch)));

                } catch (BParserException | AfterParserException | IOException e1) {
                    this.m_ConsoleWindow.GetConsole().addError(e1.getMessage());
                } catch (Exception cException) {
                    this.m_ConsoleWindow.GetConsole().addError(cException.getMessage());
                }
            }
        }
        if(e.getSource() == this.m_LoadAbstraction)
        {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            ExampleFileFilter filter = new ExampleFileFilter();
            filter.addExtension("abs");
            filter.setDescription("Abstraction Model");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    Element root = CXMLutils.loadFrom(chooser.getSelectedFile().getAbsolutePath()).getRootElement();
                    CMachine cMachine = null;
                    for (int i = 0; i < this.GetMachines().size(); i++)
                    {
                        if(this.GetMachines().get(i).toString().equals(root.getAttribute("model").getValue()))
                        {
                            cMachine = this.GetMachines().get(i);
                        }
                    }
                    if(cMachine == null)
                        throw new Exception("Unknow machine : "+root.getAttribute("model").getValue());

                    HashSet<CState> states = CXMLtoMachine.getStatesFrom(root.getChildren().get(0));
                    this.loadAbstraction(new CPredicateAbstraction(cMachine,states,CXMLtoMachine.getTransitionsFrom(root.getChildren().get(1),states)));

                }
                catch(Exception cException)
                {
                    this.m_ConsoleWindow.GetConsole().addError(cException.getMessage());
                }
            }
        }
    }
    public JDesktopPane GetDesktop()
    {
        return this.m_DesktopPane;
    }
    public CConsoleWindow GetConsoleWindow()
    {
        return this.m_ConsoleWindow;
    }
    public ArrayList<CMachine> GetMachines()
    {
        return this.m_Machines;
    }
    public ArrayList<CAbstraction> GetAbstractions()
    {
        return this.m_Abstractions;
    }
}
