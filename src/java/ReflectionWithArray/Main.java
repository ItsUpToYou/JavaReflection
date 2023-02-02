package ReflectionWithArray;

import java.lang.reflect.Array;

public class Main {

	public static void main(String[] args) {
		int[] oneDimensionalArray = { 7, 21 };
		inspectArrayObject(oneDimensionalArray);
		double[][] doubleArr = { { 3.4, 22.1 }, { 31.5, 54.7 } };
		inspectArrayObject(doubleArr);
		inspectArrayValues(doubleArr);
	}

	public static void inspectArrayValues(Object arrObject) {
		int arrayLength = Array.getLength(arrObject);
		
		System.out.print("[");
		for (int i = 0; i < arrayLength; i++) {
			Object element = Array.get(arrObject, i);
			
			if (element.getClass().isArray()) {
				inspectArrayValues(element);
			} else {
				System.out.print(element);
			} 
			
			if (i != arrayLength - 1) {
				System.out.print(",");
			}
		}
		System.out.print("]");
	}
	public static void inspectArrayObject(Object arrayObject) {
		Class<?> clazz = arrayObject.getClass();
		System.out.println(String.format( "Is array : %s ", clazz.isArray()));

		Class<?> arrayComponentType = clazz.getComponentType();
		System.out.println(String.format("This is an array of type : %s", arrayComponentType.getTypeName()));
	}
}
