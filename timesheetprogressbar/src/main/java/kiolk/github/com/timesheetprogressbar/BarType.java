package kiolk.github.com.timesheetprogressbar;

public enum BarType {

    DIVIDED(0), OVERLAID(1);

    private final int mType;

    BarType(int pType) {
        this.mType = pType;
    }

    public static BarType getType(int type) {
        switch (type) {
            case 0:
                return DIVIDED;
            case 1:
                return OVERLAID;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int getType() {
        return mType;
    }
}
