package ui;

import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * User: LoW
 * Date: 02/01/13
 * Time: 01:42
 */
public class CTreeCellRenderer  extends DefaultTreeCellRenderer
{
    public CTreeCellRenderer()
    {
        this.setOpenIcon(null);
        this.setClosedIcon(null);
        this.setLeafIcon(null);
        this.setFont(new Font("DejaVu Sans - Plain", Font.PLAIN, 14));
    }
}
