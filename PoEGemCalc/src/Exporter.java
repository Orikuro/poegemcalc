import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;


public class Exporter {

	private static GemLvl standard = new GemLvl("Normal_140");

	private static void fillStandard() {
		List<Integer> ilist = new ArrayList<Integer>();
		ilist.add(1);
		ilist.add(10);
		ilist.add(100);
		ilist.add(1000);
		ilist.add(10000);

		standard.setExp(ilist);
	}

	private static void setGemLvl(Gem gem, GemLvl lvl) {
		gem.setGemlvl(lvl);
	}

	public static void main(String[] args) throws Exception {

		fillStandard();

		Gem g = new Gem("Frenzy");
		setGemLvl(g, standard);

		Gems gems = new Gems();
		List<Gem> gs = new ArrayList<Gem>();
		gs.add(g);

		List<GemLvl> lvls = new ArrayList<GemLvl>();
		lvls.add(standard);

		gems.setGem(gs);
		gems.setGemlvl(lvls);

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
		        new FileOutputStream("gems.xml"), "UTF-8"));
		JAXBContext jc = JAXBContext.newInstance(Gems.class);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(gems, out);

	}

}
