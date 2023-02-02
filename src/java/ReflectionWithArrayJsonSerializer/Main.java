package ReflectionWithArrayJsonSerializer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import ReflectionWithFieldsJSONSerializer.Address;
import ReflectionWithFieldsJSONSerializer.Company;
import ReflectionWithFieldsJSONSerializer.Person;

public class Main {

	public static void main(String[] args) throws IllegalAccessException {
		Actor actorOne = new Actor("David Lengord", new String[] { "Any movie", "Next movie", "Another movie" });
		Actor actorTwo = new Actor("David Lengord", new String[] { "Any movie", "Next movie", "Another movie" });
		Actor actorThree = new Actor("David Lengord", new String[] { "Any movie", "Next movie", "Another movie" });
		Movie movie = new Movie("Any movie", 8.4f, new String[] { "Imagination", "Abstract", "Fantasy" }, new Actor[]{actorOne,actorTwo,actorThree});
		String json = objectToJson(actorOne, 0);
		String json1 = objectToJson(movie, 0);
		System.out.println(json);
		System.out.println(json1);

	}

	public static String objectToJson(Object instance, int identSize) throws IllegalAccessException {
		Field[] fields = instance.getClass().getDeclaredFields();
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(indent(identSize));
		stringBuilder.append("{");
		stringBuilder.append("\n");

		//iterates over the clas fields
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			if (field.isSynthetic()) {
				continue;
			}
			stringBuilder.append(indent(identSize + 1));
			stringBuilder.append(formatStringValue(field.getName()));
			stringBuilder.append(":");
			//handle all the primitive types, strings and objects
			if (field.getType().isPrimitive()) {
				stringBuilder.append(formatPrimitiveValue(field.get(instance), field.getType()));
			} else if (field.getType().equals(String.class)) {
				stringBuilder.append(formatStringValue(field.get(instance).toString()));
			} else if (field.getType().isArray()) {
				stringBuilder.append(arrayToJson(field.get(instance), identSize + 1));
			} else {
				stringBuilder.append(objectToJson(field.get(instance), identSize + 1));
			}

			if (i != fields.length - 1) {
				stringBuilder.append(",");
			}
			stringBuilder.append("\n");
		}
		stringBuilder.append(indent(identSize));
		stringBuilder.append("}");
		return stringBuilder.toString();
	}

	private static String arrayToJson(Object objArr, int identSize) throws IllegalAccessException {
		StringBuilder strBuilder = new StringBuilder();
		int arrLength = Array.getLength(objArr);
		Class<?> componentType = objArr.getClass().getComponentType();

		strBuilder.append("[");
		strBuilder.append("\n");
		for (int i = 0; i < arrLength; i++) {
			Object element = Array.get(objArr, i);
			if (componentType.isPrimitive()) {
				strBuilder.append(indent(identSize + 1));
				strBuilder.append(formatPrimitiveValue(element, componentType));
			} else if (componentType.equals(String.class)) {
				strBuilder.append(indent(identSize + 1));
				strBuilder.append(formatStringValue(element.toString()));
			}else {
				strBuilder.append(objectToJson(element, identSize + 1));
			}

			if (i != arrLength - 1) {
				strBuilder.append(",");
			}
			strBuilder.append("\n");
		}
		strBuilder.append(indent(identSize));
		strBuilder.append("]");
		return strBuilder.toString();
	}

	public static String indent(int identSize) {
		StringBuilder strB = new StringBuilder();
		for (int i = 0; i < identSize; i++) {
			strB.append("\t");
		}
		return strB.toString();
	}

	private static String formatPrimitiveValue(Object instance, Class<?> type) {
		if (type.equals(boolean.class)
				|| type.equals(int.class)
				|| type.equals(long.class)
				|| type.equals(short.class)) {
			return instance.toString();
		} else if (type.equals(double.class)
				|| type.equals(float.class)) {
			return String.format("%.02f", instance);
		}

		throw new RuntimeException(String.format("Type : %s is unsupported", type.getName()));
	}

	//add pair of quotes
	public static String formatStringValue(String value) {
		return String.format("\"%s\"", value);
	}
}
