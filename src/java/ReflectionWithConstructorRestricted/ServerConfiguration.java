package ReflectionWithConstructorRestricted;

import java.net.InetSocketAddress;

public class ServerConfiguration {

	private static ServerConfiguration serverConfiguration;
	private final InetSocketAddress serverAddress;
	private final String greetingMessage;

	public ServerConfiguration(int port, String greetingMessage) {
		this.greetingMessage = greetingMessage;
		this.serverAddress = new InetSocketAddress("localhost", port);
		
		if (serverConfiguration == null) {
			serverConfiguration = this;
		}
	}
	
	public static ServerConfiguration getInstance() {
		return serverConfiguration;	
	}
	
	
	public InetSocketAddress getServerAddress() {
		return this.serverAddress;
	}

	public String getGreetingMessage() {
		return this.greetingMessage;
	}
}
