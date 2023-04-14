import java.awt.*;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

public class FontStyleView extends JFrame implements ActionListener, ListSelectionListener {
    String[] fontName = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String[] fontStyleName = { "PLAIN", "BOLD", "ITALIC" };
    String[] fontSize = { "6", "7", "8", "9", "10", "12", "14", "17", "20", "24", "30", "40" };

    JList listFontName, listFontStyle, listFontSize;
    JPanel listPanel, centerPanel, southPanel;
    JScrollPane listScrollPane;
    JLabel textLabel;
    JButton bConfirm, bCancel;

    Font newFont = null;
    JTextArea ta;

    public FontStyleView(JTextArea ta) {
        this.ta = ta;

        // 배치 관리자
        Container con = getContentPane();
        centerPanel = new JPanel(new GridLayout(2, 1));
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(0, 3));

        // 폰트 네임 부분
        listFontName = new JList(fontName);
        listFontName.addListSelectionListener(this);
        listScrollPane = new JScrollPane(listFontName);
        listScrollPane.setBorder(new TitledBorder("Font Name"));
        listPanel.add(listScrollPane);

        // 리스트는 하나만 선택가능하고, 필드의 값은 가져오면 해당 폰트 이름에 선택 되어있음.
        listFontName.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listFontName.setSelectedValue(ta.getFont().getStyle(), false);

        // 폰트 스타일 부분
        listFontStyle = new JList(fontStyleName);
        listFontStyle.addListSelectionListener(this);
        listScrollPane = new JScrollPane(listFontStyle);
        listScrollPane.setBorder(new TitledBorder("Font Style Name"));
        listPanel.add(listScrollPane);
        // 리스트는 하나만 선택가능하고, 필드의 값을 가져오면 해당 폰트 스타일 이름에 기본적으로 선택 되어있음
        listFontStyle.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listFontStyle.setSelectedIndex(ta.getFont().getStyle());

        // 폰트 사이즈 부분
        listFontSize = new JList(fontSize);
        listFontSize.addListSelectionListener(this);
        listScrollPane = new JScrollPane(listFontSize);
        listScrollPane.setBorder(new TitledBorder("Font Size"));
        listPanel.add(listScrollPane);
        // 리스트는 하나만 선택가능하고, 필드의 값을 가져오면 해당 폰트 사이즈에 기본적으로 선택 되어있음
        listFontSize.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listFontSize.setSelectedValue("" + ta.getFont().getSize(), false);
        // 폰트 예문 부분
        textLabel = new JLabel("Hello! Andromeda");
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        // 예문 폰트는 현재 메모장 필드에 세팅된 폰트값을 가져와서 세팅됨
        try {
            textLabel.setFont(new Font((String) (listFontName.getSelectedValue()), listFontStyle.getSelectedIndex(),
                    Integer.parseInt((String) (listFontSize.getSelectedValue()))));
        } catch (NullPointerException e) {

        } catch (NumberFormatException e) {

        }

        centerPanel.add(listPanel);
        centerPanel.add(textLabel);
        bConfirm = new JButton("확인");
        bCancel = new JButton("취소");
        bConfirm.addActionListener(this);
        bCancel.addActionListener(this);
        southPanel = new JPanel();
        southPanel.add(bConfirm);
        southPanel.add(bCancel);
        con.add(centerPanel, "Center");
        con.add(southPanel, "South");
        newFont = textLabel.getFont();
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        // TODO Auto-generated method stub
        try {
            textLabel.setFont(new Font((String) (listFontName.getSelectedValue()), listFontStyle.getSelectedIndex(),
                    Integer.parseInt((String) (listFontSize.getSelectedValue()))));
            newFont = textLabel.getFont();
        } catch (NullPointerException e) {
            e.getMessage();
        } catch (NumberFormatException e) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand().equals("확인"))
            ta.setFont(newFont);
        this.dispose();
    }

}