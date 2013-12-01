
public class Gem {

	@Override
	public String toString() {
		return String.format("%s", name);
	}

	public Gem() {

	}

	public Gem(String n) {
		name = n;
	}

	public GemLvl getGemlvl() {
		return gemlvl;
	}

	public void setGemlvl(GemLvl gemlvl) {
		this.gemlvl = gemlvl;
	}

	private GemLvl gemlvl;

	private String name = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
