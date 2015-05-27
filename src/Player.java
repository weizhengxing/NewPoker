
public class Player {
	private static int playerId = IpInfo.PlayerId;
	private String playerName = "Meilier";
	private static int playerMoney;
	private SocketConnection mSocketConnection = null;
	//private HandleMessage mHandleMessage = null;
	private static boolean gameovermsg = false;

	public Player() {
		mSocketConnection = new SocketConnection();
	}

	public String RegMsg() { // �������ע���Լ���id��name
		return SocketConnection.SendMsg(Message.REG_MSG + " " + playerId + " "
				+ playerName + " \n");
	}

	public void AnalysisMsg() { // ������Ϣ��������Ϣ
		Thread t = new Thread(mSocketConnection);
		t.start();
		//mHandleMessage= new HandleMessage();
		//new Thread(mHandleMessage).start();
	}

	public static boolean isGameOver() {
		return gameovermsg;
	}

	public static void SetGameOver(boolean isOver) {
		gameovermsg = isOver;
	}

	public static void setPlayerMoney(int AddMoney) {
		playerMoney += AddMoney;
	}

	public static int getPlayerId() {
		return playerId;
	}
}
