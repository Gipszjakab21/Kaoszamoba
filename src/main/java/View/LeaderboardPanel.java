package View;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import Model.GameArchive;
import Model.Leaderboard;

public class LeaderboardPanel extends JPanel {
    private final Leaderboard leaderboard;
    private final JTable table;
    private final LBTableModel model;

    public LeaderboardPanel(GameArchive archive, Runnable toMenu){
        setLayout(new BorderLayout(10, 100));
        setBorder(new EmptyBorder(40, 20, 100, 20));

        this.leaderboard=new Leaderboard(archive);

        model=new LBTableModel();
        table=new JTable(model);

        table.setRowHeight(28);
        table.setRowSelectionAllowed(false);
        table.setFont(table.getFont().deriveFont(19f));
        DefaultTableCellRenderer dtcr=new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(dtcr);
        table.getColumnModel().getColumn(2).setCellRenderer(dtcr);


        JButton menuButton=new JButton("Vissza a menübe");
        menuButton.addActionListener(
            e->toMenu.run()
        );

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(menuButton, BorderLayout.SOUTH);
    }

    public void refresh(){
        leaderboard.build();
        model.setRecords(leaderboard.getLeaderboard());
    }
}