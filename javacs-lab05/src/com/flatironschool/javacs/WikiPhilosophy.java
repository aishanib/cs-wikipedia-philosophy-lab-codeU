package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {

	final static WikiFetcher wf = new WikiFetcher();

	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 *
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 *
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

        // some example code to get you started
		String philosophyURL = "https://en.wikipedia.org/wiki/Philosophy";
		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		while(!url.equals(philosophyURL)){
			if (url == null) {
				System.out.println("Unvalid link");
			}
			url = findURL(url);
			System.out.println("We reached");
			// Elements paragraphs = wf.fetchWikipedia(url);
			// System.out.println("This works");
			// Element firstPara = paragraphs.get(0);
			// System.out.println("I got the first paragraph of the content");
			// url = findURL(firstPara);
			// System.out.println("I got: "+url);
		}
}
	public static String findURL(String url) throws IOException{
		Elements paragraphs = wf.fetchWikipedia(url);
		Element firstPara = paragraphs.get(0);
		//String url = "";
		Iterable<Node> iter = new WikiNodeIterable(firstPara);
		int pCount = 0;
		//Node x = null;
		for (Node node: iter) {
			if (node instanceof TextNode) {
				String word = node.toString();
				if (word.contains("(")) {
					pCount++;
				}else if (word.contains(")")) {
					pCount--;
				}
			}
			// if (x!=null) {
			// 	System.out.println(x.equals(node));
			// }
			//System.out.println(node+" Testing");
			if (node instanceof Element) {
				String tagName = ((Element) node).tagName();
				if (tagName.equals("a") && pCount==0) {
					String nUrl = ((Element)node).attr("abs:href");
					//System.out.println(url);
					return nUrl;
				}
			}
		}
		return null;
	}
	private static boolean containsChar (String s, char c){
		System.out.println("we were called, and we say it contains: "+(s.indexOf(c)!=-1));
		return (s.indexOf(c)!=-1);
	}
}
