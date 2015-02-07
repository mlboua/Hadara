package ui.windows;

import javax.swing.*;

/**
 * User: LoW
 * Date: 14/03/13
 * Time: 10:25
 */
public class CProgressWindow extends CWindow
{
    JProgressBar m_JProgressBar;
    public CProgressWindow(String Name, int Length)
    {
        super("Progress : "+Name, 50, 540, 200, 50,false);

        this.setFrameIcon(new ImageIcon("img/time.png"));

        this.m_JProgressBar = new JProgressBar(0, Length);
        this.m_JProgressBar.setValue(0);
        this.m_JProgressBar.setStringPainted(true);

        this.add(this.m_JProgressBar);
    }
    public int getProgress()
    {
        return this.m_JProgressBar.getValue();
    }
    public void setProgress(int val)
    {
        this.m_JProgressBar.setValue(val);
    }
}
