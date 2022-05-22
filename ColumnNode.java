public class ColumnNode {

    private String columnName;
    private ColumnNode down;
    private CardNode right;

    public ColumnNode(String dataToAdd) {
        this.columnName= dataToAdd;
        down = null;
        right = null;

    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ColumnNode getDown() {
        return down;
    }

    public void setDown(ColumnNode down) {
        this.down = down;
    }

    public CardNode getRight() {
        return right;
    }

    public void setRight(CardNode right) {
        this.right = right;
    }




}
