
import java.util.List;

public class GemLvl {

	public GemLvl() {

	}

	public GemLvl(String n) {
		name = n;
	}

	private List<Integer> exp;

	private List<Gem> gems;

	private String name;

	public List<Integer> getExp() {
		return exp;
	}

	public void setExp(List<Integer> exp) {
		this.exp = exp;
	}

	public List<Gem> getGems() {
		return gems;
	}

	public void setGems(List<Gem> gems) {
		this.gems = gems;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
