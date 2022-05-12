import 'package:flutter/material.dart';
import 'movie_list.dart';

void main() => runApp(const SSAFYMovie());

class SSAFYMovie extends StatelessWidget {
  const SSAFYMovie({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SSAFY Movie',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const Home(),
    );
  }
}

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MovieList();
  }
}
