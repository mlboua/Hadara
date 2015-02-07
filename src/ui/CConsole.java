package ui;

import javax.swing.*;
import java.awt.*;

/**
 * User: LoW
 * Date: 31/12/12
 * Time: 01:24
 */
public class CConsole extends JEditorPane
{
    public CConsole()
    {
        super("text/html","");
        this.setEditable(false);
        this.setFont( new Font(" TimesRoman ",Font.PLAIN,14));
    }
    public void addLine(String str)
    {
        this.setText(str + "\n" + this.getText());
    }
    public void addSuccess(String str)
    {
        this.addLine("<font color=#21610B>"+str+"</font>");
    }
    public void addError(String str)
    {
        this.addLine("<font color=#610B0B>" + str + "</font>");
    }
    public void addInfo(String str)
    {
        this.addLine("<font color=#08298A>" + str + "</font>");
    }
}
