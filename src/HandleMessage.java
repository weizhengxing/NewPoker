public class HandleMessage implements Runnable {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				msgType(MessageQueue.mBlockQueue.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String msgType(String msg) { // 解析消息
		if (msg.contains(Message.SEAT_INFO_MSG_START)) {
			seatInfoMsgHandle();
			return Message.SEAT_INFO_MSG_START; // 在此处处理座次消息
		}
		if (msg.contains(Message.GAME_OVER_MSG)) {
			gameOverMsgHandle();
			return Message.GAME_OVER_MSG; // 在此处处理游戏结束消息
		}
		if (msg.contains(Message.BLIND_MSG_START)) {
			blindMsgHandle();
			return Message.BLIND_MSG_START;// 在这里处理盲注消息
		}
		if (msg.contains(Message.HOLD_CARD_MSG_START)) { // 在此处处理手牌消息
			holdCardMsgHandle();
			return Message.HOLD_CARD_MSG_START;
		}
		if (msg.contains(Message.INQUIRE_MSG_START)) {
			inquireMsgHandle();
			SocketConnection.SendMsg(" all_in ");
			return Message.INQUIRE_MSG_START; // 在这里处理询问消息
		}
		if (msg.contains(Message.FLOP_MSG_START)) {
			flopMsgHandle();
			return Message.FLOP_MSG_START; // 在这里处理公牌消息
		}
		if (msg.contains(Message.TURN_MSG_START)) {
			turnMsgHandle();
			return Message.TURN_MSG_START; // 在这里处理转牌消息
		}
		if (msg.contains(Message.RIVER_MSG_START)) {
			riverMsgHandle();
			return Message.RIVER_MSG_START; // 在这里处理河牌消息
		}
		if (msg.contains(Message.SHOW_DOWN_MSG_START)) {
			showDownMsgHandle();
			return Message.SHOW_DOWN_MSG_START; // 在这里处理摊牌消息
		}
		if (msg.contains(Message.POT_WIN_MSG_START)) {
			potWinMsgHandle();
			return Message.POT_WIN_MSG_START; // 在这里处理奖池分配消息
		}
		return "ERROR";
	}

	private void seatInfoMsgHandle() {
		String tmp = null;
		int i = 0;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.contains(Message.SEAT_INFO_MSG_END)) {
				SeatInfo.Pid[i].PlayerId = Integer.valueOf(tmp.split(" ")[1]);
				SeatInfo.Pid[i].jetton = Integer.valueOf(tmp.split(" ")[2]);
				SeatInfo.Pid[i].money = Integer.valueOf(tmp.split(" ")[3]);
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
		Player.SetGameOver(true);
		SocketConnection.CloseConnection();
	}

	private void blindMsgHandle() {
		String tmp = null;
		int i = 0;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.contains(Message.SEAT_INFO_MSG_END)) {
				Blind.BlindMoney[i] = Integer.valueOf(tmp.split(" ")[1]);
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
					.contains(Message.HOLD_CARD_MSG_END)) {
				HoldCards.mHoldCards[i].color = tmp.split(" ")[0];
				HoldCards.mHoldCards[i].point = tmp.split(" ")[1];
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
					.contains(Message.INQUIRE_MSG_END)) {
				if (tmp.contains("total")) {
					InquireMsg.TotalPot = Integer.valueOf(tmp.split(" ")[2]);
				}
				InquireMsg.Others[i].pid = Integer.valueOf(tmp.split(" ")[0]);
				InquireMsg.Others[i].jetton = Integer
						.valueOf(tmp.split(" ")[1]);
				InquireMsg.Others[i].money = Integer.valueOf(tmp.split(" ")[2]);
				InquireMsg.Others[i].bet = Integer.valueOf(tmp.split(" ")[3]);
				InquireMsg.Others[i].action = tmp.split(" ")[4];
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
					.contains(Message.FLOP_MSG_END)) {
				FlopCards.mFlopCards[i].color = tmp.split(" ")[0];
				FlopCards.mFlopCards[i].point = tmp.split(" ")[1];
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
					.contains(Message.FLOP_MSG_END)) {
				TurnCard.mTurnCard.color = tmp.split(" ")[0];
				TurnCard.mTurnCard.point = tmp.split(" ")[1];
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
					.contains(Message.FLOP_MSG_END)) {
				RiverCard.mRiverCard.color = tmp.split(" ")[0];
				RiverCard.mRiverCard.point = tmp.split(" ")[1];
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
					.contains(Message.SHOW_DOWN_MSG_END)) {

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
					.contains(Message.POT_WIN_MSG_END)) {
				if ((tmp.split(" ")[0].contains(String.valueOf(Player
						.getPlayerId())))) {
					Player.setPlayerMoney(Integer.valueOf(tmp.split(" ")[1]));
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
