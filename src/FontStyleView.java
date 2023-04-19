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
    /*
     * GraphicsEnvironment = 시스템의 그래픽 환경을 나타내는 java AWT패키지의 클래스
     * 화면 해상도, 색상 깊이 및 글꼴 기능과 같은 사용 가능한 그래픽 장치에 대한 정보를 얻는 데 사용
     */
    /*
     * .getLocalGraphicsEnviroment() = 로컬 그래픽 환경을 반환함.
     * 로컬 그래픽에는 화면크기, 해상도, 색심도, 글꼴 기능 등 시스템에서 사용할 수 있는 디스플레이 장치에 대한 정보가 포함됨
     */
    /*
     * .getAvailableFontFamilyNames() = 로컬 그래픽 환경에서 사용 가능한 모든 글꼴 패밀리의 이름을 포함하는 문자열
     * 배열을 반환함
     */
    String[] fontStyleName = { "PLAIN", "BOLD", "ITALIC" };
    String[] fontSize = { "6", "7", "8", "9", "10", "12", "14", "17", "20", "24", "30", "40" };

    JList<String> listFontName, listFontStyle, listFontSize;
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
        listFontName = new JList<String>(fontName);
        listFontName.addListSelectionListener(this);
        listScrollPane = new JScrollPane(listFontName);
        listScrollPane.setBorder(new TitledBorder("Font Name"));
        listPanel.add(listScrollPane);

        // 리스트는 하나만 선택가능하고, 필드의 값은 가져오면 해당 폰트 이름에 선택 되어있음.
        listFontName.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listFontName.setSelectedValue(ta.getFont().getStyle(), false);
        // setSelectionMode = 목록 구성 요소의 선택모드를 설정하는 메서드
        // setSelectedValue = 목록 구성 요소애서 선택한 항목을 설정하는 메서드

        /*
         * SINGLE_INTERVAL_SELECTION = 단일 구간 선택으로, 여러 개의 '연속적인' 항목들이 선택될 수 있다.
         * SINGLE_SELECTION = 단일 선택으로, 한 번에 하나의 항목만이 선택된다.
         * MULTIPLE_INTERVAL_SELECTION = 다중 구간 선택으로, 디폴트로 항목들이 자유롭게 선택될 수 있다.
         */

        // 폰트 스타일 부분
        listFontStyle = new JList<String>(fontStyleName);
        listFontStyle.addListSelectionListener(this);
        listScrollPane = new JScrollPane(listFontStyle);
        listScrollPane.setBorder(new TitledBorder("Font Style Name"));
        listPanel.add(listScrollPane);

        // 리스트는 하나만 선택가능하고, 필드의 값을 가져오면 해당 폰트 스타일 이름에 기본적으로 선택 되어있음
        listFontStyle.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listFontStyle.setSelectedIndex(ta.getFont().getStyle());
        // setSelectedIndex = 인덱스를 기반으로 목록 구성 요소에서 선택한 항목을 설정하는 메서드

        // 폰트 사이즈 부분
        listFontSize = new JList<String>(fontSize);
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
        try {
            textLabel.setFont(new Font((String) (listFontName.getSelectedValue()), listFontStyle.getSelectedIndex(),
                    Integer.parseInt((String) (listFontSize.getSelectedValue()))));
            /*
             * 목록 구성 요소에서 선택한 값을 기반으로 새 글꼴 개체를 만듦.
             * setSeletedValue또는 setSelectedIndex 메서드를 사용하여 목록 구성 요소에서 선택한 값을 가져오고 이에 따라 텍스트
             * 레이블의 글꼴을 설정함
             */
            newFont = textLabel.getFont();
            // 위에서 만든 글꼴 개체를 newFont에 저장함
        } catch (NullPointerException e) {
            e.getMessage();
        } catch (NumberFormatException e) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("확인"))
            ta.setFont(newFont);
        // 확인 버튼을 누르면 valueChanged에서 저장한 newFont값이 ta에 입혀짐
        this.dispose();
    }

}