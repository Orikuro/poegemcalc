import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class Results {

	private String Parameters;

	@Override
	public String toString() {
		NumberFormat thousands = new DecimalFormat("###,###");
		NumberFormat two_comma = new DecimalFormat("#0.00");
		NumberFormat no_comma = new DecimalFormat("#0");
		return VERSION
		        + Parameters + String.format(
		                "GemXP gained:\t%s\n" +
		                        "GemXP / hour:\t%s\n" +
		                        "GemXP / map:\t%s\n\n" +
		                        "\tMax\tMax+20q\n" +
		                        "Minutes to:\t%s\t%s\n" +

		                        "Maps to:\t%s\t%s\n" +
		                        "Total EXP to:\t%s\t%s",
		                thousands.format(gain),
		                thousands.format(exp_h),
		                thousands.format(exp_map),
		                two_comma.format(min_20),
		                two_comma.format(min_20_20),
		                two_comma.format(maps_20),
		                two_comma.format(maps_20_20),
		                thousands.format(total_20),
		                thousands.format(total_20_20));
	}

	public static final String VERSION = "Gem-Calc Version 0.1\n";

	private int total_20;
	private int total_20_20;
	private int gain;
	private double exp_h;
	private double min_20;
	private double min_20_20;
	private double maps_20;
	private double maps_20_20;
	private double exp_map;

	public Results(Gem gem, int start_level, int start_exp, int end_level, int end_exp, int maps,
	    int h, int min, int s) {
		GemLvl gemlvl = gem.getGemlvl();

		Parameters = gem.getName() + " (" + gemlvl.getName() + ")\nStart " + start_level + " " + start_exp + " End "
		        + end_level + " "
		        + end_exp + "\n" + h + " h " + min + " min " + s + " s\n" + maps + " maps\n\n";

		total_20 = calcExp(gemlvl, end_level, end_exp, 20, 0);
		total_20_20 = total_20 + calcMaxExp(gemlvl);
		gain = calcExp(gemlvl, start_level, start_exp, end_level, end_exp);

		exp_h = calcEXP_h(h, min, s);

		min_20 = (total_20 / exp_h * (1d)) * 60;
		min_20_20 = (total_20_20 / exp_h * (1d)) * 60;

		exp_map = gain / maps;
		maps_20 = (total_20 / exp_map * (1d));
		maps_20_20 = (total_20_20 / exp_map * (1d));
	}

	private int calcEXP_h(int h, int min, int s) {
		if (h == 0 && min == 0 && s == 0) {
			return 0;
		}
		else {
			return (int) (gain / (h + min / 60d + s / 3600d));
		}
	}

	private int calcMaxExp(GemLvl gemlvl) {
		return calcExp(gemlvl, 1, 0, 20, 0);
	}

	private int calcExp(GemLvl gemlvl, int start_lvl, int start_exp, int end_lvl, int end_exp) {

		if (start_lvl > end_lvl) {
			return 0;
		}

		int res = 0;
		List<Integer> explist = gemlvl.getExp();
		for (int i = start_lvl - 1; i < explist.size() && i < end_lvl - 1; i++) {
			res += explist.get(i);
		}

		return res - start_exp + end_exp;
	}

}
