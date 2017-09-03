public enum Cities {

    WARSAW(6695624), LODZ(3093133), GDANSK(7531002), LONDON(4298960), BARCELONA(6356055);

    private final int id;

    Cities(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}
