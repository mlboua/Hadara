package ui.windows;

import ui.CConsole;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 * User: LoW
 * Date: 06/02/13
 * Time: 09:26
 */
public class CConsoleWindow extends CWindow
{

    CConsole m_Console;
    public CConsoleWindow()
    {
        super("Console", 550, 540, 430, 100,false);

        this.setFrameIcon(new ImageIcon("img/console.png"));

        this.m_Console = new CConsole();
        this.m_Console.addInfo("-=| Welcome on Hadara (ALPHA) -=|=- Copyright 2012-2013 LoW |=-");

        this.add(new JScrollPane(this.m_Console));
    }
    public CConsole GetConsole()
    {
        return this.m_Console;
    }
}
