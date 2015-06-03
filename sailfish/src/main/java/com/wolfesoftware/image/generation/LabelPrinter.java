//package com.wolfesoftware.image.generation;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.FontMetrics;
//import java.awt.GradientPaint;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.io.FileUtils;
//
//import com.sun.image.codec.jpeg.ImageFormatException;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//
///*you cannot access these clases in java 1.7 - and shouldnt do anyway*/
//public class LabelPrinter {
//	
//	String name = "Verdana";
//	int size = 20;
//	Color textColor = Color.BLACK;
//	Color backgroundColor = Color.WHITE;
//
//	public static void main(String[] args) throws ImageFormatException,
//			IOException {
//		LabelPrinter createImage = new LabelPrinter();
//		long startTime = System.currentTimeMillis();
//		// File file = new
//		// File("/Users/dwolfe/development/wol2/FastMLQuery/src/main/resources/list_of_dois.txt");
//		File file = new File("/Users/dwolfe/downloads/beggars.txt");
//		String readFileToString = FileUtils.readFileToString(file, "UTF-8");
//		System.out.println(readFileToString);
//		List<String> stringArrayFromFile = getStringArrayFromFile(file);
//		String page = new String("<html><body>");
//		for (int x = 0; x < stringArrayFromFile.size(); x++) {
//			page += "<img src=\"/tmp/text" + x + ".jpg\"/>";
//			page += "<br/>";
//			createImage.createImage(new File("/tmp/text" + x + ".jpg"),
//					stringArrayFromFile.get(x));
//		}
//		page += "</body></html>";
//		FileUtils.writeStringToFile(new File("/tmp/LabelPrinter.html"), page);
//		System.out.println((System.currentTimeMillis() - startTime) / 1000);
//	}
//
//	public static List<String> getStringArrayFromFile(File file)
//			throws IOException {
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		List<String> sb = new ArrayList<String>();
//		String line;
//		while ((line = reader.readLine()) != null) {
//			sb.add(line + "\n");
//		}
//		reader.close();
//		return sb;
//	}
//
//	public void createImage(File file, String text)
//			throws ImageFormatException, IOException {
//		BufferedImage image = new BufferedImage(200, 40,
//				BufferedImage.TYPE_BYTE_INDEXED);
//		Font font = new Font(name, Font.BOLD, size);
//		Graphics2D graphics = image.createGraphics();
//		graphics.setFont(font);
//		FontMetrics metrics = graphics.getFontMetrics(font);
//		Dimension size = new Dimension(metrics.stringWidth(text),
//				metrics.getHeight());
//
//		image = new BufferedImage(size.width, size.height,
//				BufferedImage.TYPE_BYTE_INDEXED);
//		graphics = image.createGraphics();
//
//		graphics.setColor(backgroundColor);
//		graphics.fillRect(0, 0, size.width, size.height);
//		// set gradient font of text to be converted to image
//		GradientPaint gradientPaint = new GradientPaint(10, 5, textColor, 20,
//				10, textColor, true);
//		graphics.setPaint(gradientPaint);
//
//		graphics.setFont(font);
//		graphics.drawString(text, 0, size.height - 5);
//		graphics.dispose();
//
//		crudImage(file, image);
//	}
//
//	private void crudImage(File file, BufferedImage image)
//			throws FileNotFoundException, IOException {
//		FileOutputStream out = new FileOutputStream(file);
//		JPEGImageEncoder createJPEGEncoder = JPEGCodec.createJPEGEncoder(out);
//		JPEGEncodeParam jpegEncodeParam = createJPEGEncoder
//				.getDefaultJPEGEncodeParam(image);
//		jpegEncodeParam.setQuality(1, false);
//		createJPEGEncoder.encode(image, jpegEncodeParam);
//		out.close();
//	}
//
//}
