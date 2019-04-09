package carrera;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author david
 */
public class Pista extends javax.swing.JFrame {
    private Octocat octo1;
    private Octocat octo2;
    private Octocat octo3;
    private Octocat octo4;
    private String url[];
    private String posiciones[]; // recurso a compartirse
    /**
     * Creates new form Pista
     */
    public Pista() {
        initComponents();
        this.setSize(410, 600);
        this.url = new String[]{
            "files/octo1.png",
            "files/octo2.png",
            "files/octo3.png",
            "files/octo4.png"
        };
        this.posiciones = new String[4];
        disponerOctocats();
    }
    
    private void disponerOctocats() {
        // quitamos el texto
        this.lblOcto1.setText("");
        this.lblOcto2.setText("");
        this.lblOcto3.setText("");
        this.lblOcto4.setText("");
        this.lblOcto1.setName("Octo 1");
        this.lblOcto2.setName("Octo 2");
        this.lblOcto3.setName("Octo 3");
        this.lblOcto4.setName("Octo 4");
        // los reacomodamos
        this.lblOcto1.setLocation(this.lblOcto1.getX(), 490);
        this.lblOcto2.setLocation(this.lblOcto2.getX(), 490);
        this.lblOcto3.setLocation(this.lblOcto3.getX(), 490);
        this.lblOcto4.setLocation(this.lblOcto4.getX(), 490);
        // Instanciamos
        this.octo1 = new Octocat(this.lblOcto1, url[0], posiciones);
        this.octo2 = new Octocat(this.lblOcto2, url[1], posiciones);
        this.octo3 = new Octocat(this.lblOcto3, url[2], posiciones);
        this.octo4 = new Octocat(this.lblOcto4, url[3], posiciones);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnIniciarCarrera = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        lblOcto1 = new javax.swing.JLabel();
        lblOcto2 = new javax.swing.JLabel();
        lblOcto3 = new javax.swing.JLabel();
        lblOcto4 = new javax.swing.JLabel();
        btnVerTiempos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("F1 Octocats");
        setPreferredSize(new java.awt.Dimension(600, 420));
        setResizable(false);

        btnIniciarCarrera.setFont(new java.awt.Font("Ubuntu Mono", 1, 14)); // NOI18N
        btnIniciarCarrera.setForeground(new java.awt.Color(0, 204, 204));
        btnIniciarCarrera.setText("INICIAR CARRERA");
        btnIniciarCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarCarreraActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 255, 102));
        jSeparator1.setForeground(new java.awt.Color(0, 255, 153));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setBackground(new java.awt.Color(0, 255, 102));
        jSeparator2.setForeground(new java.awt.Color(0, 255, 153));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setBackground(new java.awt.Color(0, 255, 102));
        jSeparator3.setForeground(new java.awt.Color(0, 255, 153));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblOcto1.setText("octo1");

        lblOcto2.setText("octo2");

        lblOcto3.setText("octo3");

        lblOcto4.setText("octo4");

        btnVerTiempos.setFont(new java.awt.Font("Ubuntu Mono", 1, 14)); // NOI18N
        btnVerTiempos.setForeground(new java.awt.Color(0, 153, 153));
        btnVerTiempos.setText("VER TIEMPOS");
        btnVerTiempos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerTiemposActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblOcto1)
                .addGap(29, 29, 29)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblOcto2)
                .addGap(33, 33, 33)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblOcto3)
                .addGap(30, 30, 30)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblOcto4)
                .addContainerGap(71, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnIniciarCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerTiempos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblOcto1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblOcto2, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOcto3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblOcto4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnIniciarCarrera, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnVerTiempos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ## INICIAR LA CARRERA
    private void btnIniciarCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarCarreraActionPerformed
        for (int i = 0; i < 4; i++) this.posiciones[i] = null;
        if(this.btnIniciarCarrera.getText().equals("REINICIAR")) {
            disponerOctocats();
        }
        iniciarCarrera();
    }//GEN-LAST:event_btnIniciarCarreraActionPerformed

    private void iniciarCarrera() {
        this.btnIniciarCarrera.setEnabled(false);
        this.octo1.arrancar();
        this.octo2.arrancar();
        this.octo3.arrancar();
        this.octo4.arrancar();
    }
    
    private void btnVerTiemposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerTiemposActionPerformed
          if(this.octo1.getTiempoTotal() < 0) return;
          else if(this.octo2.getTiempoTotal() < 0) return;
          else if(this.octo3.getTiempoTotal() < 0) return;
          else if(this.octo4.getTiempoTotal() < 0) return;

//        System.out.println("El tiempo de octo1 es: " + this.octo1.getTiempoTotal());
//        System.out.println("El tiempo de octo2 es: " + this.octo2.getTiempoTotal());
//        System.out.println("El tiempo de octo3 es: " + this.octo3.getTiempoTotal());
//        System.out.println("El tiempo de octo4 es: " + this.octo4.getTiempoTotal());
        Map<String, Long> tiempos = new TreeMap<>();
        tiempos.put("octo1", this.octo1.getTiempoTotal());
        tiempos.put("octo2", this.octo2.getTiempoTotal());
        tiempos.put("octo3", this.octo3.getTiempoTotal());
        tiempos.put("octo4", this.octo4.getTiempoTotal());
        
        Tiempos t = new Tiempos(this, true, tiempos);
        t.setVisible(true);
        
        this.btnIniciarCarrera.setEnabled(true);
        this.btnIniciarCarrera.setText("REINICIAR");
    }//GEN-LAST:event_btnVerTiemposActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pista().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciarCarrera;
    private javax.swing.JButton btnVerTiempos;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblOcto1;
    private javax.swing.JLabel lblOcto2;
    private javax.swing.JLabel lblOcto3;
    private javax.swing.JLabel lblOcto4;
    // End of variables declaration//GEN-END:variables
}