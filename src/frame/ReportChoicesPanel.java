package frame;

import javax.swing.*;
import java.awt.*;

public class ReportChoicesPanel extends JPanel {

    public ReportChoicesPanel(){
        setLayout(new GridLayout(0,1));
        add(createNavigationButton("Shoe sales", Panels.SHOE_SALES_PANEL));
        add(createNavigationButton("Number of orders per customer", Panels.ORDERS_PER_CUSTOMER_PANEL));
        add(createNavigationButton("Revenue per customer", Panels.REVENUE_PER_CUSTOMER_PANEL));
        add(createNavigationButton("Revenue per city", Panels.REVENUE_PER_CITY_PANEL));
        add(createNavigationButton("Top 5 Shoes sold", Panels.TOP_SHOES_PANEL));
        add(new JLabel());
        add(createExitButton());
    }

    private JButton createNavigationButton(String buttonName, Panels panel){
        JButton button = new JButton(buttonName);
        button.addActionListener(e -> AdminFrame.getAdminFrame().switchPanel(panel));
        return button;
    }

    private JButton createExitButton(){
        JButton button = new JButton("Exit");
        button.setFont(button.getFont().deriveFont(Font.BOLD));
        button.addActionListener(e -> System.exit(0));
        return button;
    }


}
