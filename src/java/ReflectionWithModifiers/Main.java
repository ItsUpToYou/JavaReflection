package ReflectionWithModifiers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Main {

 

	public static void main(String[] args) throws ClassNotFoundException {
		//runAuction();
//		printClassModifiers(Auction.class);
//		printClassModifiers(Bid.class);
//		printClassModifiers(Bid.Builder.class);
//		printClassModifiers(Class.forName("ReflectionWithModifiers.Bid$Builder$BidImpl"));
//		printMethodsModifiers(Auction.class.getDeclaredMethods());
		printFiledsModifier(Auction.class.getDeclaredFields());
	}

	public static void printFiledsModifier(Field[] fields) {
		for (Field field : fields) {
			int modifier = field.getModifiers();
			System.out.println(String.format("Field \"%s\" access modifier is %s", field.getName(), getAccessModifierName(modifier)));

			if (Modifier.isVolatile(modifier)) {
				System.out.println("The field is volatile");
			}
			if (Modifier.isFinal(modifier)) {
				
				System.out.println("The field is final");
			}
			if (Modifier.isTransient(modifier)) {
				
				System.out.println("The field is transient and will not be serialized");
			}
		 
			}
		}
	
		

	public static void printMethodsModifiers(Method[] methods) {
		for (Method method : methods) {
			int modifier = method.getModifiers();
			System.out.println(String.format("%s() access modifier is %s", method.getName(), getAccessModifierName(modifier)));

			if (Modifier.isSynchronized(modifier)) {
				System.out.println("The method is synchronized");
			} else {
				System.out.println("The method is NOT synchronized");
			}
		}
	}
	public static void printClassModifiers(Class<?> clazz) {
		int modifier = clazz.getModifiers();
		System.out.println(String.format("Class %s access modifier is %s", clazz.getSimpleName(), getAccessModifierName(modifier)));
		if (Modifier.isAbstract(modifier)) {
			System.out.println("The class is abstracr");
		}
		if (Modifier.isInterface(modifier)) {
			System.out.println("The class is interface");
		}
		if (Modifier.isStatic(modifier)) {
			System.out.println("The class is static");
		}
	}

	private static String getAccessModifierName(int modifier) {
		if (Modifier.isPublic(modifier)) {
			return "Public";
		} else if (Modifier.isPrivate(modifier)) {
			return "Private";
		} else if (Modifier.isProtected(modifier)) {
			return "Protected";
		} else {
			return "Package-private";
		}
	}	
	
	public static void runAuction() {
		Auction auction = new Auction();
		auction.startAuction();

		Bid bid1 = Bid.builder()
				.setBidderName("Company1")
				.setPrice(10)
				.build();
		auction.addBid(bid1);

		Bid bid2 = Bid.builder()
				.setBidderName("Company2")
				.setPrice(12)
				.build();
		auction.addBid(bid2);

		auction.stopAuction();

		System.out.println(auction.getAllBids());
		System.out.println("Highest bid :" + auction.getHighestBid().get());
	}
}
