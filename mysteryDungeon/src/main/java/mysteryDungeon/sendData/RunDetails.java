package mysteryDungeon.sendData;

import java.util.ArrayList;

public class RunDetails {
    public String version;
    public int ascensionLevel;
    public int maxFloor;
    public float elapsedTime;
    public int score;
    public boolean win;
    public String seed;
    public String pokemon1;
    public String pokemon2;
    public ArrayList<CardDetails> cardDetails;
    public ArrayList<CardDetails> chosenCards = new ArrayList<CardDetails>();
    public ArrayList<CardDetails> notChosenCards = new ArrayList<CardDetails>();
}
