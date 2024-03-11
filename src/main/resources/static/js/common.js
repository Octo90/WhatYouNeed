let isClicked = false;
let websocket = null;

function fnOpenChat(id) {
    // 중복 클릭 방지
    if (isClicked) {
        return;
    }

    isClicked = true;

    $.ajax({
        url: "/chat/" + id,
        type: "GET",
        success: function(data) {
            $("#chat-box").html(data);
            $("#chat-box").css({'display':'block'});

            // username 설정
            const username = $("#username").val();
            console.log("Username:", username);

            // username이 정의되어 있는지 확인 후 WebSocket 연결
            if (username) {
                // WebSocket 연결
                connectWebSocket(username);
            } else {
                console.error("Username이 정의되지 않았습니다.");
            }
        },
        error: function(xhr, status, error) {
            console.error("ajax 에러", error);
        },
        complete: function() {
            isClicked = false;
        }
    });
}

function connectWebSocket(username) {
    // WebSocket 객체가 이미 있는 경우 종료
    if (websocket !== null && websocket.readyState !== WebSocket.CLOSED) {
        websocket.close();
    }

    // WebSocket 연결
    websocket = new WebSocket("ws://localhost:8080/ws/chat");
    websocket.onmessage = onMessage;
    websocket.onopen = function(evt) {
        var str = username + ": 님이 입장하셨습니다.";
        websocket.send(str);
    };
    websocket.onclose = onClose;
}

//const username = [[${name}]];
const username = $("#username").val();
console.log("Username:", username);



let isSeeing = true;
document.addEventListener("visibilitychange", function() {
    console.log(document.visibilityState);
    if(document.visibilityState == "hidden"){
        isSeeing = false;
    }else{
        isSeeing = true;
    }
});

var newExcitingAlerts = (function () {
    var oldTitle = document.title;
    var msg = "★문의사항이 도착하였습니다★";
    var timeoutId;
    var blink = function() {
        document.title = document.title == msg ? ' ' : msg;
        if(isSeeing == true){
            clear();
        }
    };
    var clear = function() {
        clearInterval(timeoutId);
        document.title = oldTitle;
        window.onmousemove = null;
        timeoutId = null;
    };
    return function () {
        if (!timeoutId) {
            timeoutId = setInterval(blink, 1000);
        }
    };
}());

function isChattingAllowed() {
    const date = new Date();
    const hour = date.getHours();
    const chatStartTime = 9;
    const chatEndTime = 17;

    return hour >= chatStartTime && hour < chatEndTime;
}

setInterval(() => console.log(new Date()), 10000);

$(document).ready(function(){
    $(".floating-chat").click();

    $("#disconn").on("click", (e) => {
        disconnect(username);
    })

    $("#button-send").on("click", (e) => {
        send(username);
    });
})

function enterkey(){
    if (window.event.keyCode == 13) {
        send();
    }
}
var lastSystemMessage = "";
function send() {
 const username = $("#username").val();
    if (isChattingAllowed()) {
        console.log(username + ":" + $("#opinion").val());
        if ($("#opinion").val() !== "") {
            websocket.send(username + ":" + $("#opinion").val());
            $("#opinion").val('');
        }
    } else {
        var systemMessage = "현재 시간엔 채팅이 불가능합니다.<br>채팅 가능시간 : 오전 09:00 ~ 오후 17:00<br>문의 사항을 통해 접수 해주시기 바랍니다.";

        if (systemMessage !== lastSystemMessage) {
            displaySystemMessage(systemMessage);
            lastSystemMessage = systemMessage;
            $("#opinion").val('');
        }
    }
}

function displaySystemMessage(message) {
    const date = new Date();
    let hour = date.getHours() < 10 ? `0${date.getHours()}` : date.getHours();
    let min = date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes();
    let amOrPm = hour >= 12 ? "PM" : "AM";
    let currentTime = hour + ":" + min + " " + amOrPm;

    let systemMessageHtml = "<li class='system-message'>" +
        "<div class='entete'>" +
        "<h3>" + currentTime + "</h3>" +
        "</div>" +
        "<div class='message' style='color: black;'>" + message + "</div></li>";

    $("#chat").append(systemMessageHtml);
    document.getElementById("chat").scrollTop = document.getElementById("chat").scrollHeight;
}
function onClose(evt) {
    // WebSocket 객체 null로 설정
    websocket = null;
    console.log("WebSocket 연결이 종료되었습니다.");
}

function onOpen(evt) {
    var str = username + ": 님이 입장하셨습니다.";
    websocket.send(str);
}

let cachedTime;
let cachedSessionId;
function onMessage(msg) {
    if (websocket === null || websocket.readyState !== WebSocket.OPEN) {
        return;
    }

    var data = msg.data;
    var sessionId = $("#username").val(); // 현재 사용자의 세션 아이디로 설정
    var message = null;
    var arr = data.split(":");
    const date = new Date();

    var chatElement = document.getElementById("chat");
    if (chatElement) {
        chatElement.scrollTop = chatElement.scrollHeight;
    } else {
        console.error("'chat' 요소를 찾을 수 없습니다.");
    }

    for (var i = 0; i < arr.length; i++) {
        console.log('arr[' + i + ']: ' + arr[i]);
    }

    var senderSessionId = arr[0];
    message = arr[1];

    console.log("senderSessionId : " + senderSessionId);
    console.log("sessionId : " + sessionId);

    if (message == " 님이 입장하셨습니다.") {
        message = senderSessionId + "님이 입장하셨습니다.";
    }
    if (message === undefined) {
        message = "채팅이 종료되었습니다.";
    }

    let hour = date.getHours() < 10 ? `0${date.getHours()}` : date.getHours();
    let min = date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes();
    let amOrPm;
    if (hour >= 12) {
        amOrPm = "PM"
    } else {
        amOrPm = "AM"
    }
    let currentTime = hour + ":" + min + " " + amOrPm;

    let userIdAndTimeHtml = "<div class='entete'>\n<h3>" + currentTime + "</h3>\n<h2>" + senderSessionId + "</h2>\n</div>";
    if (senderSessionId === sessionId) {
        userIdAndTimeHtml = "";
    }

    // 메시지를 나타내는 HTML 생성 및 클래스 부여
    var str = "<li class='" + (senderSessionId === sessionId ? 'me' : 'you') + "'>" +
        userIdAndTimeHtml +
        "<div class='message'>";
    str += message;
    str += "</div></li>";

    $("#chat").append(str);

    if (senderSessionId !== sessionId && isSeeing == false) {
        newExcitingAlerts();
    }

    cachedSessionId = senderSessionId;
    cachedTime = currentTime;

    document.getElementById("chat").scrollTop = document.getElementById("chat").scrollHeight;
}


    document.getElementById('disconn-btn').addEventListener('click', function() {
        websocket.close();
        websocket = null;
        console.log('채팅이 종료되었습니다.');
});
