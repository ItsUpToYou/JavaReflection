package exercises;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		Class<String> stringClass = String.class;
		Map<String, Integer> mapObject = new HashMap<>();
		Class<?> hashMapClass = mapObject.getClass();
		Class<?> squareClass = Class.forName("exercises.Main$Square");

		var circleObj = new Drawable() {

			@Override
			public int getNumberOfCorners() {
				return 0;
			}
		};
		printClassInfo( squareClass,hashMapClass,stringClass);
		//printClassInfo( Collection.class,boolean.class,int[][].class, Color.class,circleObj.getClass());
	}

	public static void printClassInfo(Class<?>... classes) {
		for (Class<?> clazz : classes) {
			System.out.println(String.format("Class name : %s, class package name : %s",
					clazz.getSimpleName(),//Returns the simple name of the underlying class as given in the source code
					clazz.getPackage() //Gets the package of this class.If this class represents an array type, a primitive type or void, this method returns null. 
			));

			Class<?>[] implementedInterfaces = clazz.getInterfaces();

			for (Class<?> implInterface : implementedInterfaces) {
				System.out.println(String.format("Class %s implements : %s",
						clazz.getSimpleName(),
						implInterface.getSimpleName()));
			}
			System.out.println("Is array: " + clazz.isArray());
			System.out.println("Is primitive: "+clazz.isPrimitive());
			System.out.println("Is Enum: "+ clazz.isEnum());
			System.out.println("Is interface: " + clazz.isInterface());
			System.out.println("Is anonymous: " + clazz.isAnonymousClass());
			
			System.out.println();
			System.out.println();
		}
	}

	public static class Square implements Drawable {

		@Override
		public int getNumberOfCorners() {
			return 4;
		}
	}

	private interface Drawable{

		int getNumberOfCorners();
	}

	private enum Color{
		BLUE,
		RED,
		GREEN
	}
}