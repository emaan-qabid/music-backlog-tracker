package albumJournal;

public class UnheardAlbum {

	private String title;
	private String artist;
	private int year;
	private String whyListen;
	private String whyNot;
	private int priority;
	private int Fantano;
	
	//so these are the base attributes
	//now i'm gonna put a constructor
	public UnheardAlbum(String title, String artist, int year, String whyListen, String whyNot, int priority, int Fantano) {
		
		this.title = title;
		this.artist = artist;
		this.year = year;
		this.whyListen = whyListen;
		this.whyNot = whyNot;
		this.priority = priority;
		this.Fantano = Fantano;
				
	}

	//so now its the getters i think
	
	public String getTitle() {return title;}
	public String getArtist() {return artist;}
	public String getWhyListen() {return whyListen;}
	public String getWhyNot() {return whyNot;}
	public int getYear() {return year;}
	public int getPriority() {return priority;}
	public int getFantano() {return Fantano;}
	
	//and then setters (stuff that you can edit)
	
    public void setWhyListen(String whyListen) {
        this.whyListen = whyListen;
    }

    public void setWhyNot(String whyNot) {
        this.whyNot = whyNot;
    }

    public void setPriority(int newPriority) {
        this.priority = priority;
    }
    
    public void setFantano(int Fantano) {
        this.Fantano = Fantano;
    }
	
	
	//ok so now its a method to organise this data
	public String getFullDescription() {
		String fantanoText;
		if (Fantano == -1) { fantanoText = "Not reviewed!";	}
		else { fantanoText = Fantano + "/10"; }
	return title + " by " + artist + " (" + year + ") \n" +
		" 🤔 Why do you want to listen to this? " + whyListen + "\n" +
		" 🚧 Why haven't you listened to it yet? " + whyNot + "\n" +
		" ✨ Out of 5, how much do you want to listen to it? " + priority + "\n" +
		" 🍉 What did Fantano rate it? 🍈👨🏻‍🦲 " + fantanoText;	
	}
}
