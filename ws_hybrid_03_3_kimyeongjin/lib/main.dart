import 'package:flutter/material.dart';
import 'package:ws_hybrid_03_3_kimyeongjin/shape.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter WS_Day3',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter WS_Day3'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final _textCtrl1 = TextEditingController();
  final _textCtrl2 = TextEditingController();
  String _tvResult = "";
  @override
  void dispose() {
    _textCtrl1.dispose();
    super.dispose();
  }

  void _pressFloatButton() {
    try{
      int x = int.parse(_textCtrl1.text);
      int y = int.parse(_textCtrl2.text);
      var calSquare = CalSquare(x,y);
      setState(() {
        _tvResult = "x=$x, y=$y \n 거리: ${calSquare.distanceTo(calSquare)}, \n 넓이: ${calSquare.getArea()} ";
      });
    }catch(e){
      setState(() {
        _tvResult = "숫자를 입력해 주세요";
      });
    }

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            TextField(
              controller: _textCtrl1,
              style: const TextStyle(fontSize: 24.0),
            ),
            TextField(
              controller: _textCtrl2,
              style: const TextStyle(fontSize: 24.0),
            ),
            Text(
              "결과값 : $_tvResult "
            )
          ],
        ),
      ),
      // child: TextField(
      //   controller: _textCtrl,
      //   style: const TextStyle(fontSize: 24.0),
      // )
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          // showDialog(
          //   context: context,
          //   builder: (context) {
          //     return AlertDialog(
          //         content: Text(_textCtrl1.text,
          //             style: const TextStyle(fontSize: 24.0)));
          //   },
          // );
          _pressFloatButton();
        },
        child: const Icon(Icons.text_fields),
      ),
    );
  }
}
