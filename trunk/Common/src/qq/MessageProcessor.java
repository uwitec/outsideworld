package qq;

public class MessageProcessor {

	private static MessageProcessor instance = new MessageProcessor();

	public static MessageProcessor getInstance() {
		return instance;
	}

	private MessageProcessor() {

	}

	public void processMessage(String user, String messag, String ip) {
		System.out.println(messag);
	}
}
