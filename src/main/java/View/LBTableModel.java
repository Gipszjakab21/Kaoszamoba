package View;

import Model.Leaderboard.LeaderboardRecord;

import java.util.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class LBTableModel extends AbstractTableModel{
    private final String[] columns={"Helyezés", "Név", "Pontszám"};
    private List<LeaderboardRecord> records=new ArrayList<>();
    
    @Override
    public int getRowCount(){
        return records.size();
    }

    @Override
    public int getColumnCount(){
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        LeaderboardRecord record=records.get(rowIndex);
        switch (columnIndex){
            case 0:
                return rowIndex+1; //The rank is the row+1
            case 1:
                return record.getName();
            case 2:
                return record.getScore();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column){
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex){
            case 0, 2:
                return Integer.class;
            case 1:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }

    public void setRecords(List<LeaderboardRecord> newRecords){
        records=newRecords;
        fireTableDataChanged();
    }
}
