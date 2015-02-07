package ui;

import eventB.CMachine;

import javax.swing.*;
import javax.swing.tree.*;

/**
 * User: LoW
 * Date: 31/12/12
 * Time: 00:41
 */
public class CModelEditor extends JTree
{
     public CModelEditor(CMachine cMachine)
     {
         super();
         this.setCellRenderer(new CTreeCellRenderer());
         this.setModel(new DefaultTreeModel(cMachine));
         for (int i = 0; i < cMachine.getChildCount(); i++)
         {
             DefaultMutableTreeNode tmp =  (DefaultMutableTreeNode)cMachine.getChildAt(i);
             this.expandPath(new TreePath(tmp.getPath()));
         }
     }
}
