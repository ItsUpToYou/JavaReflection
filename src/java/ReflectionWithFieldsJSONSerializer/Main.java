package ReflectionWithFieldsJSONSerializer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

 

public class Main {

	public static void main(String[] args) throws IllegalAccessException {
		Address address = new Address("Street 1", (short)1);
		Person person = new Person("Jessy", true, 25, 9104.4F, 
				new Address("Street IT", (short)1),
				new Company("NameOfCompany", "CityNice", 
						new Address("CompanyStreet", (short) 7)));
		String json = objectToJson(address,0);
		String jsonPerson = objectToJson(person,0);
		System.out.println(json);
		System.out.println(jsonPerson);
		
		
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
			stringBuilder.append(indent(identSize+1));
			stringBuilder.append(formatStringValue(field.getName()));
			stringBuilder.append(":");
			//handle all the primitive types, strings and objects
			if (field.getType().isPrimitive()) {
				stringBuilder.append(formatPrimitiveValue(field.get(instance), field.getType()));
			} else if (field.getType().equals(String.class)) {
				stringBuilder.append(formatStringValue(field.get(instance).toString()));
			} else if (field.getType().isArray()) {
				stringBuilder.append(arrayToJson(field.get(instance)));
			} else{
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

	private static String arrayToJson(Object objArr)   {
		StringBuilder stringBuilder = new StringBuilder();
		int arrLength = Array.getLength(objArr);
		Class<?> componentType = objArr.getClass().getComponentType();
		stringBuilder.append("[");
		for (int i = 0; i < arrLength; i++) {
			Object element = Array.get(objArr, i);
			if (componentType.isPrimitive()) {
				stringBuilder.append(formatPrimitiveValue(element, componentType));
			} else if (componentType.equals(String.class)) {
				stringBuilder.append(formatStringValue(element.toString()));
			}

			if (i != arrLength - 1) {
				stringBuilder.append(",");
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	public static String indent(int identSize) {
		StringBuilder strB = new StringBuilder();
		for (int i = 0; i < identSize; i++) {
			strB.append("\t");
		}
		return strB.toString();
	}
	private static String formatPrimitiveValue( Object instance, Class<?> type)  {
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
