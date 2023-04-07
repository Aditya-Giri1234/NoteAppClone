package org.example;

/* **************************************************                       NOTEPAD PROJECT           **********************************************/


//-----------------------------------------------                 import all neccesily package        --------------------------




import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;







//------------------------------------------------   start of real workin on notepad      ------------------------------------

public class Notepad {

    // start of initilize all neccessily global variabl and object
    Notepad new_w;
    static int uni_caretpos;
    JFrame jf = null;
    JMenuBar menubar;
    JMenu file, edit, format, view, help;
    JMenuItem new1, new_window, open, save, save_as, print, exit;
    JMenuItem undo, redo, cut, copy, paste, delete1, search, find, find_next, find_previous, replace, goto_line,
            select_all, time_date;
    JMenu zoom;
    JMenu color;
    JMenuItem look_and_feel;
    JMenuItem text_color, background_color, caret_color, default_color;
    JMenuItem zoom_in, zoom_out, zoom_restore;
    JMenuItem font;
    JCheckBoxMenuItem word_wrap, status_bar;
    JMenuItem view_help, send_feedback, about_notepad;

    JTextArea text;
    JScrollPane sp;
    JDialog font_d = new JDialog(jf, "Font Selection");
    JDialog rep_d = new JDialog(jf, "Replace");
    JDialog goto_d = new JDialog(jf, "Go To Line");
    JDialog find_d = new JDialog(jf, "Find");
    JDialog jif_d = new JDialog(jf, "Find");
    JDialog feed_d = new JDialog(jf, " Send Feedback");
    JDialog confirm_d = new JDialog(jf, "Notepad");
    JDialog look_d = new JDialog(jf, "Look And Feel");

    UndoManager um = new UndoManager();
    JPanel jp;
    String font_family, font_style;
    int font_size;
    int font_number = 0;
    String find_support = null;
    String show_input = null;
    String filename;
    String currentFile;
    String family, style, size;
    static  String show_look="default";
    boolean dec, pass = false;
    int number = 0;
    static String ui;
    static int count; // after this only one copy made for all object
    // end of initilize all neccessily global variabl and object

    // start of makeNotepad() method

    public void makeNotepad() {
        count++; // handling new window open and exit
        dec = true;
        jf = new JFrame();
        jf.setSize(1200, 600);
        jf.setTitle("Untitled - Notepad");
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setIconImage(new ImageIcon(getClass().getResource("/Notepad.png")).getImage()); // "/" is for absolute path before image

        // work on text area
        // JPanel textarea=new JPanel();
        text = new JTextArea();
        final int value = text.getFont().getSize();
        text.getDocument().addUndoableEditListener(e -> um.addEdit(e.getEdit()));
        sp = new JScrollPane(text);
        sp.setBorder(BorderFactory.createEmptyBorder());
        jf.add(sp, BorderLayout.CENTER);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        // end of text area work

        // ----------------------------------- start of menubar work

        menubar = new JMenuBar();
        menubar.setBorder(BorderFactory.createEmptyBorder());
        file = new JMenu("File");
        edit = new JMenu("Edit");
        format = new JMenu("Format");
        view = new JMenu("View");
        help = new JMenu("Help");

        new1 = new JMenuItem("New");
        new_window = new JMenuItem("New Window");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        save_as = new JMenuItem("Save_As");
        print = new JMenuItem("Print/Page Setup");
        exit = new JMenuItem("Exit");
        file.add(new1);
        file.add(new_window);
        file.add(open);
        file.add(save);
        file.add(save_as);
        file.add(new JSeparator());
        file.add(print);
        file.add(new JSeparator());
        file.add(exit);

        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        delete1 = new JMenuItem("Delete");
        search = new JMenuItem("Search Online");
        find = new JMenuItem("Find");
        find_next = new JMenuItem("Find Next");
        find_previous = new JMenuItem("Find Previous");
        replace = new JMenuItem("Replace");
        goto_line = new JMenuItem("Go To Line");
        select_all = new JMenuItem("Select All");
        time_date = new JMenuItem("Time/Date");
        edit.add(undo);
        edit.add(redo);
        edit.add(new JSeparator());
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete1);
        edit.add(new JSeparator());
        edit.add(search);
        edit.add(find);
        edit.add(find_next);
        edit.add(find_previous);
        edit.add(replace);
        edit.add(goto_line);
        edit.add(new JSeparator());
        edit.add(select_all);
        edit.add(time_date);

        word_wrap = new JCheckBoxMenuItem("Word Wrap");
        font = new JMenuItem("Font");

        format.add(word_wrap);
        format.add(font);

        zoom = new JMenu("Zoom");
        zoom_in = new JMenuItem("Zoom In");
        zoom_out = new JMenuItem("Zoom Out");
        zoom_restore = new JMenuItem("Restore Default Zoom");
        zoom.add(zoom_in);
        zoom.add(zoom_out);
        zoom.add(zoom_restore);
        color = new JMenu("Color");
        look_and_feel = new JMenuItem("Look And Feel");
        text_color = new JMenuItem("Text Color");
        background_color = new JMenuItem("Background Color");
        caret_color = new JMenuItem("Caret Color");
        default_color = new JMenuItem("Default Color");
        color.add(text_color);
        color.add(background_color);
        color.add(caret_color);
        color.add(default_color);

        status_bar = new JCheckBoxMenuItem("Status Bar");
        view.add(zoom);
        view.add(color);
        view.add(look_and_feel);

        view.add(status_bar);

        view_help = new JMenuItem("View Help");
        send_feedback = new JMenuItem("Send Feedback");
        about_notepad = new JMenuItem("About Notepad");
        help.add(view_help);
        help.add(send_feedback);
        help.add(new JSeparator());
        help.add(about_notepad);

        menubar.add(file);
        menubar.add(edit);
        menubar.add(format);
        menubar.add(view);
        menubar.add(help);
        jf.add(menubar, BorderLayout.PAGE_START);

        // ----------------------------------- start of menubar work
        // ------------------------------------------//

        //----------------------------------- start of working of popup menu
        // ------------------------------------------//

        // ------------------------------ start of status bar work
        // ------------------------------------------//
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        jp = new JPanel();
        jp.setLayout(gbl);
        JLabel empty = new JLabel();
        JLabel l_c = new JLabel();

