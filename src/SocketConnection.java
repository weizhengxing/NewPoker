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
	private static Socket player = null;// 静态类每个进程维护同一个链接
	// private static StringBuilder mStringBuilder;
	private static String MsgType[];
	private static PrintWriter printWriter = null;
	private static InputStreamReader streamReader = null;// InputStreamReader是低层和高层串流之间的桥梁
	private static BufferedReader reader = null; // player.getInputStream()从Socket取得输入串流

	// 向服务器端发送请求，服务器IP地址和服务器监听的端口号

	public SocketConnection() {
		try {

			// player = new Socket(IpInfo.ServerIp, IpInfo.ServerPort);
			player = new Socket(IpInfo.ServerIp, IpInfo.ServerPort,
					InetAddress.getByName(IpInfo.ClientIp), IpInfo.ClientPort);
			// 通过printWriter 来向服务器发送消息
			player.setReuseAddress(true);
			printWriter = new PrintWriter(player.getOutputStream());
			// printWriter.println(Message.REG_MSG + " " + 2333 + " playerName"+
			// " \n");注册测试
			// printWriter.flush();
			System.out.println("连接已建立...");
			streamReader = new InputStreamReader(player.getInputStream());
			// 链接数据串流，建立BufferedReader来读取，将BufferReader链接到InputStreamReder
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
					// mStringBuilder.append(servermsg); // 添加消息

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

	public static String SendMsg(String msg) { // 发送消息
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

	// 消息直接处理使用阻塞队列太慢了
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
