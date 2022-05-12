import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:ws_hybrid_04_3_jeongsanghoon/movie.dart';

class HttpHelper {
  final String _urlBase = 'https://api.themoviedb.org/3/movie';
  final String _urlUpcoming = '/upcoming?';
  final String _urlKey = 'api_key=5e03ee13586f9aa4b88093614f2293c0';
  final String _urlLanguage = '&language=ko-KR';

  Future<List<Movie>> getUpcoming() async {
    final String upcoming = _urlBase + _urlUpcoming + _urlKey + _urlLanguage;
    // http.get 함수를 이용하여 영화정보를 받고 전달된 JSON 타입의 결과를
    // 리스트 형태로 변환하여 리턴한다.
    // 코드 구현 --------------------------------
    //todo http 패키지로 접속해서 곧 개봉될 영화 정보를 가져와서 리스트를 만든 후 리턴시키기
    var response = await http.get(
      Uri.parse(upcoming)
    );
    // print(response.body);
    List<dynamic> list = jsonDecode(response.body)["results"];
    var movies = list.map((e) => Movie.fromJson(e)).toList();

    // var dataConvertedToJSON = json.decode(response.body);
    // print("여기        ${dataConvertedToJSON}");
    // List result = dataConvertedToJSON['documents'];

    print("여기 Helper  ${movies[0].title}");
    return movies;
  }
}
