package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginPanel extends JPanel {
    private final JTextField userName = new JTextField("username");
    private final JTextField password = new JTextField("password");
    private final Properties p = new Properties();

    public LoginPanel(){
        try(FileInputStream f = new FileInputStream("src/Settings.properties")){
            p.load(f);
        } catch(IOException e){
            e.printStackTrace();
        }
        initializeTextFields(userName, userName.getText());
        initializeTextFields(password, password.getText());

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> checkCredentials());

        add(userName);
        add(password);
        add(confirmButton);
    }

    private void initializeTextFields(JTextField textField, String textFieldName){
        textField.setForeground(Color.GRAY);
        textField.setPreferredSize(new Dimension(200, 40));
        textField.addActionListener(e -> checkCredentials());
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(textField.getText().equals(textFieldName)){
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
        });
    }

    private void checkCredentials(){
        if(userName.getText().equals(p.getProperty("adminUsername")) &&
            password.getText().equals(p.getProperty("adminPassword"))){
            AdminFrame.getAdminFrame().switchPanel(Panels.REPORT_CHOICES_PANEL);
        }else{
            userName.setText("name");
            password.setText("password");
            userName.setForeground(Color.GRAY);
            password.setForeground(Color.GRAY);
            JOptionPane.showMessageDialog(null, "Username and password does not match");
        }
    }
}
