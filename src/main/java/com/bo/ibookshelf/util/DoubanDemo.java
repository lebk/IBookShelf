package com.bo.ibookshelf.util;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.douban.Attribute;
import com.google.gdata.data.douban.SubjectEntry;
import com.google.gdata.data.douban.SubjectFeed;
import com.google.gdata.data.douban.Tag;
import com.google.gdata.data.extensions.Rating;
import com.google.gdata.util.ServiceException;

public class DoubanDemo {
	private static Logger logger = Logger.getLogger(DoubanDemo.class);

	public static void main(String[] args) {
		String apiKey = "059ef56f6b705e1210dce04e42511a36";
		String secret = "006ba4a489916c13";

		DoubanService myService = new DoubanService("subApplication", apiKey,
				secret);

		System.out.println("Subject related test:");
		testSubjectEntry(myService);

	}

	private static void testSubjectEntry(DoubanService myService) {
		SubjectEntry se;
		try {
			String bookId = "2023013";
			se = myService.getBook(bookId);
			logger.info(se.getSummary().getPlainText());
			
			 printSubjectEntry(se);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}

	private static void printSubjectEntry(SubjectEntry subjectEntry) {

		if (subjectEntry.getSummary() != null)
			System.out.println("summary is "
					+ subjectEntry.getSummary().getPlainText());
		System.out.println("author is "
				+ subjectEntry.getAuthors().get(0).getName());
		System.out
				.println("title is " + subjectEntry.getTitle().getPlainText());

		for (Attribute attr : subjectEntry.getAttributes()) {
			System.out.println(attr.getName() + " : " + attr.getContent());
		}
		System.out.println("id is " + subjectEntry.getId());
		for (Tag tag : subjectEntry.getTags()) {
			System.out.println(tag.getName() + " : " + tag.getCount());
		}

		Rating rating = subjectEntry.getRating();
		if (rating != null)
			System.out.println("max is " + rating.getMax() + " min is "
					+ rating.getMin() + " numRaters is "
					+ rating.getNumRaters() + " average is "
					+ rating.getAverage());
		System.out.println("-------------------");
	}

}
