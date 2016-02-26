package android.hardware.barcode;

import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * ɨ��     ��Ʒ����Ϣ�࣬��ȡ  ɨ����Ʒ�������룺
 * android.hardware.barcode
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-23
 */
public class Scanner {
	static public final int BARCODE_READ = 10;
	static public final int BARCODE_NOREAD = 12;
	static Boolean m_bASYC = false;
	static int m_nCommand = 0;
	static public Handler m_handler = null;
	
	/** �����Գ����ǲ���AudioTrack����һ���̶�Ƶ�ʵ���������ʾ���ģ�����Բ���ϵͳ�����߲���һ����Ƶ*/
	private final static int duration = 1; // seconds
	private final static int sampleRate = 2000;         
	private final static int numSamples = duration * sampleRate;
	private final static double sample[] = new double[numSamples];
	private final static double freqOfTone = 1600; // hz 
	
	private final static byte generatedSnd[] = new byte[2 * numSamples];

	/**
	 * ͬ��ɨ��һά/��ά�룬ɨ�費�����ȴ�һ��ʱ�䣬Ħ��������ɨ��ͷ�ȴ��3�룬
	 * �����з�ɨ��ͷ��ȴ�10�룬�Զ�ʶ���������з�ɨ��ͷ����Ħ��������ɨ��ͷ
	 * 
	 * @return ������ɨ�赽�����룬���ûɨ����Ϊ���ַ���
	 */
	static public native String ReadSCAAuto();

	/**
	 * ͬ��ɨ��һά/��ά�룬ɨ�費�����ȴ�һ��ʱ�䣬Ħ��������ɨ��ͷ�ȴ��3�룬 �����з�ɨ��ͷ��ȴ�10��
	 * 
	 * @param nCode
	 *            ��ɨ��ͷ��ʾ�������з�ɨ��ͷ0x55,Ħ������ɨ��ͷ0x00
	 * @return ������ɨ�赽�����룬���ûɨ����Ϊ���ַ���
	 */
	static public native String ReadSCA(int nCode);

	/**
	 * ͬ��ɨ��һά/��ά�룬ɨ�費�����ȴ�һ��ʱ�䣬Ħ��������ɨ��ͷ�ȴ��3�룬 �����з�ɨ��ͷ��ȴ�10��
	 * 
	 * @param nCommand
	 *            :ɨ��ͷ��ʾ�������з�ɨ��ͷ0x55,Ħ������ɨ��ͷ0x00
	 * @param nCode
	 *            :����ı��뷽ʽ��GB2312Ϊ1,UTFΪ0
	 * @return ������ɨ�赽�����룬���ûɨ����Ϊ���ַ���
	 */
	static public native String ReadSCAEx(int nCommand, int nCode);

	/**
	 * ͬ��ɨ��һά/��ά�룬ɨ�費�����ȴ�һ��ʱ�䣬Ħ��������ɨ��ͷ�ȴ��3�룬 �����з�ɨ��ͷ��ȴ�10��
	 * 
	 * @param nCommand
	 *            :ɨ��ͷ��ʾ�������з�ɨ��ͷ0x55,Ħ������ɨ��ͷ0x00
	 * @param buf
	 *            :����������ݣ���������������뱾���ʽת���ַ���
	 * @return ������ɨ�赽�����룬���ûɨ����Ϊ���ַ���
	 */
	static public native int ReadDataSCA(int nCommand, byte[] buf);

	/**
	 * ͬ��ɨ��һά/��ά�룬ɨ�費�����ȴ�һ��ʱ�䣬Ħ��������ɨ��ͷ�ȴ��3�룬 �����з�ɨ��ͷ��ȴ�10��
	 * 
	 * @param nCommand
	 *            :ɨ��ͷ��ʾ�������з�ɨ��ͷ0x55,Ħ������ɨ��ͷ0x00
	 * @return :����ɨ�赽����������
	 */
	static public native byte[] ReadData(int nCommand);

	/**
	 * ��ʼ���豸
	 * 
	 * @return ���ɹ�����0
	 */
	static public native int InitSCA();

	/**
	 * �첽ɨ�裬���øú����ᴴ��һ��ɨ���߳�ȥɨ�裬 ɨ�赽�������ɨ�賬ʱ����ͨ��handle������Ϣ�������ߣ�
	 * ����ʹ���첽ɨ��Ļ���������Ľ��մ����ﴴ����handle, ����handle����m_handler��ɨ�赽���뷢��Message
	 * BARCODE_READ�� ��ʱ����Message BARCODE_NOREAD
	 */
	static public void Read() {
		if (m_bASYC) {
			return;
		} else {
			// m_nCommand=nCode;
			StartASYC();
		}
	}

	/**
	 * ɨ���߳�
	 */
	static void StartASYC() {
		m_bASYC = true;
		Thread thread = new Thread(new Runnable() {
			public void run() {

				if (m_handler != null) {

					String str = ReadSCAAuto();
					Message msg = new Message();
					msg.what = str.length() > 0 ? BARCODE_READ : BARCODE_NOREAD;
					msg.obj = str;

					m_handler.sendMessage(msg);

				}

				m_bASYC = false;
			}
		});
		thread.start();
	}

	/**
	 * ���������Ϣ�����԰�String��������Ϣ��ʽ���͸�ϵͳ
	 * 
	 * @param str
	 *            ��Ҫ���͵��ַ���
	 */
	static public void SendString(String str) {
		try {
			Runtime.getRuntime().exec("input keyevent 66");
			Runtime.getRuntime().exec("input text " + str);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static private void showToast(String str) {
		Toast.makeText(null, str, Toast.LENGTH_SHORT).show();
	}

	static {

		System.loadLibrary("hardware-print");
	}

	public static void play()
    {
 		Thread thread = new Thread(new Runnable() {
		   public void run() {
		     genTone();
		     playSound();   
		   }  
		 });   
		 thread.start();
    } 
	
	public static void genTone(){
    	// fill out the array
    	for (int i = 0; i < numSamples; ++i) {
    	sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
    	}

    	// convert to 16 bit pcm sound array
    	// assumes the sample buffer is normalised.   
    	int idx = 0;
    	for (double dVal : sample) {
    	short val = (short) (dVal * 32767);
    	generatedSnd[idx++] = (byte) (val & 0x00ff);
    	generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
    	}
 
    	}

    public static void playSound(){
    	AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
    			8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
    			AudioFormat.ENCODING_PCM_16BIT, numSamples,
    			AudioTrack.MODE_STATIC);
    			audioTrack.write(generatedSnd, 0, numSamples);
    			audioTrack.play();
    			try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			audioTrack.release();   

    	}
	
}
