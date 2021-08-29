import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

public class Noter extends JFrame implements ActionListener {
    JTextArea area;
    JScrollPane pane;
    String text;
    Noter(){
        setBounds(0,0,700,600);
        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        //objects inside/dropdown of File
        JMenuItem newdoc = new JMenuItem("New");
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newdoc.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0));
        exit.addActionListener(this);

        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(exit);
        //*******************end*********************

        JMenu edit = new JMenu("Edit");
        //objects inside/dropdown of Edit
        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
        selectAll.addActionListener(this);

        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectAll);
        //*******************end*********************

        JMenu help = new JMenu("Help");
        //object inside the help menu/dropdown
        JMenuItem about = new JMenuItem("About");
        help.addActionListener(this);
        help.add(about);
        //*******************end*********************

        menubar.add(file);
        menubar.add(edit);
        menubar.add(help);

        setJMenuBar(menubar);

//        TEXT AREA
        area = new JTextArea();
        area.setFont(new Font("SAN_SERIF",Font.PLAIN,20));
        area.setLineWrap((true));
        area.setWrapStyleWord(true);

        pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createEmptyBorder());
        add(pane,BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent ae){
//        command for file dropdown menu
        if(ae.getActionCommand().equals("New")){
            area.setText("");
        }

        else if(ae.getActionCommand().equals("Save")){
            JFileChooser saveAs = new JFileChooser();
            saveAs.setApproveButtonText("Save");

            int action = saveAs.showOpenDialog(this);
            if(action != JFileChooser.APPROVE_OPTION){
                return;
            }
            File fileName = new File(saveAs.getSelectedFile()+ ".txt");
            BufferedWriter outFile = null;
            try{
                outFile = new BufferedWriter(new FileWriter(fileName));
                area.write(outFile);
            }
            catch (Exception e){ }
        }

        else if(ae.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
            chooser.addChoosableFileFilter(restrict);

            int action = chooser.showOpenDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File file = chooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                area.read(reader, null);
            } catch (Exception e) { }
        }

        else if(ae.getActionCommand().equals("Exit")){
            System.exit(0);
        }
//      ***************END***************

//        command for "edit" dropdown menu
        else if(ae.getActionCommand().equals("Copy")){
            text = area.getSelectedText();
        }

        else if(ae.getActionCommand().equals("Paste")){
            area.insert(text,area.getCaretPosition());
        }

        else if(ae.getActionCommand().equals("Cut")){
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        }

        else if(ae.getActionCommand().equals("Select All")){
            area.selectAll();
        }
//        ***************END***************
    }

    public static void main(String[] args) {
        new Noter().setVisible(true);
    }
}