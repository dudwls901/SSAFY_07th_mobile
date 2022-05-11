import 'dart:math';

class Position{
  int x=0;
  int y=0;

  double distanceTo(Position other){
      return sqrt(other.x*other.x + other.y*other.y);
  }
}

class Square{
  int width=0;
  int height=0;

  int getArea(){
    return width*height;
  }
}

class CalSquare extends Square with Position{

  CalSquare(int _x, int _y){
    x = _x;
    y = _y;
    width = _x;
    height = _y;
  }

}

class Frog{
  int r=0;
  int c=0;
  int dir=0;
  Frog(this.r, this.c, this.dir);
// Frog();
}