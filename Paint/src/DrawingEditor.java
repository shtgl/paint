import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DrawingEditor extends JFrame {

    Random rnd = new Random();
    //Icons
    Icon rec = new ImageIcon(getClass().getResource("img\\rec.png"));
    Icon circ = new ImageIcon(getClass().getResource("img\\cir.png"));
    Icon line = new ImageIcon(getClass().getResource("img\\line.png"));
    Icon garbage = new ImageIcon(getClass().getResource("img\\garbage.png"));
    Icon del = new ImageIcon(getClass().getResource("img\\del.png"));
    Icon tic = new ImageIcon(getClass().getResource("img\\tik.png"));
    Icon sve = new ImageIcon(getClass().getResource("img\\save.png"));
    //Icons
    static Color currentCol = Color.BLACK;
    Color systemColor = new Color(245, 245, 245);

    static Color lineCol;
    static int lineSize;

    JRadioButton emptyRec, fullRec, emptyCir, fullCir;
    int recW, recH, cirW, cirH;
    //int pointCounter = 0;
    DrawingArea center;
    static JPanel topPanel = new JPanel(), 
    SideRec = new JPanel(), SideCir = new JPanel(), SideLine = new JPanel();
    JButton save, rect, circle, lne, rmv, clearAll, DrawRec, setRecCol, DrawCir, setCirCol, setLineCol;
    static boolean remove = false;
    static JPanel currentSide = SideRec;

    public DrawingEditor() {

        //Main Frame
        setSize(1500, 800); //location and size
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close button stops the app
        setLocationRelativeTo(null);// middle of the screen
        //setResizable(false);
        setVisible(true);
        setTitle("Draw Tool");
        //Main Frame

        //Drawing Area Center JPanel
        center = new DrawingArea();
        add(center, BorderLayout.CENTER);
        //Drawing Area Center JPanel

        //Left JPanel
        SideRec.setBackground(systemColor);
        add(SideRec, BorderLayout.WEST);
        recButtons();
        //Left JPanel

        //TOP PANEL SETTINGS
        topPanel.setBackground(systemColor);
        add(topPanel, BorderLayout.NORTH);
        addButtonsToTop();

        SideCir.setBackground(systemColor);
        add(SideRec, BorderLayout.WEST);
        circButtons();

        SideLine.setBackground(systemColor);
        add(SideRec, BorderLayout.WEST);
        lineButtons();
        //TOP PANEL SETTING

        //DUTY OF BUTTONS
        dutyButtons();
        //DUTY OF BUTTONS

    }

    public void addButtonsToTop() {

        rect = new JButton(null, rec);
        circle = new JButton(null, circ);
        lne = new JButton(null, line);
        clearAll = new JButton(null, garbage);
        rmv = new JButton(null, tic);
        save = new JButton(null, sve);

        rect.setFocusPainted(false);
        circle.setFocusPainted(false);
        lne.setFocusPainted(false);
        clearAll.setFocusPainted(false);
        rmv.setFocusPainted(false);
        save.setFocusPainted(false);

        rect.setBackground(Color.white);
        circle.setBackground(Color.white);
        lne.setBackground(Color.white);
        clearAll.setBackground(Color.white);
        rmv.setBackground(Color.white);
        save.setBackground(Color.white);

        JButton visible = new JButton();
        visible.setVisible(false);
        JButton visible1 = new JButton();
        visible1.setVisible(false);
        JButton visible2 = new JButton();
        visible2.setVisible(false);
        JButton visible3 = new JButton();
        visible3.setVisible(false);

        JButton visible4 = new JButton();
        visible4.setVisible(false);
        JButton visible5 = new JButton();
        visible5.setVisible(false);
        JButton visible6 = new JButton();
        visible6.setVisible(false);
        JButton visible7 = new JButton();
        visible7.setVisible(false);

        topPanel.setLayout(new GridLayout(1, 5));

        topPanel.add(save);
        topPanel.add(rmv);
        topPanel.add(clearAll);
        topPanel.add(visible1);
        topPanel.add(visible2);
        topPanel.add(visible3);
        topPanel.add(visible5);
        topPanel.add(rect);
        topPanel.add(circle);
        topPanel.add(lne);
        topPanel.add(visible6);
        topPanel.add(visible7);
    }

    public void SideRec() {
        if (currentSide != SideRec) {
            remove(currentSide);
            add(SideRec, BorderLayout.WEST);
            currentSide = SideRec;
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    public void SideCir() {
        if (currentSide != SideCir) {
            remove(currentSide);
            add(SideCir, BorderLayout.WEST);
            currentSide = SideCir;
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    public void SideLine() {
        if (currentSide != SideLine) {
            remove(currentSide);
            add(SideLine, BorderLayout.WEST);
            currentSide = SideLine;
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    public void recButtons() {

        SpinnerModel value1 = new SpinnerNumberModel(0, 0, 800, 50);
        SpinnerModel value2 = new SpinnerNumberModel(0, 0, 800, 50);

        JLabel wid = new JLabel("Width: ");
        wid.setFont(new Font("Segoe UI", 100, 15));
        SideRec.add(wid);
        JSpinner w = new JSpinner(value1);

        w.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                recW = (int) ((JSpinner) ce.getSource()).getValue();
            }
        });
        SideRec.add(w);

        SideRec.add(new JLabel("        "));

        JLabel hei = new JLabel("Height: ");
        hei.setFont(new Font("Segoe UI", 100, 15));
        SideRec.add(hei);
        JSpinner h = new JSpinner(value2);
        h.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                recH = (int) ((JSpinner) ce.getSource()).getValue();
            }
        });
        SideRec.add(h);

        SideRec.add(new JLabel("        "));

        setRecCol = new JButton("Choose Colour");
        setRecCol.setFocusPainted(false);
        setRecCol.setBackground(Color.WHITE);
        SideRec.add(setRecCol);

        //ISFileed
        ButtonGroup g1 = new ButtonGroup();
        SideRec.add(new JLabel("      "));

        emptyRec = new JRadioButton("No fill");
        fullRec = new JRadioButton("Fill");
        SideRec.add(emptyRec);
        SideRec.add(fullRec);

        g1.add(emptyRec);
        g1.add(fullRec);

        SideRec.add(new JLabel(" \n              "));

        DrawRec = new JButton("Insert Rectangle");
        DrawRec.setFocusPainted(false);
        DrawRec.setBackground(Color.WHITE);
        SideRec.add(DrawRec);
        //IsFilled  

        SwingUtilities.updateComponentTreeUI(this);
    }


    public void circButtons() {
        SpinnerModel value3 = new SpinnerNumberModel(0, 0, 800, 50);
        SpinnerModel value4 = new SpinnerNumberModel(0, 0, 800, 50);

        JLabel wid = new JLabel("Radius Horizontal: ");
        wid.setFont(new Font("Segoe UI", 100, 15));
        SideCir.add(wid);
        JSpinner w2 = new JSpinner(value3);

        w2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                cirW = (int) ((JSpinner) ce.getSource()).getValue();
            }
        });
        SideCir.add(w2);

        SideCir.add(new JLabel("        "));

        JLabel hei = new JLabel("Radius Vertical: ");
        hei.setFont(new Font("Segoe UI", 100, 15));
        SideCir.add(hei);
        JSpinner h2 = new JSpinner(value4);
        h2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                cirH = (int) ((JSpinner) ce.getSource()).getValue();
            }
        });
        SideCir.add(h2);

        SideCir.add(new JLabel("        "));

        setCirCol = new JButton("Choose Colour");
        setCirCol.setFocusPainted(false);
        setCirCol.setBackground(Color.WHITE);
        SideCir.add(setCirCol);

        //ISFileed
        ButtonGroup g1 = new ButtonGroup();
        emptyCir = new JRadioButton("No fill");
        fullCir = new JRadioButton("Fill with color");
        SideCir.add(emptyCir);
        SideCir.add(fullCir);

        g1.add(emptyCir);
        g1.add(fullCir);

        SideCir.add(new JLabel("               "));
        //IsFilled  

        DrawCir = new JButton("Insert Oval");
        DrawCir.setFocusPainted(false);
        DrawCir.setBackground(Color.WHITE);
        SideCir.add(DrawCir);

        SwingUtilities.updateComponentTreeUI(this);
    }

    public void lineButtons() {
        SpinnerModel value4 = new SpinnerNumberModel(0, 0, 5, 1);
        JLabel s = new JLabel("Width: ");
        s.setFont(new Font("Segoe UI", 100, 15));
        SideLine.add(s);

        JSpinner w5 = new JSpinner(value4);
        w5.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                lineSize = (int) ((JSpinner) ce.getSource()).getValue();
            }
        });
        SideLine.add(w5);

        SideLine.add(new JLabel("                       "));

        setLineCol = new JButton("Colour");

        setLineCol.setFocusPainted(false);
        setLineCol.setBackground(Color.WHITE);
        SideLine.add(setLineCol);

        SideLine.add(new JLabel("              "));
        JLabel firstP = new JLabel("Please follow mouse pointer upto fixed position to view the object ");
        firstP.setFont(new Font("Segoe UI", 100, 20));
        SideLine.add(firstP);

    }

    public void dutyButtons() {

        //LEFT
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                takeSnapShot(center);
            }
        });

        rect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SideRec();

            }
        });

        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SideCir();
            }
        });

        lne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SideLine();
            }
        });

        rmv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (remove == true) {
                    remove = false;
                    rmv.setIcon(tic);

                } else {
                    rmv.setIcon(del);
                    remove = true;
                }

            }

        });

        clearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                DrawingArea.shapes.clear();
                center.drawing();
            }

        });
        //LEFT

        //topREC
        setRecCol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Color c = JColorChooser.showDialog(null, "Choose Color", Color.BLACK);
                if (c != null) {
                    currentCol = c;
                }
            }
        });

        DrawRec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (fullRec.isSelected()) {
                    DrawingArea.shapes.add(new Rectangle(rnd.nextInt(800), rnd.nextInt(600), recW, recH, currentCol, 1));
                    center.drawing();
                } else {
                    DrawingArea.shapes.add(new Rectangle(rnd.nextInt(800), rnd.nextInt(600), recW, recH, currentCol, 0));
                    center.drawing();
                }
            }
        });
        //SideRec

        //SideCir
        setCirCol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Color c = JColorChooser.showDialog(null, "Choose Color", Color.BLACK);
                if (c != null) {
                    currentCol = c;
                }
            }
        });

        DrawCir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (fullCir.isSelected()) {
                    DrawingArea.shapes.add(new Circle(rnd.nextInt(800), rnd.nextInt(600), cirW, cirH, currentCol, 1));
                } else {
                    DrawingArea.shapes.add(new Circle(rnd.nextInt(800), rnd.nextInt(600), cirW, cirH, currentCol, 0));
                }
                center.drawing();
            }
        });
        //SideCir

        //SideLine
        setLineCol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Color c = JColorChooser.showDialog(null, "Choose Color", Color.BLACK);
                if (c != null) {
                    lineCol = c;
                }
            }
        });
        //SideLine
    }

    void takeSnapShot(JPanel panel) {
        BufferedImage bufImage = new BufferedImage(panel.getSize().width, panel.getSize().height, BufferedImage.TYPE_INT_RGB);
        panel.paint(bufImage.createGraphics());

        // parent component of the dialog
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("File will be saved as jpeg format");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                if (!new File(fileToSave.getAbsolutePath() + ".jpg").isFile()) {
                    ImageIO.write(bufImage, "jpeg", new File((fileToSave.getAbsolutePath() + ".jpg")));
                    JOptionPane.showMessageDialog(panel, "The image has been saved successfully");
                } else {
                    String[] options = {"Yes", "No"};
                    int x = JOptionPane.showOptionDialog(null, "Do you want to overwrite " + fileToSave.getAbsolutePath() + ".jpg",
                            "Click a button",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if (x == 0) {
                        ImageIO.write(bufImage, "jpeg", new File((fileToSave.getAbsolutePath() + ".jpg")));
                        JOptionPane.showMessageDialog(panel, "The image has been saved successfully");
                    } else {
                        takeSnapShot(panel);
                    }

                }
            } catch (Exception ex) {
            }
        }
    }

    public static void main(String args[]) throws Exception{

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                DrawingEditor app = new DrawingEditor();
                
            }
        });
    }

}
