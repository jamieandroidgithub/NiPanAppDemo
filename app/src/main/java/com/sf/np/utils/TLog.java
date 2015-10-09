package com.sf.np.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;




public class TLog {

	/**
	 * 本地写死的调试开关,在发布时关掉
	 */
	private static boolean mHardDebugFlag = true;
	
	//	在启动时，是否要弹测试版本的法律免责申明，正式版本关掉该标志
	public static boolean mCopyRightDeclareFlag = false;

	public static final String NET_LOG = "net_log_";

	public static final String SERVER_ERROR_LOG = "serv_error_log_";

	private static Callback mCallBack = null;
	private static Handler mUiHandler = null;
	private static final int MSG_PROCOTOL_ERROR = 77;
	private static final int MSG_DEBUG_TOAST = 78;

	private static HashMap<String, ArrayList<String>> mUseTimeStringList = new HashMap<String, ArrayList<String>>();
	private static HashMap<String, ArrayList<Long>> mUseTimeLongList = new HashMap<String, ArrayList<Long>>();


	public static void v(String t, String m) {
		if (mHardDebugFlag) {
			if (m == null) {
				m = "............";
			}
			android.util.Log.v(t, m);
		}
	}
	
	public static void i(String t, String m) {
		if (mHardDebugFlag) {
			if (m == null) {
				m = "............";
			}
			android.util.Log.i(t, m);
		}
	}
	
	public static void d(String t, String m) {
		if (mHardDebugFlag) {
			if (m == null) {
				m = "............";
			}
			android.util.Log.d(t, m);
		}
	}
	
	public static void e(String t, String m, Throwable er)
	{
		e(t, m);
	}