        JLabel z_i_o = new JLabel("100%");
        z_i_o.setFont(new Font("Arial", Font.PLAIN, 13));
        JLabel t_s = new JLabel();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx=850;
        gbc.ipady=20;
        jp.add(empty, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        jp.add(new JSeparator(JSeparator.VERTICAL), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.ipadx=100;
        gbc.ipady=20;
        l_c.setText("ln 1, col 1");
        l_c.setFont(new Font("Arial", Font.PLAIN, 13));
        jp.add(l_c, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        jp.add(new JSeparator(JSeparator.VERTICAL), gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.ipadx=100;
        gbc.ipady=20;
        jp.add(z_i_o, gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        jp.add(new JSeparator(JSeparator.VERTICAL), gbc);

        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.ipadx=150;
        gbc.ipady=20;
        Timer t = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                t_s.setText(dateFormat.format(date));
                t_s.setFont(new Font("Arial", Font.PLAIN, 13));
            }

        });
        t.start();
        jp.add(t_s, gbc);

        // ------------------------------ start of status bar work
        // ------------------------------------------//

        // ------------------------------------------- start of all menu item event
        // ---------------------------------------//

        // start of file menu event

        new1.addActionListener(e -> {
            jf.dispose();
            makeNotepad();

        });

        new_window.addActionListener(e -> {
            new_w = new Notepad();
            new_w.makeNotepad();
        });

        // start of open menu item event

        open.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            int select = jfc.showOpenDialog(null);
            if (select != JFileChooser.APPROVE_OPTION) {
                return;
            } else {
                String filename = jfc.getSelectedFile().getAbsolutePath().toString();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(filename));
                    jf.setTitle(jfc.getSelectedFile().getName());
                    text.read(reader, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // start of open menu item event

        // start of save menu item event

        save.addActionListener(e -> {
            if (dec) {
                JFileChooser jfc = new JFileChooser();
                FileNameExtensionFilter onlytext = new FileNameExtensionFilter("only textfile(.txt)", "txt");
                jfc.addChoosableFileFilter(onlytext);
                int select = jfc.showSaveDialog(null);
                if (select != JFileChooser.APPROVE_OPTION) {
                    return;
                } else {
                    String filename = jfc.getSelectedFile().getAbsolutePath();
                    String name = jfc.getSelectedFile().getName();
                    if (!filename.contains(".")) {
                        filename += ".txt";
                        name += ".txt";
                        currentFile = name;
                    }

                    if (filename.contains(".txt")) {
                        int a = name.indexOf(".txt");
                        String s = name.replaceAll(name.substring(a), "");
                        jf.setTitle(s + " - Notepad");
                    } else {
                        currentFile = jfc.getSelectedFile().getName();
                        int a = name.indexOf(".");
                        String s = name.replaceAll(name.substring(a), "");
                        jf.setTitle(s + " - Notepad");
                    }

                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                        dec = true;
                        text.write(writer);
                        jfc.setCurrentDirectory(new File("."));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                dec = false;
            } else {
                JFileChooser jfc = new JFileChooser();
                File f = jfc.getCurrentDirectory();
                String filepath = f.getAbsolutePath().toString();
                String title = jf.getTitle();
                title = title.replace("*", " ").trim();
                jf.setTitle(title);
                try {
                    BufferedWriter writer = new BufferedWriter(
                            new FileWriter(filepath + "\\" + currentFile));
                    text.write(writer);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        // start of save menu item event

        // start of save_as menu item event
        save_as.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            FileNameExtensionFilter onlytext = new FileNameExtensionFilter("only textfile(.txt)", "txt");
            jfc.addChoosableFileFilter(onlytext);
            int select = jfc.showSaveDialog(null);
            if (select != JFileChooser.APPROVE_OPTION) {
                return;
            } else {
                filename = jfc.getSelectedFile().getAbsolutePath().toString();
                if (!filename.contains("."))
                    filename += ".txt";

                if (filename.contains(".txt")) {
                    String name = jfc.getSelectedFile().getName();
                    jf.setTitle(name.replaceAll(".*", "") + " - Notepad");
                } else {
                    String name = jfc.getSelectedFile().getName();
                    int a = name.indexOf(".");
                    String s = name.replaceAll(name.substring(a), "");
                    jf.setTitle(s + " - Notepad");

                }

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                    dec = true;
                    text.write(writer);
                    jfc.setCurrentDirectory(new File("."));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // end of save_as menu item event

        print.addActionListener(e -> {
            try {
                text.print();
            } catch (PrinterException e1) {
                e1.printStackTrace();
            }

        });

        exit.addActionListener(e -> {
            jf.dispose();
        });

        // start of file menu item short cut key

        new1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        new_window.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        save_as.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));

        // end of file menu item short cut key

        // end of file menu event

        // start of edit menu event

        undo.addActionListener(e -> {
            um.undo();
        });
        redo.addActionListener(e -> {
            try {
                um.redo();
            } catch (CannotRedoException ex) {
            }
        });
        cut.addActionListener(e -> {
            text.cut();
        });
        copy.addActionListener(e -> {
            text.copy();
        });
        paste.addActionListener(e -> {
            text.paste();
        });
        delete1.addActionListener(e -> {
            text.replaceSelection(null);
        });
        search.addActionListener(e -> {
            String s = text.getSelectedText();
            String query = s.replaceAll(" ", "+");
            try {
                Desktop.getDesktop().browse(new URL("https://www.google.com/search?q=" + query).toURI());
                jf.validate();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        });
        // end of search menu event

        // start of find menu event
        find.addActionListener(e -> {
            find_d = new JDialog(jf, "Find");
            find_d.setSize(400, 150);
            find_d.setLocationRelativeTo(null);
            find_d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            find_d.setResizable(false);
            find_d.setLayout(null);
            JLabel f_w = new JLabel("Find What:");
            JLabel dir = new JLabel("Direction");
            JTextField v_f_w = new JTextField();
            v_f_w.setText(text.getSelectedText());
            find_support = v_f_w.getText();
            JButton f_n = new JButton("Find Next");
            JButton cancel = new JButton("Cancel");
            JRadioButton up = new JRadioButton("Up");
            JRadioButton down = new JRadioButton("Down");
            ButtonGroup direction = new ButtonGroup();
            direction.add(up);
            direction.add(down);
            JCheckBox match = new JCheckBox("Match Case");
            f_w.setBounds(10, 10, 60, 20);
            v_f_w.setBounds(80, 10, 180, 20);
            f_n.setBounds(270, 10, 100, 20);
            cancel.setBounds(270, 35, 100, 20);
            dir.setBounds(150, 35, 70, 20);
            up.setBounds(150, 60, 50, 20);
            down.setBounds(200, 60, 60, 20);
            match.setBounds(10, 80, 100, 20);

            down.setSelected(true);

            find_d.add(f_w);
            find_d.add(v_f_w);
            find_d.add(f_n);
            find_d.add(cancel);
            find_d.add(dir);
            find_d.add(up);
            find_d.add(down);
            find_d.add(match);

            find_d.setVisible(true);

            f_n.addActionListener(ex -> { // start of f_n event
                int caretpos = text.getCaretPosition();
                String search = v_f_w.getText();
                String search_place = text.getText();
                String sub_serach;
                int start_index;
                int last_index;
                int search_num = (search_place.length() - search.length());
                Boolean dec1 = true;

                if (down.isSelected()) {
                    if (match.isSelected()) {
                        while (caretpos <= search_num) {
                            start_index = text.getCaretPosition();
                            last_index = start_index + search.length();
                            // System.out.println(start_index+" to "+last_index);
                            sub_serach = search_place.substring(start_index, last_index);
                            // System.out.println("before checking "+search+" and "+sub_serach);
                            if (search.equals(sub_serach)) {
                                // System.out.println("and after checking "+search+" and "+sub_serach);
                                dec1 = false;
                                text.requestFocus();
                                text.select(start_index, last_index);
                                break;
                            } else {
                                // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                                text.setCaretPosition(text.getCaretPosition() + 1);
                                caretpos = text.getCaretPosition();
                                // System.out.println(" and after upadteting caretpostion
                                // "+text.getCaretPosition());
                            }
                        }
                        if (dec1) {
                            JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        while (caretpos <= search_num) {
                            start_index = text.getCaretPosition();
                            last_index = start_index + search.length();
                            // System.out.println(start_index+" to "+last_index);
                            sub_serach = search_place.substring(start_index, last_index);
                            // System.out.println("before checking "+search+" and "+sub_serach);
                            if (search.equalsIgnoreCase(sub_serach)) {
                                // System.out.println("and after checking "+search+" and "+sub_serach);
                                dec1 = false;
                                text.requestFocus();
                                text.select(start_index, last_index);
                                break;
                            } else {
                                // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                                text.setCaretPosition(text.getCaretPosition() + 1);
                                caretpos = text.getCaretPosition();
                                // System.out.println(" and after upadteting caretpostion
                                // "+text.getCaretPosition());
                            }
                        }
                        if (dec1) {
                            JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
                if (up.isSelected()) {
                    text.setCaretPosition(text.getCaretPosition() - 1);
                    caretpos = text.getCaretPosition();
                    if (match.isSelected()) {
                        while (caretpos >= search.length()) {
                            last_index = text.getCaretPosition();
                            start_index = last_index - search.length();
                            // System.out.println(start_index+" to "+last_index);
                            sub_serach = search_place.substring(start_index, last_index);
                            // System.out.println("before checking "+search+" and "+sub_serach);
                            if (search.equals(sub_serach)) {
                                // System.out.println("and after checking "+search+" and "+sub_serach);
                                text.setCaretPosition(last_index);
                                dec1 = false;
                                text.requestFocus();
                                text.select(start_index, last_index);
                                break;
                            } else {
                                // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                                text.setCaretPosition(text.getCaretPosition() - 1);
                                caretpos = text.getCaretPosition();
                                // System.out.println(" and after upadteting caretpostion
                                // "+text.getCaretPosition());
                            }
                        }
                        if (dec1) {
                            JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } // end of if
                    else {
                        while (caretpos != 0 || text.getCaretPosition() < 0) {
                            last_index = text.getCaretPosition();
                            start_index = last_index - search.length();
                            // System.out.println(start_index+" to "+last_index);
                            sub_serach = search_place.substring(start_index, last_index);
                            // System.out.println("before checking "+search+" and "+sub_serach);
                            if (search.equalsIgnoreCase(sub_serach)) {
                                // System.out.println("and after checking "+search+" and "+sub_serach);
                                dec1 = false;
                                text.setCaretPosition(last_index);
                                text.requestFocus();
                                text.select(start_index, last_index);
                                break;
                            } else {
                                // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                                text.setCaretPosition(text.getCaretPosition() - 1);
                                caretpos = text.getCaretPosition();
                                // System.out.println(" and after upadteting caretpostion
                                // "+text.getCaretPosition());
                            }
                        }
                        if (dec1) {
                            JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            });// end of f_n
            cancel.addActionListener(ex -> {
                find_d.dispose();
            }); // end of cancel
            v_f_w.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void removeUpdate(DocumentEvent e) {
                    boolean value = !v_f_w.getText().trim().isEmpty();
                    f_n.setEnabled(value);
                    find_support = v_f_w.getText();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    boolean value = !v_f_w.getText().trim().isEmpty();
                    f_n.setEnabled(value);
                    find_support = v_f_w.getText();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    boolean value = !v_f_w.getText().trim().isEmpty();
                    f_n.setEnabled(value);
                    find_support = v_f_w.getText();
                }
            }); // end of v_f_w doucument event
        });

        // end of find event

        // start of find next event
        find_next.addActionListener(e -> {
            int caretpos = text.getCaretPosition();
            String search = find_support;
            String search_place = text.getText();
            String sub_serach;
            int start_index;
            int last_index;
            int search_num = 0;
            int s = 1;
            Boolean dec1 = true;

            try {
                search_num = (search_place.length() - search.length());
            } catch (NullPointerException ex) {
                s = 0;
            }
            if (s != 0) {

                while (caretpos <= search_num) {
                    start_index = text.getCaretPosition();
                    last_index = start_index + search.length();
                    // System.out.println(start_index+" to "+last_index);
                    sub_serach = search_place.substring(start_index, last_index);
                    // System.out.println("before checking "+search+" and "+sub_serach);
                    if (search.equals(sub_serach)) {
                        // System.out.println("and after checking "+search+" and "+sub_serach);
                        dec1 = false;
                        text.requestFocus();
                        text.select(start_index, last_index);
                        break;
                    } else {
                        // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                        text.setCaretPosition(text.getCaretPosition() + 1);
                        caretpos = text.getCaretPosition();
                        // System.out.println(" and after upadteting
                        // caretpostion"+text.getCaretPosition());
                    }
                }
                if (dec1) {
                    JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // end of find next event

        // start of find previous event
        find_previous.addActionListener(e -> {
            int caretpos;
            String search = find_support;
            String search_place = text.getText();
            String sub_serach;
            int start_index;
            int last_index;
            int search_num = 0;
            Boolean dec1 = true;
            int s = 1;

            try {
                search_num = (search_place.length() - search.length());
            } catch (NullPointerException ex) {
                s = 0;
            }
            if (search_num != 0) {
            } // for avoiding last one warbing,  this statement have zero value
            if (s != 0) {
                text.setCaretPosition(text.getCaretPosition() - 1);
                caretpos = text.getCaretPosition();
                while (caretpos >= search.length()) {
                    last_index = text.getCaretPosition();
                    start_index = last_index - search.length();
                    // System.out.println(start_index+" to "+last_index);
                    sub_serach = search_place.substring(start_index, last_index);
                    // System.out.println("before checking "+search+" and "+sub_serach);
                    if (search.equals(sub_serach)) {
                        // System.out.println("and after checking "+search+" and "+sub_serach);
                        text.setCaretPosition(last_index);
                        dec1 = false;
                        text.requestFocus();
                        text.select(start_index, last_index);
                        break;
                    } else {
                        // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                        text.setCaretPosition(text.getCaretPosition() - 1);
                        caretpos = text.getCaretPosition();
                        // System.out.println(" and after upadteting caretpostion
                        // "+text.getCaretPosition());
                    }
                }
                if (dec1) {
                    JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        // end of find previous event

        // start of replace event
        replace.addActionListener(e -> {

            uni_caretpos = 0;
            rep_d = new JDialog(jf, "Replace");
            rep_d.setSize(400, 200);
            rep_d.setLocationRelativeTo(null);
            rep_d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            rep_d.setResizable(false);
            rep_d.setLayout(null);
            JLabel f_w = new JLabel("Find What:");
            JLabel r_w = new JLabel("Replace With:");
            JTextField v_f_w = new JTextField();
            v_f_w.setFont(new Font("Times New Roman ", Font.ITALIC, 15));
            v_f_w.setText(text.getSelectedText());
            JTextField v_r_w = new JTextField();
            v_r_w.setFont(new Font("Times New Roman ", Font.ITALIC, 15));
            JButton f_n = new JButton("Find Next");
            JButton rep = new JButton("Replace");
            JButton r_a = new JButton("Replace All");
            JButton cancel = new JButton("Cancel");
            JCheckBox match = new JCheckBox("Match Case");
            f_w.setBounds(10, 10, 70, 30);
            v_f_w.setBounds(90, 10, 180, 30);
            f_n.setBounds(290, 10, 90, 30);
            r_w.setBounds(10, 45, 90, 30);
            v_r_w.setBounds(90, 45, 180, 30);
            rep.setBounds(290, 45, 90, 30);
            r_a.setBounds(290, 80, 90, 30);
            cancel.setBounds(290, 115, 90, 30);
            match.setBounds(10, 100, 100, 30);

            rep_d.add(f_w);
            rep_d.add(v_f_w);
            rep_d.add(f_n);
            rep_d.add(r_w);
            rep_d.add(v_r_w);
            rep_d.add(rep);
            rep_d.add(r_a);
            rep_d.add(cancel);
            rep_d.add(match);
            jf.validate();
            rep_d.setVisible(true);

            f_n.addActionListener(ex -> { // start of f_n event
                int caretpos = text.getCaretPosition();
                String search = v_f_w.getText();
                String search_place = text.getText();
                String sub_serach;
                int start_index;
                int last_index;
                int search_num = (search_place.length() - search.length());
                Boolean dec1 = true;
                if (match.isSelected()) {
                    while (caretpos <= search_num) {
                        start_index = text.getCaretPosition();
                        last_index = start_index + search.length();
                        // System.out.println(start_index+" to "+last_index);
                        sub_serach = search_place.substring(start_index, last_index);
                        // System.out.println("before checking "+search+" and "+sub_serach);
                        if (search.equals(sub_serach)) {
                            // System.out.println("and after checking "+search+" and "+sub_serach);
                            dec1 = false;
                            text.requestFocus();
                            text.select(start_index, last_index);
                            break;
                        } else {
                            // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                            text.setCaretPosition(text.getCaretPosition() + 1);
                            caretpos = text.getCaretPosition();
                            // System.out.println(" and after upadteting caretpostion
                            // "+text.getCaretPosition());
                        }
                    }
                    if (dec1) {
                        JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    while (caretpos <= search_num) {
                        start_index = text.getCaretPosition();
                        last_index = start_index + search.length();
                        // System.out.println(start_index+" to "+last_index);
                        sub_serach = search_place.substring(start_index, last_index);
                        // System.out.println("before checking "+search+" and "+sub_serach);
                        if (search.equalsIgnoreCase(sub_serach)) {
                            // System.out.println("and after checking "+search+" and "+sub_serach);
                            dec1 = false;
                            text.requestFocus();
                            text.select(start_index, last_index);
                            break;
                        } else {
                            // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                            text.setCaretPosition(text.getCaretPosition() + 1);
                            caretpos = text.getCaretPosition();
                            // System.out.println(" and after upadteting caretpostion
                            // "+text.getCaretPosition());
                        }
                    }
                    if (dec1) {
                        JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            });//// end of f_n event

            rep.addActionListener(ex -> { // start of rep event
                int caretpos = text.getCaretPosition();
                String search = v_f_w.getText();
                String search_place = text.getText();
                String sub_serach;
                int start_index;
                int last_index;
                int search_num = (search_place.length() - search.length());
                Boolean dec1 = true;
                if (match.isSelected()) {
                    if (text.getSelectedText() != null) {
                        text.replaceSelection(v_r_w.getText());
                    } else {
                        while (caretpos <= search_num) {
                            start_index = text.getCaretPosition();
                            last_index = start_index + search.length();
                            // System.out.println(start_index+" to "+last_index);
                            sub_serach = search_place.substring(start_index, last_index);
                            // System.out.println("before checking "+search+" and "+sub_serach);
                            if (search.equals(sub_serach)) {
                                // System.out.println("and after checking "+search+" and "+sub_serach);
                                dec1 = false;
                                text.requestFocus();
                                text.select(start_index, last_index);
                                break;
                            } else {
                                // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                                text.setCaretPosition(text.getCaretPosition() + 1);
                                caretpos = text.getCaretPosition();
                                // System.out.println(" and after upadteting caretpostion
                                // "+text.getCaretPosition());
                            }
                        }
                        if (dec1) {
                            JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    if (text.getSelectedText() != null) {
                        text.replaceSelection(v_r_w.getText());
                    } else {
                        while (caretpos <= search_num) {
                            start_index = text.getCaretPosition();
                            last_index = start_index + search.length();
                            // System.out.println(start_index+" to "+last_index);
                            sub_serach = search_place.substring(start_index, last_index);
                            // System.out.println("before checking "+search+" and "+sub_serach);
                            if (search.equalsIgnoreCase(sub_serach)) {
                                // System.out.println("and after checking "+search+" and "+sub_serach);
                                dec1 = false;
                                text.requestFocus();
                                text.select(start_index, last_index);
                                break;
                            } else {
                                // System.out.print("before upadteting caretpostion "+text.getCaretPosition());
                                text.setCaretPosition(text.getCaretPosition() + 1);
                                caretpos = text.getCaretPosition();
                                // System.out.println(" and after upadteting caretpostion
                                // "+text.getCaretPosition());
                            }
                        }
                        if (dec1) {
                            JOptionPane.showMessageDialog(rep_d, "Cannot not find " + search, "Notepad",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                }
            }); // end of replace button event

            r_a.addActionListener(ex -> {
                String sup = text.getText();
                sup = sup.replaceAll(v_f_w.getText(), v_r_w.getText());
                text.setText(sup);
            });// end of r_a
            cancel.addActionListener(ex -> {
                rep_d.dispose();
            }); // end of cancel

            v_f_w.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void removeUpdate(DocumentEvent e) {
                    boolean value = !v_f_w.getText().trim().isEmpty();
                    f_n.setEnabled(value);
                    rep.setEnabled(value);
                    r_a.setEnabled(value);
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    boolean value = !v_f_w.getText().trim().isEmpty();
                    f_n.setEnabled(value);
                    rep.setEnabled(value);
                    r_a.setEnabled(value);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    boolean value = !v_f_w.getText().trim().isEmpty();
                    f_n.setEnabled(value);
                    rep.setEnabled(value);
                    r_a.setEnabled(value);
                }
            }); // v_f_w event
        }); // end of rep event

        // end of replace window event

        // start of goto line menu item event

        goto_line.addActionListener(e -> {
            goto_d = new JDialog(jf, "Go TO Line");
            goto_d.setSize(250, 150);
            goto_d.setLocationRelativeTo(null);
            goto_d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            goto_d.setResizable(false);
            goto_d.setLayout(null);
            JLabel title = new JLabel("Line Number:");
            title.setBounds(10, 20, 100, 20);
            JTextField title_value = new JTextField();
            title_value.setBounds(10, 50, 220, 20);
            title_value.setText("1");
            JButton ok = new JButton("Ok");
            ok.setBounds(50, 80, 80, 20);
            JButton cancel = new JButton("Cancel");
            cancel.setBounds(150, 80, 80, 20);
            goto_d.add(title);
            goto_d.add(title_value);
            goto_d.add(ok);
            goto_d.add(cancel);
            ok.addActionListener(e1 -> {
                int index = text.getLineCount();
                boolean dec = false;

                try {
                    int line_go = Integer.valueOf(title_value.getText());
                    if (line_go == 1) {
                        dec = true;
                    }
                    if (line_go <= index) {
                        text.setCaretPosition(
                                text.getDocument().getDefaultRootElement().getElement(line_go).getStartOffset());
                        text.requestFocusInWindow();
                        goto_d.dispose();
                    } else {
                        JOptionPane.showMessageDialog(goto_d, "The line number is beyond the total line number ",
                                "Notepad - Go TO Line", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (Exception ex1) {
                    if (!dec) {
                        JOptionPane.showMessageDialog(goto_d, "Please enter valid Integer Number", "error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        text.setCaretPosition(
                                text.getDocument().getDefaultRootElement().getElement(0).getStartOffset());
                        text.requestFocusInWindow();
                        goto_d.dispose();
                    }
                }
                jf.setEnabled(true);

            });

            cancel.addActionListener(ex -> {
                goto_d.dispose();
            });

            goto_d.setModal(true);
            goto_d.setVisible(true);
        });

        // end of goto line menu item event

        // start of select all menu item event

        select_all.addActionListener(e -> {
            text.selectAll();
        });
        // end of select all menu item event

        // start of time date menu item event

        time_date.addActionListener(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd:mm:yy");
            LocalDateTime dtf = LocalDateTime.now();
            String s = formatter.format(dtf).toString();
            text.append(s);
        });
        // end of time date menu item event

        // start of edit menu item short key
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        delete1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
        find_next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        find_previous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, KeyEvent.SHIFT_DOWN_MASK));
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
        goto_line.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
        select_all.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        time_date.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

        // end of edit menu item short key

        // end of edit menu event

        // start of format menu event

        // start of word wrap event

        word_wrap.addItemListener(e -> {
            if (word_wrap.isSelected()) {
                sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                text.setLineWrap(true);
                text.setWrapStyleWord(true);
                goto_line.setEnabled(false);
            } else {
                sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                text.setLineWrap(false);
                text.setWrapStyleWord(false);
                goto_line.setEnabled(true);
            }
        });

        // end of word wrap event


        // start of font event

        font.addActionListener(e -> {

            font_d = new JDialog(jf, "Font Selection");
            font_d.setSize(600, 500);
            font_d.setLocationRelativeTo(null);
            font_d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            font_d.setResizable(false);
            font_d.setLayout(null);
            JLabel f_f = new JLabel("Font-Family:");
            JLabel f_s = new JLabel("Font-Style:");
            JLabel f_si = new JLabel("Font-Size:");
            JLabel show = new JLabel(" AaBbYyZz",JLabel.CENTER);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fonts = ge.getAvailableFontFamilyNames();
            String s_v[] = {"Regular", "Italic", "Bold", "Italic Bold"};
            String si_v[] = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48",
                    "72"};
            JComboBox<String> v_f_f = new JComboBox<>(fonts);
            v_f_f.setMaximumRowCount(7);
            v_f_f.setEditable(true);
            JComboBox<String> v_f_s = new JComboBox<>(s_v);
            v_f_s.setEditable(true);
            JComboBox<String> v_f_si = new JComboBox<>(si_v);
            v_f_si.setEditable(true);
            JButton ok = new JButton("OK");
            JButton cancel = new JButton("Cancel");
            f_f.setBounds(10, 20, 100, 20);
            v_f_f.setBounds(10, 45, 150, 20);
            f_s.setBounds(200, 20, 100, 20);
            v_f_s.setBounds(200, 45, 150, 20);
            f_si.setBounds(400, 20, 100, 20);
            v_f_si.setBounds(400, 45, 150, 20);
            show.setBounds(50, 80, 500, 280);
            ok.setBounds(180, 400, 100, 40);
            cancel.setBounds(300, 400, 100, 40);
            v_f_s.setSelectedItem("Regular");
            show.setBorder(BorderFactory.createDashedBorder(Color.BLACK));


            if (pass) {
                v_f_f.setSelectedItem(family);
                v_f_s.setSelectedItem(style);
                v_f_si.setSelectedItem(size);
            }

            // int size=Integer.valueOf(v_f_si.getSelectedItem().toString());

            show.setFont(new Font(v_f_f.getSelectedItem().toString(), font_number,
                    Integer.valueOf(v_f_si.getSelectedItem().toString())));

            font_d.add(f_f);
            font_d.add(v_f_f);
            font_d.add(f_s);
            font_d.add(v_f_s);
            font_d.add(f_si);
            font_d.add(v_f_si);
            font_d.add(show);
            font_d.add(ok);
            font_d.add(cancel);

            v_f_f.addItemListener(ex -> {
                switch (v_f_s.getSelectedItem().toString()) {
                    case "Bold" -> font_number = Font.BOLD;
                    case "Regular" -> font_number = Font.PLAIN;
                    case "Italic" -> font_number = Font.ITALIC;
                    case "Italic Bold" -> font_number = Font.BOLD | Font.ITALIC;
                }
                show.setFont(new Font(v_f_f.getSelectedItem().toString(), font_number,
                        Integer.valueOf(v_f_si.getSelectedItem().toString())));
            });// end of v_f_f


            v_f_s.addItemListener(ex -> { // start of v_f_s
                switch (v_f_s.getSelectedItem().toString()) {
                    case "Bold" -> font_number = Font.BOLD;
                    case "Regular" -> font_number = Font.PLAIN;
                    case "Italic" -> font_number = Font.ITALIC;
                    case "Italic Bold" -> font_number = Font.BOLD | Font.ITALIC;
                }
                show.setFont(new Font(v_f_f.getSelectedItem().toString(), font_number,
                        Integer.valueOf(v_f_si.getSelectedItem().toString())));
            });// end of v_f_s

            v_f_si.addItemListener(ex -> { // start of v_f_si
                switch (v_f_s.getSelectedItem().toString()) {
                    case "Bold" -> font_number = Font.BOLD;
                    case "Regular" -> font_number = Font.PLAIN;
                    case "Italic" -> font_number = Font.ITALIC;
                    case "Italic Bold" -> font_number = Font.BOLD | Font.ITALIC;
                }
                show.setFont(new Font(v_f_f.getSelectedItem().toString(), font_number,
                        Integer.valueOf(v_f_si.getSelectedItem().toString())));
            }); // end of v_f_si


            // start of ok and cancel event

            ok.addActionListener(ex -> { // start of ok event
                switch (v_f_s.getSelectedItem().toString()) {
                    case "Bold" -> {
                        font_number = Font.BOLD;
                        style = "Bold";
                    }
                    case "Regular" -> {
                        font_number = Font.PLAIN;
                        style = "Regular";
                    }
                    case "Italic" -> {
                        font_number = Font.ITALIC;
                        style = "Italic";
                    }
                    case "Italic Bold" -> {
                        font_number = Font.BOLD | Font.ITALIC;
                        style = "Italic Bold";
                    }
                }

                text.setFont(new Font(v_f_f.getSelectedItem().toString(), font_number, Integer.valueOf(v_f_si.getSelectedItem().toString())));
                family = v_f_f.getSelectedItem().toString();
                size = (String) v_f_si.getSelectedItem();
                pass = true;

                font_d.dispose();
            }); // end of ok event

            cancel.addActionListener(ex -> { // start of cancel event
                font_d.dispose();
            });// end of cancel

            font_d.setModal(true);
            font_d.setVisible(true);
        });

        // end of font_d

        // end of format menu event

        // start of view menu event

        zoom_in.addActionListener(e -> {
            text.setFont(new Font(text.getFont().getFontName(), text.getFont().getStyle(), text.getFont().getSize() + 2));
            z_i_o.setText(String.valueOf(Integer.valueOf(z_i_o.getText().replace("%", "")) + 10));
            z_i_o.setText(z_i_o.getText() + "%");
        });
        zoom_out.addActionListener(e -> {
            text.setFont(new Font(text.getFont().getFontName(), text.getFont().getStyle(), text.getFont().getSize() - 2));
            z_i_o.setText(String.valueOf(Integer.valueOf(z_i_o.getText().replace("%", "")) - 10));
            z_i_o.setText(z_i_o.getText() + "%");
        });
        zoom_restore.addActionListener(e -> {
            text.setFont(new Font(text.getFont().getFontName(), text.getFont().getStyle(), value));
            z_i_o.setText("100%");
        });

        // start of color menu event

        text_color.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Select a Text Color", Color.BLACK);
            text.setForeground(c);
        });
        background_color.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Select a Background Color", Color.BLACK);
            if (c == Color.BLACK) {
                text.setCaretColor(Color.WHITE);
            } else {
                text.setCaretColor(Color.BLACK);
            }
            text.setBackground(c);
        });
        caret_color.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Select a Caret Color", Color.BLACK);
            text.setCaretColor(c);
        });
        default_color.addActionListener(e -> {
            text.setBackground(Color.white);
            text.setForeground(Color.black);
            text.setCaretColor(Color.black);
        });
        // end of color menu event

        // look and feel start

        look_and_feel.addActionListener(e -> {
            look_d = new JDialog(jf, "Look And Feel");
            look_d.setSize(400, 300);
            look_d.setLocationRelativeTo(null);
            look_d.setResizable(false);
            look_d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            look_d.setModal(true);
            JLabel show=new JLabel("Selected Theme :->"+show_look,JLabel.CENTER);

            String containt[] = {"Metal", "Nimbus", "Motif", "Windows", "WindoesClassic", "Texture", "Smart", "Noire", "Acry", "Aero", "Aluminium", "Bernstein", "Fast", "Graphite", "Hifi", "Luna", "McWin", "Mint"};

            JList<String[]> list = new JList(containt);
            JScrollPane scroll = new JScrollPane();
            scroll.setViewportView(list);
            list.setLayoutOrientation(JList.VERTICAL);

            JButton apply = new JButton("Apply");
            JButton cancel = new JButton("Cancel");
            JPanel panel = new JPanel();


            panel.add(apply);
            panel.add(cancel);
            look_d.add(show,BorderLayout.PAGE_START);
            look_d.add(panel, BorderLayout.PAGE_END);
            look_d.add(scroll);


            apply.addActionListener(ex -> {
                switch (list.getSelectedIndex()) {
                    case 0: {
                        show_look="metal";
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }

                    case 1: {
                        show_look="Nimbus";
                        try {
                            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 2: {
                        show_look="Motif";
                        try {
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 3: {
                        show_look="Windows";
                        try {
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 4: {
                        show_look="WindowsClassic";
                        try {
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                            ui="com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 5: {
                        show_look="Texture";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 6: {
                        show_look="Smart";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 7: {
                        show_look="Noire";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 8: {
                        show_look="Acryl";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 9: {
                        show_look="Aero";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 10: {
                        show_look="Aluminium";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 11: {
                        show_look="Bernstein";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 12: {
                        show_look="Fast";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 13: {
                        show_look="Graphite";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 14: {
                        show_look="HiFi";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 15: {
                        show_look="Luna";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 16: {
                        show_look="McWin";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }
                    case 17: {
                        show_look="Mint";
                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        break;
                    }

                    default: {break;}

                }//end of switch
                Notepad launch=new Notepad();
                launch.confirm(jf,save);
                look_d.dispose();
                jf.dispose();
                launch.makeNotepad();
            });

            cancel.addActionListener(ex1 -> {
                look_d.dispose();
            });
            look_d.setVisible(true);
        });

        status_bar.addItemListener(e -> {
            if (status_bar.isSelected()) {
                jf.add(jp, BorderLayout.PAGE_END);
            } else {
                t.stop();
                jf.add(new JLabel(), BorderLayout.PAGE_END);
            }
            jf.validate(); // after this use u can refresh jf than show changes fast
        });

        zoom_in.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.CTRL_DOWN_MASK));
        zoom_out.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK));
        zoom_restore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK));

        // end of view menu event

        // start of help event

        view_help.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URL(
                        "https://www.bing.com/search?q=get+help+with+notepad+in+windows&filters=guid:%224466414-en-dia%22%20lang:%22en%22&form=T00032&ocid=HelpPane-BingIA")
                        .toURI());
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        });




        send_feedback.addActionListener(e->{
            feed_d = new JDialog(jf, "User Feedback");
            feed_d.setSize(600, 500);
            feed_d.setLocationRelativeTo(null);
            feed_d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            feed_d.setResizable(false);

            JLabel title=new JLabel();
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setVerticalAlignment(SwingConstants.CENTER);
            title.setFont(new Font("Serif",Font.BOLD,20));

            JPanel body=new JPanel();
            body.setLayout(null);

            JLabel user=new JLabel("From : ");
            JTextField user_v=new JTextField();
            JLabel password=new JLabel("Email-App_Passowrd (To) : ");
            JTextField password_v=new JTextField();


            JLabel to=new JLabel("To(Developer Email) : ");
            JTextField to_v=new JTextField("adityagiri359@gmail.com");
            to_v.setHorizontalAlignment(SwingConstants.CENTER);
            to_v.setEditable(false);
            JLabel sub=new JLabel("Subject : ");
            JTextField sub_v=new JTextField("Sending Email regarding app feedback!");
            sub_v.setHorizontalAlignment(SwingConstants.CENTER);
            sub_v.setEditable(false);
            JLabel message=new JLabel("Message : ");
            JTextArea message1=new JTextArea();
            message1.setWrapStyleWord(true);
            message1.setLineWrap(true);
            JScrollPane message_v=new JScrollPane(message1);
            message_v.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            message_v.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            user.setBounds(10,20,150,30);
            user_v.setBounds(180,20,350,30);
            password.setBounds(10,60,150,30);
            password_v.setBounds(180,60,350,30);
            to.setBounds(10,100,150,30);
            to_v.setBounds(180,100,350,30);
            sub.setBounds(10,140,150,30);
            sub_v.setBounds(180,140,350,30);
            message.setBounds(10,180,150,30);
            message_v.setBounds(180,180,350,210);

            user_v.setFont(new Font("Times New Roman",Font.BOLD,15));
            password_v.setFont(new Font("Times New Roman",Font.BOLD,15));
            to_v.setFont(new Font("Times New Roman",Font.BOLD,15));
            sub_v.setFont(new Font("Times New Roman",Font.BOLD,15));
            message1.setFont(new Font("Times New Roman",Font.BOLD,15));




            body.add(user);
            body.add(user_v);
            body.add(password);
            body.add(password_v);
            body.add(to);
            body.add(to_v);
            body.add(sub);
            body.add(sub_v);
            body.add(message);
            body.add(message_v);

            JButton send =new JButton("Send");
            JButton cancel=new JButton("cancel");
            JPanel bottom=new JPanel();
            bottom.add(send);
            bottom.add(cancel);


            feed_d.add(title,BorderLayout.PAGE_START);
            feed_d.add(body,BorderLayout.CENTER);
            feed_d.add(bottom,BorderLayout.PAGE_END);

            send.addActionListener(ex->{
                Properties properties=new Properties();
                properties.put("mail.smtp.auth",true);
                properties.put("mail.smtp.starttls.enable",true);
                properties.put("mail.smtp.port","587");
                properties.put("mail.smtp.host","smtp.gmail.com");

                Session session=Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user_v.getText().replace("@gmail.com","").toString().trim(),password_v.getText().toString().trim());
                    }
                });

                        try {
                            Message mimeMessage = new MimeMessage(session);
                            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("adityagiri359@gmail.com"));
                            mimeMessage.setFrom(new InternetAddress(user_v.getText().toString().trim()));
                            mimeMessage.setSubject(sub_v.getText().toString().trim());
                            mimeMessage.setText(message1.getText().toString().trim());
                            Transport.send(mimeMessage);


                        }
                        catch (Exception ex1){
                            ex1.printStackTrace();
                            System.out.println("Email not Send"+" , "+user_v.getText());
                        }

                    title.setText("Email send Successfuly");
                    title.setForeground(Color.GREEN);



            });
            cancel.addActionListener(ex->{
                feed_d.dispose();
            });

            feed_d.setModal(true);
            feed_d.setVisible(true);
        });



        about_notepad.addActionListener(e -> {
            jif_d = new JDialog(jf, "About Notepad");
            jif_d.setSize(700, 500);
            jif_d.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
            jif_d.setLocationRelativeTo(null);
            jif_d.setResizable(false);
            jif_d.setModal(true);
            JPanel title = new JPanel();
            BoxLayout bl1 = new BoxLayout(title, BoxLayout.Y_AXIS);
            title.setLayout(bl1);
            JPanel body = new JPanel();
            BoxLayout bl2 = new BoxLayout(body, BoxLayout.Y_AXIS);
            body.setLayout(bl2);
            JEditorPane context = new JEditorPane();
            context.setEditable(false);
            JScrollPane sp1 = new JScrollPane(context);
            JPanel end = new JPanel();

            JLabel l1 = new JLabel("GEC GANDHINAGAR", new ImageIcon(new ImageIcon(getClass().getResource("/GECG_logo.png")).getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH)),
                    JLabel.RIGHT);
            // JLabel l1=new JLabel("GEC GANDHINAGAR");
            l1.setFont(new Font("Arial", Font.BOLD, 45));

            context.setContentType("text/html");


            if(show_look.trim().equals("Noire")||show_look.trim().equals("HiFi")){
                context.setText("<html>"
                        + "<head>"
                        +"<style>"
                        +"li{color:white}"
                        +"</style>"
                        + "</head>"
                        + "<body>"
                        + "<h2 style='text-align: center; color:white;font:italic 20px Times New  Roman;'>This Notepad application  created by</h2>"
                        + "<h1 style='text-align:center;'>Giri Aditya</h1>"
                        + "<p style='margin:0px' ><h2 style='margin:0px; color:white; font:18px Bedrock;'>In this notepad application various feature supported. Like:&dArr;</h2><br><h2 style='margin:0px; color:white;font:12px Bedrock;'><b>undo,redo,zoom-in,zoom-out,open file,save file,shortcut key,time/date,print,status bar,theme change,font change,etc.</b></h2></p>"
                        + "</body>"
                        + "</html>");
            }// end of if
            else {
                context.setText("<html>"
                        + "<head>"
                        + "</head>"
                        + "<body>"
                        + "<h2 style='text-align: center; font:italic 20px Times New  Roman;'>This Notepad application  created by</h2>"
                        + "<h1 style='text-align:center;'>Giri Aditya</h1>"
                        + "<p style='margin:0px;' ><h2 style='margin:0px; font:18px Bedrock;'>In this notepad application various feature supported. Like:&dArr;</h2><br><h2 style='margin:0px;font:12px Bedrock;'><b>undo,redo,zoom-in,zoom-out,open file,save file,shortcut key,time/date,print,status bar,theme change,font change,etc.</b></h2></p>"
                        + "</body>"
                        + "</html>");
            }// end of else
            JLabel l2 = new JLabel("Notepad Project",
                    new ImageIcon(new ImageIcon(getClass().getResource("/Notepad.png")).getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH)),
                    JLabel.RIGHT);
            // JLabel l2=new JLabel("Notepad Project");
            l2.setFont(new Font("Times New Roman ", Font.BOLD, 30));
            l2.setAlignmentX(Component.CENTER_ALIGNMENT);
            body.add(l2);
            body.add(sp1, Component.LEFT_ALIGNMENT);

            JLabel l3 = new JLabel("Thank You, for using this app!");
            l3.setFont(new Font("Times New Roman", Font.BOLD, 20));
            end.add(l3);

            l1.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.add(l1);
            title.add(new JSeparator(JSeparator.HORIZONTAL));
            jif_d.add(title, BorderLayout.PAGE_START);
            jif_d.add(body, BorderLayout.CENTER);

            jif_d.add(end, BorderLayout.PAGE_END);
            jif_d.setVisible(true);
        });

        // end of help menu event


        // ------------------------------------------- end of all menu item event
        // ---------------------------------------//

        jf.setVisible(true);


        // start of jf event

        jf.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                if (jf.getTitle().startsWith("*")) {
                    confirm_d = new JDialog(jf, "Notepad");
                    confirm_d.setSize(400, 150);
                    confirm_d.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
                    confirm_d.setLocationRelativeTo(null);
                    confirm_d.setResizable(false);
                    JTextArea notice = new JTextArea("Do you want to save changes to " + jf.getTitle().replace("*", " ").trim().replaceAll(" - Notepad", "").trim() + "?");
                    notice.setFont(new Font("Arial", Font.PLAIN, 20));
                    notice.setForeground(Color.blue);
                    notice.setLineWrap(true);
                    notice.setWrapStyleWord(true);
                    notice.setEditable(false);
                    JScrollPane notice_sp = new JScrollPane(notice, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    JPanel button_panel = new JPanel();
                    JButton save_text = new JButton("Save");
                    JButton dont_save = new JButton("Don't Save");
                    JButton cancel_1 = new JButton("Cancel");


                    confirm_d.add(notice_sp);
                    button_panel.add(save_text);
                    button_panel.add(dont_save);
                    button_panel.add(cancel_1);
                    confirm_d.add(button_panel, BorderLayout.PAGE_END);

                    save_text.addActionListener(ex -> {
                        confirm_d.dispose();
                        save.doClick();
                    });
                    dont_save.addActionListener(ex -> {
                        confirm_d.dispose();
                        jf.dispose();
                    });

                    cancel_1.addActionListener(ex -> {
                        confirm_d.dispose();

                    });

                    confirm_d.setModal(true);
                    confirm_d.setVisible(true);


                }// end of confirm dialog



            }// end of windowclosing method

            @Override
            public void windowClosed(WindowEvent e) {
                count--;
                if(count==0){
                    System.exit(0);
                }
            }

            public void windowOpened(WindowEvent e) {
                undo.setEnabled(false);
                redo.setEnabled(false);
                cut.setEnabled(false);
                copy.setEnabled(false);
                delete1.setEnabled(false);
                search.setEnabled(false);
                find.setEnabled(false);
                find_next.setEnabled(false);
                find_previous.setEnabled(false);
            }// end of window opend method

            public void windowActivated(WindowEvent e) {
                // System.out.println("jframe is active");
                text.addMouseMotionListener(new MouseMotionListener() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        if (text.getSelectedText() == null) {
                            undo.setEnabled(false);
                            cut.setEnabled(false);
                            copy.setEnabled(false);
                            paste.setEnabled(false);
                            search.setEnabled(false);
                        } else {
                            undo.setEnabled(true);
                            redo.setEnabled(true);
                            cut.setEnabled(true);
                            copy.setEnabled(true);
                            paste.setEnabled(true);
                            delete1.setEnabled(true);
                            search.setEnabled(true);
                            find.setEnabled(true);
                            find_next.setEnabled(true);
                            find_previous.setEnabled(true);
                        }
                    }

                    @Override
                    public void mouseMoved(MouseEvent e) {
                    }
                }); // text mouse motion listener


                text.addKeyListener(new KeyListener() { // for status bar

                    @Override
                    public void keyTyped(KeyEvent e) {
                        try {
                            int caretpos = text.getCaretPosition();
                            int line;
                            int column;
                            line = text.getLineOfOffset(caretpos);
                            column = caretpos - text.getLineStartOffset(line);
                            line += 1;
                            column += 1;
                            l_c.setText("ln " + line + " , " + "col " + column);
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                        if (text.getSelectedText() != null) {

                            cut.setEnabled(true);
                            copy.setEnabled(true);
                            delete1.setEnabled(true);
                            search.setEnabled(true);
                            find.setEnabled(true);
                            find_next.setEnabled(true);
                            find_previous.setEnabled(true);
                        }
                        try {
                            int caretpos = text.getCaretPosition();
                            int line;
                            int column;
                            line = text.getLineOfOffset(caretpos);
                            column = caretpos - text.getLineStartOffset(line);
                            line += 1;
                            column += 1;
                            l_c.setText("ln " + line + " , " + "col " + column);
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }


                    }

                });// end of text key listener

                text.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        if (text.getText().length() != 0) {
                            undo.setEnabled(true);
                            redo.setEnabled(true);
                            find.setEnabled(true);
                            find_next.setEnabled(true);
                            find_previous.setEnabled(true);
                            if (!jf.getTitle().startsWith("*")) {
                                jf.setTitle("*" + jf.getTitle());
                            }
                        } else {

                            find.setEnabled(false);
                            find_next.setEnabled(false);
                            find_previous.setEnabled(false);


                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        if (text.getText().length() != 0) {
                            undo.setEnabled(true);
                            redo.setEnabled(true);
                            find.setEnabled(true);
                            find_next.setEnabled(true);
                            find_previous.setEnabled(true);
                            if (!jf.getTitle().startsWith("*")) {
                                jf.setTitle("*" + jf.getTitle());
                            }
                        } else {

                            find.setEnabled(false);
                            find_next.setEnabled(false);
                            find_previous.setEnabled(false);
                            jf.setTitle(jf.getTitle().replace("*", ""));
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        if (text.getText().length() != 0) {
                            undo.setEnabled(true);
                            redo.setEnabled(true);
                            find.setEnabled(true);
                            find_next.setEnabled(true);
                            find_previous.setEnabled(true);
                            if (!jf.getTitle().startsWith("*")) {
                                jf.setTitle("*" + jf.getTitle());
                            }
                        } else {

                            find.setEnabled(false);
                            find_next.setEnabled(false);
                            find_previous.setEnabled(false);

                        }
                    }


                });// end of text document listener

            }// end of window activated method

        }); // end of jf window listener

        // end of jf event
        text.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu edit_popup = new JPopupMenu();
                    int x = e.getX();
                    int y = e.getY();
                    edit_popup.add(undo);
                    edit_popup.add(redo);
                    edit_popup.add(new JSeparator());
                    edit_popup.add(cut);
                    edit_popup.add(copy);
                    edit_popup.add(paste);
                    edit_popup.add(delete1);
                    edit_popup.add(new JSeparator());
                    edit_popup.add(select_all);
                    edit_popup.add(new JSeparator());
                    edit_popup.add(search);
                    edit_popup.show(text, x, y);
                }
            }
        });
        text.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                try {
                    if (text.getSelectedText() == null) {
                        cut.setEnabled(false);
                        copy.setEnabled(false);
                        delete1.setEnabled(false);
                        search.setEnabled(false);
                    }
                } catch (NullPointerException ex) {

                }

            }

            @Override
            public void focusLost(FocusEvent e) {

            }

        });



    }


    public static void main(String[] args) {
        Notepad launch = new Notepad();
        launch.makeNotepad();
    }


    public  void confirm(JFrame jf,JMenuItem save){
        if (jf.getTitle().startsWith("*")) {
            confirm_d = new JDialog(jf, "Notepad");
            confirm_d.setSize(400, 150);
            confirm_d.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
            confirm_d.setLocationRelativeTo(null);
            confirm_d.setResizable(false);
            JTextArea notice = new JTextArea("Do you want to save changes to " + jf.getTitle().replace("*", " ").trim().replaceAll(" - Notepad", "").trim() + "?");
            notice.setFont(new Font("Arial", Font.PLAIN, 20));
            notice.setForeground(Color.blue);
            notice.setLineWrap(true);
            notice.setWrapStyleWord(true);
            notice.setEditable(false);
            JScrollPane notice_sp = new JScrollPane(notice, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            JPanel button_panel = new JPanel();
            JButton save_text = new JButton("Save");
            JButton dont_save = new JButton("Don't Save");
            JButton cancel_1 = new JButton("Cancel");


            confirm_d.add(notice_sp);
            button_panel.add(save_text);
            button_panel.add(dont_save);
            button_panel.add(cancel_1);
            confirm_d.add(button_panel, BorderLayout.PAGE_END);

            save_text.addActionListener(ex -> {
                confirm_d.dispose();
                save.doClick();
            });
            dont_save.addActionListener(ex -> {
                confirm_d.dispose();
                jf.dispose();
            });

            cancel_1.addActionListener(ex -> {
                confirm_d.dispose();
            });

            confirm_d.setModal(true);
            confirm_d.setVisible(true);


        }// end of confirm dialog

    }
}