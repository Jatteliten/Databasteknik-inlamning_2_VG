package frame;

import javax.swing.*;

public class AdminFrame extends JFrame {
    private static AdminFrame adminFrame;
    private final ReportChoicesPanel reportChoicesPanel = new ReportChoicesPanel();
    private final ShoeSalesPanel shoeSalesPanel = new ShoeSalesPanel();
    private final NumberOfOrdersPerCustomerPanel numberOfOrdersPerCustomerPanel = new NumberOfOrdersPerCustomerPanel();
    private final RevenuePerCustomerPanel revenuePerCustomerPanel = new RevenuePerCustomerPanel();
    private final RevenuePerCityPanel revenuePerCityPanel = new RevenuePerCityPanel();
    private final TopShoesSoldPanel topShoesSoldPanel = new TopShoesSoldPanel();
    private AdminFrame(){
        setTitle("Store statistics");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new LoginPanel());
        refreshFrame();
        setVisible(true);
    }

    public static AdminFrame getAdminFrame(){
        if(adminFrame == null){
            adminFrame = new AdminFrame();
        }
        return adminFrame;
    }

    public void switchPanel(Panels panel){
        getContentPane().removeAll();
        switch(panel) {
            case REPORT_CHOICES_PANEL -> add(reportChoicesPanel);
            case SHOE_SALES_PANEL -> add(shoeSalesPanel);
            case ORDERS_PER_CUSTOMER_PANEL -> addAndRefreshPanel(numberOfOrdersPerCustomerPanel);
            case REVENUE_PER_CUSTOMER_PANEL -> addAndRefreshPanel(revenuePerCustomerPanel);
            case REVENUE_PER_CITY_PANEL -> addAndRefreshPanel(revenuePerCityPanel);
            case TOP_SHOES_PANEL -> addAndRefreshPanel(topShoesSoldPanel);
        }
        refreshFrame();
    }

    private void addAndRefreshPanel(StatisticsPanel panel){
        panel.refreshDataPanel();
        add(panel);
    }

    public void refreshFrame(){
        pack();
        revalidate();
        repaint();
    }
}
