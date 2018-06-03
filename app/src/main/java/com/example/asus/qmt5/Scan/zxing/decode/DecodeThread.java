/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.asus.qmt5.Scan.zxing.decode;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

import com.example.asus.qmt5.Scan.zxing.app.CaptureActivity;
import com.example.asus.qmt5.Scan.zxing.app.PreferencesActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;

import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;


/**
 * This thread does all the heavy lifting of decoding the images.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
/**
 * This thread does all the heavy lifting of decoding the images.
 * 二维码扫描处理线程
 */

/**
 * 在DecodeThread类中，主要创建了一个DecodeHandler类，并使用了
 * 一个CountDownLatch同步计数器，保证getHandler()方法返回的Handler
 * 已经被创建。
 * */
final class DecodeThread extends Thread
{

	public static final String BARCODE_BITMAP = "barcode_bitmap";

	private final CaptureActivity activity;
	/**编码类型*/
	private final Hashtable<DecodeHintType, Object> hints;
	private Handler handler;
	/**扫描处理的同步计数器*/
	private final CountDownLatch handlerInitLatch;

	DecodeThread(CaptureActivity activity, Vector<BarcodeFormat> decodeFormats, String characterSet,
                 ResultPointCallback resultPointCallback)
	{

		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);//初始计数值

		hints = new Hashtable<DecodeHintType, Object>(3);

		// The prefs can't change while the thread is running, so pick them up once here.
		if (decodeFormats == null || decodeFormats.isEmpty())
		{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
			decodeFormats = new Vector<BarcodeFormat>();
			if (prefs.getBoolean(PreferencesActivity.KEY_DECODE_1D, true))
			{
				decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
			}
			if (prefs.getBoolean(PreferencesActivity.KEY_DECODE_QR, true))
			{
				decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
			}
			if (prefs.getBoolean(PreferencesActivity.KEY_DECODE_DATA_MATRIX, true))
			{
				decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
			}
		}
		hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

		if (characterSet != null)
		{
			hints.put(DecodeHintType.CHARACTER_SET, characterSet);
		}

		hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
	}

	Handler getHandler()
	{
		try
		/**阻塞当前线程,直到计数器的值为0，在此主要用于保证run()方法已经执行，Handler已经创建*/
		{
			handlerInitLatch.await();
		}
		catch (InterruptedException ie)
		{
			// continue?
		}
		return handler;
	}

	@Override
	public void run()
	{
		Looper.prepare();
		handler = new DecodeHandler(activity, hints);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
