
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import java.awt.Point;
import java.awt.event.*;
import java.io.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;

public class MemoMain extends JFrame implements ActionListener, UndoableEditListener {
	JTextArea ta;
	JFileChooser jfc;
	boolean isNew = false;
	int re;
	boolean ck = false;
	JCheckBox cb1;
	File file;
	String data = "";
	FontStyleView fontStyleView;

	UndoManager undoManager = new UndoManager();

	public static void main(String[] args) {
		MemoMain mm = new MemoMain();
		mm.setVisible(true);
	}

	public MemoMain() {
		initUI();

	}

	private void initUI() {
		setTitle("제목 없음 - 용섭이의 메모장");
		MainView();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ta = new JTextArea();
		JScrollPane jsp = new JScrollPane(ta);
		jfc = new JFileChooser();

		ta.getDocument().addUndoableEditListener(this);

		this.add(jsp);
		setSize(500, 500);
		centerFrame();

	}

	private void centerFrame() {
		Dimension windowSize = getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		setLocation(dx, dy);

	}

	public void MainView() {
		KeyStroke key;
		JMenuBar mb = new JMenuBar();
		JMenu m1, m2, m3, m4, m5;
		JMenuItem item;

		// 메뉴 구성
		m1 = new JMenu("파일(F)");
		m1.setMnemonic(KeyEvent.VK_F);

		item = new JMenuItem("새로 만들기(N)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);

		item = new JMenuItem("열기(O)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);

		m1.addSeparator();

		item = new JMenuItem("저장(S)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);

		item = new JMenuItem("다른 이름으로 저장(A)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);

		m1.addSeparator();

		item = new JMenuItem("끝내기(X)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		/// --------------------------------------------------------///
		m2 = new JMenu("편집(E)");
		m2.setMnemonic(KeyEvent.VK_E);

		item = new JMenuItem("실행 취소(U)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					undoManager.undo();
				} catch (CannotUndoException e1) {
					System.out.println("되돌릴 값이 없습니다.");
				}

			}
		});
		m2.add(item);

		m2.addSeparator();

		item = new JMenuItem("잘라내기(X)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ta.cut();
			}
		});
		m2.add(item);

		item = new JMenuItem("복사(C)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ta.copy();

			}
		});
		m2.add(item);

		item = new JMenuItem("붙혀넣기(P)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ta.paste();
			}
		});
		m2.add(item);

		item = new JMenuItem("삭제(L)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ta.replaceSelection("");
			}
		});
		m2.add(item);

		m2.addSeparator();

		item = new JMenuItem("찾기(F)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m2.add(item);

		item = new JMenuItem("찾아 바꾸기(R)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m2.add(item);

		item = new JMenuItem("이동(G)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		m2.add(item);

		m2.addSeparator();

		item = new JMenuItem("모두 선택(A)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ta.selectAll();

			}
		});
		m2.add(item);

		item = new JMenuItem("시간/날짜(D)");
		key = KeyStroke.getKeyStroke("F5");
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Date d = new Date();
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd aa HH:mm:ss");

				ta.append(sd.format(d));

			}
		});
		m2.add(item);
		/// --------------------------------------------------------///
		m3 = new JMenu("서식(O)");
		m3.setMnemonic(KeyEvent.VK_O);

		item = new JMenuItem("배경 색 변경");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color setColor = JColorChooser.showDialog(getParent(), "색상표", Color.yellow);
				if (setColor != null) {
					ta.setBackground(setColor);
				}
			}

		});
		m3.add(item);

		item = new JMenuItem("글자 색 변경");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color setColor = JColorChooser.showDialog(getParent(), "색상표", Color.yellow);
				if (setColor != null) {
					ta.setForeground(setColor);
				}
			}

		});
		m3.add(item);

		item = new JMenuItem("글꼴(F)...");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fontStyleView = new FontStyleView(ta);
				fontStyleView.setBounds(200, 200, 400, 350);
				fontStyleView.setVisible(true);
			}

		});
		m3.add(item);

		/// --------------------------------------------------------///
		m4 = new JMenu("보기(V)");
		m4.setMnemonic(KeyEvent.VK_V);

		// item = new JMenuItem("확대하기/축소하기");
		// item.addActionListener(this);
		// m4.add(item);

		JMenu submenu = new JMenu("확대하기/축소하기");
		m4.add(submenu);
		submenu.add(new JMenuItem("확대(I)"));
		submenu.add(new JMenuItem("축소(O)"));
		submenu.add(new JMenuItem("확대하기/축소하기 기본값 복원"));

		cb1 = new JCheckBox("상태 표시줄(S)");
		m4.add(cb1);

		/// --------------------------------------------------------///
		m5 = new JMenu("도움말(H)");
		m5.setMnemonic(KeyEvent.VK_H);

		item = new JMenuItem("메모장 정보(A)");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final ImageIcon icon = new ImageIcon("src/images/umm.jpeg");
					JOptionPane.showMessageDialog(null,
							"Yongseop's Memojang.\n Windows 메모장을 클론코딩하여 제작했습니다. \n 기능은 Windows 메모장과 유사합니다. \n Version : 1.0.0 \n Application By. Yongseop Company\n\n\n\n\n ※해당 프로그램을 무단 복제/판매 시 처벌됩니다. ",
							"자기소개",
							JOptionPane.PLAIN_MESSAGE, icon);
				} catch (Exception e1) {
					e1.printStackTrace();

				}

			}
		});

		m5.add(item);

		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
		mb.add(m5);
		this.setJMenuBar(mb);

	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		if (undoManager != null)
			undoManager.addEdit(e.getEdit());
	}

	void open() {
		int re = jfc.showOpenDialog(this);
		if (re == JFileChooser.APPROVE_OPTION) {
			file = jfc.getSelectedFile();
			setTitle(file.getName() + " 용섭이의 메모장");

			int ch;
			try {
				FileReader fr = new FileReader(file);
				while ((ch = fr.read()) != -1)
					data = data + (char) ch;

				ta.setText(data);
				fr.close();

			} catch (Exception e) {
				e.getMessage();
			}
		}
	}

	void save() {
		if (ck == false) {
			re = jfc.showSaveDialog(this);
			ck = true;
		}
		if (re == JFileChooser.APPROVE_OPTION) {
			file = jfc.getSelectedFile();
			try {
				FileWriter fw = new FileWriter(file);
				fw.write(ta.getText());
				fw.close();
				JOptionPane.showMessageDialog(this, "저장 완료");
				setTitle(file.getName() + " 용섭이의 메모장");
				isNew = false;

			} catch (IOException e) {
				e.getMessage();
			}
		}
	}

	void saveAs() {
		re = jfc.showSaveDialog(this);

		if (re == JFileChooser.APPROVE_OPTION) {
			file = jfc.getSelectedFile();
			try {
				FileWriter fw = new FileWriter(file);
				fw.write(ta.getText());
				fw.close();
				JOptionPane.showMessageDialog(this, "저장 완료");
				setTitle(file.getName() + " 용섭이의 메모장");
				isNew = false;

			} catch (IOException e) {
				e.getMessage();
			}
		}
	}

	void quit() {

		System.exit(0);
	}

	int check() {
		int a = 0;
		String data = "";
		int ch;
		try {
			if (isNew == true) {
				FileReader fr = new FileReader(file);
				while ((ch = fr.read()) != -1)
					fr.close();
				if (!ta.getText().equals(data))
					a = 1;
			} else if (isNew == false && !ta.getText().equals(""))
				a = 1;
		} catch (Exception e) {
			e.getMessage();
		}
		return a;
	}

	@Override
	public void actionPerformed(ActionEvent e) { // e는 이벤트를 발생시킨녀석
		String cmd = e.getActionCommand(); // <- 이벤트를 발생시킨 녀석의 글자를 가져오는 녀석

		switch (cmd) {
			case "새로 만들기(N)" -> {
				int a = check();
				if (a == 1) {
					int b = JOptionPane.showConfirmDialog(this, "작성된 내용을 저장 하시겠습니까?", "저장여부확인", 1);
					if (b == 0)
						save();
					else if (b == 1) {
						ta.setText("");
						isNew = false;
						file = null;
					}
				}
			}
			case "열기(O)" -> {
				int a = check();
				if (a == 1) {
					int b = JOptionPane.showConfirmDialog(this, "작성된 내용을 저장 하시겠습니까?", "저장여부확인", 1);
					if (b == 0) {
						save();
					} else if (b == 1)
						open();
				} else
					open();
			}
			case "저장(S)" -> save();
			case "다른 이름으로 저장(A)" -> saveAs();
			case "끝내기(X)" -> quit();
			case "찾기(F)" -> {
				Find fi = new Find(this, ta);
				fi.showFind();
			}
			case "찾아 바꾸기(R)" -> {
				Find fi = new Find(this, ta);
				fi.showReplace();
			}
			case "이동(G)" -> {
				ta.setCaretPosition(0);
			}
		}
	}

}
