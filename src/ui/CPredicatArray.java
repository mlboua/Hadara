package ui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * User: LoW
 * Date: 06/01/13
 * Time: 00:34
 */
class CPredicatArrayModel extends AbstractTableModel
{
    private String[] m_columnNames;
    private Object[][] m_data;
    public CPredicatArrayModel(String[] columnNames, Object[][] data)
    {
        this.m_data = data;
        this.m_columnNames = columnNames;
    }

    public int getColumnCount()
    {
        return m_columnNames.length;
    }

    public int getRowCount()
    {
        return m_data.length;
    }

    public String getColumnName(int col)
    {
        return m_columnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
        return m_data[row][col];
    }

    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col)
    {
         return true;
    }

    public void setValueAt(Object value, int row, int col)
    {
        m_data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
public class CPredicatArray extends JTable
{
    public CPredicatArray(String[] columnNames, Object[][] data)
    {
        super(new CPredicatArrayModel(columnNames,data));
        if(columnNames.length == 2)
        {
            this.getColumnModel().getColumn(0).setPreferredWidth(50);
            this.getColumnModel().getColumn(1).setPreferredWidth(400);
        }
        else if(columnNames.length == 3)
        {
            this.getColumnModel().getColumn(0).setPreferredWidth(50);
            this.getColumnModel().getColumn(1).setPreferredWidth(50);
            this.getColumnModel().getColumn(2).setPreferredWidth(400);
        }

    }
}
