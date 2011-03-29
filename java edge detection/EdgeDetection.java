import java.awt.*;
import java.awt.image.*;
import java.applet.Applet;
import java.util.*;
import java.awt.event.*;

public class EdgeDetection extends Applet implements 
				ActionListener, ItemListener, Runnable{

	/* Global Variables */
	Thread t = null;
	
	//GUI
	Choice selectpicture = null;
	TextField filepath = null;
	Button loadnewimage = null;
	TextArea info = null;
	Button calculate = null;
	Choice edgealgorithm = null;
	Choice gaussianblur = null;
	Checkbox nonmaxsup = null;
	Checkbox thresholdingon = null;
	TextField thresholdlevel = null;
	
	//Other Info
	Image originalImage = null;
	Image processedImage = null;	
	
	//stores the paths to the pictures
	String sample1val = new String("");
	String sample2val = new String("");
	String orchidval = new String("");
	String shapesval = new String("");
	String yosemiteval = new String("");
	String snowval = new String("");
	String customval = new String("");
	
	//stores the arrays of loadedpictures
	int[] sample1array = null;
	int sample1width, sample1height;
	int[] sample2array = null;
	int sample2width, sample2height;
	int[] orchidarray = null;
	int orchidwidth, orchidheight;
	int[] shapesarray = null;
	int shapeswidth, shapesheight;
	int[] yosemitearray = null;
	int yosemitewidth, yosemiteheight;
	int[] snowarray = null;
	int snowwidth, snowheight;
	int[] customarray = null;
	int customwidth, customheight;
	String infoval = new String("");
	boolean change = true;
	int pixels[];
	boolean pixelsloaded = false;

	//for edge algorithm
	final int SIMPLE = 0;
	final int ROBERTSCROSS = 1;
	final int PREWITT = 2;
	final int SOBEL3X3 = 3;
	int typeofoperator = SIMPLE;

	//for gaussian blur
	final int NONE = 0;
	final int GB3X3 = 1;
	final int GB5X5 = 2;
	int typeofgaussianblur = NONE;
	
	//for non-maximumsupression
	boolean nonmaxsupenabled = false;
	
	//for thresholding
	boolean thresholdingenabled = false;
	int thresholdval = 50;
	
	
	/* overrides the insets function for layout manager
	 */
	public Insets getInsets(){
		return new Insets(50,0,50,330);
	}
	
	/*imageUPDATE function tells how much of the image is loaded.
	public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h){
		if((flags & SOMEBITS) != 0){ //new partial data
			//repaint(x,y,w,h);
			System.out.println("loading...");
		} else if((flags & ABORT) != 0){
			error = true;
			System.out.println("file not found");
			//repaint();
		}
		return (flags & (ALLBITS|ABORT)) == 0;
	}
	
	/* Called first
	 */
	public void init(){
		
		setBackground(Color.gray);
		
		//load paths from htm file
		sample1val = getParameter("sample1");
		sample2val = getParameter("sample2");
		orchidval = getParameter("orchid");
		shapesval = getParameter("shapes");
		yosemiteval = getParameter("yosemite");
		snowval = getParameter("snow");
		
		//String original = getParameter("image");
		//originalImage = getImage(getDocumentBase(),orchidval);
		//processedImage = originalImage;
		
		//label
		Label stepone = new Label("============= Step 1: Select Picture =============");
		add(stepone);
		//load choice controls
		selectpicture = new Choice();
		selectpicture.add("Orchid");
		selectpicture.add("Sample1");
		selectpicture.add("Sample2");
		selectpicture.add("Shapes");
		selectpicture.add("Snow");
		selectpicture.add("Yosemite");
		selectpicture.add("Custom...");
		Label selectpicturelabel = new Label("Select Picture to Manipulate: ");
		selectpicture.addItemListener(this);
		add(selectpicturelabel);
		add(selectpicture);
		
		//load file dialog box
		//Frame f = new Frame("This is a test");
		//f.setVisible(false);
		//f.setSize(100,100);
		Label filepathlabel = new Label("--- or select a Custom Path (don't include 'C:\\') ---");
		filepath = new TextField(27);
		add(filepathlabel);
		add(filepath);
		Button loadnewimage = new Button("Load Picture");
		add(loadnewimage);
		loadnewimage.addActionListener(this);

		//label
		Label steptwo = new Label("============= Step 2: Select Options =============");
		add(steptwo);
		
		//choice controls
		Label gaussianblurlabel = new Label("Gaussian Blur Intensity:", Label.RIGHT);
		gaussianblur = new Choice();
		gaussianblur.add("None");
		gaussianblur.add("3x3");
		gaussianblur.add("5x5");
		add(gaussianblurlabel);
		add(gaussianblur);
		gaussianblur.addItemListener(this);
		
		Label edgealgorithmlabel = new Label("Select Edge Algorithm:", Label.RIGHT);
		edgealgorithm = new Choice();
		edgealgorithm.add("Simple");
		edgealgorithm.add("Roberts Cross");
		edgealgorithm.add("Prewitt Edge Detection");
		edgealgorithm.add("Sobel Edge Detection [3x3]");
		add(edgealgorithmlabel);
		add(edgealgorithm);
		edgealgorithm.addItemListener(this);
		
		//non maximum suppression
		nonmaxsup = new Checkbox("Non-Maximum Supression:",false);
		add(nonmaxsup);
		nonmaxsup.addItemListener(this);

		//Optional "SPACER"
		Label emptylabel = new Label("                    ", Label.RIGHT);
		add(emptylabel);
		
		//thresholding
		thresholdingon = new Checkbox("Thresholding    Value:", false);
		thresholdlevel = new TextField("50",3);
		add(thresholdingon);
		add(thresholdlevel);
		thresholdingon.addItemListener(this);
		thresholdlevel.addActionListener(this);
		
		//Optional "SPACER"
		//Label emptylabel = new Label("                    ", Label.RIGHT);
		//add(emptylabel);
	
		//label
		//Label stepthree = new Label("=============== Step 3: Calculate ===============");
		//add(stepthree);	
	
		calculate = new Button("Calculate");
		add(calculate);
		calculate.addActionListener(this);
		info = new TextArea(infoval, 10,40);
		add(info);
		
		//make it draw default
		itemStateChanged(null);
	}
	
	public void itemStateChanged(ItemEvent ie){
		//Picture selection
		String tempsel = selectpicture.getSelectedItem();
		if(tempsel.equals("Orchid")){
			if(orchidarray == null){
				originalImage = getImage(getDocumentBase(),orchidval);
				do{			
					orchidwidth = originalImage.getWidth(this);
					orchidheight = originalImage.getHeight(this);
				} while(orchidwidth < 0 || orchidheight < 0);
				try{
					orchidarray = new int[orchidwidth*orchidheight];
					PixelGrabber pg = new PixelGrabber(originalImage,0,0,orchidwidth, 
						orchidheight,orchidarray,0,orchidwidth);
					pg.grabPixels();
				} catch(Exception e){
				}
				
			} else{	
				MemoryImageSource newimage = new MemoryImageSource(orchidwidth,
					orchidheight,orchidarray,0,orchidwidth);
				Canvas c = new Canvas();
				originalImage = c.createImage(newimage);
			}
		} else if(tempsel.equals("Sample1")){
			if(sample1array == null){
				originalImage = getImage(getDocumentBase(),sample1val);
				System.out.println("I CAME HERE");
				do{
					sample1width = originalImage.getWidth(null);
					sample1height = originalImage.getHeight(null);
				} while(sample1width < 0 || sample1height < 0);
				try{
					sample1array = new int[sample1width*sample1height];				
					PixelGrabber pg = new PixelGrabber(originalImage,0,0,sample1width, 
						sample1height,sample1array,0,sample1width);
					pg.grabPixels();
				} catch(Exception e){}
			} else{
				MemoryImageSource newimage = new MemoryImageSource(sample1width,
					sample1height,sample1array,0,sample1width);
				Canvas c = new Canvas();
				originalImage = c.createImage(newimage);
			}		
		} else if(tempsel.equals("Sample2")){
			if(sample2array == null){
				originalImage = getImage(getDocumentBase(),sample2val);
				do{
					sample2width = originalImage.getWidth(null);
					sample2height = originalImage.getHeight(null);
				} while (sample2width < 0 || sample2height < 0);
				try{
					sample2array = new int[sample2width*sample2height];				
					PixelGrabber pg = new PixelGrabber(originalImage,0,0,sample2width, 
						sample2height,sample2array,0,sample2width);
					pg.grabPixels();
				} catch(Exception e){}
			} else{
				MemoryImageSource newimage = new MemoryImageSource(sample2width,
					sample2height,sample2array,0,sample2height);
				Canvas c = new Canvas();
				originalImage = c.createImage(newimage);
			}		
		} else if(tempsel.equals("Shapes")){
			if(shapesarray == null){
				originalImage = getImage(getDocumentBase(),shapesval);
				do{
					shapeswidth = originalImage.getWidth(null);
					shapesheight = originalImage.getHeight(null);
				} while(shapeswidth < 0 || shapesheight < 0);
				try{
					shapesarray = new int[shapeswidth*shapesheight];						
					PixelGrabber pg = new PixelGrabber(originalImage,0,0,shapeswidth, 
						shapesheight,shapesarray,0,shapeswidth);
					pg.grabPixels();
				} catch(Exception e){}
			} else{
				MemoryImageSource newimage = new MemoryImageSource(shapeswidth,
					shapesheight,shapesarray,0,shapeswidth);
				Canvas c = new Canvas();
				originalImage = c.createImage(newimage);
			}		
		} else if(tempsel.equals("Snow")){
			if(snowarray == null){
				originalImage = getImage(getDocumentBase(),snowval);
				do{
					snowwidth = originalImage.getWidth(null);
					snowheight = originalImage.getHeight(null);
				} while(snowwidth < 0 || snowheight < 0);
				try{
					snowarray = new int[snowwidth*snowheight];					
					PixelGrabber pg = new PixelGrabber(originalImage,0,0,snowwidth, 
						snowheight,snowarray,0,snowwidth);
					pg.grabPixels();
				} catch(Exception e){}
			} else{
				MemoryImageSource newimage = new MemoryImageSource(snowwidth,
					snowheight,snowarray,0,snowwidth);
				Canvas c = new Canvas();
				originalImage = c.createImage(newimage);
			}			
		} else if(tempsel.equals("Yosemite")){
			if(yosemitearray == null){
				originalImage = getImage(getDocumentBase(),yosemiteval);
				do{
					yosemitewidth = originalImage.getWidth(null);
					yosemiteheight = originalImage.getHeight(null);
				} while(yosemitewidth < 0 || yosemiteheight < 0);
				try{
					yosemitearray = new int[yosemitewidth*yosemiteheight];					
					PixelGrabber pg = new PixelGrabber(originalImage,0,0,yosemitewidth, 
						yosemiteheight,yosemitearray,0,yosemitewidth);
					pg.grabPixels();
				} catch(Exception e){}
			} else{
				MemoryImageSource newimage = new MemoryImageSource(yosemitewidth,
					yosemiteheight,yosemitearray,0,yosemitewidth);
				Canvas c = new Canvas();
				originalImage = c.createImage(newimage);
			}		
		} else{
			customval = filepath.getText();
			originalImage = getImage(getDocumentBase(),customval);		
		}
	
		//Gaussian blur choice control
		System.out.println(gaussianblur.getSelectedItem() + "");
		if(gaussianblur.getSelectedItem().equals("3x3")){
			typeofgaussianblur = GB3X3;
		} else if(gaussianblur.getSelectedItem().equals("5x5")){
			typeofgaussianblur = GB5X5;
		} else{
			typeofgaussianblur = NONE;
		}
	
		//edge algorithm choice control
		if(edgealgorithm.getSelectedItem().equals("Simple")){
			typeofoperator = SIMPLE;
		} else if (edgealgorithm.getSelectedItem().equals("Roberts Cross")){
			typeofoperator = ROBERTSCROSS;
		} else if(edgealgorithm.getSelectedItem().equals("Prewitt Edge Detection")){
			typeofoperator = PREWITT;
		} else{
			typeofoperator = SOBEL3X3;
		}
		
		//non-maximum supression control
		nonmaxsupenabled = nonmaxsup.getState();	
		
		//thresholding
		thresholdingenabled = thresholdingon.getState();

		updateInfo(0,0,0,"Press CALCULATE to view results");	
		
		repaint();		
	}
	
	/* a textfield changes or button is pressed 
	 */
	public void actionPerformed(ActionEvent ae){
		//get file for image.
		String str = ae.getActionCommand();		
		if(str.equals("Load Picture")){
			selectpicture.select(6);
			originalImage = getImage(getDocumentBase(),filepath.getText());
			repaint();
		}		
		
		//make sure the thresholdlevel is in range.
		int thtemp;
		try{
			thtemp = Integer.parseInt(thresholdlevel.getText(),10);
			if(thtemp > 255 | thtemp < 0){
				throw new NumberFormatException("Range of Threshold Value is 0 - 255");
			}
			updateInfo(0,0,0,"Press CALCULATE to view results");
			thresholdval = thtemp;
		} catch(NumberFormatException e){
			thresholdlevel.setText(thresholdval + "");
			updateInfo(0,0,0,"Error: " + e.getMessage());
		}
	
		str = ae.getActionCommand();
		if(str.equals("Calculate")){
			change = true;
			repaint();
		}
	
	}
		
	
	/* Called after init, also called when applet is restarted
	 */
	public void start(){
		t = new Thread(this);
		t.start();
	}
	
	public void run(){
		while(true){
			try{
				t.sleep(100);
			} catch(Exception e){}
		}
	}
	
	/* Called when the applet is stopped
	 */
	public void stop(){}
	
	/* Called when the applet is terminated.  
	 * This is the last method executed. 
	 */
	public void destroy(){}
	
	/* Called when an applet's window must be restored.
	 */
	public void paint(Graphics g){
		update(g);
	}

	/* this applet also overwrites UPDATE method
	 */
	public void update(Graphics g){

		System.out.println("update called");
		g.setColor(new Color(255,0,0));
		g.fillRect(320,0,320,260);
		g.setColor(new Color(0,0,0));		
		g.fillRect(320,265,320,260);
		
		g.setColor(new Color(255,255,255));
		g.drawString("Original Image",440,250);
		g.drawString("   New Image",440,515);

		boolean drawnA = false;
		boolean drawnB = false;
			drawnA = g.drawImage(originalImage,320,0,320,240,this);
		if(drawnA){
			GaussianBlurOp apgausblur; 
			System.out.println(typeofgaussianblur);
			switch(typeofgaussianblur){
				case NONE:						
					processedImage = originalImage;
					pixelsloaded = false;
					break;
				case GB3X3:
					apgausblur = new GaussianBlurOp(originalImage,3);
					processedImage = apgausblur.getImage();
					pixelsloaded = false;
					break;
				case GB5X5:
					apgausblur = new GaussianBlurOp(originalImage,5);
					processedImage = apgausblur.getImage();	
					pixelsloaded = false;
					break;
			}
			 switch(typeofoperator){
				case SIMPLE: {
					Simple tempimage = new Simple(processedImage);
					processedImage = tempimage.getImage();
					//NonMaxSupression
					if(nonmaxsupenabled){
						NonMaxSupression tempimage2 = new NonMaxSupression(processedImage);
						processedImage = tempimage2.getImage();
					}
					updateInfo(0,0,0,"press CALCULATE to view results");
					break;
				}
				case ROBERTSCROSS: {
					RobertsCrossOp tempimage = new RobertsCrossOp(processedImage);
					processedImage = tempimage.getImage();
					//NonMaxSupression
					if(nonmaxsupenabled){
						NonMaxSupression tempimage2 = new NonMaxSupression(processedImage);
						processedImage = tempimage2.getImage();
					}
					updateInfo(0,0,0,"press CALCULATE to view results");					
					break;
				}
				case PREWITT: {
					PrewittOp tempimage = new PrewittOp(processedImage);			
					processedImage = tempimage.getImage();
					//NonMaxSupression
					if(nonmaxsupenabled){
						NonMaxSupression tempimage2 = new NonMaxSupression(processedImage);
						processedImage = tempimage2.getImage();
					}
					updateInfo(0,0,0,"press CALCULATE to view results");				
					break;
				}
				case SOBEL3X3: {
					Sobel3x3Op tempimage = new Sobel3x3Op(processedImage);
					processedImage = tempimage.getImage();
					//NonMaxSupression
					if(nonmaxsupenabled){
						NonMaxSupression tempimage2 = new NonMaxSupression(processedImage);
						processedImage = tempimage2.getImage();
					}				
					updateInfo(0,0,0,"press CALCULATE to view results");				
					break;				
				}
			}
			if(thresholdingenabled){
				Threshold newThreshold = new Threshold(processedImage,thresholdval);
				processedImage = newThreshold.getImage();
			}
			while(!(drawnB)){
				drawnB = g.drawImage(processedImage,320,265,320,240,null);	

			}
		}
	}
	
	private void updateInfo(int length, int width, int bit, String alt){
		//System.out.println("UPDATEINFO CALLED " + length + " " + width + " " + bit + " " + alt);
		if(alt == null){
			infoval = "Comparison of File Sizes\n" +
					  "(Assuming they are uncompressed)\n" +
					  "Original Size: " + length*width*24/8192 + "KB\n" +
					  "New Size: " + length*width*bit/8192 + "KB\n";
		} else{
			infoval = alt;
		}
		info.setText(infoval);
	}
}
