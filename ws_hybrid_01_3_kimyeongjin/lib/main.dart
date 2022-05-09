import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
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
  String _tvText = "SSAFY";

  void _pressButton() {
    setState(() {
      _tvText = "SSAFY \n Mobile Track 화이팅 ~~ ";
    });
  }

  void _pressFloatButton() {
    setState(() {
      _tvText = "구미 6반 만세~";
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              _tvText,
              style: const TextStyle(
                color: Colors.orange,
                fontSize: 30
              ),
            ),
            ElevatedButton(
                onPressed: _pressButton,
                child: const Icon(Icons.local_printshop))
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _pressFloatButton,
        child: const Icon(Icons.ads_click),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
