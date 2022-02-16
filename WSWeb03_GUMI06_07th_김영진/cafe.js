
window.onload = function () {

    var isLogin;
    var logoutBtn = document.getElementById("logout");
    var myPageBtn = document.getElementById("myPage");
    var adminBtn = document.getElementById("admin");
    var loginBtn = document.getElementById("login");
    var makeAccountBtn = document.getElementById("makeAccount");
    var profileImg = document.getElementById("profile_img");

    var isSpreaded = true;
    var allSpread = document.getElementById("allSpread")
    var seoul = document.getElementById("seoul");
    var daejon = document.getElementById("daejon");
    var gumi = document.getElementById("gumi");
    var gwangju = document.getElementById("gwangju");
    var seoulList = document.getElementById("seoulList");
    var daejonList = document.getElementById("daejonList");
    var gumiList = document.getElementById("gumiList");
    var gwangjuList = document.getElementById("gwangjuList");
    var allSpreadList = document.getElementsByClassName("store_list")
    //init
    changeView();


    //버튼들 클릭 이벤트 정의
    loginBtn.onclick = function () { openIdPrompt() };
    logoutBtn.onclick = function () { logout() };
    adminBtn.onclick = function () { openAdmin() };
    allSpread.onclick = function () { spreadClick(allSpread.id) };
    seoul.onclick = function () { spreadClick(seoul.id) };
    daejon.onclick = function () { spreadClick(daejon.id) };
    gumi.onclick = function () { spreadClick(gumi.id) };
    gwangju.onclick = function () { spreadClick(gwangju.id) };


    //admin popup
    function openAdmin() {
        window.open("./makepoll.html", "mp", "width=500, height=500, top=0, left=0");
    }

    //spread
    //다 열려있을 때만 접기
    function changeAllSpreadState() {
        for (let i = 0; i < allSpreadList.length; i++){
            //하나라도 닫혀있는 경우 펼치기
            if (allSpreadList[i].style.display == "none") {
                isSpreaded = false;
                allSpread.textContent = "전국매장펼치기";
                return;
            }
        }
        isSpreaded = true;
        allSpread.textContent = "전국매장접기";
    }


    //spread
    function changeSpreadState(regionWrap) {
        //단일 요소인 경우 각 지역
        if (regionWrap.id != undefined){
            if (regionWrap.style.display == "none") {
                regionWrap.style.display = "block";
                changeAllSpreadState();
            }
            else {
                regionWrap.style.display = "none";
                changeAllSpreadState();
            }
        }
        //리스트인 경우 allspread
        else {
            if (isSpreaded) {
                isSpreaded = false;
                allSpread.textContent = "전국매장펼치기";
                for (let i = 0; i < regionWrap.length; i++){
                    regionWrap[i].style.display = "none";
                }
            }
            else {
                isSpreaded=true;
                allSpread.textContent = "전국매장접기";
                for (let i = 0; i < regionWrap.length; i++){
                    regionWrap[i].style.display = "block";
                } 
            }
        }
    }

    //spread
    function spreadClick(clickedId) {
        switch (clickedId) {
            case "allSpread":
                changeSpreadState(allSpreadList);
                break;
            case "seoul":
                changeSpreadState(seoulList);
                break;
            case "daejon":
                changeSpreadState(daejonList);
                break;
            case "gumi":
                changeSpreadState(gumiList);
                break;
            case "gwangju":
                changeSpreadState(gwangjuList);
                break;
        }
    }

    //account
    function logout() {
        localStorage.setItem("id", null);
        localStorage.setItem("pw", null);
        changeView();
    }
    //account
    function changeView() {
        isLogin = checkLogin();
        //로그인 안 된 경우
        if (!isLogin) {
            
            console.log(loginBtn);
            loginBtn.style.display = "inline";
            makeAccountBtn.style.display = "inline";
            logoutBtn.style.display = "none";
            myPageBtn.style.display = "none";
            adminBtn.style.display = "none";
            profileImg.src = "img/noimg.png";
        }
        //이미 로그인 된 경우
        else {
            console.log(logoutBtn);
            loginBtn.style.display = "none";
            makeAccountBtn.style.display = "none";
            logoutBtn.style.display = "inline";
            myPageBtn.style.display = "inline";
            adminBtn.style.display = "inline";
            profileImg.src = "img/profile.png";
            console.log(logoutBtn)

        }
    }

    //account_utils
    function getId() {
        return localStorage.getItem("id");
    }

    function getPw() {
        return localStorage.getItem("pw");
    }

    function checkLogin() {
        if (getId() == "ssafy" && getPw() == "1234") {
            return true;
        }
        else
            return false;
    }

    //account_prompts
    function openIdPrompt() {
        var id = prompt("아이디입력", "아이디입력");
        console.log(id);
        openPwPrompt(id)
    }
    function openPwPrompt(id) {
        var pw = prompt("비밀번호입력", "비밀번호입력");
        console.log(id +" " +pw);
        openAlert(id,pw)
    }
    function openAlert(id, pw) {
        if (id=="ssafy" && pw =="1234") {
            localStorage.setItem("id", id);
            localStorage.setItem("pw", pw);
            alert("로그인 성공!!!");
            changeView();
        }
        else {
            alert("아이디랑 비번 그거 아니자나 다시해");
            
        }
    console.log(id, pw);
    }

}