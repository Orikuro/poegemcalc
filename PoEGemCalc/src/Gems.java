import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * Copyright: Bundesagentur für Arbeit
 */
@XmlRootElement
public class Gems {

	public List<Gem> getGem() {
		return gem;
	}

	public void setGem(List<Gem> gem) {
		this.gem = gem;
	}

	public List<GemLvl> getGemlvl() {
		return gemlvl;
	}

	public void setGemlvl(List<GemLvl> gemlvl) {
		this.gemlvl = gemlvl;
	}

	private List<Gem> gem;
	private List<GemLvl> gemlvl;

}
