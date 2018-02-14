package gang.of.four.structural.design.patterns;

public class BridgePattern {
	
	//abstraction - i.e. to write some contents to a disk in different formats
	abstract class FileFormat {
		Encoder encoder;
		String content;
		
		FileFormat(Encoder encoder, String content){
			this.encoder=encoder;
			this.content=content;
		}
		abstract void writeFileToDisk(String filename);
	}
	
	//refined abstraction, a specific format 
	class XMLFileFormat extends FileFormat{
		public XMLFileFormat(Encoder encoder, String string) {
			super(encoder, string);
		}

		void writeFileToDisk(String filename) {
			System.out.println(encoder.getEncodedContent(content));
			System.out.println("Wrote " + filename + ".xml");
		}
	}
	
	//refined abstraction, a specific format 
	class TextFileFormat extends FileFormat{
		public TextFileFormat(Encoder encoder, String string) {
			super(encoder, string);
		}
		
		void writeFileToDisk(String filename) {
			System.out.println(encoder.getEncodedContent(content));
			System.out.println("Wrote " + filename + ".txt");
		}
	}
	
	/***********************/
	
	//implementor
	interface Encoder{
		String getEncodedContent(String contents);
	}
	
	//concrete implementor
	class EncoderUTF8 implements Encoder{
		public String getEncodedContent(String contents) {
			return contents + ".UTF-8";
		}
		
	}
	//concrete implementor
	class EncoderASCII implements Encoder{
		public String getEncodedContent(String contents) {
			return contents + ".ASCII";
		}
		
	}
	
	public static void main(String... args) {
		BridgePattern bp = new BridgePattern();
		bp.new XMLFileFormat(bp.new EncoderUTF8(), "<myContent/>").writeFileToDisk("myXMLFile");
		bp.new XMLFileFormat(bp.new EncoderASCII(), "<myContent/>").writeFileToDisk("myXMLFile");
		bp.new TextFileFormat(bp.new EncoderASCII(), "myContent").writeFileToDisk("myTxtFile");
		
		//allowing new Encoder at run time
		bp.new TextFileFormat(s -> s + "RUNTIME-ENCODER", "runtimeContent").writeFileToDisk("myTxtFile");
	}
}
