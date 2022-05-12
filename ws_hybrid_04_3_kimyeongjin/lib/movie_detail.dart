import 'package:flutter/material.dart';
import 'movie.dart';

class MovieDetail extends StatelessWidget {
  const MovieDetail({Key? key, required this.movie}) : super(key: key);

  final Movie movie;
  final String _posterBase = 'https://image.tmdb.org/t/p/w500/';
  final String _defaultImage = 'https://images.freeimages.com/images/large-previews/5eb/movie-clapboard-1184339.jpg';

  @override
  Widget build(BuildContext context) {
    String? posterUrl = movie.posterPath;

    posterUrl = posterUrl != null ? _posterBase + posterUrl : _defaultImage;
    NetworkImage image = NetworkImage(posterUrl);


    double height = MediaQuery.of(context).size.height;
    String title = movie.title ?? "(제목없음)";
    String overview = movie.overview ?? "(개요없음)";

    return Scaffold(
        appBar: AppBar(title: Text(title)),
        // body에 이미지, 영화에 대한 전반적 리뷰를 표출한다.
        body: SingleChildScrollView(
            child: Center(
                child: Column(
                  children: <Widget>[
                      Image(image: image, height: height,),
                    Text(overview)
                  ]
                )
            )
        )
    );
  }
}
