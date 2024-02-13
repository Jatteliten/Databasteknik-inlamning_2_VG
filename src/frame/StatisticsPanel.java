package frame;

import database.Data;
import database.Datatype;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class StatisticsPanel extends JPanel {
    protected final JPanel dataPanel = new JPanel(new GridLayout(0, 2));

    public StatisticsPanel() {
        setLayout(new BorderLayout());
        add(dataPanel, BorderLayout.CENTER);
        refreshDataPanel();

        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> refreshDataPanel());
        buttonPanel.add(updateButton);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> AdminFrame.getAdminFrame().switchPanel(Panels.REPORT_CHOICES_PANEL));
        buttonPanel.add(cancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    protected abstract String setFirstInformationLabelName();
    protected abstract String setSecondInformationLabelName();
    protected abstract Map<Datatype, Integer> loadData(Map<Datatype, Integer> map);

    public void refreshDataPanel() {
        Data.getData().reloadSales();
        Data.getData().reloadOrders();
        createInformationDisplayFromHashMap(loadData(new HashMap<>()));
        revalidate();
        repaint();
    }

    public void createInformationDisplayFromHashMap(Map<Datatype, Integer> map){
        dataPanel.removeAll();
        dataPanel.add(new JLabel(setFirstInformationLabelName(), SwingConstants.LEFT));
        dataPanel.add(new JLabel(setSecondInformationLabelName(), SwingConstants.CENTER));
        dataPanel.add(new JLabel());
        dataPanel.add(new JLabel());
        map.forEach((d, num) -> {
            dataPanel.add(new JLabel(d.getName()));
            JLabel tempLabel = new JLabel(num.toString());
            tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
            dataPanel.add(tempLabel);
        });
    }

}

