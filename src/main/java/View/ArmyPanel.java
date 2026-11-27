package View;

import static Model.PieceType.ANCHOR;
import static Model.PieceType.BLINDSPOT;
import static Model.PieceType.CHAMELEON;
import static Model.PieceType.CONCRETE;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import Controller.Actions;
import Model.Army;
import Model.PieceType;

public class ArmyPanel extends JPanel {
    private final JSpinner anchorSpinner;
    private final JSpinner concreteSpinner;
    private final JSpinner chameleonSpinner;
    private final JSpinner blindspotSpinner;

    public ArmyPanel(Actions actions){
        anchorSpinner=new JSpinner();
        concreteSpinner=new JSpinner();
        chameleonSpinner=new JSpinner();
        blindspotSpinner=new JSpinner();

        JButton menuButton=new JButton("Vissza a menübe");
        menuButton.addActionListener(
            e->{
                actions.saveDefArmy(new Army((int)anchorSpinner.getValue(), (int)concreteSpinner.getValue(), (int)chameleonSpinner.getValue(), (int)blindspotSpinner.getValue()));
                actions.toMenu();
            }
        );

        add(anchorSpinner);
        add(concreteSpinner);
        add(chameleonSpinner);
        add(blindspotSpinner);
        add(menuButton);
    }

    public void refresh(Army defArmy){
        Map<PieceType, Integer> pieces=defArmy.getPieceMap();
        anchorSpinner.setValue(pieces.get(ANCHOR));
        concreteSpinner.setValue(pieces.get(CONCRETE));
        chameleonSpinner.setValue(pieces.get(CHAMELEON));
        blindspotSpinner.setValue(pieces.get(BLINDSPOT));
    }
}
