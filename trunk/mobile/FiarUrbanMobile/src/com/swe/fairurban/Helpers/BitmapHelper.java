package com.swe.fairurban.Helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;

public class BitmapHelper {
	public static Integer[] GetThumbnailSize(Bitmap bmp, int fitWidth, int fitHeight)
	{
		Integer[] result = new Integer[2];
		
		
		int orgWidth = bmp.getWidth();
		
		int orgHeight = bmp.getHeight();
		
		int resultWidth = orgWidth, resultHeight = orgHeight;
		
		if (orgWidth > fitWidth || orgHeight > fitHeight) {
		
			
			 if (orgWidth > orgHeight)
	         {
				 resultWidth = fitWidth;
				 resultHeight = (resultWidth * orgHeight) / orgWidth;
	         }
	         else
	         {
	        	 resultHeight = fitHeight;
	        	 resultWidth = (resultHeight * orgWidth) / orgHeight;
	         }

			
		}
		
		 result[0] = resultWidth;
		 result[1] = resultHeight;
		
		
		
		return result;
	}
}
