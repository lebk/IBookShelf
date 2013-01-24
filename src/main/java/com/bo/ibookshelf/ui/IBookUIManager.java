
package com.bo.ibookshelf.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.io.File;
import java.util.*;

/**
 * Author: Terry_Lei Date: 6:01:33 PM
 */

public class IBookUIManager extends JPanel implements ListSelectionListener {
	private JTextArea rightContent;
	private JTree leftTree;
	private JSplitPane splitPane;


	public IBookUIManager(File dir) {

		// Create the list of images and put it in a scroll pane.
        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("我的书");
        createNodes(top,dir);
        
		leftTree = new JTree(top);

		JScrollPane listScrollPane = new JScrollPane(leftTree);
		rightContent = new JTextArea();
		rightContent.setFont(rightContent.getFont().deriveFont(Font.ITALIC));

		JScrollPane pictureScrollPane = new JScrollPane(rightContent);

		// Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane,
				pictureScrollPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		// Provide minimum sizes for the two components in the split pane.
		Dimension minimumSize = new Dimension(100, 50);
		listScrollPane.setMinimumSize(minimumSize);
		pictureScrollPane.setMinimumSize(minimumSize);

		// Provide a preferred size for the split pane.
		splitPane.setPreferredSize(new Dimension(400, 200));
	}

	  /** Add nodes from under "dir" into curTop. Highly recursive. */
	  DefaultMutableTreeNode createNodes(DefaultMutableTreeNode curTop, File dir) {
	    String curPath = dir.getPath();
	    DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
	    if (curTop != null) { // should only be null at root
	      curTop.add(curDir);
	    }
	    Vector ol = new Vector();
	    String[] tmp = dir.list();
	    for (int i = 0; i < tmp.length; i++)
	      ol.addElement(tmp[i]);
	    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
	    File f;
	    Vector files = new Vector();
	    // Make two passes, one for Dirs and one for Files. This is #1.
	    for (int i = 0; i < ol.size(); i++) {
	      String thisObject = (String) ol.elementAt(i);
	      String newPath;
	      if (curPath.equals("."))
	        newPath = thisObject;
	      else
	        newPath = curPath + File.separator + thisObject;
	      if ((f = new File(newPath)).isDirectory())
	    	  createNodes(curDir, f);
	      else
	        files.addElement(thisObject);
	    }
	    // Pass two: for files.
	    for (int fnum = 0; fnum < files.size(); fnum++)
	      curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
	    return curDir;
	  }
	  


	
	// Listens to the list
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();

	}

	// Renders the selected image
	protected void updateLabel(String name) {
		ImageIcon icon = createImageIcon("images/" + name + ".gif");

	}

	// Used by SplitPaneDemo2
	public JTree getImageList() {
		return leftTree;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = IBookUIManager.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	public static void createAndShowGUI(File dir) {

		// Create and set up the window.
		JFrame frame = new JFrame("我的书籍管家");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		IBookUIManager splitPaneDemo = new IBookUIManager(dir);
		frame.getContentPane().add(splitPaneDemo.getSplitPane());

		// Display the window.
		frame.pack();
		JMenuBar mb = getMenu();
		frame.setJMenuBar(mb);
		frame.setVisible(true);

	}

	public static JMenuBar getMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem sMenuItem = new JMenuItem("Save");

		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_C);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}

		});
		sMenuItem.setToolTipText("Save the file");
		file.add(sMenuItem);
		file.add(eMenuItem);

		JMenu tools = new JMenu("Tools");

		JMenuItem admin = new JMenuItem("Admin...");

		tools.add(admin);

		mb.add(file);

		mb.add(tools);
		return mb;
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String dir=null;
				createAndShowGUI(new File("."));
			}
		});
	}

}
