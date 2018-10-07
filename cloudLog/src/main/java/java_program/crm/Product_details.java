package crm;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.log4j.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.*;

public class Product_details extends javax.swing.JFrame {

    public Product_details() {
    setTitle("Products Details");
    Logger logger = Logger.getLogger(this.getClass());
    ArrayList columnNames = new ArrayList();
    ArrayList data = new ArrayList();
    try
    {
    	final String url = "jdbc:mysql://localhost/crm?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, "root", "1234");
        System.out.println(con.toString());
        String sql = "SELECT * FROM products";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( sql );

        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();

        for (int i = 1; i <= columns; i++)
        {
            columnNames.add( md.getColumnName(i) );
        }

        while (rs.next())
        {
            ArrayList row = new ArrayList(columns);

            for (int i = 1; i <= columns; i++)
            {
                row.add( rs.getObject(i) );
            }

            data.add( row );
        }
            
        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();

        for (int i = 0; i < data.size(); i++)
        {
            ArrayList subArray = (ArrayList)data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++)
            {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++ )
            columnNamesVector.add(columnNames.get(i));

        JTable table = new JTable(dataVector, columnNamesVector)
        {
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };

        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );

        JPanel buttonPanel = new JPanel();
        getContentPane().add( buttonPanel, BorderLayout.SOUTH );
        }
    
    catch(Exception e)
    {
    	logger.error(e);
        JOptionPane.showMessageDialog(this,"Error in connectivity" );
    }  
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        pack();
    }

 
    public static void main(String args[]) {
    	//Logger logger = Logger.getLogger(Product_details.class);
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        	//logger.error(ex);
        } catch (InstantiationException ex) {
        	///logger.error(ex);
        } catch (IllegalAccessException ex) {
        	//logger.error(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        	//logger.error(ex);
        }
       
        Product_details frame = new Product_details();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);
       
    }
}
