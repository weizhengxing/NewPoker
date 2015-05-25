import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {
	public static BlockingQueue<String> mBlockQueue = new ArrayBlockingQueue<String>(
			100);
}
