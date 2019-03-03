package com.memory.platform.global;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

public class TwoDimensionCodeImage implements QRCodeImage {

	BufferedImage bufImg;

	public TwoDimensionCodeImage(BufferedImage bufImg) {
		// TODO Auto-generated constructor stub
		this.bufImg = bufImg;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return bufImg.getHeight();
	}

	@Override
	public int getPixel(int x, int y) {
		// TODO Auto-generated method stub
		return bufImg.getRGB(x, y);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return bufImg.getWidth();
	}

}
