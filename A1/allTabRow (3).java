package AssemblerPass1;

public class allTabRow {
    private int index, address;
    private String symbol;

    allTabRow(String symbol, int address, int index) {
        this.symbol = symbol;
        this.address = address;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
