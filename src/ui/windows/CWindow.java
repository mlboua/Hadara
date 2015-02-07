package ui.windows;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: LoW
 * Date: 20/01/13
 * Time: 14:40
 */
public class CWindow extends JInternalFrame implements ActionListener,InternalFrameListener
{
      public CWindow(String title, int x, int y, int w, int h, boolean bClosable)
      {
          super(title, true, bClosable, true, true);
          this.setVisible(true);
          this.setBounds(x, y, w, h);

          this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

          this.addInternalFrameListener(this);
      }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e)
    {

    }
}
