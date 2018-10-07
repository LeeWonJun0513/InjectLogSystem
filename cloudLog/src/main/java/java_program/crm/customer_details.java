package crm;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.log4j.Logger;

public class customer_details extends javax.swing.JFrame {

    public static String name;
    public static String date;
    public customer_details() {
        initComponents();
        setTitle("Customer Details");
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(68, 68, 68));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14)); 
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Customer Name :");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Date of Purchase (YYYY-MM-DD) :");

        jButton1.setBackground(new java.awt.Color(180, 43, 250));
        jButton1.setFont(new java.awt.Font("Verdana", 1, 14)); 
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SUBMIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jTextField2))
                .addGap(45, 45, 45))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(jButton1)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jMenu1.setText("HOME");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sell A Product");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Product Details");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Customer Details");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        jMenu5.setText("Add A Product");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        name = jTextField1.getText();
        date = jTextField2.getText();
        customer_details_table frame = new customer_details_table();
        frame.pack();
        frame.setVisible(true);
    }

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {

        this.setVisible(false);
        this.dispose();
        Home frame = new Home();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);        
    }

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {

        this.setVisible(false);
        this.dispose();
        Sell frame = new Sell();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);
    }

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {
       
        Product_details frame = new Product_details();
        frame.pack();
        frame.setVisible(true);        
    }
    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {
       
        this.setVisible(false);
        this.dispose();
        customer_details frame = new customer_details();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);       
    }

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {
       
        this.setVisible(false);
        this.dispose();
        Add_product frame = new Add_product();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);        
    }

  
    public static void main(String args[]) {
    	Logger logger = Logger.getLogger(customer_details.class); 
    
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        	logger.error(ex);
        } catch (InstantiationException ex) {
        	logger.error(ex);
        } catch (IllegalAccessException ex) {
        	logger.error(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        	logger.error(ex);
        }
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new customer_details().setVisible(true);
            }
        });
        
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    
}

class customer_details_table extends javax.swing.JFrame {

    public customer_details_table() {
        setTitle("Products Details");
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();
        try
        {
        	final String url = "jdbc:mysql://localhost/crm?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            customer_details demo = new customer_details();
            System.out.println(demo.name+ " " + demo.date);
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, "root", "1234");
            System.out.println(con.toString());
            String sql = "SELECT * FROM products_sold WHERE Customer_name='"+demo.name+"' AND date='"+demo.date+"';";
            System.out.println(sql);
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
            JOptionPane.showMessageDialog(this,"Error in connectivity" );
        }      
    }
}
