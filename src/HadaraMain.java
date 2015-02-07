import abstraction.CAbstractInterpretation;
import abstraction.CAbstraction;
import abstraction.CPredicateAbstraction;
import abstraction.CPredicateTriModalsAbstraction;
import abstraction.sts.CState;
import booleanExpression.CAnd;
import booleanExpression.CBooleanExpression;
import booleanExpression.CEquals;
import booleanExpression.COr;
import eventB.CMachine;
import eventB.eventBVisitor.CToXMLVisitor;
import generalExpression.CNumber;
import generalExpression.CVariable;
import materials.CMachineMaker;
import materials.CXMLtoMachine;
import org.jdom2.Document;
import org.jdom2.Element;
import ui.CMainFrame;
import utils.CXMLutils;

import java.util.HashSet;

/**
 * User: LoW
 * Date: 25/12/12
 * Time: 23:49
 */
public class HadaraMain
{
    public static void main (String[] args)
    {
       

        CMainFrame cCMainFrame = new CMainFrame();
        //cMachine m = new CMachine();
        try
        {
            //CMachine cMachine =  CXMLtoMachine.getMachineFrom(CXMLutils.loadFrom("materials\\ExempleSysT\\ExempleSys.ebm"));
            CMachine cMachine = CMachineMaker.getFermatTestMachine();
           // System.out.println(cMachine.toString());
            //CMachine cMachine = CMachineMaker.getESSUYAGE_AVMachine();
            cCMainFrame.loadMachineXML(cMachine);

            /*Element root = CXMLutils.loadFrom("materials\\RobotSimple\\RobotSimpleAbs.abs").getRootElement();
            HashSet<CState> states = CXMLtoMachine.getStatesFrom(root.getChildren().get(0));
            CAbstraction cAbstraction = new CPredicateAbstraction(cMachine,states,CXMLtoMachine.getTransitionsFrom(root.getChildren().get(1),states));
            cCMainFrame.loadAbstraction(cAbstraction);

            CAbstraction test = new CAbstractInterpretation(cMachine,cAbstraction);
            cCMainFrame.loadAbstraction(test); */
        }
        catch(Exception cException)
        {
            cCMainFrame.GetConsoleWindow().GetConsole().addError(cException.getMessage());

            cException.printStackTrace();
        }
    }
}
