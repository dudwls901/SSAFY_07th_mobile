//onload -> html문서가 다 로딩된 담에 실행됨
window.onload = function () {
    document
        .getElementById("btn-add")
        .addEventListener("click", function () {
            var listDiv = document.getElementById("poll-answer-list");

            var divEl = document.createElement("div"); //div 생성
            divEl.setAttribute("class", "poll-answer-item"); // 만든 div에 class 속성 추가
            var inputEl = document.createElement("input");
            inputEl.setAttribute("type", "text");
            inputEl.setAttribute("name", "answer");
            var btnEl = document.createElement("button");
            btnEl.setAttribute("type", "button");
            btnEl.setAttribute("class", "button");
            btnEl.appendChild(document.createTextNode("삭제"));

            btnEl.addEventListener("click", function () {
                var parent = this.parentNode;
                listDiv.removeChild(parent);
            });

            divEl.appendChild(inputEl);
            divEl.appendChild(btnEl);

            listDiv.appendChild(divEl);
        });

        document.getElementById("btn-make").addEventListener("click", function () {
            //document.getElementById("question").value
            var question = document.querySelector("#question").value;
            if (!question) {
                alert("투표 내용을 입력해주세요!!");
                return;
            }

            var answers = document.getElementsByName("answer");
            for (var i = 0; i < answers.length; i++){
                if (!answers[i].value) {
                    alert("답변 항목 입력하세요!!!");
                    return;
                }
            }

            var answerArr = [];
            for (var i = 0; i < answers.length; i++){
                answerArr.push(answers[i].value);
            }
            console.log(answerArr);


            //날짜 입력 받기
            var sd = document.getElementById("sd").value;
            var ed = document.getElementById("ed").value;

            localStorage.setItem("question", question);
            //localStrage는 문자열만 저장 가능
            //stringfy -> json을 문자열로 전환하기!!!!!!!!!!!!!!!!!!!!!
            localStorage.setItem("answers", JSON.stringify(answerArr));
            localStorage.setItem("sd", sd);
            localStorage.setItem("ed", ed);

            alert("투표를 생성합니다!!");
            //window.self.close()랑 같음 , opener도 window 객체
            self.close();
            opener.location.reload();

        });
}







