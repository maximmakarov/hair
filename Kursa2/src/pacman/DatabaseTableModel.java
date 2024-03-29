package pacman;

//com/porty/swing/DatabaseTableModel. java 
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
@SuppressWarnings("serial")
public class DatabaseTableModel extends AbstractTableModel {
	
	// ����� �� ����� ������� �������� �������� 
	@SuppressWarnings("rawtypes")
	private ArrayList columnNames = new ArrayList(); 
	
	// ������ ����� �������� 
    @SuppressWarnings("rawtypes")
	private ArrayList columnTypes = new ArrayList(); 
    
	// ��������� ��� ���������� ������ �� ���� ������ 
    	@SuppressWarnings("rawtypes")
		private ArrayList data = new ArrayList();
    	
    		// ����������� ��������� ������ ����������� �������������� 
    		public DatabaseTableModel(boolean editable) { 
    			this.editable = editable;
    			} 
    		
    private boolean editable; 
    
    // ���������� ����� 
    public int getRowCount() { 
    	synchronized (data) { 
    		return data.size(); 
    	} } 
    
    // ���������� �������� 
    public int getColumnCount()  { 
    	return columnNames.size(); 
    } 
    
    // ��� ������ ������� 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) { 
    	return (Class)columnTypes.get(column); 
    } 
    // �������� ������� 
    public String getColumnName(int column) { 
    	return (String)columnNames.get(column);
    }
    	// ������ � ������ 
    	@SuppressWarnings("rawtypes")
		public Object getValueAt(int row, int column) { 
    		synchronized (data){
    		return ((ArrayList)data.get(row)).get(column); }
    		} 
    		// ����������� �������������� 
    		public boolean isEditablet(int row, int column) { 
    			return editable; 
    		}	
    		// ������ �������� ������ 
    			@SuppressWarnings({ "unchecked", "rawtypes" })
				public void setValueAt( Object value, int row, int column){synchronized (data) { 
    			((ArrayList)data.get(row)).set(column, value); } } 
    		// ��������� ������ �� ������� ResultSet 
    		@SuppressWarnings({ "unchecked", "rawtypes" })
			public void setDataSource( ResultSet rs) throws Exception { 
    			//������� ������� ������ 
    			data.clear (); 
    		columnNames.clear(); 
    		columnTypes.clear();
    			// �������� ��������������� ���������� � �������� 
    			ResultSetMetaData rsmd = rs.getMetaData(); 
    			int columnCount = rsmd.getColumnCount(); 
    		for ( int i=0;  i<columnCount;  i++)  { 
    				// �������� ������� 
    			
    			columnNames.add(rsmd.getColumnName(i+1));
    			// ��� ������� 
    			Class type = Class.forName(rsmd.getColumnClassName(i+1)); 
    				columnTypes.add(type); 
    	} 
    	// �������� �� ���������� � ��������� ������ 
    	fireTableStructureChanged(); // �������� ������ 
    	while ( rs.next() ) { // ����� ����� ������� ������ ����� ������ 
    		ArrayList row = new ArrayList(); 
    		for ( int i=0;  i<columnCount;  i++) { 
    			if (columnTypes.get(i) == String.class) 
    				row.add(rs.getString(i+1));
    				else 
    					row.add(rs.getObject(i+1)); 
    		} 
    		synchronized (data) { 
    			data.add(row); 
    				// �������� � ����������� ������ 
    				fireTableRowsInserted( 
    						data.size()-1, data.size()-1); 

}}}}