package ui.windows;

import testGeneration.CTestGeneration;
import ui.CMainFrame;

import javax.swing.*;

/**
 * User: LoW
 * Date: 16/05/13
 * Time: 00:42
 */
public class CTestListWindow extends CWindow
{
    CMainFrame m_MainFrame;
    CTestGeneration m_TestGeneration;
    public CTestListWindow(CTestGeneration cTestGeneration, int x, int y, CMainFrame cMainFrame)
    {
        super("Test suite : "+cTestGeneration.GetAbs().toString() ,  x, y, 500, 180, true);

        this.m_MainFrame = cMainFrame;
        this.m_TestGeneration = cTestGeneration;
        this.setFrameIcon(new ImageIcon("img/test.png"));

        JList myList = new JList(cTestGeneration.toStringTab());
        this.add(new JScrollPane(myList));

    }
}
