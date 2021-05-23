package mysteryDungeon.util;

import java.io.File;

import com.megacrit.cardcrawl.core.Settings;

public class LocalizationTool {
    public static String LocalizationPath()
    {
        String langPathDir;
        switch (Settings.language) {
            case ENG :
                langPathDir = "localization" + File.separator + "eng";
                break;
            case DUT :
                langPathDir = "localization" + File.separator + "dut";
                break;
            case EPO :
                langPathDir = "localization" + File.separator + "epo";
                break;
            case PTB :
                langPathDir = "localization" + File.separator + "ptb";
                break;
            case ZHS :
                langPathDir = "localization" + File.separator + "zhs";
                break;
            case ZHT :
                langPathDir = "localization" + File.separator + "zht";
                break;
            case FRA :
                langPathDir = "localization" + File.separator + "fra";
                break;
            case DEU :
                langPathDir = "localization" + File.separator + "deu";
                break;
            case GRE :
                langPathDir = "localization" + File.separator + "gre";
                break;
            case IND :
                langPathDir = "localization" + File.separator + "ind";
                break;
            case ITA :
                langPathDir = "localization" + File.separator + "ita";
                break;
            case JPN :
                if(Settings.isConsoleBuild)
                {
                    langPathDir = "localization" + File.separator + "jpn";
                    break;
                }
                langPathDir = "localization" + File.separator + "jpn2";
                break;
            case KOR :
                langPathDir = "localization" + File.separator + "kor";
                break;
            case NOR :
                langPathDir = "localization" + File.separator + "nor";
                break;
            case POL :
                langPathDir = "localization" + File.separator + "pol";
                break;
            case RUS :
                langPathDir = "localization" + File.separator + "rus";
                break;
            case SPA :
                langPathDir = "localization" + File.separator + "spa";
                break;
            case SRP :
                langPathDir = "localization" + File.separator + "srp";
                break;
            case SRB :
                langPathDir = "localization" + File.separator + "srb";
                break;
            case THA :
                langPathDir = "localization" + File.separator + "tha";
                break;
            case TUR :
                langPathDir = "localization" + File.separator + "tur";
                break;
            case UKR :
                langPathDir = "localization" + File.separator + "ukr";
                break;
            case VIE :
                langPathDir = "localization" + File.separator + "vie";
                break;
            case WWW :
                langPathDir = "localization" + File.separator + "www";
                break;
            default:
                langPathDir = "localization" + File.separator + "www";
                break;
        }
        return langPathDir;
    }
}
