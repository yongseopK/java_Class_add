import java.awt.event.*;

import javax.swing.*;

public class Find extends JDialog implements ActionListener {
    private JLabel lFind = new JLabel("찾을 문자열: ");
    private JLabel lReplace = new JLabel("바꿀 문자열: ");
    private JTextField tFind = new JTextField(20);
    private JTextField tReplace = new JTextField(10);
    private JButton bFind = new JButton("찾기");
    private JButton bReplace = new JButton("바꾸기");
    private JTextArea ta;

    public Find(JFrame parent, JTextArea ta) {
        super(parent, "찾기", false);
        this.ta = ta;
        setLayout(null);
        lFind.setBounds(10, 30, 80, 30);
        lReplace.setBounds(10, 90, 80, 30);
        tFind.setBounds(90, 30, 150, 30);
        tReplace.setBounds(90, 90, 150, 30);
        bFind.setBounds(250, 30, 80, 30);
        bReplace.setBounds(250, 90, 80, 30);
        add(lFind);
        add(tFind);
        add(bFind);
        add(lReplace);
        add(tReplace);
        add(bReplace);
        setResizable(false);
        bFind.addActionListener(this);
        bReplace.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Find.this.dispose();
            }
        });

    }

    public void showFind() {
        setTitle("찾기");
        this.setLocationRelativeTo(this);
        setSize(400, 120);
        setVisible(true);
    }

    public void showReplace() {
        setTitle("찾아 바꾸기");
        this.setLocationRelativeTo(this);
        setSize(400, 200);
        setVisible(true);
    }

    private void find() {
        String text = ta.getText();
        text = text.replaceAll("\\r", "");
        String str = tFind.getText();
        int end = text.length();
        int len = str.length();
        int start = ta.getSelectionEnd();
        if (start == end)
            start = 0;
        for (; start <= end - len; start++) {
            if (text.substring(start, start + len).equals(str)) {
                ta.setText(text);
                ta.setSelectionStart(start);
                ta.setSelectionEnd(start + len);
                ta.requestFocus();
                return;
            }
        }
        ta.setSelectionStart(end);
        ta.setSelectionEnd(end);
        ta.requestFocus();
    }

    private void replace() {
        String str = tReplace.getText();
        if (ta.getSelectedText().equals(tFind.getText()))
            ta.replaceRange(str, ta.getSelectionStart(), ta.getSelectionEnd());
        else
            find();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == bFind)
            find();
        else if (e.getSource() == bReplace)
            replace();
    }
}
