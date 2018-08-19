import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PointsSplitter extends JPanel
        implements ActionListener {

    Box mainBox = Box.createVerticalBox();
    private JButton splitAndSaveButton, loadButton;
    private JLabel description;
    private JFileChooser fileChooser;
    private File file;
    private String savePath;

    PointsSplitter() {
        setLayout(new BorderLayout());
        fileChooser = new JFileChooser();

        description = new JLabel("<html><center>Program rozdziela plik CSV na oddzielne piętra <br/> biorąc jako kryterium pierwszą literę,<br/> a następnie zapisuje jako oddzielne pliki .txt</center></html>", JLabel.CENTER);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadButton = new JButton("Załaduj plik CSV");
        loadButton.addActionListener(this);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        splitAndSaveButton = new JButton("Podziel i zapisz");
        splitAndSaveButton.addActionListener(this);
        splitAndSaveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainBox.add(Box.createRigidArea(new Dimension(0, 20)));
        mainBox.add(description);
        mainBox.add(Box.createRigidArea(new Dimension(0, 10)));
        mainBox.add(loadButton);
        mainBox.add(splitAndSaveButton);

        this.add(mainBox);
    }


    private void SplitInput() {
        Splitter splitter = new Splitter(file, savePath);
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loadButton) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
            fileChooser.setFileFilter(filter);
            int returnVal = fileChooser.showOpenDialog(PointsSplitter.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();

                System.out.println("Successfully loaded File");

            } else {
                System.out.println("Loading File corrupted");
            }
        } else if (e.getSource() == splitAndSaveButton) {
            int returnVal = fileChooser.showSaveDialog(PointsSplitter.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                savePath = file.getPath();
                SplitInput();
            }
        }

    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Points Splitter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new PointsSplitter());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
