public class Cursor {
    private int x = 1;
    private int y = 1;

    public void Move(int direction){
        if(direction == 1 && x != 5) // right
            x++;
        if(direction == 2 && x != 1) // left
            x--;
        if(direction == 3 && y != 1)// up
            y--;
        if(direction == 4)// down
            y++;
    }




    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
