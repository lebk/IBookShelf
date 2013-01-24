package com.bo.ibookshelf.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bo.ibookshelf.po.Book;

/**
 * 
 * Author : Bo
 * 
 * Creation time : 2012-11-28
 */

public class BookManager {

	private Logger logger = Logger.getLogger(BookManager.class);
	private String baseBookLocation;
	private List<Book> bookList = new ArrayList<Book>();

	// To hold the book with (name, location (list)), there might be the same
	// books be stored in different location.

	private Map<String, List> bookMap = new HashMap<String, List>();

	public BookManager(String booksBaseLocation) {
		this.baseBookLocation = booksBaseLocation;
		this.init();
	}

	// This method will be called when the BookManager is initialized
	// , and it will read all the books file
	// in the source directory

	private void init() {

		traverseTree(baseBookLocation);
	}

	private void traverseTree(String baseLocation) {

		File file = new File(baseLocation);

		// Reading directory contents
		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			File bookFile = files[i];

			if (bookFile.isDirectory()) {
				this.traverseTree(bookFile.getAbsolutePath());
			} else {
				String bookFileName = bookFile.getName();
				logger.info("find book:" + bookFileName);
				this.addToBookMap(bookFile);
				// bookList.add(bookFile);
			}
		}

	}

	private void addToBookMap(File bookFile) {
		if (bookMap.keySet().contains(bookFile.getName())) {
			List dirList = bookMap.get(bookFile.getName());
			dirList.add(bookFile.getAbsoluteFile());

		} else {
			List sublist = new ArrayList();
			sublist.add(bookFile.getAbsoluteFile());
			bookMap.put(bookFile.getName(), sublist);
		}

	}

	public void showAllBooksInfo() {
		logger.info("Show the book info");

		int count = 0;
		Iterator mIt = bookMap.keySet().iterator();

		while (mIt.hasNext()) {
			String bookName = (String) mIt.next();
			count++;
			// logger.info("The book name is:" + mIt.next());
			List dirList = bookMap.get(bookName);
			if (dirList.size() > 1) {
				Iterator dIt = dirList.iterator();
				while (dIt.hasNext()) {
					logger.info(dIt.next());
				}
			}
		}
		logger.info("There are: " + count + " books in:"
				+ this.baseBookLocation);
	}
}
