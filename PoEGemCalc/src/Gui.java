
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.bind.JAXB;

public class Gui extends JFrame {

	private JPanel contentPane;
	private JComboBox cb_Gem;
	private JComboBox cb_start = new JComboBox();
	private JComboBox cb_end = new JComboBox();
	private JTextField tf_s_exp;
	private JTextField tf_min;
	private JTextField tf_h;
	private JTextField tf_s;
	private JTextField tf_e_exp;
	private JTextField tf_maps;

	private long start_time = 0;
	private long end_time = 0;
	private JTextField tf_Search;

	private List<Gem> GEMS = new ArrayList<Gem>();

	private DefaultComboBoxModel GEMMODEL_START = new DefaultComboBoxModel(new String[] {
	        "1", "2", "3", "4", "5",
	        "6", "7", "8", "9", "10",
	        "11", "12", "13", "14", "15",
	        "16", "17", "18", "19" });
	private DefaultComboBoxModel GEMMODEL_END = new DefaultComboBoxModel(new String[] {
	        "1", "2", "3", "4", "5",
	        "6", "7", "8", "9", "10",
	        "11", "12", "13", "14", "15",
	        "16", "17", "18", "19" });

	private DocumentListener SEARCHLISTENER = new DocumentListener() {
		private void filterComboBox(String text) {

			List<Gem> newmodel = new ArrayList<Gem>();
			String search = tf_Search.getText().toLowerCase();
			for (Gem g : GEMS) {
				if (g.getName().toLowerCase().contains(search)) {
					newmodel.add(g);
				}
			}

			cb_Gem.setModel(new DefaultComboBoxModel(newmodel.toArray()));
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			filterComboBox(tf_Search.getText());
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			filterComboBox(tf_Search.getText());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			filterComboBox(tf_Search.getText());
		}

	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void importGems() {
		Gems gems = new Gems();

		try {
			gems = JAXB.unmarshal(new File("gems.xml"), Gems.class);
		} catch (Exception e) {
			try {
				Exporter.main(null);
				gems = JAXB.unmarshal(new File("gems.xml"), Gems.class);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(1);
			}
		}
		GEMS = gems.getGem();

	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		importGems();
		setTitle("PoE GemCalc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAbout = new JMenu("Help");
		menuBar.add(mnAbout);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnAbout.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.35);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JPanel left = new JPanel();
		splitPane.setLeftComponent(left);
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

		JPanel panel_Gem = new JPanel();
		left.add(panel_Gem);

		JLabel label_Gem = new JLabel("Gem");
		panel_Gem.add(label_Gem);

		cb_Gem = new JComboBox();
		cb_Gem.setFont(new Font("Arial", Font.PLAIN, 9));

		cb_Gem.setModel(new DefaultComboBoxModel(GEMS.toArray()));
		panel_Gem.add(cb_Gem);

		JLabel label_Search = new JLabel("Search");
		panel_Gem.add(label_Search);

		tf_Search = new JTextField();
		tf_Search.getDocument().addDocumentListener(SEARCHLISTENER);

		panel_Gem.add(tf_Search);
		tf_Search.setColumns(10);

		JPanel panel_Start = new JPanel();
		panel_Start.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Start",
		        TitledBorder.LEADING,
		        TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_Start.setToolTipText("");
		left.add(panel_Start);

		JLabel label_s_Level = new JLabel("Level");
		panel_Start.add(label_s_Level);

		cb_start.setModel(GEMMODEL_START);
		panel_Start.add(cb_start);

		JLabel label_s_Exp = new JLabel("Exp");
		panel_Start.add(label_s_Exp);

		tf_s_exp = new JTextField();
		tf_s_exp.setText("0");
		panel_Start.add(tf_s_exp);
		tf_s_exp.setColumns(10);

		JPanel panel_Time = new JPanel();
		panel_Time.setBorder(new TitledBorder(null, "Time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		left.add(panel_Time);
		panel_Time.setLayout(new BoxLayout(panel_Time, BoxLayout.Y_AXIS));

		JPanel panel_4 = new JPanel();
		panel_Time.add(panel_4);

		JLabel label_h = new JLabel("Hours");
		panel_4.add(label_h);

		tf_h = new JTextField();
		tf_h.setText("0");
		panel_4.add(tf_h);
		tf_h.setColumns(2);

		JLabel label_min = new JLabel("Minutes");
		panel_4.add(label_min);

		tf_min = new JTextField();
		tf_min.setText("0");
		panel_4.add(tf_min);
		tf_min.setColumns(4);

		JLabel label_s = new JLabel("Seconds");
		panel_4.add(label_s);

		tf_s = new JTextField();
		tf_s.setText("1");
		panel_4.add(tf_s);
		tf_s.setColumns(4);

		JPanel panel_5 = new JPanel();
		panel_Time.add(panel_5);

		final JLabel label_start_time = new JLabel("Start");
		final JLabel label_end_time = new JLabel("End");
		panel_5.add(label_start_time);

		JButton btn_SetStart = new JButton("Set Start");
		btn_SetStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				start_time = System.currentTimeMillis();
				if (end_time < start_time) {
					end_time = start_time;
					label_end_time.setText(df.format(new Date(start_time)));
				}
				label_start_time.setText(df.format(new Date(start_time)));
			}
		});
		panel_5.add(btn_SetStart);

		JLabel label_4 = new JLabel("            ");
		panel_5.add(label_4);

		JButton btn_SetEnd = new JButton("Set End");
		btn_SetEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				end_time = System.currentTimeMillis();
				label_end_time.setText(df.format(new Date(end_time)));

				long secs = TimeUnit.MILLISECONDS.toSeconds(end_time - start_time);
				long hs = secs / 3600;
				long mins = (secs - hs * 3600) / 60;
				long sss = secs - hs * 3600 - mins * 60;

				tf_h.setText(hs + "");
				tf_min.setText(mins + "");
				tf_s.setText(sss + "");
			}
		});
		panel_5.add(btn_SetEnd);

		panel_5.add(label_end_time);

		JPanel panel_End = new JPanel();
		panel_End.setBorder(new TitledBorder(null, "End", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		left.add(panel_End);

		JLabel lblLevel = new JLabel("Level");
		panel_End.add(lblLevel);

		cb_end.setModel(GEMMODEL_END);
		panel_End.add(cb_end);

		JLabel lblExp = new JLabel("Exp");
		panel_End.add(lblExp);

		tf_e_exp = new JTextField();
		tf_e_exp.setText("0");
		tf_e_exp.setColumns(10);
		panel_End.add(tf_e_exp);

		JLabel lblNewLabel_5 = new JLabel("Maps");
		panel_End.add(lblNewLabel_5);

		tf_maps = new JTextField();
		tf_maps.setText("1");
		panel_End.add(tf_maps);
		tf_maps.setColumns(2);

		JButton btn_Calc = new JButton("Calculate Exp");
		left.add(btn_Calc);

		JPanel right = new JPanel();
		splitPane.setRightComponent(right);

		final JTextArea txt_Results = new JTextArea();
		txt_Results
		        .setText("Gem-Calc Version 0.1\r\nFrenzy (Normal_140)\r\nStart 1 0 End 1 0\r\n0 h 0 min 1 s\r\n1 maps\r\n\r\nGemXP gained:\t0\r\nGemXP / hour:\t0\r\nGemXP / map:\t0\r\n\r\n\tMax\tMax+20q\r\nMinutes to:\t∞\t∞\r\nMaps to:\t∞\t∞\r\nTotal EXP to:\t11.114\t22.228");
		right.add(txt_Results);

		btn_Calc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cb_Gem.getSelectedIndex() < 0) {
					return;
				}

				Gem currentgem = (Gem) cb_Gem.getSelectedItem();

				int s_lvl = new Integer(cb_start.getSelectedItem() + "");
				int s_exp = new Integer(tf_s_exp.getText().trim());
				int e_lvl = new Integer(cb_end.getSelectedItem() + "");
				int e_exp = new Integer(tf_e_exp.getText().trim());
				int maps = new Integer(tf_maps.getText().trim());
				int h = new Integer(tf_h.getText().trim());
				int min = new Integer(tf_min.getText().trim());
				int s = new Integer(tf_s.getText().trim());

				Results res = new Results(currentgem, s_lvl, s_exp, e_lvl, e_exp, maps, h, min, s);
				txt_Results.setText(res.toString());
			}
		});
	}
}
