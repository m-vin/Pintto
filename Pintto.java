import java.awt.*;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.BorderFactory; 
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.net.URL;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import static javax.swing.GroupLayout.Alignment.*;

import twitter4j.conf.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import javax.swing.border.*;


public class Pintto extends JFrame {

  private PinttoTextArea tfTweet = new PinttoTextArea();
	private GroupLayout layout;
	private PinttoTweetButton btTweet;
	private JLabel imgIconLabel, lbRemainingCh, profilePhoto;
	private File file = new File("");

	public final String CONSUMER_KEY = "";
	public final String CONSUMER_SECRET = "";
	public final String ACCESS_KEY = "";
	public final String ACCESS_SECRET = "";

	public Pintto () throws TwitterException{

		ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
            .setOAuthConsumerKey(CONSUMER_KEY) //CONSUMER_KEY
            .setOAuthConsumerSecret(CONSUMER_SECRET) //CONSUMER_SECRET
            .setOAuthAccessToken(ACCESS_KEY) //ACCESS_KEY
            .setOAuthAccessTokenSecret(ACCESS_SECRET); //ACCESS_SECRET

    TwitterFactory tf = new TwitterFactory(cb.build());
    System.out.println("new TwitterFactory()");

    Twitter twitter = tf.getInstance();
		System.out.println("tf.getInstance()");


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Pintto");
		getContentPane().setBackground(new Color(36, 52, 71));


		User user = twitter.showUser(twitter.getId());
		try {
			System.out.println("url: " + user.getMiniProfileImageURL());
			profilePhoto = new JLabel(new ImageIcon(ImageIO.read(new URL(user.getMiniProfileImageURL()))));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		tfTweet.setBorder(new RoundBorder(20));
		tfTweet.setLineWrap(true);
		tfTweet.setWrapStyleWord(true);
		tfTweet.setFont(new Font("Helvetica", Font.PLAIN, 15));
		tfTweet.setText("What's happening?");
		tfTweet.setSize(new Dimension (500, 25));
		tfTweet.setBackground(new Color(20, 29, 38));
		tfTweet.setFocusable(false);
		
		btTweet = new PinttoTweetButton("Tweet");

		btTweet.setEnabled(false);
		btTweet.setPreferredSize(new Dimension(60, 30));
		//btTweet.setBorder(new RoundBorder(20));

    lbRemainingCh = new JLabel(String.valueOf(140));
    lbRemainingCh.setFont(new Font("Helvetica", Font.PLAIN, 14));
    lbRemainingCh.setForeground(Color.WHITE);

    imgIconLabel = new JLabel(new ImageIcon("img-icon-2.png"));
    imgIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		populateFrame();
		defineEvents();
	}

	public void populateFrame(){

		layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(profilePhoto)
			.addComponent(tfTweet)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(imgIconLabel))
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(profilePhoto)
					.addComponent(tfTweet)
					.addComponent(imgIconLabel))
			);
			pack();
	}

	public void defineEvents(){
		tfTweet.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				tfTweet.setFocusable(true);
				tfTweet.requestFocus();
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
		});

		tfTweet.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tfTweet.getText().equals("What's happening?")) {
					tfTweet.setText("");
					tfTweet.setForeground(new Color(150, 150, 150));
					tfTweet.setPreferredSize(new Dimension(500, 60));

				}

		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(LEADING)
				.addComponent(profilePhoto))
			.addGroup(layout.createParallelGroup(LEADING)
				.addComponent(tfTweet))
			.addGroup(layout.createParallelGroup(LEADING)
				.addComponent(imgIconLabel)
				.addComponent(lbRemainingCh)
				.addComponent(btTweet))
			);


			layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(profilePhoto)
					.addComponent(tfTweet)
	        .addGroup(layout.createSequentialGroup()
	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                .addComponent(imgIconLabel))
	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                .addComponent(lbRemainingCh))
	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                .addComponent(btTweet))))
				);
			pack();
		}

			@Override
			public void focusLost(FocusEvent e) {
				if (tfTweet.getText().isEmpty()) {
					tfTweet.setForeground(new Color(67, 62, 114));
					tfTweet.setText("What's happening?");
				}
			}
		});

		tfTweet.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateRemainingLabel();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateRemainingLabel();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
	
		imgIconLabel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Área de Trabalho"));
				file = new File("");
				int returnVal = fileChooser.showOpenDialog(new JFrame());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
	        file = fileChooser.getSelectedFile();
	        System.out.println("Opening: " + file.getName());
	        		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(tfTweet)
			.addGroup(layout.createParallelGroup(LEADING)
				.addComponent(imgIconLabel)
				.addComponent(lbRemainingCh)
				.addComponent(btTweet)
			));

			layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(tfTweet)
					.addComponent(imgIconLabel))
				.addGroup(layout.createParallelGroup(LEADING)
					.addComponent(lbRemainingCh))
				.addGroup(layout.createParallelGroup(LEADING)
					.addComponent(btTweet))
				);
			pack();				
			if (tfTweet.getText().equals("What's happening?")) {
					tfTweet.setText("");
					tfTweet.setForeground(new Color(150, 150, 150));
					tfTweet.setPreferredSize(new Dimension(500, 30));
				}
			tfTweet.setFocusable(true);
			tfTweet.requestFocus();
			updateRemainingLabel();
        } else {
        	System.out.println("Open command cancelled by user.");
        }
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
		});

	btTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfTweet.getText().isEmpty()){
					System.out.println("Você ainda não escreveu nada...");
				}
				else {
					System.out.println("Enviando tweet: " + tfTweet.getText() + " " + tfTweet.getText().length());

						sendTweet();
				}
			}
		});
	}

	public void updateRemainingLabel(){
		if (tfTweet.getText().isEmpty() && file.getName().length() == 0){
			btTweet.setEnabled(false);
		}
		else
			btTweet.setEnabled(true);
				lbRemainingCh.setText(String.valueOf(140 - tfTweet.getText().length()));
	}

	public void sendTweet(){
		System.out.println("tweet para enviar: " + tfTweet.getText());
    
		StatusUpdate status = new StatusUpdate(tfTweet.getText());
		System.out.println("new StatusUpdate()");
    
		if (file.getName().length() != 0)
			status.setMedia(file);
			System.out.println("file.getName().length() == " + file.getName().length());
		/*
		try{
			twitter.updateStatus(status);
		} catch (TwitterException e){
			e.printStackTrace();
			System.out.println("TwitterException - sendTweet()");
		}
		*/
		System.out.println("tweet: " + tfTweet.getText());
		System.out.println("img: " + file.getName());
		System.out.println("executado");
	}

	public static void main(String[] args) {

  java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
    try {
            // Set cross-platform Java L&F (also called "Metal")
    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }

	    try{
		    new Pintto().setVisible(true);
		    } catch (TwitterException te){
		    	te.printStackTrace();
		    } catch (Exception ex) {
		    	ex.printStackTrace();
		    }
      }
    });
	}
}