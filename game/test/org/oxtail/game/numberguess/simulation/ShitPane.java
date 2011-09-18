package org.oxtail.game.numberguess.simulation;

import java.io.IOException;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;

public class ShitPane extends JPanel{
	
	private List<Object[]> data = Lists.newArrayList();
	private static final long serialVersionUID = 1L;
	private AbstractTableModel model = new MyTableModel(data);
	private JTable table = new JTable(model);
	
	private static class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] columnNames = new String[]{"player","message"};
	    private List<Object[]> data;
	    
	    
	    public MyTableModel(List<Object[]> data) {
			super();
			this.data = data;
		}

		public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return data.size();
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data.get(row)[col];
	    }

	    public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }

	    public boolean isCellEditable(int row, int col) {
	    	return false;
	    }
	    
	    public void setValueAt(Object value, int row, int col) {
	        data.get(row)[col] = value;
	        fireTableCellUpdated(row, col);
	    }
	    
	};
	
	void notifyMessage(String player, String message) {
		Object[] row = new Object[]{loadIcon(player),message};
		data.add(row);
		model.fireTableDataChanged();
	}
	
	private Icon loadIcon(String player) {
		Resource resource = new ClassPathResource("icons/"+player+".jpg",getClass());
		try {
			return new ImageIcon(resource.getURL(),player);
		} catch (IOException e) {
			e.printStackTrace();
			return new ImageIcon(player);
		}
	}
	
	public ShitPane() {
		table.setRowHeight(64);
		table.getColumnModel().getColumn(0).setWidth(64);
		table.getColumnModel().getColumn(0).setPreferredWidth(64);
		JScrollPane scrollPane = new JScrollPane(table);
        //Add the scroll pane to this panel.
        add(scrollPane);
	}
	
	public static ShitPane create() {
		JFrame frame = new JFrame();
		ShitPane shitPane = new ShitPane();
		frame.getContentPane().add(shitPane);
		frame.pack();
		frame.setVisible(true);
		return shitPane;
	}
	
	
}
