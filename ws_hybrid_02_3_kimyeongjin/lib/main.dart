import 'dart:convert';
import 'dart:io';

void main() {
  File('sample_input.txt').readAsString().then((String contents) {
    var lineSplitter = LineSplitter();
    var lines = lineSplitter.convert(contents);
    var it = lines.iterator;

    it.moveNext();
    var T = int.parse(it.current);
    for (int test_case = 1; test_case <= T; test_case++) {
      stdout.write('#$test_case ');

      var solution = Solution();
      solution.run(it);

    }
  });
}

class Frog{
  int r=0;
  int c=0;
  int dir=0;
  Frog(this.r, this.c, this.dir);
  // Frog();
}

class Solution {
  int n = 0;
  int m = 0;
  var answer=0;
  var frogList;
  var graph = null;
  var dir = [
    [0,0],
    [-1,0],//상
    [1,0],//하
    [0,-1],//좌
    [0,1],//우
  ];

  void simulation(int n, int m){

    var frogs =(frogList as List<Frog>);

      frogs.forEach((frog) {
        int r = frog.r;
        int c = frog.c;
        int d = frog.dir;

        //이동
        graph[r][c] = false;
        for(int jump=3; jump>=1; jump--){
          int nr = r+dir[d][0]*jump;
          int nc = c+dir[d][1]*jump;
          //그래프 나간 경우
          if(nr <0 || nr >=n || nc <0 || nc >= n) break;

          //개구리가 뛰어넘을 수 있는 경우 밟지만 않으면 됨
          if(graph[nr][nc]) break;
          //3번 뛰어서 살아있는 경우 그래프에 표시
          if(jump==1){
            graph[nr][nc] = true;
            answer++;
          }
          //현재 좌표 초기화
          r = nr;
          c = nc;
        }

      });
  }

  void run(Iterator<String> it) {
    it.moveNext();
    //input
    var temp = it.current.split(' ');
    // print(temp);
    n = int.parse(temp[0]);
    m = int.parse(temp[1]);

    frogList = List.generate(m, (index) =>  Frog(0,0,0)); // 일차원 배열
    graph = List.generate(n, (index) => List.filled(n, false)); // 이차원 배열

    for(int i=0; i< m; i++){
      it.moveNext();
      var temp = it.current.split(' ').map((e) => int.parse(e)).toList();
      frogList[i] = Frog(temp[0],temp[1],temp[2]);
      graph[temp[0]][temp[1]] = true;

    }

    //solve
    simulation(n,m);

    //output
    print("$answer");

  }
}

