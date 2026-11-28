package View;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import Controller.Actions;
import Model.Leaderboard.LeaderboardRecord;

public class LeaderboardPanel extends JPanel {
    private final LBTableModel model;

    public LeaderboardPanel(Actions actions){
        setLayout(new BorderLayout(10, 60));
        setBorder(new EmptyBorder(40, 20,100, 20));

        JLabel title=new JLabel("Ranglista", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(25f));

        model=new LBTableModel();
        JTable table=new JTable(model);

        table.setRowHeight(28);
        table.setRowSelectionAllowed(false);
        table.setFont(table.getFont().deriveFont(19f));
        DefaultTableCellRenderer dtcr=new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(dtcr);
        table.getColumnModel().getColumn(2).setCellRenderer(dtcr);


        JButton menuButton=new JButton("Vissza a menübe");
        menuButton.addActionListener( e->actions.toMenu() );

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(menuButton, BorderLayout.SOUTH);
    }

    public void refresh(List<LeaderboardRecord> newLeaderboard) {
        model.setRecords(newLeaderboard);
    }
}