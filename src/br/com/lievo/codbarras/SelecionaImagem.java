package br.com.lievo.codbarras;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

class ImageSelection extends TransferHandler implements Transferable {
	private static final DataFlavor flavors[] = { DataFlavor.imageFlavor };
	private Image image;

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.COPY;
	}

	@Override
	public boolean canImport(JComponent comp, DataFlavor flavor[]) {
		if (!(comp instanceof JLabel))
			return false;

		for (int counter1 = 0, length1 = flavor.length; counter1 < length1; counter1++) {
			for (int counter2 = 0, length2 = flavors.length; counter2 < length2; counter2++)
				if (flavor[counter1].equals(flavors[counter2]))
					return true;
		}

		return false;
	}

	@Override
	public Transferable createTransferable(JComponent comp) {
		image = null;

		if (comp instanceof JLabel) {
			JLabel label = (JLabel) comp;
			Icon icon = label.getIcon();
			
			if (icon instanceof ImageIcon) {
				image = ((ImageIcon) icon).getImage();
				return this;
			}
		}
		return null;
	}
	@Override
	public boolean importData(JComponent comp, Transferable t) {
		if (comp instanceof JLabel) {
			JLabel label = (JLabel) comp;

			if (t.isDataFlavorSupported(flavors[0])) {
				try {
					image = (Image) t.getTransferData(flavors[0]);
					ImageIcon icon = new ImageIcon(image);
					label.setIcon(icon);
					return true;
				} catch (UnsupportedFlavorException ignored) {
					Logger.getLogger(getClass().getName()).log(null, null, ignored);
				} catch (IOException ignored) {
					Logger.getLogger(getClass().getName()).log(null, null, ignored);
				}
			}
		}
		
		return false;
	}
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(DataFlavor.imageFlavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) {
		if (isDataFlavorSupported(flavor))
			return image;
		return null;
	}
}