	public static void e(String t, String m) {
		if (mHardDebugFlag) {
//			BufferedWriter bos = null;
//			try {
//				String path = ExceptionHandler.getStorePath(DLApp.getContext(), ExceptionHandler.LOG_PATH_SDCARD)
//						+ "/" + "file_list" + new Date(System.currentTimeMillis()).getDate() + ".txt";
//				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path, true),"UTF-8");
//				
//				bos = new BufferedWriter(out);
//				bos.write("\t\n" + t + "**********************\t\n");
//				android.text.format.Time tmtxt = new android.text.format.Time();
//				tmtxt.setToNow();
//				bos.write(tmtxt.format("%Y-%m-%d %H:%M:%S") + "\n");
//				bos.write(m);
//				bos.write("\t\n");
//				bos.flush();
//				bos.close();
//				bos = null;
//			} catch (Exception ebos) {
//				// ebos.printStackTrace();
//			} finally {
//				try {
//					if (bos != null) {
//						bos.close();
//						bos = null;
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			android.util.Log.e(t, m);
		}
	}

//	public static void w(String m) {
//		w("", m);
//		v("", m);
//	}

//	public static void w(String t, String m) {
//		if (isForServerDebug()) {
//			v(t, m);
//			BufferedWriter bos = null;
//			try {
//				String path = ExceptionHandler.getStorePath(DLApp.getContext(), ExceptionHandler.LOG_PATH_SDCARD) + "/" + NET_LOG
//						+ new Date(System.currentTimeMillis()).getDate() + ".txt";
//				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path, true),"UTF-8");
//				bos = new BufferedWriter(out);
//				
////				bos = new BufferedWriter(new FileWriter(ExceptionHandler.getStorePath(DLApp.getContext(), ExceptionHandler.LOG_PATH_SDCARD) + "/" + NET_LOG
////						+ new Date(System.currentTimeMillis()).getDate() + ".txt", true));
////				bos.write("\t\n" + t + "**********************\t\n");
////				bos.write("APP_VERSION:" + ExceptionHandler.G.APP_VERSION + "\t\n");
////				bos.write("PHONE_MODEL:" + ExceptionHandler.G.PHONE_MODEL + "\t\n");
////				bos.write("ANDROID_VERSION:" + ExceptionHandler.G.ANDROID_VERSION + "\t\n");
////				android.text.format.Time tmtxt = new android.text.format.Time();
////				tmtxt.setToNow();
////				bos.write(tmtxt.format("%Y-%m-%d %H:%M:%S") + "\n");
//				bos.write(m);
//				bos.write("\t\n");
//				bos.flush();
//				bos.close();
//				bos = null;
//			} catch (Exception ebos) {
//				// ebos.printStackTrace();
//			} finally {
//				try {
//					if (bos != null) {
//						bos.close();
//						bos = null;
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	private static Executor singgleThread = Executors.newSingleThreadExecutor();
	private static long logTime = System.currentTimeMillis() / 1000;
//	public static void s(final String t, final String m) {
//		singgleThread.execute(new Runnable() {
//
//			@Override
//			public void run() {
//				BufferedWriter bos = null;
//				try {
//					String path = FileDownloadPath.mLog.getPath() + "/protocol"+logTime+".log";
//					OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path, true),"UTF-8");
//
//					bos = new BufferedWriter(out);
//					bos.write("\t\n" + t + "**********************\t\n");
//					android.text.format.Time tmtxt = new android.text.format.Time();
//					tmtxt.setToNow();
//					bos.write(tmtxt.format("%Y-%m-%d %H:%M:%S") + "\n");
//					bos.write(m);
//					bos.write("\t\n");
//					bos.flush();
//					bos.close();
//					bos = null;
//				} catch (Exception ebos) {
//					// ebos.printStackTrace();
//				} finally {
//					try {
//						if (bos != null) {
//							bos.close();
//							bos = null;
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//
//	}

//	public static void protocolError(String url, String funName, String s, final ResHeader resHeader, int cmd) {
//		if (isForDebug()) {
//			url = (url == null ? "" : url);
//			funName = (funName == null ? "" : funName);
//			String alert = s + "\n" + "[server]" + "\n" + "url:" + url + "\n" + "funName:" + funName + "\n" + " svrcode:"
//					+ (resHeader == null ? "" : resHeader.svrcode) + "\n" + " busicode:" + (resHeader == null ? "" : resHeader.busicode) + "\n" + " msg:"
//					+ (resHeader == null ? "" : resHeader.msg) + "\n" + "[client] cmd:" + cmd + "\n";
//
//			if (mUiHandler != null) {
//				Message msg = Message.obtain();
//				msg.what = MSG_PROCOTOL_ERROR;
//				msg.obj = alert;
//				mUiHandler.sendMessage(msg);
//			}
//			BufferedWriter bos = null;
//			try {
//				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(ExceptionHandler.getStorePath(DLApp.getContext(), ExceptionHandler.LOG_PATH_SDCARD) + "/" + SERVER_ERROR_LOG
//						+ new Date(System.currentTimeMillis()).getDate() + ".txt", true),"UTF-8");
//				bos = new BufferedWriter(out);
//				
////				bos = new BufferedWriter(new FileWriter(ExceptionHandler.getStorePath(DLApp.getContext(), ExceptionHandler.LOG_PATH_SDCARD) + "/" + SERVER_ERROR_LOG
////						+ new Date(System.currentTimeMillis()).getDate() + ".txt", true));
//				bos.write("\t\n**********************\t\n");
//				android.text.format.Time tmtxt = new android.text.format.Time();
//				tmtxt.setToNow();
//				bos.write(tmtxt.format("%Y-%m-%d %H:%M:%S") + "\n");
//				bos.write(alert);
//				bos.write("\t\n");
//				bos.flush();
//				bos.close();
//				bos = null;
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					if (bos != null) {
//						bos.close();
//						bos = null;
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	public static void debugToast(String text) {
		if (mHardDebugFlag) {
			if (mUiHandler != null) {
				Message msg = Message.obtain();
				msg.what = MSG_DEBUG_TOAST;
				msg.obj = text;
				mUiHandler.sendMessage(msg);
			}
		}
	}

//	public static void createCallback() {
//		if (mHardDebugFlag) {
//			if (mCallBack == null) {
//				mCallBack = new Callback() {
//
//					@Override
//					public boolean handleMessage(Message msg) {
//						switch (msg.what) {
//						case MSG_PROCOTOL_ERROR: {
//							String alert = (String) msg.obj;
//							Toast t = Toast.makeText(AppContext.get(), "Debug版本Toast \n更多信息到"  + "/log/serv_error_log_.txt \n\n" + alert,
//									Toast.LENGTH_LONG);
//							t.setGravity(Gravity.CENTER, 0, 0);
//							t.show();
//						}
//							break;
//						case MSG_DEBUG_TOAST: {
//							String alert = (String) msg.obj;
//							Toast t = Toast.makeText(AppContext.get(), "Debug版本Toast \n\n" + alert, Toast.LENGTH_LONG);
//							t.setGravity(Gravity.CENTER, 0, 0);
//							t.show();
//						}
//							break;
//						default:
//							break;
//						}
//						return true;
//					}
//				};
//
//				mUiHandler = new Handler(mCallBack);
//			}
//		}
//	}

	public static void destory() {
		mCallBack = null;
		mUiHandler = null;
	}

	public static void time(String logPoint) {
		time("UseTime", logPoint, false);
	}

	public static void time(String logPoint, boolean print) {
		time("UseTime", logPoint, print);
	}

	public static void time(String tag, String logPoint) {
		time(tag, logPoint, false);
	}

	public static void time(String tag, String logPoint, boolean print) {
		if (!mHardDebugFlag) {
			return;
		}
		// Log.v(tag, logPoint);

		ArrayList<String> sList = mUseTimeStringList.get(tag);
		if (sList == null) {
			sList = new ArrayList<String>();
			mUseTimeStringList.put(tag, sList);
		}
		sList.add(logPoint);

		ArrayList<Long> lList = mUseTimeLongList.get(tag);
		if (lList == null) {
			lList = new ArrayList<Long>();
			mUseTimeLongList.put(tag, lList);
		}
		lList.add(System.currentTimeMillis());

		if (print) {
			StringBuffer sb = new StringBuffer();
			long lastT = lList.get(0);
			sb.append("total time:");
			sb.append(lList.get(lList.size() - 1) - lastT);
			sb.append(" ");
			for (int i = 0; i < sList.size(); i++) {
				sb.append(lList.get(i) - lastT);
				lastT = lList.get(i);
				sb.append(" ");
				sb.append(sList.get(i));
				sb.append(" ");
			}
			TLog.v(tag, sb.toString());

			sList.clear();
			lList.clear();
		}
	}
	
	/**
	 * 打印调用栈
	 */
	static public void printCallTraces(String tag) {
		if (mHardDebugFlag) {
			java.util.Map<Thread, StackTraceElement[]> ts = Thread.getAllStackTraces();
			StackTraceElement[] ste = ts.get(Thread.currentThread());
			v(tag, "======================start============================");
			for (StackTraceElement s : ste) {
				v(tag, s.toString());
			}
			v(tag, "=======================end============================");
		}
	}

	public static boolean isDebugFlag() {
		return mHardDebugFlag;
	}

	public static void setHardDebugFlag(boolean mHardDebugFlag) {
		TLog.mHardDebugFlag = mHardDebugFlag;
	}

	public static String mQQDLActState = null;

	public static String mCurActState = null;


	public static void w(String t, String m) {
		if (mHardDebugFlag) {
			if (m == null) {
				m = "............";
			}
			android.util.Log.w(t, m);
		}
	}
}
