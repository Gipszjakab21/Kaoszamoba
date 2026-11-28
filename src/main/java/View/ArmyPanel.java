package View;

import static Model.PieceType.ANCHOR;
import static Model.PieceType.BLINDSPOT;
import static Model.PieceType.CHAMELEON;
import static Model.PieceType.CONCRETE;

import java.awt.Color;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import Controller.Actions;
import Model.Army;
import Model.PieceType;

public class ArmyPanel extends JPanel {
    private final JSpinner anchorSpinner;
    private final JSpinner concreteSpinner;
    private final JSpinner chameleonSpinner;
    private final JSpinner blindspotSpinner;
    private final JTextField inputFileName;
    private final JTextField outputFileName;
    private final JLabel IOFeedback;

    public ArmyPanel(Actions actions){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(50, 60, 50, 60));

        anchorSpinner=new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        concreteSpinner=new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        chameleonSpinner=new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        blindspotSpinner=new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

        JLabel anchorLabel=new JLabel("Vasmacska: ");
        JLabel concreteLabel=new JLabel("Betontömb: ");
        JLabel chameleonLabel=new JLabel("Kaméleon ");
        JLabel blindspotLabel=new JLabel("Vakfolt: ");

        JButton menuButton=new JButton("Vissza a menübe");
        menuButton.addActionListener(
            e->{
                actions.saveDefArmy(new Army((int)anchorSpinner.getValue(), (int)concreteSpinner.getValue(), (int)chameleonSpinner.getValue(), (int)blindspotSpinner.getValue()));
                actions.toMenu();
            }
        );
        menuButton.setAlignmentX(CENTER_ALIGNMENT);

        inputFileName=new JTextField();
        outputFileName=new JTextField();
        JLabel txtLabel1=new JLabel(".txt");
        JLabel txtLabel2=new JLabel(".txt");
        JButton loadButton=new JButton("Betöltés");
        JButton saveButton=new JButton("Mentés");
        loadButton.addActionListener(
            e->actions.loadArmy(inputFileName.getText())
        );

        IOFeedback=new JLabel();

        add(IOFeedback);
        add(anchorLabel);
        add(anchorSpinner);
        add(concreteLabel);
        add(concreteSpinner);
        add(chameleonLabel);
        add(chameleonSpinner);
        add(blindspotLabel);
        add(blindspotSpinner);
        add(Box.createVerticalStrut(50));
        add(inputFileName);
        add(txtLabel1);
        add(loadButton);
        add(outputFileName);
        add(txtLabel2);
        add(saveButton);
        add(menuButton);
    }

    public void refresh(Army defArmy){
        IOFeedback.setVisible(false);

        Map<PieceType, Integer> pieces=defArmy.getPieceMap();
        anchorSpinner.setValue(pieces.get(ANCHOR));
        concreteSpinner.setValue(pieces.get(CONCRETE));
        chameleonSpinner.setValue(pieces.get(CHAMELEON));
        blindspotSpinner.setValue(pieces.get(BLINDSPOT));

        inputFileName.setText("");
        outputFileName.setText("");
    }

    public void showIOFeedback(boolean success){
        if (success){
            IOFeedback.setText("Sikeres művelet");
            IOFeedback.setForeground(Color.GREEN);
        }
        else{
            IOFeedback.setText("Sikertelen művelet - Hiba történt");
            IOFeedback.setForeground(Color.RED);
        }
        IOFeedback.setVisible(true);
    }
}
