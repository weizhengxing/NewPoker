public class HandleMessage implements Runnable {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			String tmp=null;
			try {
				tmp=MessageQueue.mBlockQueue.take();
				if(tmp.equals(Message.INQUIRE_MSG_START)){
					SocketConnection.SendMsg(" all_in ");
				}
				msgType(tmp);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String msgType(String msg) { // ������Ϣ
		if (msg.equals(Message.SEAT_INFO_MSG_START)) {
			seatInfoMsgHandle();
			return Message.SEAT_INFO_MSG_START; // �ڴ˴�����������Ϣ
		}
		if (msg.equals(Message.GAME_OVER_MSG)) {
			gameOverMsgHandle();
			return Message.GAME_OVER_MSG; // �ڴ˴�������Ϸ������Ϣ
		}
		if (msg.equals(Message.BLIND_MSG_START)) {
			blindMsgHandle();
			return Message.BLIND_MSG_START;// �����ﴦ��äע��Ϣ
		}
		if (msg.equals(Message.HOLD_CARD_MSG_START)) { // �ڴ˴�����������Ϣ
			holdCardMsgHandle();
			return Message.HOLD_CARD_MSG_START;
		}
		if (msg.equals(Message.INQUIRE_MSG_START)) {
			inquireMsgHandle();
			SocketConnection.SendMsg(" all_in ");
			return Message.INQUIRE_MSG_START; // �����ﴦ��ѯ����Ϣ
		}
		if (msg.equals(Message.FLOP_MSG_START)) {
			flopMsgHandle();
			return Message.FLOP_MSG_START; // �����ﴦ������Ϣ
		}
		if (msg.equals(Message.TURN_MSG_START)) {
			turnMsgHandle();
			return Message.TURN_MSG_START; // �����ﴦ��ת����Ϣ
		}
		if (msg.equals(Message.RIVER_MSG_START)) {
			riverMsgHandle();
			return Message.RIVER_MSG_START; // �����ﴦ�������Ϣ
		}
		if (msg.equals(Message.SHOW_DOWN_MSG_START)) {
			showDownMsgHandle();
			return Message.SHOW_DOWN_MSG_START; // �����ﴦ��̯����Ϣ
		}
		if (msg.equals(Message.POT_WIN_MSG_START)) {
			potWinMsgHandle();
			return Message.POT_WIN_MSG_START; // �����ﴦ���ط�����Ϣ
		}
		return "ERROR";
	}

	private void seatInfoMsgHandle() {
		String tmp = null;
		int i = 0;
		try {
			while (!(tmp = MessageQueue.mBlockQueue.take())
					.equals(Message.SEAT_INFO_MSG_END)) {
				//SeatInfo.Pid[i].PlayerId = Integer.valueOf(tmp.split(" ")[1]);
				//SeatInfo.Pid[i].jetton = Integer.valueOf(tmp.split(" ")[2]);
				//SeatInfo.Pid[i].money = Integer.valueOf(tmp.split(" ")[3]);
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
					.equals(Message.SEAT_INFO_MSG_END)) {
				//Blind.BlindMoney[i] = Integer.valueOf(tmp.split(" ")[1]);
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
				//HoldCards.mHoldCards[i].color = tmp.split(" ")[0];
				//HoldCards.mHoldCards[i].point = tmp.split(" ")[1];
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
				/*if (tmp.equals("total")) {
					InquireMsg.TotalPot = Integer.valueOf(tmp.split(" ")[2]);
				}
				InquireMsg.Others[i].pid = Integer.valueOf(tmp.split(" ")[0]);
				InquireMsg.Others[i].jetton = Integer
						.valueOf(tmp.split(" ")[1]);
				InquireMsg.Others[i].money = Integer.valueOf(tmp.split(" ")[2]);
				InquireMsg.Others[i].bet = Integer.valueOf(tmp.split(" ")[3]);
				InquireMsg.Others[i].action = tmp.split(" ")[4];*/
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
				//FlopCards.mFlopCards[i].color = tmp.split(" ")[0];
				//FlopCards.mFlopCards[i].point = tmp.split(" ")[1];
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
				//TurnCard.mTurnCard.color = tmp.split(" ")[0];
				//TurnCard.mTurnCard.point = tmp.split(" ")[1];
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
				//RiverCard.mRiverCard.color = tmp.split(" ")[0];
				//RiverCard.mRiverCard.point = tmp.split(" ")[1];
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
				//	Player.setPlayerMoney(Integer.valueOf(tmp.split(" ")[1]));
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
