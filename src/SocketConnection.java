import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketOptions;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SocketConnection implements Runnable {
	private static Socket player = null;// ��̬��ÿ������ά��ͬһ������
	// private static StringBuilder mStringBuilder;
	private static String MsgType[];
	private static PrintWriter printWriter = null;
	private static InputStreamReader streamReader = null;// InputStreamReader�ǵͲ�͸߲㴮��֮�������
	private static BufferedReader reader = null; // player.getInputStream()��Socketȡ�����봮��

	// ��������˷������󣬷�����IP��ַ�ͷ����������Ķ˿ں�

	public SocketConnection() {
		try {

			// player = new Socket(IpInfo.ServerIp, IpInfo.ServerPort);
			player = new Socket(IpInfo.ServerIp, IpInfo.ServerPort,
					InetAddress.getByName(IpInfo.ClientIp), IpInfo.ClientPort);
			// ͨ��printWriter ���������������Ϣ
			player.setReuseAddress(true);
			printWriter = new PrintWriter(player.getOutputStream());
			// printWriter.println(Message.REG_MSG + " " + 2333 + " playerName"+
			// " \n");ע�����
			// printWriter.flush();
			System.out.println("�����ѽ���...");
			streamReader = new InputStreamReader(player.getInputStream());
			// �������ݴ���������BufferedReader����ȡ����BufferReader���ӵ�InputStreamReder
			reader = new BufferedReader(streamReader);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		PrintWriter mPrintWriter = null;
		try {
			mPrintWriter = new PrintWriter("/home/game/output.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (!Player.isGameOver()) {
			String servermsg = null;
			try {
				while ((servermsg = reader.readLine()) != null) {
					// mStringBuilder.append(servermsg); // �����Ϣ
					/*
					 * if (servermsg.contains(Message.INQUIRE_MSG_START)) {
					 * SendMsg(" all_in "); } if
					 * (servermsg.contains(Message.GAME_OVER_MSG)) {
					 * CloseConnection(); Player.SetGameOver(true); }
					 */

					mPrintWriter.println(servermsg);
					mPrintWriter.flush();
					MessageQueue.mBlockQueue.put(servermsg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String SendMsg(String msg) { // ������Ϣ
		printWriter.println(msg);
		printWriter.flush();
		return msg;
	}

	public static void CloseConnection() {
		try {
			player.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String[] getMsgList() {
		return MsgType;
	}

	public static Socket getSocket() {
		return player;

	}

	public static PrintWriter getPrintWriter() {
		return printWriter;
	}

}
