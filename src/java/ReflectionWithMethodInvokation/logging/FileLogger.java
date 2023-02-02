package ReflectionWithMethodInvokation.logging;

public class FileLogger {
	public void sendLog(String data) {
		System.out.println(String.format("Data : %s was logged to the file system", data));
	 
	}

}
