package com.snezana.doctorpractice.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;

/**
 * Utility class that generates pdf file 
 */
@Component
public class PdfGeneratorUtil {

	@Autowired
	private TemplateEngine templateEngine;

	/**
	 * Generates a PDF and stores it to a File.
	 * <p>
	 * This is done by injecting the data from database (contained in the supplied {@code Map} object
	 * into the template html file, governed by Thymeleaf library. This 'complete' html is then rendered
	 * into the PDF file.
	 * @param templateName name of the template HTML document.
	 * @param map {@code Map} containing all the data that needs to appear in the generated (PDF)
	 * medical report.
	 * @throws Exception if anything goes wrong during PDF generation phase. The caller must therefore
	 * provide complete exception handling.
	 */
	public void createPdf(String templateName, Map<String, String> map) throws Exception {
		Assert.notNull(templateName, "The templateName cannot be null");
		Context ctx = new Context();
		if (map != null) {
			Iterator<Map.Entry<String, String>> itMap = map.entrySet().iterator();
			while (itMap.hasNext()) {
				Map.Entry<String, String> pair = itMap.next();
				ctx.setVariable(pair.getKey(), pair.getValue());
			}
		}

		String processedHtml = templateEngine.process(templateName, ctx);
		FileOutputStream os = null;
		String fileName = UUID.randomUUID().toString();

		try {
			final File outputFile = File.createTempFile(fileName, ".pdf"); //creates an empty file in the default temporary-file directory
			os = new FileOutputStream(outputFile);
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver resolver = renderer.getFontResolver();
			resolver.addFont("/fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(processedHtml);
			renderer.layout();
			renderer.createPDF(os, false);
			renderer.finishPDF();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					//
				}
			}
		}
	}
}
