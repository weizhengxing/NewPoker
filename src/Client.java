public class Client { // 程序入口

	public static void main(String[] args) {
		IpInfo.ServerIp = args[0];
		IpInfo.ServerPort = Integer.valueOf(args[1]).intValue();
		IpInfo.ClientIp = args[2];
		IpInfo.ClientPort = Integer.valueOf(args[3]).intValue();
		IpInfo.PlayerId = Integer.valueOf(args[4]).intValue();
		Player Meilier = new Player();
		// 游戏没有结束
		while (!SocketConnection.getSocket().isConnected()) {

		}
		Meilier.RegMsg();
		Meilier.AnalysisMsg();
	}
}
