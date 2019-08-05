package controler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class GetPlainText {
	public static String text(String fullyQualifiedPathOfFile) {
		String extension="";
		String plainText="";
		BufferedReader reader=null;
		File file = new File(fullyQualifiedPathOfFile);
		extension = FilenameUtils.getExtension(fullyQualifiedPathOfFile);
		if(extension.equalsIgnoreCase("pdf")) {
			try {
				PDDocument document = PDDocument.load(file);
				PDFTextStripper stripper = new PDFTextStripper();
				plainText = stripper.getText(document);
			} catch (IOException e) {
				System.out.println(e);
			}
		}else {
			try {
				reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				while (line!=null) {
					plainText = plainText.concat(line);
					plainText.concat("\n");
					line = reader.readLine();
				}
			} catch (IOException e) {
				System.out.println(e);
			}finally {
				try {
					reader.close();
				} catch (IOException e2) {
					System.out.println(e2);
				}
			}
		}
		return plainText;
		}
}
