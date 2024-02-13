package frame;

import database.Data;
import database.Sale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ShoeSalesPanel extends JPanel {
    private final JTextField brand = new JTextField();
    private final JTextField colour = new JTextField();
    private final JTextField sizeMinimum = new JTextField();
    private final JTextField sizeMaximum = new JTextField();
    private final JPanel results = new JPanel();
    FilterList filterBrand = l -> {
        if(!brand.getText().isEmpty()) {
            return new ArrayList<>(l.stream().filter
                    (s -> s.getShoe().getBrand().equalsIgnoreCase(brand.getText())).toList());
        }
        return l;
    };
    FilterList filterColour = l -> {
        if(!colour.getText().isEmpty()){
            return new ArrayList<>(l.stream().filter
                    (s -> s.getShoe().getColour().name().equalsIgnoreCase(colour.getText())).toList());
        }
        return l;
    };
    FilterList filterSize = l -> {
        int minNumber;
        int maxNumber;
        try{
            minNumber = sizeMinimum.getText().isEmpty() ? 0 : Integer.parseInt(sizeMinimum.getText());
            maxNumber = sizeMaximum.getText().isEmpty() ? 0 : Integer.parseInt(sizeMaximum.getText());
            if(minNumber <= maxNumber){
                return new ArrayList<>(l.stream().filter
                        (s -> s.getShoe().getSize() >= minNumber && s.getShoe().getSize() <= maxNumber).toList());
            }else{
                resetMinAndMaxTextFields();
                return l;
            }
        }catch(NumberFormatException e){
            resetMinAndMaxTextFields();
            return l;
        }
    };

    public ShoeSalesPanel(){
        setLayout(new BorderLayout());

        resetMinAndMaxTextFields();

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> { AdminFrame.getAdminFrame().switchPanel(Panels.REPORT_CHOICES_PANEL);
            results.removeAll();
            resetMinAndMaxTextFields();
            resetMinAndMaxTextFields();});
        add(cancel, BorderLayout.SOUTH);

        add(initializeSearchPanel(), BorderLayout.NORTH);

        results.setLayout(new GridLayout(0, 1));
        add(results, BorderLayout.CENTER);
    }

    private JPanel initializeSearchPanel(){
        JPanel search = new JPanel();
        search.add(new JLabel("Brand:"));
        search.add(initializeTextFields(brand, true));
        search.add(new JLabel("Colour:"));
        search.add(initializeTextFields(colour, true));
        search.add(new JLabel("Size:"));
        search.add(initializeTextFields(sizeMinimum, false));
        search.add(new JLabel("-"));
        search.add(initializeTextFields(sizeMaximum, false));
        add(search, BorderLayout.NORTH);

        return search;
    }

    private JTextField initializeTextFields(JTextField textField, boolean large){
        textField.setForeground(Color.GRAY);
        if(large) {
            textField.setPreferredSize(new Dimension(150, 30));
        }else{
            textField.setPreferredSize(new Dimension(40, 30));
        }
        textField.addActionListener(e -> displayResults());
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        });
        return textField;
    }

    private void displayResults(){
        results.removeAll();
        Data.getData().reloadSales();
        ArrayList<Sale> salesList = filterShoes(filterShoes(filterShoes(Data.getData().getSales(),
                        filterBrand),
                        filterSize),
                        filterColour);

        salesList.forEach(s -> {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0, 3));
            panel.add(new JLabel(s.getOrderId().getCustomer().getName()));
            panel.add(new JLabel(s.getOrderId().getCustomer().getAdress()));
            panel.add(new JLabel(s.getOrderId().getDate().toString()));
            results.add(panel);
        });

        AdminFrame.getAdminFrame().refreshFrame();
    }

    private ArrayList<Sale> filterShoes(ArrayList<Sale> salesList, FilterList f1){
        return new ArrayList<>(f1.apply(salesList));
    }

    private void resetMinAndMaxTextFields(){
        sizeMinimum.setText("0");
        sizeMaximum.setText("100");
    }

}
