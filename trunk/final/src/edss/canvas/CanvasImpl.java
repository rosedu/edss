package edss.canvas;

import java.awt.geom.AffineTransform;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.svg.SVGDocument;

import edss.interf.Canvas;
import edss.interf.CanvasMediator;


public class CanvasImpl implements Canvas {

	CanvasMediator mediator;
	
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(int factor) {
		// zoom out
		Constant.matrix.scale = factor/100.0;

		AffineTransform at = new AffineTransform();
		at.scale(Constant.matrix.scale, Constant.matrix.scale);
		Constant.canvas.setRenderingTransform(at);
		Constant.canvas.repaint();


		int canvH = (int) Math.round(Constant.canvas.getHeight() / Constant.matrix.ratio);
		int canvW = (int) Math.round(Constant.canvas.getWidth() / Constant.matrix.ratio);
		System.out.println(canvW + " " + canvH);
		Constant.canvas.setSize(canvW, canvH);
//		canvas.setPreferredSize(new Dimension(canvW, canvH));
		Constant.panel.revalidate();
		Constant.canvas.repaint();
		Constant.matrix.repaint();
//		Constant.pas /= matrix.ratio;
	}
	
	public CanvasImpl(CanvasMediator mediator) {
		
		this.mediator = mediator;
		Constant.mediator = mediator;
		mediator.registerCanvas(this);
		
	}

	@Override
	public void enterInsertState() {
		Constant.stateManager.enterInsertState();
		
	}

	@Override
	public void enterPieceState() {
		Constant.stateManager.enterPieceState();
		
	}

	@Override
	public void enterWireState() {
		Constant.stateManager.enterWireState();
		
	}

	@Override
	public void rotate(int angle) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void openSVG(String fileName) throws IOException {
		Constant.domFactory = (SVGDocument) Constant.saxFactory.createDocument(fileName);
		
	}

	@Override
	public void saveSVG(String fileName) throws TransformerException, IOException {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer trans = transFactory.newTransformer();
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		StringWriter stringWriter = new StringWriter();
		StreamResult result = new StreamResult(stringWriter);
		DOMSource source = new DOMSource(Constant.domFactory);
		trans.transform(source, result);
		String xmlString = stringWriter.toString();

		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(xmlString);
		out.close();		
	}

}
