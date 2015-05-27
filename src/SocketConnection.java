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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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

					if (servermsg.equals(Message.SEAT_INFO_MSG_START)) {
						seatInfoMsgHandle();
					} else if (servermsg.equals(Message.BLIND_MSG_START)) {
						blindMsgHandle();
					} else if (servermsg.equals(Message.HOLD_CARD_MSG_START)) {
						holdCardMsgHandle();
					} else if (servermsg.equals(Message.INQUIRE_MSG_START)) {
						inquireMsgHandle();
					} else if (servermsg.equals(Message.FLOP_MSG_START)) {
						flopMsgHandle();
					} else if (servermsg.equals(Message.TURN_MSG_START)) {
						turnMsgHandle();
					} else if (servermsg.equals(Message.RIVER_MSG_START)) {
						riverMsgHandle();
					} else if (servermsg.equals(Message.SHOW_DOWN_MSG_START)) {
						showDownMsgHandle();
					} else if (servermsg.equals(Message.POT_WIN_MSG_START)) {
						potWinMsgHandle();
					} else if (servermsg.equals(Message.GAME_OVER_MSG)) {
						gameOverMsgHandle();

					}

					mPrintWriter.println(servermsg);
					mPrintWriter.flush();
				}
			} catch (IOException e) {
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

	// ��Ϣֱ�Ӵ���ʹ����������̫����
	private void seatInfoMsgHandle() {
		String tmp = null;
		int i = 0;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.SEAT_INFO_MSG_END)) {
				// SeatInfo.Pid[i].PlayerId =
				// Integer.valueOf(tmp.split(" ")[1]);
				// SeatInfo.Pid[i].jetton = Integer.valueOf(tmp.split(" ")[2]);
				// SeatInfo.Pid[i].money = Integer.valueOf(tmp.split(" ")[3]);
				i++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void gameOverMsgHandle() {
		CloseConnection();
		Player.SetGameOver(true);
	}

	private void blindMsgHandle() {
		String tmp = null;
		int i = 0;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.SEAT_INFO_MSG_END)) {
				// Blind.BlindMoney[i] = Integer.valueOf(tmp.split(" ")[1]);
				i++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void holdCardMsgHandle() {
		String tmp = null;
		int i = 0;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.HOLD_CARD_MSG_END)) {
				// HoldCards.mHoldCards[i].color = tmp.split(" ")[0];
				// HoldCards.mHoldCards[i].point = tmp.split(" ")[1];
				i++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void inquireMsgHandle() {
		String tmp = null;
		int i = 0;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.INQUIRE_MSG_END)) {
				/*
				 * if (tmp.equals("total")) { InquireMsg.TotalPot =
				 * Integer.valueOf(tmp.split(" ")[2]); }
				 * InquireMsg.Others[i].pid =
				 * Integer.valueOf(tmp.split(" ")[0]);
				 * InquireMsg.Others[i].jetton = Integer
				 * .valueOf(tmp.split(" ")[1]); InquireMsg.Others[i].money =
				 * Integer.valueOf(tmp.split(" ")[2]); InquireMsg.Others[i].bet
				 * = Integer.valueOf(tmp.split(" ")[3]);
				 * InquireMsg.Others[i].action = tmp.split(" ")[4];
				 */
				i++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void flopMsgHandle() {
		String tmp = null;
		int i = 0;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.FLOP_MSG_END)) {
				// FlopCards.mFlopCards[i].color = tmp.split(" ")[0];
				// FlopCards.mFlopCards[i].point = tmp.split(" ")[1];
				i++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void turnMsgHandle() {
		String tmp = null;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.FLOP_MSG_END)) {
				// TurnCard.mTurnCard.color = tmp.split(" ")[0];
				// TurnCard.mTurnCard.point = tmp.split(" ")[1];
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void riverMsgHandle() {
		String tmp = null;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.FLOP_MSG_END)) {
				// RiverCard.mRiverCard.color = tmp.split(" ")[0];
				// RiverCard.mRiverCard.point = tmp.split(" ")[1];
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showDownMsgHandle() {
		String tmp = null;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.SHOW_DOWN_MSG_END)) {
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void potWinMsgHandle() {
		String tmp = null;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.POT_WIN_MSG_END)) {
				if ((tmp.split(" ")[0].equals(String.valueOf(Player
						.getPlayerId())))) {
					// Player.setPlayerMoney(Integer.valueOf(tmp.split(" ")[1]));
				}

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public String SeatInfoMsg() { return SocketConnection.ReadMsg(); }
	 * 
	 * public String GameOverMag() { return SocketConnection.ReadMsg(); }
	 * 
	 * public String BlindMsg() { return SocketConnection.ReadMsg();
	 * 
	 * }
	 * 
	 * public String HoldCardsMsg() { return SocketConnection.ReadMsg();
	 * 
	 * }
	 * 
	 * public String InquireMsg() { return SocketConnection.ReadMsg();
	 * 
	 * }
	 */

	public String ActionMsg(String msg) {
		return SocketConnection.SendMsg(msg);

	}
	/*
	 * public String FlopMsg() { return SocketConnection.ReadMsg();
	 * 
	 * }
	 * 
	 * public String TurnMsg() { return SocketConnection.ReadMsg();
	 * 
	 * }
	 * 
	 * public String RiverMsg() { return SocketConnection.ReadMsg();
	 * 
	 * }
	 * 
	 * public String ShowDownMsg() { return SocketConnection.ReadMsg();
	 * 
	 * }
	 * 
	 * public String PotWinMsg() { return SocketConnection.ReadMsg(); }
	 */

}
