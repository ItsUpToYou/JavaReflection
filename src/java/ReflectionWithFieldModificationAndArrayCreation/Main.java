package ReflectionWithFieldModificationAndArrayCreation;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Scanner;

import ReflectionWithFieldModificationAndArrayCreation.data.GameConfig;
import ReflectionWithFieldModificationAndArrayCreation.data.UserInterfaceConfig;

public class Main {

	private static final Path GAME_CONFIG_PATH = Path.of("resources/game-properties.cfg");
	private static final Path UI_CONFIG_PATH = Path.of("resources/user-interface.cfg");
	
	public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		GameConfig config = createConfigObject(GameConfig.class, GAME_CONFIG_PATH);
		UserInterfaceConfig uiConfig = createConfigObject(UserInterfaceConfig.class, UI_CONFIG_PATH);
		System.out.println(config);
		System.out.println(uiConfig);
	}

	private static Object parseArray(Class<?> arrayElementType, String value) {
		String[] elementValues = value.split(",");
		Object arrayObject = Array.newInstance(arrayElementType, elementValues.length);
		for (int i = 0; i < elementValues.length; i++) {
			Array.set(arrayObject, i, parseValue(arrayElementType,elementValues[i]));
		}
		return arrayObject;
	}

	public static <T> T createConfigObject(Class<T> clazz, Path filePath) throws NoSuchMethodException, IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Scanner sc = new Scanner(filePath);
		Constructor<?> constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true);

		T configInstace = (T) constructor.newInstance();

		while (sc.hasNextLine()) {
			String configLine = sc.nextLine();
			String[] nameValuePair = configLine.split("=");
			String propertyName = nameValuePair[0];
			String propertyValue = nameValuePair[1];

			Field field;
			try {
				field = clazz.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				System.out.println(String.format("Property name : %s is unsupported", propertyName));
				continue;
			}
			field.setAccessible(true);
			
			Object parsedValue ; 
			if (field.getType().isArray()) {
				parsedValue = parseArray(field.getType().getComponentType(), propertyValue);
			}else {
				parsedValue = parseValue(field.getType(), propertyValue);
			}
			field.set(configInstace, parsedValue);
			
		}
		return configInstace;
	}

	private static Object parseValue(Class<?> type, String value) {
		if (type.equals(int.class)) {
			return Integer.parseInt(value);
		} else if (type.equals(short.class)) {
			return Short.parseShort(value);
		} else if (type.equals(long.class)) {
			return Long.parseLong(value);
		} else if (type.equals(double.class)) {
			return Double.parseDouble(value);
		} else if (type.equals(float.class)) {
			return Float.parseFloat(value);
		} else if (type.equals(String.class)) {
			return value;
		}
		throw new RuntimeException(String.format("Type : %s unsupported", type.getTypeName()));
	}
}
