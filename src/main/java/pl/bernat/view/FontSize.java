package pl.bernat.view;

public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;
    public static String getCssPath(FontSize fontSize){
        switch (fontSize){
            case SMALL:
                return "/view/css/fontSmall.css";
            case MEDIUM:
                return "/view/css/fontMedium.css";
            case BIG:
                return "/view/css/fontBig.css";
            default:
                return null;
        }
    }
}
