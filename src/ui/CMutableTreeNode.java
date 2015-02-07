package ui;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.util.Vector;

/**
 * User: LoW
 * Date: 02/01/13
 * Time: 00:42
 */
public class CMutableTreeNode extends DefaultMutableTreeNode
{
    public boolean isExpanded;
    public CMutableTreeNode()
    {
        super();
        this.isExpanded = false;
    }
    public CMutableTreeNode(String str)
    {
        super(str);
        this.isExpanded = false;
    }
    public void setExpanded(boolean b)
    {
        this.isExpanded = b;
    }
    public void insert(MutableTreeNode newChild, int childIndex) {
        if (!allowsChildren) {
            throw new IllegalStateException("node does not allow children");
        } else if (newChild == null) {
            throw new IllegalArgumentException("new child is null");
        } else if (isNodeAncestor(newChild)) {
            throw new IllegalArgumentException("new child is an ancestor");
        }

        newChild.setParent(this);
        if (children == null) {
            children = new Vector();
        }
        children.insertElementAt(newChild, childIndex);
    }
}
