package edss.med;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.xml.transform.TransformerException;

import edss.canvas.CanvasImpl;
import edss.canvas.StateConstant;
import edss.canvas.StateConstant;
import edss.interf.Canvas;
import edss.interf.CanvasMediator;
import edss.interf.Gui;
import edss.interf.GuiMediator;
import edss.interf.Model;
import edss.interf.ModelMediator;
import edss.interf.Piece;
import edss.model.ModelImpl;

//TODO State pattern pt mediator
@SuppressWarnings("serial")
public class Mediator implements CanvasMediator, GuiMediator, ModelMediator, Serializable {

	private Gui gui;
	private Model model;
	private Canvas canvas;

	@Override
	public void registerGui(Gui gui) {
		this.gui = gui;
	}

	@Override
	public void registerModel(Model model) {
		this.model = model;
	}

	@Override
	public void registerCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void editPieceProperties(String id) {
		Piece piece = model.getPiece(id);
		gui.editPieceProperties(piece);
		model.update();
	}

	@Override
	public void update() {
		canvas.update();
	}

	@Override
	public void scale(int factor) {
		canvas.scale(factor);
	}

	@Override
	public void addPanel() {
		// CanvasImpl canv = new CanvasImpl(gui.getCenterPanel());
		canvas.createNewCanvas(gui.getCenterPanel());
		gui.getCenterPanel().add(canvas.getCanvas());
		// return gui.getCenterPanel();

		// gui.getCenterPanel().add(canvas.getPreview("file:///C:/My Documents 1/EDSS/svn/trunk/final/svg/amplifier.svg"));
	}

	@Override
	public void enterState(int state) {
		switch (state) {
		case StateConstant.PIECESTATE:
			canvas.enterInsertState();
			break;
		case StateConstant.DELETESTATE:
			canvas.enterDeleteState();
			break;
		case StateConstant.DRAGSTATE:
			break;
		case StateConstant.MOUSESTATE:
			canvas.enterPieceState();
			break;
		case StateConstant.WIRESTATE:
			canvas.enterWireState();
			break;
		default:
			break;

		}
	}

	@Override
	public void setPiece(String name, String category, String subcategory) {
		model.setLastSelected(category, subcategory, name);
	}

	@Override
	public String addPiece(int x, int y) {
		return model.addPiece(x, y);
	}

	@Override
	public String getSVG() {
		try {
			String res = new File("svg/" + model.getSVG()).getCanonicalPath();
			System.out.println(res);
			return "file:///" + res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void save(String file) {
		try {
			if (file.contains(".svg")) {
				canvas.saveSVG(file);
				model.saveScheme(file.substring(0, file.lastIndexOf('.')));
			} else {
				canvas.saveSVG(file + ".svg");
				model.saveScheme(file);
			}
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open(String file) {

		// canvas.setCanvasVariables(gui.getCenterPanel());
		// gui.getCenterPanel().add(canvas.getCanvas());

		try {
			System.out.println(file);
			canvas.openSVG(gui.getCenterPanel(), file + ".svg");
			model.openScheme(file, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rotate(int angle) {
		String lastSelectedId = canvas.getLastSelectedPieceId();
		canvas.rotate(angle);// tine minte singur piesa selectata

		// DE AVUT grija sa se faca null la loc cand se intra in alt mod!!!
		// String lastSelectedId = canvas.getLastSelectedPieceId();
		// model.rotate(r,lastSelectedId);
		// }
	}

	public void delete() {
		// canvas sterge aici dupa click!! -> enterState(int state)
		// model.delete();
	}

	public void init() {
		canvas = new CanvasImpl(this);
		model = new ModelImpl(this);
	}

	public void setPreview() {
		gui.getLeftPreview().removeAll();
		gui.getLeftPreview().add(canvas.getPreview(getSVG()));
		gui.getLeftPreview().repaint();
		gui.getLeftPreview().validate();
	}

	@Override
	public String addWire(List<? extends Point> pointList, String idStartPiece, String idStartPin,
			String idEndPiece, String idEndPin) {
		return model.addWire(idStartPiece, idStartPin, idEndPiece, idEndPin, pointList);
	}

}
