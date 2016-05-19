/****************************************************************************
Copyright (c) 2008-2010 Ricardo Quesada
Copyright (c) 2010-2012 cocos2d-x.org
Copyright (c) 2011      Zynga Inc.
Copyright (c) 2013-2014 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxHelper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class AppActivity extends Cocos2dxActivity {
	static public Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					callNativeFunction1();
				} else if (msg.what == 1) {
					callNativeFunction2();
				}
			}
		};
	}

	private native void addSpriteInNative();
	private native void removeSpriteInNative();

	public void callNativeFunction1() {
		// Java 쪽에서 run(){...} 안에서 Native Function 을 호출하면
		// C 쪽에서 glthread 를 사용해도 쓰레드 관련 에러가 나지 않습니다.
		Cocos2dxHelper.runOnGLThread(new Runnable() {

			@Override
			public void run() {
				addSpriteInNative();

			}

		});
	}
	public void callNativeFunction2() {
		// Java 쪽에서 run(){...} 안에서 Native Function 을 호출하면
		// C 쪽에서 glthread 를 사용해도 쓰레드 관련 에러가 나지 않습니다.
		Cocos2dxHelper.runOnGLThread(new Runnable() {

			@Override
			public void run() {
				removeSpriteInNative();

			}

		});
	}

	public static void AddSprite() {
		handler.sendEmptyMessage(0);
	}

	public static void RemoveSprite() {
		handler.sendEmptyMessage(1);
	}
}
