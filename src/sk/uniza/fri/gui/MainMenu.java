package sk.uniza.fri.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public class MainMenu {
    private final JFrame frame;
    private final JLabel kodLabel;
    private final JTextField kodTextField;
    private final JComponent komponentPredPripojenim;
    private final JComponent komponentPoPripojeni;

    private IListener listener;

    public MainMenu() {
        this.frame = new JFrame();
        this.frame.setTitle("Šach");
        this.frame.setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (MainMenu.this.listener != null) {
                    MainMenu.this.listener.onExit();
                }
            }
        });

        this.komponentPoPripojeni = new JPanel();
        this.komponentPoPripojeni.setLayout(new BoxLayout(this.komponentPoPripojeni, BoxLayout.Y_AXIS));

        this.kodLabel = new JLabel();
        this.kodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.komponentPoPripojeni.add(this.kodLabel);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Kód pre pripojenie k inému hráčovi: "));
        this.kodTextField = new JTextField(5);
        panel.add(this.kodTextField);
        this.komponentPoPripojeni.add(panel);

        JButton button = new JButton("Pripojiť");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onConnect(this.kodTextField.getText());
            }
        });
        this.komponentPoPripojeni.add(button);

        this.komponentPredPripojenim = new JLabel("Pripaja sa k serveru...");

        this.setKod(null);
        this.frame.pack();
    }

    public void zobraz() {
        this.frame.setVisible(true);
    }

    public void skry() {
        this.frame.setVisible(false);
    }

    public void setListener(IListener listener) {
        this.listener = listener;
    }

    public void setKod(String kod) {
        if (kod == null) {
            this.frame.remove(this.komponentPoPripojeni);
            this.frame.add(this.komponentPredPripojenim);
        } else {
            this.frame.remove(this.komponentPredPripojenim);
            this.frame.add(this.komponentPoPripojeni);
            this.kodLabel.setText("Váš kód na vytvorenie hry: " + kod);
        }
        this.frame.pack();
    }

    public void codeNotFound() {

    }

    public interface IListener {
        void onConnect(String kod);

        void onExit();
    }
}
