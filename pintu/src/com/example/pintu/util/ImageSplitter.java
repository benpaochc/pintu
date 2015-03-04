package com.example.pintu.util;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.graphics.Bitmap;

public class ImageSplitter {

	public ImageSplitter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param bitmap
	 * @param num
	 * @return
	 */
	public static List<ImagePiece> splitImage(Bitmap bitmap,int num){
		
		List<ImagePiece> imagePieces = new ArrayList<ImagePiece>();
		
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		int pieceWitdth = Math.min(width, height)/num;
		
		for (int row = 0; row < num; row++) {
			for (int line = 0; line < num; line++) {
				
				ImagePiece imagePiece = new ImagePiece();
				imagePiece.setIndex(line + row*num);
				
				int x = line*pieceWitdth;
				int y = row*pieceWitdth;
				
				imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y, pieceWitdth, pieceWitdth));
				
				imagePieces.add(imagePiece);
			}
		}
		return imagePieces;
	}
	
}
