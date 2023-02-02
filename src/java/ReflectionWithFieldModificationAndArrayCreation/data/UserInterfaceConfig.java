package ReflectionWithFieldModificationAndArrayCreation.data;

import java.util.Arrays;

public class UserInterfaceConfig {

	private String[] titleFonts;
	private String titleColor;
	private String footerText;
	private short titleFontSize;
	private short footerFontSize;

	public String[] getTitleFonts() {
		return titleFonts;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public String getFooterText() {
		return footerText;
	}

	public short getTitleFontSize() {
		return titleFontSize;
	}

	public short getFooterFontSize() {
		return footerFontSize;
	}

	@Override
	public String toString() {
		return "UserInterfaceConfig{" +
				"titleFonts=" + Arrays.toString(titleFonts) +
				", titleColor='" + titleColor + '\'' +
				", footerText='" + footerText + '\'' +
				", titleFontSize=" + titleFontSize +
				", footerFontSize=" + footerFontSize +
				'}';
	}
}
