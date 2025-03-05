package QuanLyTracNghiem.GUI;

class ThongTinUI extends javax.swing.JPanel {

    public ThongTinUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        panel_title = new java.awt.Panel();
        lbl_title = new javax.swing.JLabel();
        phone_label = new javax.swing.JLabel();
        gmail_label = new javax.swing.JLabel();
        user_id_label = new javax.swing.JLabel();
        panel_right = new java.awt.Panel();
        lbl_cpass = new javax.swing.JLabel();
        lbl_new_pas = new javax.swing.JLabel();
        txt_cpass = new javax.swing.JPasswordField();
        txt_newpass = new javax.swing.JPasswordField();
        btn_changepass = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        full_name_label = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        txt_gmail = new javax.swing.JTextField();
        txt_fullname = new javax.swing.JTextField();
        uer_id = new javax.swing.JLabel();

        jTextField3.setText("jTextField1");

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1500, 1000));
        setPreferredSize(new java.awt.Dimension(1500, 1000));

        panel_title.setBackground(new java.awt.Color(204, 255, 255));

        lbl_title.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setText("THÔNG TIN NGƯỜI DÙNG");

        javax.swing.GroupLayout panel_titleLayout = new javax.swing.GroupLayout(panel_title);
        panel_title.setLayout(panel_titleLayout);
        panel_titleLayout.setHorizontalGroup(
                panel_titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_titleLayout.setVerticalGroup(
                panel_titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        phone_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        phone_label.setText("Phone :");

        gmail_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        gmail_label.setText("Gmail :");

        user_id_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        user_id_label.setText("User :");

        panel_right.setBackground(new java.awt.Color(204, 255, 255));

        lbl_cpass.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_cpass.setText("Confirm Password :");

        lbl_new_pas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_new_pas.setText("New password :");

        txt_cpass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_cpass.setText("jPasswordField1");

        txt_newpass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_newpass.setText("jPasswordField1");

        btn_changepass.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btn_changepass.setText("Đổi Mật Khẩu");
        btn_changepass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_changepassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_rightLayout = new javax.swing.GroupLayout(panel_right);
        panel_right.setLayout(panel_rightLayout);
        panel_rightLayout.setHorizontalGroup(
                panel_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_rightLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(panel_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_rightLayout.createSequentialGroup()
                                                .addComponent(lbl_new_pas, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_rightLayout.createSequentialGroup()
                                                .addGroup(panel_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_changepass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_rightLayout.createSequentialGroup()
                                                                .addComponent(lbl_cpass, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txt_cpass, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)))
                                                .addGap(14, 14, 14))))
                        .addGroup(panel_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_rightLayout.createSequentialGroup()
                                        .addContainerGap(215, Short.MAX_VALUE)
                                        .addComponent(txt_newpass, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)))
        );
        panel_rightLayout.setVerticalGroup(
                panel_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_rightLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(lbl_new_pas, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lbl_cpass, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_cpass, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
                                .addComponent(btn_changepass, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(157, Short.MAX_VALUE))
                        .addGroup(panel_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panel_rightLayout.createSequentialGroup()
                                        .addGap(46, 46, 46)
                                        .addComponent(txt_newpass, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(327, Short.MAX_VALUE)))
        );

        lbl_cpass.getAccessibleContext().setAccessibleName("New password :");

        btn_update.setBackground(new java.awt.Color(204, 255, 255));
        btn_update.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btn_update.setText("Chỉnh Sửa");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        full_name_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        full_name_label.setText("Full name :");

        txt_phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_phoneActionPerformed(evt);
            }
        });

        txt_gmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gmailActionPerformed(evt);
            }
        });

        txt_fullname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fullnameActionPerformed(evt);
            }
        });

        uer_id.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        uer_id.setText("maianh1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(user_id_label, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(uer_id, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(full_name_label, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(phone_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(27, 27, 27)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txt_fullname, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                                                        .addComponent(txt_phone)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(btn_update, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                        .addComponent(gmail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(59, 59, 59)
                                                        .addComponent(txt_gmail, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(panel_right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panel_title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(user_id_label, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(uer_id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(full_name_label, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txt_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(phone_label, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(gmail_label, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txt_gmail, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(44, 44, 44)
                                                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(panel_right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(483, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txt_gmailActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txt_fullnameActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txt_phoneActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btn_changepassActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }


    // Variables declaration - do not modify
    private javax.swing.JButton btn_changepass;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel full_name_label;
    private javax.swing.JLabel gmail_label;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lbl_cpass;
    private javax.swing.JLabel lbl_new_pas;
    private javax.swing.JLabel lbl_title;
    private java.awt.Panel panel_right;
    private java.awt.Panel panel_title;
    private javax.swing.JLabel phone_label;
    private javax.swing.JPasswordField txt_cpass;
    private javax.swing.JTextField txt_fullname;
    private javax.swing.JTextField txt_gmail;
    private javax.swing.JPasswordField txt_newpass;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JLabel uer_id;
    private javax.swing.JLabel user_id_label;
    // End of variables declaration
}
