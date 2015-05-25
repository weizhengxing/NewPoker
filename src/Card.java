public class Card{
	//point
	public static final int ONE=1;
	public static final int TWO=2;
	public static final int THREE=3;
	public static final int FOUR=4;
	public static final int FIVE=5;
	public static final int SIX=6;
	public static final int SEVEN=7;
	public static final int EIGHT=8;
	public static final int NINE=9;
	public static final int TEN=10;
	public static final int J=11;
	public static final int Q=12;
	public static final int K=13;
	//color
	public static final String SPADES="SPADES";
	public static final String HEARTS="HEARTS";
	public static final String CLUBS="CLUBS";
	public static final String DIAMONDS="DIAMONDS";
	//one card
}

class CardInfo{
	public String point;
	public String color;
}
class HoldCards{
	public static CardInfo[] mHoldCards =new CardInfo[2];
}
class FlopCards{
	public static CardInfo[] mFlopCards =new CardInfo[3];
}
class TurnCard{
	public static CardInfo mTurnCard;
}
class RiverCard{
	public static CardInfo mRiverCard;
}
